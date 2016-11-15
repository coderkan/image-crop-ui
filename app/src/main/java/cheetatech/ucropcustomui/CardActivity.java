package cheetatech.ucropcustomui;

import android.*;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.baoyz.pg.PG;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cheetatech.ucropcustomui.activitys.BaseActivity;
import cheetatech.ucropcustomui.adapters.MyCardAdapter;
import cheetatech.ucropcustomui.decision.Desc;
import cheetatech.ucropcustomui.decision.FileDesc;
import cheetatech.ucropcustomui.fileutil.FileUtilz;
import cheetatech.ucropcustomui.firebase.FBaseModel;
import cheetatech.ucropcustomui.firebase.FirebaseModel;
import cheetatech.ucropcustomui.firebase.FirebaseParcel;
import cheetatech.ucropcustomui.firebase.FirebaseParcelable;
import cheetatech.ucropcustomui.utils.RecyclerItemClickListener;

public class CardActivity extends BaseActivity implements ChildEventListener{


    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.web_imageview)
    ImageView loadImageView;


    ArrayList<String> fileList = new ArrayList<String>();
    //ArrayList<FirebaseParcel> parcelList = new ArrayList<FirebaseParcel>();
    ArrayList<FirebaseModel> parcelList = new ArrayList<FirebaseModel>();


    private int j = 0;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager  mLayoutManager;


    private FirebaseAuth mAuth = null;
    private FirebaseAuth.AuthStateListener mAuthListener = null;

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();

    private FirebaseStorage storage = null;
    private StorageReference storageRef = null;

    public static String BUCKETPATH = "gs://fir-storageexample-15b35.appspot.com";

    private ArrayList<FirebaseModel> models = new ArrayList<FirebaseModel>();

    private String TAG = "ImageLoadActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_load);
        ButterKnife.bind(this);

        checkPermission();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        controlAuth();
        controlStorage();

        mRecyclerView.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(CardActivity.this,4);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mAdapter = new MyCardAdapter(fileList);
        mRecyclerView.setAdapter(mAdapter);



        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getApplicationContext(),"indeximiz "+ position, Toast.LENGTH_SHORT).show();

                FirebaseModel firebaseModel = parcelList.get(position);
                Log.e(TAG,"Parcelll "+ firebaseModel.getName());
                FBaseModel.getInstance().setModel(firebaseModel);
                Intent intent = new Intent(CardActivity.this,ShowActivity.class);
                startActivity(intent);
            }
        }));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

    }

    public void checkPermission(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.INTERNET,
                    getString(R.string.permission_write_storage_rationale),
                    REQUEST_INTERNET_ACCESS_PERMISSION);
        }else
            Log.e(TAG,"Permission ALREADY granted access internet");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.ACCESS_NETWORK_STATE,
                    getString(R.string.permission_write_storage_rationale),
                    REQUEST_ACCESS_NETWORK_STATE_PERMISSION);
        }else
            Log.e(TAG,"Permission ALREADY granted access network");

    }


    private void controlStorage() {

        storage = FirebaseStorage.getInstance();
        if(storage == null)
            Log.e(TAG,"storage null");
        storageRef = storage.getReferenceFromUrl(BUCKETPATH);
        if(storageRef == null)
            Log.e(TAG,"storage REF null");

        StorageReference reference = storageRef.child("images");

        if(reference == null)
            Log.e(TAG,"REF null");
    }

    private void signIn(){
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.e(TAG,"signInAnonymously:onComplete: "+ task.isSuccessful());
                        if(!task.isSuccessful()){
                            Log.e(TAG,"signInAnonymously",task.getException());
                            Toast.makeText(CardActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void controlAuth() {

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Log.e(TAG,"onAuthStateChanged: signed_in: "+user.getUid());
                }else{
                    Log.e(TAG,"onAuthStateChanged:signed_out");
                }
            }
        };

        signIn();

        root.addChildEventListener(this);
    }

    @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop(){
        super.onStop();
        if(mAuthListener != null)
            mAuth.removeAuthStateListener(mAuthListener);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode)
        {
            case REQUEST_ACCESS_NETWORK_STATE_PERMISSION:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Log.e(TAG,"Permission granted access network");
                }
                break;
            case REQUEST_INTERNET_ACCESS_PERMISSION:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Log.e(TAG,"Permission granted access internet");
                }
            default:
                super.onRequestPermissionsResult(requestCode,permissions,grantResults);
                break;
        }
    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

    private  boolean checkAndRequestPermissions() {

        if(Build.VERSION.SDK_INT >= 23) {
            int permissionInternet = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.INTERNET);
            int permissionAccessNetworkState = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_NETWORK_STATE);
            List<String> listPermissionsNeeded = new ArrayList<>();
            if (permissionInternet != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(android.Manifest.permission.INTERNET);
            }
            if (permissionAccessNetworkState != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(android.Manifest.permission.ACCESS_NETWORK_STATE);
            }

            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 3);
                return false;
            }
        }
        return true;
    }

    /// Firebase Database interfaces...


    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        getUpdates(dataSnapshot);
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        getUpdates(dataSnapshot);
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        getUpdates(dataSnapshot);
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    private void addFireBase() {
        String nm = "Image " + (j);
        String url = "URL " + (j++);
        FirebaseModel user = new FirebaseModel(nm,url);
        root.child("ModelPaths").push().setValue(user);
    }

    private void getUpdates(DataSnapshot ds)
    {
        Log.e(TAG,"UPDATES");
        models.clear();
        parcelList.clear();
        for(DataSnapshot data : ds.getChildren())
        {
            FirebaseModel model = new FirebaseModel();
            model.setName(data.getValue(FirebaseModel.class).getName());
            model.setUrl(data.getValue(FirebaseModel.class).getUrl());
            model.setFileName(data.getValue(FirebaseModel.class).getFileName());
            model.setState((data.getValue(FirebaseModel.class).getState()));

            Log.e(TAG,model.getName() + " :: FileName :: " + model.getFileName() + " // // "+ model.getUrl());

            if(FileDesc.getInstance().getDesc() == Desc.BACKGROUND)
            {
                if(model.getState() == 0){
                    models.add(model);
                    fileList.add(model.getUrl());
                    parcelList.add(model);
                }
            }

            if(FileDesc.getInstance().getDesc() == Desc.CUBESIDE)
            {
                if(model.getState() == 1){
                    models.add(model);
                    fileList.add(model.getUrl());
                    parcelList.add(model);
                }
            }
        }
        mAdapter.notifyDataSetChanged();

    }
}

