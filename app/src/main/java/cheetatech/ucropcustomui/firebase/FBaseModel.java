package cheetatech.ucropcustomui.firebase;

/**
 * Created by Erkan.Guzeler on 15.11.2016.
 */
public class FBaseModel {

    private FirebaseModel model = null;
    private static FBaseModel ourInstance = new FBaseModel();

    public static FBaseModel getInstance() {
        return ourInstance;
    }

    private FBaseModel() {
    }

    public void setModel(FirebaseModel model){
        this.model = model;
    }
    public FirebaseModel getModel(){
        return this.model;
    }
}
