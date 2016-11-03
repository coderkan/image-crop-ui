package cheetatech.ucropcustomui.permissions.interfaces.models;

import android.Manifest;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.PortUnreachableException;

/**
 * Created by erkan on 03.11.2016.
 */

public class Action {


    @IntDef({ACTION_CODE_SAVE_IMAGE,ACTION_CODE_READ_IMAGE,ACTION_CODE_OPEN_CAMERA})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ActionCode{

    }
    public static final int ACTION_CODE_SAVE_IMAGE = 1;
    public static final int ACTION_CODE_READ_IMAGE = 2;
    public static final int ACTION_CODE_OPEN_CAMERA = 3;
    
    public static final Action READ_IMAGE = new Action(ACTION_CODE_READ_IMAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
    public static final Action WRITE_IMAGE = new Action(ACTION_CODE_SAVE_IMAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE);
    public static final Action OPEN_CAMERA = new Action(ACTION_CODE_OPEN_CAMERA,Manifest.permission.CAMERA);

    private int code;
    private String permission;

    public Action(@ActionCode int actionCode, String name) {
        this.code = actionCode;
        this.permission = name;
    }

    @Action.ActionCode
    public int getCode(){
        return code;
    }
    public String getPermission(){
        return permission;
    }

}


