package cheetatech.ucropcustomui.backgroundactivity;

import android.graphics.Bitmap;
import android.view.View;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by erkan on 08.11.2016.
 */

public interface BackView {


    void onLoadGalleryViews(ArrayList<ImageModel> models);
    void onSetClickListeners(ArrayList<ImageModel> models);
    void onLoadBackgroundImage(Bitmap bitmap);

}
