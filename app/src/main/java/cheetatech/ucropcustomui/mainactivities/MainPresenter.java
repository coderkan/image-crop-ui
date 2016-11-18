package cheetatech.ucropcustomui.mainactivities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;

import cheetatech.ucropcustomui.R;
import cheetatech.ucropcustomui.controllers.ImageController;
import cheetatech.ucropcustomui.controllers.Side;
import cheetatech.ucropcustomui.fileutil.FileUtilz;

import static cheetatech.ucropcustomui.fileutil.FileUtilz.save;

/**
 * Created by erkan on 08.11.2016.
 */

public class MainPresenter {


    private MainView view = null;
    private Context context = null;
    private ImageController imageController = null;
    private String directory;

    public MainPresenter(Context context,MainView view,String directory){
        this.context = context;
        this.view = view;
        this.directory = directory;
    }

    public void loadBackground()
    {
        File file = FileUtilz.getOutMediaFile(this.context,FileUtilz.accomplish(this.directory,Side.BACKGROUNDPATH));
        if(!file.exists()){
            Bitmap bitmap_ = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.bg1);
            save(file,bitmap_);
        }
        this.view.onLoadBackground(file);
    }

    public void loadCubeImages(){
        File[] files = getCubeSides(this.directory);
        this.view.onLoadCubeSides(files);
    }

    private File[] getCubeSides(String rootPath)
    {
        FileUtilz.directoryControl(this.context,rootPath);

        File[] files = new File[6];
        for(int i = 0; i < 6; i++){
            String mPath = Side.cubeSidePath[i];
            String path = FileUtilz.accomplish(rootPath,mPath);

            File pictureFile = FileUtilz.getOutMediaFile(context, path);
            if(!pictureFile.exists())
            {
                int[] ints = new int[]{R.drawable.im1,R.drawable.im2,R.drawable.im3,R.drawable.im4,R.drawable.im5,R.drawable.im6};
                Bitmap bitmap_ = BitmapFactory.decodeResource(this.context.getResources(), ints[i%6]);
                save(pictureFile,bitmap_);
            }
            pictureFile = FileUtilz.getOutMediaFile(context,path);
            files[i] = pictureFile;
        }
        return files;
    }

}
