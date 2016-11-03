package cheetatech.ucropcustomui.permissions.interfaces.interfaces;

/**
 * Created by erkan on 03.11.2016.
 */

public interface PermissionAction {

    boolean hasSelfPermission(String permission);

    void requestPermission(String permission,int requestCode);

    boolean shouldShowRequestPermissionRationale(String permission);
}
