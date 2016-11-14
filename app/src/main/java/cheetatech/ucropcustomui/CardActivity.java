package cheetatech.ucropcustomui;

import android.content.DialogInterface;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

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
import cheetatech.ucropcustomui.adapters.MyCardAdapter;
import cheetatech.ucropcustomui.controllers.Side;
import cheetatech.ucropcustomui.fileutil.FileUtilz;
import cheetatech.ucropcustomui.firebase.FirebaseModel;
import cheetatech.ucropcustomui.utils.RecyclerItemClickListener;

public class CardActivity extends AppCompatActivity  implements ChildEventListener{


    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.web_imageview)
    ImageView loadImageView;


    ArrayList<String> fileList = new ArrayList<String>();

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
        checkAndRequestPermissions();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        controlAuth();

        controlStorage();






//        ArrayList<File> fileList = (ArrayList<File>) FileUtilz.getListFiles(getApplicationContext(), Side.BACKGROUND);
//
//        for (File file: fileList
//             ) {
//            Log.e("TAG","Name "+ file.getAbsolutePath().toString());
//        }


        mRecyclerView.setHasFixedSize(true);

        //mLayoutManager = new LinearLayoutManager(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(CardActivity.this,4);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mAdapter = new MyCardAdapter(fileList);
        mRecyclerView.setAdapter(mAdapter);



        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getApplicationContext(),"indeximiz "+ position, Toast.LENGTH_SHORT).show();
            }
        }));


//        storage = FirebaseStorage.getInstance();
//        if(storage == null)
//            Log.e(TAG,"storage null");
//        storageRef = storage.getReferenceFromUrl(BUCKETPATH);
//        if(storageRef == null)
//            Log.e(TAG,"storage REF null");
//
//        StorageReference reference = storageRef.child("images");
//
//        if(reference == null)
//            Log.e(TAG,"REF null");






//        ArrayList<String> list = new ArrayList<String>();
//        for(int i = 0; i < 20; i++)
//            list.add("Erkan " + i);
//
//        ArrayList<File> fileList = (ArrayList<File>) FileUtilz.getListFiles(getApplicationContext(), Side.BACKGROUND);
//
//        for (File file: fileList
//             ) {
//            Log.e("TAG","Name "+ file.getAbsolutePath().toString());
//        }
//
//        mRecyclerView.setHasFixedSize(true);
//
//        mLayoutManager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//
//        mAdapter = new MyAdapter(fileList);
//        mRecyclerView.setAdapter(mAdapter);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadImage();
                //addFireBase();
                //signIn();

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void loadImage(){
        if(models.size() > 0){
            StorageReference imagesRef = storageRef.child("images");
            Log.e(TAG,"imagesRef Path: "+ imagesRef.getPath() + " name : "+ imagesRef.getName());

            String filename = models.get(0).getFileName();
            StorageReference spaceRef = imagesRef.child(filename);
            Log.e(TAG,"spacesRef Path: "+ spaceRef.getPath() + " name : "+ spaceRef.getName());

            StorageReference imRef = imagesRef.child(filename);

            File localFile = null;

            localFile = FileUtilz.getOutMediaFile(this,"erkanimagedownload.jpg");

            final File finalLocalFile = localFile;
            imRef.getFile(finalLocalFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Log.e(TAG,"OnSuccess Download File "+ finalLocalFile.getAbsolutePath().toString() );
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG,"Error onFailure Download Image");
                }
            });


            String url = models.get(0).getUrl();
            Picasso.with(this).load(url).into(loadImageView);
        }else{
            Picasso.with(this).load(R.drawable.bg4).into(loadImageView);
        }

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


        //        storage = FirebaseStorage.getInstance();
//        if(storage == null)
//            Log.e(TAG,"storage null");
//        storageRef = storage.getReferenceFromUrl(BUCKETPATH);
//        if(storageRef == null)
//            Log.e(TAG,"storage REF null");
//
//        StorageReference reference = storageRef.child("images");
//
//        if(reference == null)
//            Log.e(TAG,"REF null");
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
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Log.d("TAG", "Permission callback called-------");
        switch (requestCode) {
            case 3: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(android.Manifest.permission.INTERNET, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.ACCESS_NETWORK_STATE, PackageManager.PERMISSION_GRANTED);
                //perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);*/
                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(android.Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(android.Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
                        //&& perms.get(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
                            ) {
                        Log.d("TAG", "sms & location services permission granted");
                        // process the normal flow
                        //else any one or both the permissions are not granted
                    } else {
                        Log.d("TAG", "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.INTERNET)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_NETWORK_STATE)
                            // || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)
                                ) {
                            showDialogOK("Internet and Phone State Permission required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG)
                                    .show();
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
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
            int permissionInternet = ContextCompat.checkSelfPermission(this, android.Manifest.permission.INTERNET);
            int permissionAccessNetworkState = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_NETWORK_STATE);
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
        for(DataSnapshot data : ds.getChildren())
        {
            FirebaseModel model = new FirebaseModel();
            model.setName(data.getValue(FirebaseModel.class).getName());
            model.setUrl(data.getValue(FirebaseModel.class).getUrl());
            model.setFileName(data.getValue(FirebaseModel.class).getFileName());
            Log.e(TAG,model.getName() + " :: FileName :: " + model.getFileName() + " // // "+ model.getUrl());
            models.add(model);
            fileList.add(model.getUrl());
        }
        mAdapter.notifyDataSetChanged();

    }
}

