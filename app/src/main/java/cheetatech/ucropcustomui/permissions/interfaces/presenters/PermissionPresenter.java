package cheetatech.ucropcustomui.permissions.interfaces.presenters;

import android.content.pm.PackageManager;

import cheetatech.ucropcustomui.permissions.interfaces.interfaces.PermissionAction;
import cheetatech.ucropcustomui.permissions.interfaces.models.Action;

/**
 * Created by erkan on 03.11.2016.
 */

public class PermissionPresenter {

    private PermissionAction permissionAction;
    private PermissionCallbacks permissionCallbacks;

    public PermissionPresenter(PermissionAction permissionAction, PermissionCallbacks permissionCallbacks){
        this.permissionAction = permissionAction;
        this.permissionCallbacks = permissionCallbacks;
    }


    public void requestReadStoragePermission(){
        checkAndRequestPermission(Action.READ_IMAGE);
    }
    public void requestReadStoragePermissionAfterRationale(){
        requestPermission(Action.READ_IMAGE);
    }

    public void requestWriteStoragePermission(){
        checkAndRequestPermission(Action.WRITE_IMAGE);
    }
    public void requestWriteStoragePermissionAfterRationale(){
        requestPermission(Action.WRITE_IMAGE);
    }

    public void requestCameraPermission(){
        checkAndRequestPermission(Action.OPEN_CAMERA);
    }
    public void requestCameraPermissionAfterRationale(){
        requestPermission(Action.OPEN_CAMERA);
    }



    public void checkAndRequestPermission(Action action){
        if(permissionAction.hasSelfPermission(action.getPermission())){
            permissionCallbacks.permissionAccepted(action.getCode());
        }else {
            if(permissionAction.shouldShowRequestPermissionRationale(action.getPermission())){
                permissionCallbacks.showRationale(action.getCode());
            }else {
                permissionAction.requestPermission(action.getPermission(),action.getCode());
            }
        }
    }

    private void requestPermission(Action action){
        permissionAction.requestPermission(action.getPermission(),action.getCode());
    }

    public void checkGrantedPermission(int[] grantResults,int requestCode){
        if(verifyGrantedPermission(grantResults))
            permissionCallbacks.permissionAccepted(requestCode);
        else
            permissionCallbacks.permissionDenied(requestCode);
    }

    private boolean verifyGrantedPermission(int[] grantResults){
        for(int result: grantResults
                ){
            if(result != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }




    public interface PermissionCallbacks{

        void permissionAccepted(@Action.ActionCode int actionCode);

        void permissionDenied(@Action.ActionCode int actionCode);

        void showRationale(@Action.ActionCode int actionCode);

    }

}
