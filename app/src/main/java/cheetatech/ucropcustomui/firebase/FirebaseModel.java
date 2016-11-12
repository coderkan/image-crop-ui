package cheetatech.ucropcustomui.firebase;

/**
 * Created by erkan on 13.11.2016.
 */

public class FirebaseModel {
    private String name;
    private String url;

    public FirebaseModel(){

    }
    public FirebaseModel(String name, String url) {
        this.name = name;
        this.url = url;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
