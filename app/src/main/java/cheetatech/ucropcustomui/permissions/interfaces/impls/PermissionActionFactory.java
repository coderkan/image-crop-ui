package cheetatech.ucropcustomui.permissions.interfaces.impls;

import android.app.Activity;

import cheetatech.ucropcustomui.permissions.interfaces.interfaces.PermissionAction;

/**
 * Created by erkan on 03.11.2016.
 */

public class PermissionActionFactory {

    public static final int ACTIVITY_IMPL = 1;
    public static final int SUPPORT_IMPL = 2;

    private Activity activity;

    public PermissionActionFactory(Activity activity){
        this.activity = activity;
    }

    public PermissionAction getPermissionAction(int type){
        if(type == SUPPORT_IMPL)
            return new SupportPermissionActionImpl(activity);
        else
            return new ActivityPermissionActionImpl(activity);
    }

}
