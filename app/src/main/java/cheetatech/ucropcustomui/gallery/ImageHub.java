package cheetatech.ucropcustomui.gallery;

import android.media.Image;

import java.util.ArrayList;

/**
 * Created by erkan on 29.10.2016.
 */

public class ImageHub {

    private ArrayList<Integer> imageList = null;
    public ImageHub()
    {
        if(imageList == null)
            imageList = new ArrayList<Integer>();
    }

    public void add(int image)
    {
        createImageListIfNotExist();
        imageList.add(image);
    }

    private void createImageListIfNotExist()
    {
        if(imageList == null)
            imageList = new ArrayList<>();
    }

    public ArrayList<Integer> getImageList()
    {
        return this.imageList;
    }
    public int getImage(int index)
    {
        if(index >= 0 && index < this.imageList.size())
        {
            return this.imageList.get(index);
        }
        return -1;
    }
    public void add(int[] list)
    {
        createImageListIfNotExist();
        for (int i :list
             ) {
            this.imageList.add(i);
        }
    }




}
