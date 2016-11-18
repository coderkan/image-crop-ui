package cheetatech.ucropcustomui.fileutil;

import cheetatech.ucropcustomui.decision.Desc;
import cheetatech.ucropcustomui.decision.FileDesc;

/**
 * Created by erkan on 19.11.2016.
 */

public class DirString {

    private String string;
    private static DirString ourInstance = new DirString();

    public static DirString getInstance() {
        return ourInstance;
    }

    private DirString() {
    }

    public String getString(){
        return this.string;
    }
    public void setString(String string){
        this.string = string;
    }


}
