package cheetatech.ucropcustomui.mainactivities;

import android.graphics.Bitmap;

/**
 * Created by erkan on 08.11.2016.
 */

public interface MainView {

    void onLoadBackground(Bitmap bitmap);

    void onLoadCubeSides(Bitmap[] bitmaps);

    void onLoadCubeSide(Bitmap bitmap);


}
