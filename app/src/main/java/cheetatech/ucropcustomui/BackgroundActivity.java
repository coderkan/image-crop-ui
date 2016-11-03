package cheetatech.ucropcustomui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BackgroundActivity extends AppCompatActivity {

//
    @BindView(R.id.fabCamera) FloatingActionButton fabCamera;
    @BindView(R.id.fabGallery) FloatingActionButton fabGallery;
    @BindView(R.id.iconOk) ImageView iconApply;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.fabCamera) void clickFabCamera(){
        Toast.makeText(getApplicationContext(),"clickFabCamera",Toast.LENGTH_SHORT).show();
    }
    @OnClick(R.id.fabGallery) void clickFabGallery(){
        Toast.makeText(getApplicationContext(),"clickFab Gallery",Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.iconOk) void clickApplyIcon(){
        Toast.makeText(getApplicationContext(),"click Apply Icon",Toast.LENGTH_SHORT).show();
    }


}
