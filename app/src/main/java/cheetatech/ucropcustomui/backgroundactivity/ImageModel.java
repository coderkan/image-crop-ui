package cheetatech.ucropcustomui.backgroundactivity;

import android.graphics.Bitmap;
import android.view.View;

import java.io.File;

/**
 * Created by erkan on 08.11.2016.
 */

public class ImageModel {



    public static int counter = 0;
    private int id;
    private int index;
    private Bitmap bitmap;
    private View view = null;
    private File file = null;

    public ImageModel(){

    }
    public ImageModel(Bitmap bitmap,int id,int index){
        this.bitmap = bitmap;
        this.id = id;
        this.index = index;
    }
    public ImageModel(File file ,int id,int index){
        this.file = file;
        this.id = id;
        this.index = index;
    }

    public File getFile(){return this.file;}
    public void setFile(File file){this.file = file;}

    public View getView(){
        return view;
    }
    public void setView(View view){
        this.view = view;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
