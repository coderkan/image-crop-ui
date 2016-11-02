package cheetatech.ucropcustomui.image;

import android.content.Context;

import cheetatech.ucropcustomui.ChangeBackground;

/**
 * Created by Erkan.Guzeler on 2.11.2016.
 */

public class BackgroundImageController {
    

    private Context context = null;
    private BaseImage backgroundImage = null;

    public BackgroundImageController() {
    }

    public BackgroundImageController(Context context) {
        this.context = context;
        if(backgroundImage == null)
            backgroundImage = new BaseImage(this.context);
    }






}
