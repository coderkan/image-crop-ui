package cheetatech.ucropcustomui.backgroundactivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cheetatech.ucropcustomui.controllers.ImageController;
import cheetatech.ucropcustomui.controllers.Side;
import cheetatech.ucropcustomui.fileutil.FileUtilz;

/**
 * Created by erkan on 08.11.2016.
 */

public class BackgroundPresenter {

    private ArrayList<ImageModel> imageModels = new ArrayList<ImageModel>();

    private BackView view = null;
    private Context context = null;
    private ImageController imageController = null;
    private Bitmap currentBitmap = null;
    private File currentFile = null;

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

        List<File> files = imageController.getAllGalleryFileList(Side.BACKGROUND);

        if(files.size() > 0){
            this.imageModels.clear();
            int i = 0;
            for (File file:files
                 ) {
                imageModels.add(imageController.getImageModel(file));
                Log.e("TAG","Bitmaps " + imageModels.get(i++).getIndex());
            }
            try {
                FileUtilz.copyFileToDestination(this.context,
                        FileUtilz.accomplish(Side.CUBE1,Side.BACKGROUNDPATH),
                        FileUtilz.accomplish(Side.CUBE1,Side.REF_BACKGROUND)
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.view.onLoadGalleryViews(imageModels);
            this.view.onSetClickListeners(imageModels);
        }

    }
//    public void init(){
//        Bitmap[] bitmaps = imageController.getAllGalleryFile(Side.BACKGROUND);
//        if(bitmaps == null)
//            Log.e("TAG","Bitmaps are NULL");
//        this.imageModels.clear();
//        int i = 0;
//        for (Bitmap bitmap:
//                bitmaps
//             ) {
//            imageModels.add(imageController.getImageModel(bitmap));
//            Log.e("TAG","Bitmaps " + imageModels.get(i++).getIndex());
//        }
//        try {
//            FileUtilz.copyFileToDestination(this.context,
//                    FileUtilz.accomplish(Side.CUBE1,Side.BACKGROUNDPATH),
//                    FileUtilz.accomplish(Side.CUBE1,Side.REF_BACKGROUND)
//                    );
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        this.view.onLoadGalleryViews(imageModels);
//        this.view.onSetClickListeners(imageModels);
//    }


    public void loadBackground() {
        Bitmap bitmap = imageController.getBackgroundBitmapInDirectory(Side.CUBE1);//imageController.getBackgroundBitmap();
        this.currentBitmap = bitmap;
        this.view.onLoadBackgroundImage(bitmap);
    }

    public void setSelectedImageModel(ImageModel selectedImageModel) {
        //this.currentBitmap = selectedImageModel.getBitmap();
        this.currentFile = selectedImageModel.getFile();
        this.view.onLoadBackgroundImage(this.currentFile);
    }
    public Bitmap getCurrentBitmap(){
        return this.currentBitmap;
    }
    public void setCurrentBitmap(Bitmap bitmap){
        this.currentBitmap = bitmap;
    }

    public File getCurrentFile(){
        return this.currentFile;
    }
    public void setCurrentFile(File file){
        this.currentFile = file;
    }
}
