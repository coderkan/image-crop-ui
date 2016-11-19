package cheetatech.ucropcustomui.controllers;

import android.graphics.Bitmap;

import java.io.File;

/**
 * Created by Erkan.Guzeler on 10.11.2016.
 */

public class BaseCube {

    private int index;
    private int currentIndex = 0;
    private File[] files = null;

    public BaseCube(){
        if(this.files == null)
            this.files = new File[6];
    }


    public BaseCube(File[] files)
    {
        int i = 0;
        if(this.files == null)
            this.files = new File[6];
        setCurrentIndex(0);
        for (File file: files
             ) {
            this.files[i] = file;
            this.index = i;
            i++;
        }
    }

    public void addFile(File[] files){
        int i = 0;
        if(this.files == null)
            this.files = new File[6];
        setCurrentIndex(0);
        for (File file: files
                ) {
            this.files[i] = file;
            this.index = i;
            i++;
        }
    }

    public void addFile(int index,File file){
        if(index < 6 && index > 0)
        { }else index = 0;
        if(this.files == null)
            this.files = new File[6];
        this.files[index] = file;
        this.index = index;
    }

    public File[] getFiles(){
        return this.files;
    }
    public File getFile(int index){
        return this.files[index];
    }
    public void setCurrentIndex(int index){
        this.currentIndex = index;
    }
    public int getCurrentIndex(){
        return this.currentIndex;
    }

}
