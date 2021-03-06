package cheetatech.ucropcustomui;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.MediaStoreSignature;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cheetatech.ucropcustomui.activitys.BaseActivity;
import cheetatech.ucropcustomui.controllers.ImageController;
import cheetatech.ucropcustomui.controllers.Side;
import cheetatech.ucropcustomui.decision.Desc;
import cheetatech.ucropcustomui.decision.FileDesc;
import cheetatech.ucropcustomui.ecoinlib.OnCoinLibListener;
import cheetatech.ucropcustomui.ecoinlib.eCoinLib;
import cheetatech.ucropcustomui.fileutil.DirString;
import cheetatech.ucropcustomui.fileutil.FileUtilz;
import cheetatech.ucropcustomui.mainactivities.MainPresenter;
import cheetatech.ucropcustomui.mainactivities.MainView;

import static cheetatech.ucropcustomui.fileutil.FileUtilz.save;

public class MainActivity extends BaseActivity implements MainView,OnCoinLibListener{

    @BindView(R.id.backgroundIconChange) ImageView backgroundIconChange;
    @BindView(R.id.cubeBackgroundChange) ImageView cubeBackgroundChange;
    @BindView(R.id.frontImView) ImageView frontImView;
    @BindView(R.id.backImView) ImageView backImView;
    @BindView(R.id.leftImView) ImageView leftImView;
    @BindView(R.id.rightImView) ImageView rightImView;
    @BindView(R.id.topImView) ImageView topImView;
    @BindView(R.id.bottomImView) ImageView bottomImView;
    @BindView(R.id.backgroundImView) ImageView backgroundImView;

    public int i = 0;
    private ImageView[] views = null;


    private MainPresenter presenter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        views = new ImageView[]{frontImView,backImView,leftImView,rightImView,topImView,bottomImView};

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(presenter == null)
            presenter = new MainPresenter(getApplicationContext(),this, DirString.getInstance().getString());

        eCoinLib eCoin = new eCoinLib(getApplicationContext(),this);
        //eCoin.removeCoin(700);
        //eCoin.addCoin(380);
        loadElements();
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadElements();

    }

    private void loadElements()
    {
        loadImages();
    }


    private void loadImages() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    getString(R.string.permission_write_storage_rationale),
                    REQUEST_STORAGE_WRITE_ACCESS_PERMISSION);
        }else{
            presenter.loadBackground();
            presenter.loadCubeImages();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode)
        {
            case REQUEST_STORAGE_READ_ACCESS_PERMISSION:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){

                }
                break;
            case REQUEST_CAMERA_ACCESS_PERMISSION:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){

                }
            case REQUEST_STORAGE_WRITE_ACCESS_PERMISSION:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    presenter.loadBackground();
                    presenter.loadCubeImages();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode,permissions,grantResults);
                break;
        }
    }

    @OnClick(R.id.backgroundIconChange) void clickBackgroundImageChange(){

        Toast.makeText(getApplicationContext(),"click Apply Icon",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ChangeBackground.class);
        startActivity(intent);

    }
    @OnClick(R.id.cubeBackgroundChange) void clickCubeBackgroundImageChange(){

        Toast.makeText(getApplicationContext(),"click Background Icon",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, ChangeCube.class));

    }


    @Override
    public void onLoadBackground(File file) {
        Picasso.with(getApplicationContext()).load(file)
                .resize(270,480)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(backgroundImView);

        //Glide.with(this).load(files).signature(new MediaStoreSignature("mimeType",100+i,1+i)).override(270,480).into(backgroundImView);
    }
    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Images.Media._ID },
                MediaStore.Images.Media.DATA + "=? ",
                new String[] { filePath }, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            cursor.close();
            return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }


    @Override
    public void onLoadCubeSides(File[] files) {
        for(int i = 0; i < this.views.length; i++){
            Picasso.with(getApplicationContext())
                    .load(files[i])
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .resize(150,150)
                    .into(this.views[i]);
        }
    }



    @Override
    public void onNeedMoreCoin() {
        Log.e("onLoad","Sorry You cannot downlond File :)) You Don^t Have Enough Coin ");
    }

    @Override
    public void onEnoughCoin() {
        Log.e("onLoad","Download File :)) You Enough Coin ");
    }
    @Override
    public void onLoadCoin(int coin) {
        Log.e("onLoad","Loaded Coin "+ coin);
    }
}
