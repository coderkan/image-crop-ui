package cheetatech.ucropcustomui.fileutil;

import cheetatech.ucropcustomui.decision.Desc;

/**
 * Created by erkan on 19.11.2016.
 */

public class DirectoryType {

    private String name;
    private Desc desc;


    public DirectoryType(String name, Desc desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Desc getDesc() {
        return desc;
    }

    public void setDesc(Desc desc) {
        this.desc = desc;
    }
}
