package cheetatech.ucropcustomui.backgroundactivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cheetatech.ucropcustomui.R;
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
    private String directory = null;

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
    public BackgroundPresenter(Context context, BackView view,String directory){
        this.context = context;
        this.directory = directory;
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
                        FileUtilz.accomplish(this.directory,Side.BACKGROUNDPATH),
                        FileUtilz.accomplish(this.directory,Side.REF_BACKGROUND)
                );
            } catch (Exception e) {
                e.printStackTrace();
            }

            try { // copy background to reference
                copyBackgroundToRef(this.directory);
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.view.onLoadGalleryViews(imageModels);
            this.view.onSetClickListeners(imageModels);
        }

    }

    // Copy background image to ref
    public void copyBackgroundToRef(String directory) throws Exception {
        FileUtilz.directoryControl(this.context,directory);
        String backgroundPath = FileUtilz.accomplish(directory,Side.BACKGROUNDPATH);
        String referencePath = FileUtilz.accomplish(directory,Side.REF_BACKGROUND);
        FileUtilz.copyFileToDestination(this.context,backgroundPath,referencePath);
    }
    public void loadBackground() {
        String referencePath = FileUtilz.accomplish(this.directory,Side.REF_BACKGROUND);
        File file = FileUtilz.getOutMediaFile(this.context,referencePath);
        if(file.exists()){
            this.view.onLoadBackgroundImage(file);
        }else{
            Log.e("TAG","Reference File does not exists");
        }
        Log.e("TAG","Ref Path is " +file.getAbsolutePath().toString());
    }

    public void setSelectedImageModel(ImageModel selectedImageModel) {
        //this.currentBitmap = selectedImageModel.getBitmap();
        this.currentFile = selectedImageModel.getFile();
        try {
            FileUtilz.copyFiles(this.context,this.currentFile,FileUtilz.accomplish(this.directory,Side.BACKGROUNDPATH));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.view.onLoadBackgroundImage(this.currentFile);
    }
    public Bitmap getCurrentBitmap(){
        return this.currentBitmap;
    }
    public void setCurrentBitmap(Bitmap bitmap){
        this.currentBitmap = bitmap;
    }

    public File getCurrentFile(){
        FileUtilz.directoryControl(this.context,this.directory);
        String referencePath = FileUtilz.accomplish(this.directory,Side.REF_BACKGROUND);
        File current = FileUtilz.getOutMediaFile(this.context,referencePath);
        return current;
    }

    public void setCurrentFile(File file){
        this.currentFile = file;
    }

    public String getDirectory(){return this.directory;}
    public void setDirectory(String directory){
        this.directory = directory;
    }


}
