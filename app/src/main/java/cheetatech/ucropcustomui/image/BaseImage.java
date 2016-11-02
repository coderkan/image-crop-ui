package cheetatech.ucropcustomui.image;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by Erkan.Guzeler on 2.11.2016.
 */

public class BaseImage {

    private Bitmap bitmap = null;
    private int index;
    private String path;

    private Context context = null;

    public BaseImage(){}
    public BaseImage(Context context){
        this.context = context;
    }
    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getIndex() {
        return index;
    }

    public String getPath() {
        return path;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
