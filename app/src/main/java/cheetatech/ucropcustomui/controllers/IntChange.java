package cheetatech.ucropcustomui.controllers;

/**
 * Created by erkan on 01.11.2016.
 */
public class IntChange {
    private onChangeBackground listener = null;
    private static IntChange ourInstance = new IntChange();

    public static IntChange getInstance() {
        return ourInstance;
    }

    private IntChange() {
    }
    public void setListener(onChangeBackground listener){
        this.listener = listener;
    }
    public onChangeBackground getListener(){
        return this.listener;
    }


}
