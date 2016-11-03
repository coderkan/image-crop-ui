package cheetatech.ucropcustomui.permissions.interfaces.impls;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;

import cheetatech.ucropcustomui.permissions.interfaces.interfaces.PermissionAction;
import cheetatech.ucropcustomui.permissions.interfaces.utils.CommonUtils;

/**
 * Created by erkan on 03.11.2016.
 */

public class ActivityPermissionActionImpl implements PermissionAction {

    private Activity activity;

    public ActivityPermissionActionImpl(Activity activity){
        this.activity = activity;
    }

    @Override
    public boolean hasSelfPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            return this.activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        return true;
    }


    @Override
    public void requestPermission(String permission, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            activity.requestPermissions(new String[]{permission},requestCode);
    }


    @Override
    public boolean shouldShowRequestPermissionRationale(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            return this.activity.shouldShowRequestPermissionRationale(permission);
        return false;
    }
}
