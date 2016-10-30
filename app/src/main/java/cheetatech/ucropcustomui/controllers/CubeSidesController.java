package cheetatech.ucropcustomui.controllers;

import android.media.Image;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ToggleButton;

import java.util.ArrayList;

/**
 * Created by erkan on 30.10.2016.
 */

public class CubeSidesController extends BaseCubeSide {


    public CubeSidesController(){
        super();
    }
    public CubeSidesController(int[] imIds,int[] toggleIds)
    {
        super(imIds,toggleIds);
    }

    public CubeSidesController(ToggleButton[] toggleButtons, ImageView[] imageViews)
    {
        super(toggleButtons,imageViews);
    }

    public void controlToggleButtons(int id)
    {
        int i = 0;
        for (ToggleButton toggleButton: this.toggleButtons
             ) {
            if(toggleButton.getId() == id) {
                toggleButton.setChecked(true);
                setSelectedIndex(i);
            }
            else
                toggleButton.setChecked(false);
            i++;
        }
    }

    public ImageView getCurrentImageView()
    {
        return this.imageViews.get(getSelectedIndex());
    }



}
