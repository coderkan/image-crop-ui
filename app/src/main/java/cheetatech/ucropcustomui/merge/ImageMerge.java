package cheetatech.ucropcustomui.merge;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by erkan on 19.11.2016.
 */

public class ImageMerge {

    int[] wh = new int[]{156,200,275,200,175,245};
    int[][] xy = new int[][]{{0,240},{42,82},{230,120},{480,210},{623,94},{768,142}};
    private ArrayList<File> files = new ArrayList<File>();
    private Context context = null;
    private Bitmap bitmap = null;

    public ImageMerge(){}
    public ImageMerge(Context context){
        this.context = context;
    }
    public ImageMerge(Context context, ArrayList<File> files){
        this.context = context;
        for (File f: files
             ) {
            this.files.add(f);
        }
    }

    public void merge(){
        int len = this.files.size();
        Bitmap[] bitmaps = new Bitmap[len];
        for(int i = 0; i < len; i++){
            Bitmap bitmap = BitmapFactory.decodeFile(this.files.get(i).getAbsolutePath());
            Bitmap resizedBitmap = getResizedBitmap(bitmap,wh[i],wh[i]);
            bitmaps[i] = resizedBitmap;
        }

        Bitmap template = Bitmap.createBitmap(1024,512, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(template);
        Paint paint = new Paint();

        for(int i = 0; i < len; i++){
            canvas.drawBitmap(bitmaps[i],xy[i][0],xy[i][1],paint);
        }
        this.bitmap = template;
    }

    public Bitmap getBitmap(){return this.bitmap;}

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    public void save(File filename){

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filename);
            this.bitmap.compress(Bitmap.CompressFormat.PNG,100,fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                if(fos != null){
                    fos.flush();
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


}
