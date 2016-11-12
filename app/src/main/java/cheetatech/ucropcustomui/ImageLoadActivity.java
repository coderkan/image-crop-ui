package cheetatech.ucropcustomui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cheetatech.ucropcustomui.adapters.MyAdapter;
import cheetatech.ucropcustomui.controllers.Side;
import cheetatech.ucropcustomui.fileutil.FileUtilz;

public class ImageLoadActivity extends AppCompatActivity {


    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;



    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager  mLayoutManager;


    private FirebaseAuth mAuth = null;
    private FirebaseAuth.AuthStateListener mAuthListener = null;
    private FirebaseStorage storage = null;
    private StorageReference storageRef = null;

    public static String BUCKETPATH = "gs://fir-storageexample-15b35.appspot.com";


    private String TAG = "ImageLoadActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_load);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        controlAuth();


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

                //signIn();

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void signIn(){
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.e(TAG,"signInAnonymously:onComplete: "+ task.isSuccessful());
                        if(!task.isSuccessful()){
                            Log.e(TAG,"signInAnonymously",task.getException());
                            Toast.makeText(ImageLoadActivity.this, "Authentication failed.",
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

//        mAuth.signInAnonymously()
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Log.e(TAG,"signInAnonymously:onComplete: "+ task.isSuccessful());
//                        if(!task.isSuccessful()){
//                            Log.e(TAG,"signInAnonymously",task.getException());
//                            Toast.makeText(ImageLoadActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });




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

}
