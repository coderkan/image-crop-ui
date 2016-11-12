package cheetatech.ucropcustomui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
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


    private FirebaseAuth mAuth;
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


        mAuth = FirebaseAuth.getInstance();

        storage = FirebaseStorage.getInstance();
        if(storage == null)
            Log.e(TAG,"storage null");
        storageRef = storage.getReferenceFromUrl(BUCKETPATH);
        if(storageRef == null)
            Log.e(TAG,"storage REF null");

        StorageReference reference = storageRef.child("images");

        if(reference == null)
            Log.e(TAG,"REF null");






        ArrayList<String> list = new ArrayList<String>();
        for(int i = 0; i < 20; i++)
            list.add("Erkan " + i);

        ArrayList<File> fileList = (ArrayList<File>) FileUtilz.getListFiles(getApplicationContext(), Side.BACKGROUND);

        for (File file: fileList
             ) {
            Log.e("TAG","Name "+ file.getAbsolutePath().toString());
        }

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyAdapter(fileList);
        mRecyclerView.setAdapter(mAdapter);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
