package cheetatech.ucropcustomui.mainactivities;

import android.content.Context;
import android.graphics.Bitmap;

import cheetatech.ucropcustomui.controllers.ImageController;

/**
 * Created by erkan on 08.11.2016.
 */

public class MainPresenter {


    private MainView view = null;
    private Context context = null;
    private ImageController imageController = null;


    public MainPresenter(){}

    public MainPresenter(MainView view){
        this.view = view;
    }
    public MainPresenter(Context context,MainView view){
        this.context = context;
        this.view = view;
        init();
    }

    private void init() {
        if(imageController == null)
            imageController = new ImageController(this.context);
    }

    public void loadBackground()
    {
        Bitmap bitmap = imageController.getBackgroundBitmap();
        this.view.onLoadBackground(bitmap);
    }

    public void loadCubeImages(){
        Bitmap[] bitmaps = imageController.getBitmapsCubeSidesFromPicture();
        this.view.onLoadCubeSides(bitmaps);
    }



}
