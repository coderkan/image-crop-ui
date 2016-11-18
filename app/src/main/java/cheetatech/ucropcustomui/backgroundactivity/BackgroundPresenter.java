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


    // Ok
    public void init(){

        List<File> files = imageController.getAllGalleryFileList(Side.BACKGROUND);

        if(files.size() >= 0){
            this.imageModels.clear();
            int i = 0;
            for (File file:files
                 ) {
                imageModels.add(imageController.getImageModel(file));
            }
            File background = FileUtilz.getOutMediaFile(this.context,FileUtilz.accomplish(Side.BACKGROUND,"first.jpg"));
            if(files.size() == 0){

                Bitmap bitmap = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.bg3);
                FileUtilz.save(background,bitmap);
                background = FileUtilz.getOutMediaFile(this.context,FileUtilz.accomplish(this.directory,Side.BACKGROUNDPATH));
                if(background.exists()){
                    imageModels.add(imageController.getImageModel(background));
                }
            }
            File backgroundFile = FileUtilz.getOutMediaFile(this.context,FileUtilz.accomplish(this.directory,Side.BACKGROUNDPATH));
            if(backgroundFile.exists()){
                this.currentFile = backgroundFile;
                this.view.onLoadBackgroundImage(this.currentFile);
                Log.e("TAG","XXXXXXX");
            }else{
                Bitmap bitmap = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.bg1);
                FileUtilz.save(backgroundFile,bitmap);
                backgroundFile = FileUtilz.getOutMediaFile(this.context,FileUtilz.accomplish(this.directory,Side.BACKGROUNDPATH));
                if(backgroundFile.exists()){
                    this.currentFile = backgroundFile;
                    this.view.onLoadBackgroundImage(this.currentFile);
                    Log.e("TAG","YYYYYYY");
                }
            }
            if(this.currentFile != null)
                Log.e("TAG","Current File "+ this.currentFile.getAbsolutePath());
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
        if(this.currentFile == null)
            return;
        if(this.currentFile.exists())
            this.view.onLoadBackgroundImage(this.currentFile);
        Log.e("TAG","PathPathPath is " +this.currentFile.getAbsolutePath().toString());
    }

    public void setSelectedImageModel(ImageModel selectedImageModel) {
        this.currentFile = selectedImageModel.getFile();
        Log.e("TAG","Erkannn "+ this.currentFile.getAbsolutePath().toString());
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

    public String getDirectory(){return this.directory;}
    public void setDirectory(String directory){
        this.directory = directory;
    }


}
