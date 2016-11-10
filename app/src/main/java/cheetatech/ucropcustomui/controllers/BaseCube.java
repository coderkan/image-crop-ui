package cheetatech.ucropcustomui.controllers;

import android.graphics.Bitmap;

/**
 * Created by Erkan.Guzeler on 10.11.2016.
 */

public class BaseCube {

    private int index;
    private int currentIndex = 0;
    private Bitmap[] bitmaps = null;

    public BaseCube(){
        if(this.bitmaps == null)
            this.bitmaps = new Bitmap[6];
    }

    public BaseCube(Bitmap[] bitmaps)
    {
        int i = 0;
        if(this.bitmaps == null)
            this.bitmaps = new Bitmap[6];
        setCurrentIndex(0);
        for (Bitmap bitmap: bitmaps
             ) {
            this.bitmaps[i] = bitmap;
            this.index = i;
            i++;
        }
    }

    public void addBitmap(Bitmap[] bitmaps){
        int i = 0;
        if(this.bitmaps == null)
            this.bitmaps = new Bitmap[6];
        setCurrentIndex(0);
        for (Bitmap bitmap: bitmaps
                ) {
            this.bitmaps[i] = bitmap;
            this.index = i;
            i++;
        }
    }

    public void addBitmap(int index,Bitmap bitmap){
        if(index < 6 && index > 0)
        { }else index = 0;
        if(this.bitmaps == null)
            this.bitmaps = new Bitmap[6];
        this.bitmaps[index] = bitmap;
        this.index = index;
    }

    public Bitmap[] getBitmaps(){
        return this.bitmaps;
    }
    public Bitmap getBitmap(int index){
        return this.bitmaps[index];
    }
    public void setCurrentIndex(int index){
        this.currentIndex = index;
    }
    public int getCurrentIndex(){
        return this.currentIndex;
    }

}
