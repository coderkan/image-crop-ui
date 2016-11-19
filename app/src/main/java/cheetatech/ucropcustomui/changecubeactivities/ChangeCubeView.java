package cheetatech.ucropcustomui.changecubeactivities;

import android.graphics.Bitmap;

import java.io.File;
import java.util.ArrayList;

import cheetatech.ucropcustomui.backgroundactivity.BackView;
import cheetatech.ucropcustomui.backgroundactivity.ImageModel;


public interface ChangeCubeView extends BackView {

    void onLoadCubeSideBitmap(int side,File file);
    void onLoadCubeSides(File[] files);
    void onSavedImage();
}
