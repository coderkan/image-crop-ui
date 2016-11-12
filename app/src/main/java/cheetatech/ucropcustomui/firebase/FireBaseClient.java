package cheetatech.ucropcustomui.firebase;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

/**
 * Created by erkan on 12.11.2016.
 */

public class FireBaseClient {

    private Context context;
    private String DB_URL;
    private RecyclerView recyclerView;


    public FireBaseClient(Context context, RecyclerView recyclerView, String DB_URL) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.DB_URL = DB_URL;
    }

    public void saveOnline(String name,String url){

    }

}
