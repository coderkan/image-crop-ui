package cheetatech.ucropcustomui.changecubeactivities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cheetatech.ucropcustomui.R;
import cheetatech.ucropcustomui.activitys.BaseActivity;
import cheetatech.ucropcustomui.backgroundactivity.ImageModel;
import cheetatech.ucropcustomui.controllers.BaseCube;
import cheetatech.ucropcustomui.controllers.ImageController;
import cheetatech.ucropcustomui.controllers.Side;
import cheetatech.ucropcustomui.fileutil.FileUtilz;

/**
 * Created by Erkan.Guzeler on 10.11.2016.
 */

public class ChangeCubePresenter {

    private ArrayList<ImageModel> imageModels = new ArrayList<ImageModel>();
    private Bitmap currentBitmap = null;
    private File currentFile = null;
    private ChangeCubeView view = null;
    private Context context = null;
    private String directory;

    private ImageController controller = null;

    private BaseCube baseCube = null;

    public ChangeCubePresenter(){}

    public ChangeCubePresenter(ChangeCubeView view){
        this.view = view;
    }

    public ChangeCubePresenter(Context context,ChangeCubeView view,String directory){
        this.context = context;
        this.view = view;
        this.directory = directory;
        init();
    }

    private void init(){
        if(controller == null)
            controller = new ImageController(this.context);

        if(baseCube == null)
            baseCube = new BaseCube();

        List<File> files = controller.getAllGalleryFileList(Side.CUBEGALLERY);

        if(files.size() >= 0){
            this.imageModels.clear();
            int i = 0;
            for (File file:files
                    ) {
                imageModels.add(controller.getImageModel(file));
            }
            File background = FileUtilz.getOutMediaFile(this.context,FileUtilz.accomplish(Side.CUBEGALLERY,"c1.jpg"));
            if(files.size() == 0){

                Bitmap bitmap = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.im1);
                FileUtilz.save(background,bitmap);
                background = FileUtilz.getOutMediaFile(this.context,FileUtilz.accomplish(Side.CUBEGALLERY,"c1.jpg"));
                if(background.exists()){
                    imageModels.add(controller.getImageModel(background));
                }
            }
            this.view.onLoadGalleryViews(imageModels);
            this.view.onSetClickListeners(imageModels);
        }
    }


    public void loadCubeSideImages(){
        File[] files = controller.getCubeSidesFile(this.directory);
        baseCube.addFile(files);
        this.view.onLoadCubeSides(files);
    }

    public void setSelectedImageModel(ImageModel selectedImageModel) {
        this.currentFile = selectedImageModel.getFile();
        this.view.onLoadBackgroundImage(this.currentFile);
    }

    public void setCurrentIndex(int getIndx) {
        this.baseCube.setCurrentIndex(getIndx);
        this.currentFile = this.baseCube.getFile(getIndx);
        this.view.onLoadBackgroundImage(this.baseCube.getFile(getIndx));
    }

    public void setCubeSideBitmap() {
        this.baseCube.addFile(this.baseCube.getCurrentIndex(),this.currentFile);
        this.view.onLoadCubeSideBitmap(this.baseCube.getCurrentIndex(),this.currentFile);
    }

    public void saveCubeSideImage(ImageView[] imageViews) {
        //controller.saveCubeSidesImage(imageViews);
        controller.saveCubeSides(this.baseCube.getFiles(),this.directory);
        this.view.onSavedImage();
    }
    public File getCurrentFile(){
        return this.currentFile;
    }

    public void setCurrentFile(File file){
        this.currentFile = file;
        this.view.onLoadBackgroundImage(file);
    }

    public void loadCroppedImageFromCamera() {
//        Bitmap bitmap = controller.getBitmap(BaseActivity.CUBESIDE_BACKGROUND_IMAGE_NAME,512,512,1);
//        this.currentBitmap = bitmap;
//        this.view.onLoadBackgroundImage(bitmap);
    }
}
