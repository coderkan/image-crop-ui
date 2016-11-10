package cheetatech.ucropcustomui.changecubeactivities;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;

import cheetatech.ucropcustomui.backgroundactivity.ImageModel;
import cheetatech.ucropcustomui.controllers.ImageController;

/**
 * Created by Erkan.Guzeler on 10.11.2016.
 */

public class ChangeCubePresenter {

    private ArrayList<ImageModel> imageModels = new ArrayList<ImageModel>();
    private Bitmap currentBitmap = null;
    private ChangeCubeView view = null;
    private Context context = null;

    private ImageController controller = null;

    public ChangeCubePresenter(){}

    public ChangeCubePresenter(ChangeCubeView view){
        this.view = view;
    }

    public ChangeCubePresenter(Context context,ChangeCubeView view){
        this.context = context;
        this.view = view;
        init();
    }

    private void init(){
        if(controller == null)
            controller = new ImageController(this.context);

        Bitmap[] bitmaps = controller.getAllGalleryFile();
        if(bitmaps == null)
            Log.e("TAG","Bitmaps are NULL");
        this.imageModels.clear();
        int i = 0;
        for (Bitmap bitmap:
                bitmaps
                ) {
            imageModels.add(controller.getImageModel(bitmap));
            Log.e("TAG","Bitmaps " + imageModels.get(i++).getIndex());
        }
        this.view.onLoadGalleryViews(imageModels);
        this.view.onSetClickListeners(imageModels);
    }


    public void loadElements() {

    }
    public void loadCubeSideImages(){
        Bitmap[] bitmaps = controller.getBitmapsCubeSidesFromPicture();
        this.view.onLoadCubeSides(bitmaps);
    }

    public void setSelectedImageModel(ImageModel selectedImageModel) {
        this.currentBitmap = selectedImageModel.getBitmap();
        this.view.onLoadBackgroundImage(this.currentBitmap);
    }
}
