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
import cheetatech.ucropcustomui.permissions.interfaces.impls.PermissionActionFactory;
import cheetatech.ucropcustomui.permissions.interfaces.interfaces.PermissionAction;
import cheetatech.ucropcustomui.permissions.interfaces.models.Action;
import cheetatech.ucropcustomui.permissions.interfaces.presenters.PermissionPresenter;
import cheetatech.ucropcustomui.permissions.interfaces.views.BackgroundView;

public class BackgroundActivity extends AppCompatActivity implements BackgroundView, PermissionPresenter.PermissionCallbacks{

//
    @BindView(R.id.fabCamera) FloatingActionButton fabCamera;
    @BindView(R.id.fabGallery) FloatingActionButton fabGallery;
    @BindView(R.id.iconOk) ImageView iconApply;


    // Presenter
    private PermissionPresenter permissionPresenter ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background);
        ButterKnife.bind(this);
        PermissionActionFactory permissionActionFactory = new PermissionActionFactory(this);
        PermissionAction permissionAction = permissionActionFactory.getPermissionAction(PermissionActionFactory.SUPPORT_IMPL);
        permissionPresenter = new PermissionPresenter(permissionAction, (PermissionPresenter.PermissionCallbacks) this);



    }

    @OnClick(R.id.fabCamera) void clickFabCamera(){
        Toast.makeText(getApplicationContext(),"clickFabCamera",Toast.LENGTH_SHORT).show();
        permissionPresenter.requestCameraPermission();;
    }
    @OnClick(R.id.fabGallery) void clickFabGallery(){
        Toast.makeText(getApplicationContext(),"clickFab Gallery",Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.iconOk) void clickApplyIcon(){
        Toast.makeText(getApplicationContext(),"click Apply Icon",Toast.LENGTH_SHORT).show();
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Action.ACTION_CODE_READ_IMAGE:
            case Action.ACTION_CODE_SAVE_IMAGE:
            case Action.ACTION_CODE_OPEN_CAMERA:
                permissionPresenter.checkGrantedPermission(grantResults, requestCode);
        }
    }


    @Override
    public void requestCameraOpen() {

    }

    @Override
    public void permissionAccepted(@Action.ActionCode int actionCode) {
        switch (actionCode) {
            case Action.ACTION_CODE_OPEN_CAMERA:
                Toast.makeText(getApplicationContext(),"OPEN CAMERA ACCEPTED",Toast.LENGTH_SHORT).show();
                break;
            case Action.ACTION_CODE_SAVE_IMAGE:
                Toast.makeText(getApplicationContext(),"SAVE_IMAGE ACCEPTED",Toast.LENGTH_SHORT).show();
                break;
            case Action.ACTION_CODE_READ_IMAGE:
                Toast.makeText(getApplicationContext(),"READ_IMAGE ACCEPTED",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void permissionDenied(@Action.ActionCode int actionCode) {
        Toast.makeText(getApplicationContext(),"Permission Denied",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRationale(@Action.ActionCode int actionCode) {

    }
}
