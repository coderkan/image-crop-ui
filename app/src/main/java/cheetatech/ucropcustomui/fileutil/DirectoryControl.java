package cheetatech.ucropcustomui.fileutil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import cheetatech.ucropcustomui.R;
import cheetatech.ucropcustomui.decision.Desc;

/**
 * Created by erkan on 18.11.2016.
 */

public class DirectoryControl {


    private String TAG = "DirectoryControl";
    private Context context = null;
    private DirectoryType[] directoryTypes = null;
    private int[] cubeImages = new int[]{R.drawable.im1,R.drawable.im2,R.drawable.im3,R.drawable.im4,R.drawable.im5,R.drawable.im6};
    private int[] backgroundImages = new int[]{R.drawable.bg1,R.drawable.bg2,R.drawable.bg3,R.drawable.bg4,R.drawable.bg5,R.drawable.bg6};
    public static String[] cubeSidePath = new String[]{
            "cube_side_front.jpg",
            "cube_side_back.jpg",
            "cube_side_left.jpg",
            "cube_side_right.jpg",
            "cube_side_top.jpg",
            "cube_side_bottom.jpg"
    };
    public static String[] cubeGalleryNames = new String[]{
            "c1.jpg",
            "c2.jpg",
            "c3.jpg",
            "c4.jpg",
            "c5.jpg",
            "c6.jpg"
    };


    private String cubeBackground = "cube_background.jpg";


    public DirectoryControl(){}

    public DirectoryControl(Context context){
        this.context = context;
    }
    public DirectoryControl(Context context,DirectoryType[] directoryTypes){
        this.context = context;
        this.directoryTypes = new DirectoryType[directoryTypes.length];
        for (int i = 0; i < directoryTypes.length; i++) {
            this.directoryTypes[i] = directoryTypes[i];
            loadDirectory(directoryTypes[i]);
        }
    }

    private void loadDirectory(DirectoryType type){
        if(type.getDesc() == Desc.BACKGROUND){
            if(FileUtilz.directoryControl(this.context,type.getName())){
                if(getList(type.getName()).size() == 0){
                    for(int i = 0; i < 2;i++){
                        String[] sname = new String[]{"first.jpg","second.jpg"};
                        File file = FileUtilz.getOutMediaFile(this.context,FileUtilz.accomplish(type.getName(),sname[i]));
                        Bitmap bitmap = BitmapFactory.decodeResource(this.context.getResources(), backgroundImages[i]);
                        save(file,bitmap);
                    }
                }
            }else
                Log.e(TAG,"Error when loading directory "+ type.getName());
        }
        if(type.getDesc() == Desc.CUBESIDE){
            if(FileUtilz.directoryControl(this.context,type.getName())){
                if(getList(type.getName()).size() == 0){

                    File file = FileUtilz.getOutMediaFile(this.context,FileUtilz.accomplish(type.getName(),cubeBackground));
                    Bitmap bitmap = BitmapFactory.decodeResource(this.context.getResources(), backgroundImages[0]);
                    save(file,bitmap);

                    for(int i = 0; i < 6 ;i++){

                        file = FileUtilz.getOutMediaFile(this.context,FileUtilz.accomplish(type.getName(),cubeSidePath[i]));
                        bitmap = BitmapFactory.decodeResource(this.context.getResources(), cubeImages[i]);
                        save(file,bitmap);
                    }
                }
            }else
                Log.e(TAG,"Error when loading directory "+ type.getName());
        }
        if(type.getDesc() == Desc.CUBEBACKGROUND){
            if(FileUtilz.directoryControl(this.context,type.getName())){
                if(getList(type.getName()).size() == 0){
                    for(int i = 0; i < 6 ;i++){
                        File file = FileUtilz.getOutMediaFile(this.context,FileUtilz.accomplish(type.getName(),cubeGalleryNames[i]));
                        Bitmap bitmap = BitmapFactory.decodeResource(this.context.getResources(), cubeImages[i]);
                        save(file,bitmap);
                    }
                }
            }else
                Log.e(TAG,"Error when loading directory "+ type.getName());
        }

    }

    private List<File> getList(String rootPath)
    {
        FileUtilz.directoryControl(this.context,rootPath);
        File file = FileUtilz.getDirectoryInRoot(this.context,rootPath); // get backgroundimages directory
        List<File> parentFiles = null;
        if(file.exists())
            parentFiles = FileUtilz.getListFiles(file);
        return parentFiles;
    }
    public void save(File pictureFile,Bitmap bitmap)
    {
        if(pictureFile == null){
            Log.e("TAG","Error creating media file , check permissions...");
            return;
        }
        try{
            FileOutputStream fos = new FileOutputStream(pictureFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG,90,fos);
            fos.flush();
            fos.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
