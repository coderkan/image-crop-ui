package cheetatech.ucropcustomui.changecubeactivities;

import android.graphics.Bitmap;

import java.util.ArrayList;

import cheetatech.ucropcustomui.backgroundactivity.BackView;
import cheetatech.ucropcustomui.backgroundactivity.ImageModel;


public interface ChangeCubeView extends BackView {

    void onLoadCubeSideBitmap(int side,Bitmap bitmap);
    void onLoadCubeSides(Bitmap[] bitmaps);

}
