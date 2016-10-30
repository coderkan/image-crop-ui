package cheetatech.ucropcustomui.controllers;

import android.widget.ImageView;
import android.widget.ToggleButton;

import java.util.ArrayList;

/**
 * Created by erkan on 30.10.2016.
 */

public class BaseCubeSide {

    protected ArrayList<Integer> imageIds = null;
    protected ArrayList<Integer> toggleIds = null;
    protected ArrayList<ToggleButton> toggleButtons = null;
    protected ArrayList<ImageView> imageViews = null;

    protected int selectedIndex = 0;

    public BaseCubeSide()
    {
        control();
    }

    public BaseCubeSide(int[] imIds, int[] toggleIds)
    {
        control();
        addImageIds(imIds);
        addToggleIds(toggleIds);
    }

    public BaseCubeSide(ToggleButton[] toggleButtons, ImageView[] imageViews)
    {
        control();
        addToggleButton(toggleButtons);
        addImageView(imageViews);
    }

    protected void control()
    {
        if(imageIds == null)
            imageIds = new ArrayList<Integer>();
        if(toggleIds == null)
            toggleIds = new ArrayList<Integer>();
        if(toggleButtons == null)
            toggleButtons = new ArrayList<ToggleButton>();
        if(imageViews == null)
            imageViews = new ArrayList<ImageView>();
    }

    public void addToggleIds(int id)
    {
        control();
        toggleIds.add(id);
    }
    public void addToggleIds(int[] id)
    {
        control();
        for (int i:id
                ) {
            toggleIds.add(i);
        }
    }
    public void addImageIds(int id)
    {
        control();
        imageIds.add(id);
    }
    public void addImageIds(int[] id)
    {
        control();
        for (int i:id
                ) {
            imageIds.add(i);
        }
    }

    public void addToggleButton(ToggleButton toggleButton)
    {
        control();
        this.toggleButtons.add(toggleButton);
    }
    public void addToggleButton(ToggleButton[] toggleButtons)
    {
        control();
        for (ToggleButton i:toggleButtons
                ) {
            this.toggleButtons.add(i);
        }
    }



    public void addImageView(ImageView imageView)
    {
        control();
        imageViews.add(imageView);
    }
    public void addImageView(ImageView[] imageViews)
    {
        control();
        for (ImageView i:imageViews
                ) {
            this.imageViews.add(i);
        }
    }

    public void setSelectedIndex(int index)
    {
        this.selectedIndex = index;
    }
    public int getSelectedIndex()
    {
        return this.selectedIndex;
    }

}
