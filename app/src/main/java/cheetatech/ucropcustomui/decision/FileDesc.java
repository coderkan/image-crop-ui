package cheetatech.ucropcustomui.decision;

/**
 * Created by erkan on 15.11.2016.
 */
public class FileDesc {
    private Desc desc;
    private static FileDesc ourInstance = new FileDesc();

    public static FileDesc getInstance() {
        return ourInstance;
    }

    private FileDesc() {
    }

    public Desc getDesc(){
        return this.desc;
    }
    public void setDesc(Desc desc){
        this.desc = desc;
    }
}
