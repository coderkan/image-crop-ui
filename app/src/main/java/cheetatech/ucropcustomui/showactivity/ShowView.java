package cheetatech.ucropcustomui.showactivity;

/**
 * Created by Erkan.Guzeler on 15.11.2016.
 */

public interface ShowView {


    void onChangeFabButtonDisable();
    void onChangeFabButtonDownload();

    void onPreviewImage(String url);
    void onAlreadyDownload(String name);
    void onDownloadSuccessfully(String name);
    void onFailureDownloadImage();

}
