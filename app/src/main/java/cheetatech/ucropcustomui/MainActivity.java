package cheetatech.ucropcustomui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cheetatech.ucropcustomui.activitys.BaseActivity;
import cheetatech.ucropcustomui.controllers.ImageController;
import cheetatech.ucropcustomui.mainactivities.MainPresenter;
import cheetatech.ucropcustomui.mainactivities.MainView;

public class MainActivity extends BaseActivity implements MainView{

    @BindView(R.id.backgroundIconChange) ImageView backgroundIconChange;
    @BindView(R.id.cubeBackgroundChange) ImageView cubeBackgroundChange;
    @BindView(R.id.frontImView) ImageView frontImView;
    @BindView(R.id.backImView) ImageView backImView;
    @BindView(R.id.leftImView) ImageView leftImView;
    @BindView(R.id.rightImView) ImageView rightImView;
    @BindView(R.id.topImView) ImageView topImView;
    @BindView(R.id.bottomImView) ImageView bottomImView;
    @BindView(R.id.backgroundImView) ImageView backgroundImView;

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
            presenter = new MainPresenter(getApplicationContext(),this);
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
        //presenter.loadBackground();
        //presenter.loadCubeImages();
    }


    private void loadImages() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    getString(R.string.permission_write_storage_rationale),
                    REQUEST_STORAGE_WRITE_ACCESS_PERMISSION);
        }else{
            //imageController.saveCubeSidesImage();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    @OnClick(R.id.backgroundIconChange) void clickBackgroundImageChange(){
        Toast.makeText(getApplicationContext(),"click Apply Icon",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ChangeBackground.class);
        startActivity(intent);
    }
    @OnClick(R.id.cubeBackgroundChange) void clickCubeBackgroundImageChange(){
        Toast.makeText(getApplicationContext(),"click Background Icon",Toast.LENGTH_SHORT).show();
        //startActivity(new Intent(this, BackgroundActivity.class));
        startActivity(new Intent(this, ChangeCube.class));
    }





    @Override
    public void onLoadBackground(Bitmap bitmap) {

        backgroundImView.setImageBitmap(bitmap);
    }

    @Override
    public void onLoadCubeSides(Bitmap[] bitmaps) {
        for(int i = 0; i < this.views.length; i++)
            this.views[i].setImageBitmap(bitmaps[i]);
    }

    @Override
    public void onLoadCubeSide(Bitmap bitmap) {

    }
}
