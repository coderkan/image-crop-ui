package cheetatech.ucropcustomui.permissions.interfaces.utils;

import android.os.Build;

/**
 * Created by erkan on 03.11.2016.
 */

public class CommonUtils {

    public static boolean isMarsMallowOrHigher(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO &&
            Build.VERSION.SDK_INT < (int)23 )
            return false;
        else
            return true;
    }
}
