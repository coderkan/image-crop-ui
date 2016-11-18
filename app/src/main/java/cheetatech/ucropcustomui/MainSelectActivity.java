package cheetatech.ucropcustomui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cheetatech.ucropcustomui.controllers.Side;
import cheetatech.ucropcustomui.decision.Desc;
import cheetatech.ucropcustomui.decision.FileDesc;
import cheetatech.ucropcustomui.fileutil.DirString;
import cheetatech.ucropcustomui.fileutil.DirectoryControl;
import cheetatech.ucropcustomui.fileutil.DirectoryType;

public class MainSelectActivity extends AppCompatActivity {


    @BindView(R.id.button_background)
    Button backgroundStore;
    @BindView(R.id.button_background_image)
    Button backgroundImage;
    @BindView(R.id.button_cubeside)
    Button cubeSideStore;
    @BindView(R.id.button_cubeside_image)
    Button cubeSideImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_select);
        ButterKnife.bind(this);
        loadDirectories();
    }

    private void loadDirectories() {
        DirectoryType[] types = new DirectoryType[]{
                new DirectoryType(Side.CUBE1,Desc.CUBESIDE),
                new DirectoryType(Side.CUBE2,Desc.CUBESIDE),
                new DirectoryType(Side.BACKGROUND,Desc.BACKGROUND),
        };
        DirectoryControl dir = new DirectoryControl(getApplicationContext(),types);



    }

    @OnClick(R.id.button_background) void backgroundStore(){
        FileDesc.getInstance().setDesc(Desc.BACKGROUND);
        Intent intent = new Intent(this, CardActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_background_image) void backgroundImage(){
        DirString.getInstance().setString(Side.CUBE1);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.button_cubeside) void cubeSideStore(){
        FileDesc.getInstance().setDesc(Desc.CUBESIDE);
        Intent intent = new Intent(this, CardActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.button_cubeside_image) void cubeSideImage(){

    }
}
