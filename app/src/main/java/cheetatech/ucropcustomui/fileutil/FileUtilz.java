package cheetatech.ucropcustomui.fileutil;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.PortUnreachableException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cheetatech.ucropcustomui.controllers.Side;

/**
 * Created by erkan on 31.10.2016.
 */

public class FileUtilz {






    public static void copyFileToDestination(Context context,String src,String dest) throws Exception {
        File srcFile = FileUtilz.getOutMediaFile(context, src); //new File(downloadsDirectoryPath, filename);
        File dstFile = FileUtilz.getOutMediaFile(context, dest); //new File(downloadsDirectoryPath, filename);

        FileInputStream inStream = new FileInputStream(srcFile);
        FileOutputStream outStream = new FileOutputStream(dstFile);
        FileChannel inChannel = inStream.getChannel();
        FileChannel outChannel = outStream.getChannel();
        inChannel.transferTo(0, inChannel.size(), outChannel);
        inStream.close();
        outStream.close();
    }


    public static String accomplish(String s1, String s2){
        return s1 + File.separator + s2;
    }

    public static boolean directoryControl(Context context,String path){
        File dir = null;
        File rootFile = getRootFile(context);
        if(rootFile != null){
            dir = new File(rootFile.getAbsolutePath() + File.separator + path);
            if(!dir.exists()){
                if(!dir.mkdirs()){
                    return false;
                }
            }
        }
        return true;
    }




    public static File getRootFile(Context context){
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + context.getApplicationContext().getPackageName()
                + "/Files");
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        return mediaStorageDir;
    }
    public static File getDirectoryInRoot(Context context, String directoryName){
        File root = getRootFile(context);
        File nfile = new File(root.getPath()+ File.separator+ directoryName);
        return nfile;
    }

    public static File getOutputMediaFileAndCreate(Context context,String mImageName){

        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + context.getApplicationContext().getPackageName()
                + "/Files");


        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }

        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);

        if(!mediaFile.exists())
        {
            try {
                mediaFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return mediaFile;
    }

    public static File getOutputMediaFile(Context context,String mImageName){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + context.getApplicationContext().getPackageName()
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        // Create a media file name
        ///String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        //String mImageName="MI_"+ timeStamp +".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);

        if(mediaFile.exists())
        {
            Log.e("TAG","File is exists");
            return mediaFile;
        }else{
            return null;
        }
    }

    public static File getOutMediaFile(Context context,String mImageName){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + context.getApplicationContext().getPackageName()
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        // Create a media file name
        ///String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        //String mImageName="MI_"+ timeStamp +".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);

        return mediaFile;
    }

    public static File createImageFile(Context context) throws IOException {
        // Create an image file name
        //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "reference_image.png";// "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        /*File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".png",         /* suffix */
//                storageDir      /* directory */
//        );
        File image = new File(storageDir,imageFileName);
        return image;
    }

    public static List<File> getListFiles(File parentDir){

        ArrayList<File> inFiles = new ArrayList<>();
        File[] files = parentDir.listFiles();

        for (File file: files
             ) {
            if(file.isDirectory()){
                inFiles.addAll(getListFiles(file));
            }else{
                if(file.getName().endsWith(".jpg"))
                    inFiles.add(file);
            }
        }
        return inFiles;
    }

    public static List<File> getListFiles(Context context,String parentName){

        File parentDir = getDirectoryInRoot(context,parentName);

        ArrayList<File> inFiles = new ArrayList<>();
        File[] files = parentDir.listFiles();

        for (File file: files
                ) {
            if(file.isDirectory()){
                inFiles.addAll(getListFiles(file));
            }else{
                if(file.getName().endsWith(".jpg"))
                    inFiles.add(file);
            }
        }
        return inFiles;
    }
}
