package cheetatech.ucropcustomui.backgroundactivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;

import cheetatech.ucropcustomui.controllers.ImageController;

/**
 * Created by erkan on 08.11.2016.
 */

public class BackgroundPresenter {

    private ArrayList<ImageModel> imageModels = new ArrayList<ImageModel>();

    private BackView view = null;
    private Context context = null;
    private ImageController imageController = null;
    private Bitmap currentBitmap = null;

    public BackgroundPresenter(){}

    public BackgroundPresenter(BackView view){
        this.view = view;
    }
    public BackgroundPresenter(Context context, BackView view){
        this.context = context;
        this.view = view;
        if(imageController == null)
            imageController = new ImageController(this.context);
    }

    public void init(){
        Bitmap[] bitmaps = imageController.getAllGalleryFile();
        if(bitmaps == null)
            Log.e("TAG","Bitmaps are NULL");
        this.imageModels.clear();
        int i = 0;
        for (Bitmap bitmap:
                bitmaps
             ) {
            imageModels.add(imageController.getImageModel(bitmap));
            Log.e("TAG","Bitmaps " + imageModels.get(i++).getIndex());
        }
        this.view.onLoadGalleryViews(imageModels);
        this.view.onSetClickListeners(imageModels);
    }


    public void loadBackground() {
        Bitmap bitmap = imageController.getBackgroundBitmap();
        this.currentBitmap = bitmap;
        this.view.onLoadBackgroundImage(bitmap);
    }

    public void setSelectedImageModel(ImageModel selectedImageModel) {
        this.currentBitmap = selectedImageModel.getBitmap();
        this.view.onLoadBackgroundImage(this.currentBitmap);
    }
    public Bitmap getCurrentBitmap(){
        return this.currentBitmap;
    }
    public void setCurrentBitmap(Bitmap bitmap){
        this.currentBitmap = bitmap;
    }
}
