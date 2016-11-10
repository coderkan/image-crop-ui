package cheetatech.ucropcustomui.changecubeactivities;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;

import cheetatech.ucropcustomui.activitys.BaseActivity;
import cheetatech.ucropcustomui.backgroundactivity.ImageModel;
import cheetatech.ucropcustomui.controllers.BaseCube;
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

    private BaseCube baseCube = null;

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

        if(baseCube == null)
            baseCube = new BaseCube();

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
        baseCube.addBitmap(bitmaps);
        this.view.onLoadCubeSides(bitmaps);
    }

    public void setSelectedImageModel(ImageModel selectedImageModel) {
        this.currentBitmap = selectedImageModel.getBitmap();
        this.view.onLoadBackgroundImage(this.currentBitmap);
    }

    public void setCurrentIndex(int getIndx) {
        this.baseCube.setCurrentIndex(getIndx);
        this.currentBitmap = this.baseCube.getBitmap(getIndx);
        this.view.onLoadBackgroundImage(this.baseCube.getBitmap(getIndx));
    }

    public void setCubeSideBitmap() {
        this.view.onLoadCubeSideBitmap(this.baseCube.getCurrentIndex(),this.currentBitmap);
    }

    public void saveCubeSideImage(ImageView[] imageViews) {
        controller.saveCubeSidesImage(imageViews);
        this.view.onSavedImage();
    }

    public void loadCroppedImageFromCamera() {
        Bitmap bitmap = controller.getBitmap(BaseActivity.CUBESIDE_BACKGROUND_IMAGE_NAME);
        this.currentBitmap = bitmap;
        this.view.onLoadBackgroundImage(bitmap);
    }
}
