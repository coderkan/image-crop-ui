package cheetatech.ucropcustomui.ecoinlib;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ConcurrentModificationException;
import java.util.Locale;

import cheetatech.ucropcustomui.R;

/**
 * Created by Erkan.Guzeler on 15.11.2016.
 */

public class eCoinLib {

    private int defValue = -1;
    private final String coinKey = "coin";
    private final int biasCoin = 50;
    private final int zeroCoin = 0;
    private final int halfCoin = 25;
    private final int fullCoin = 50;

    private OnCoinLibListener listener = null;
    private Context context = null;

    public eCoinLib(){}

    public eCoinLib(Context context,OnCoinLibListener listener){
        this.context = context;
        this.listener = listener;
        init();
    }

    private void init() {
        SharedPreferences sharedPreferences = this.context.getSharedPreferences(context.getString(R.string.preference_file_key),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int val = sharedPreferences.getInt(this.coinKey,defValue);
        if(val == defValue){
            Log.e("SHARED", "SharedPreferences" + val);
            editor.putInt(this.coinKey, biasCoin);
            editor.commit();
            this.listener.onLoadCoin(biasCoin);
        }
        else{
            Log.e("SHARED", "SharedPreferences Another" + val);
            this.listener.onLoadCoin(val);
        }
    }


    public void addCoin(int coin){
        SharedPreferences sharedPreferences = this.context.getSharedPreferences(context.getString(R.string.preference_file_key),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int val = sharedPreferences.getInt(this.coinKey,defValue);
        if(val == defValue){
            Log.e("SHARED", "SharedPreferences" + val);
            editor.putInt(this.coinKey, biasCoin);
            editor.commit();
            this.listener.onLoadCoin(biasCoin);
        }
        else{
            Log.e("SHARED", "SharedPreferences Another" + val);
            val += coin;
            editor.putInt(this.coinKey, val);
            editor.commit();
            this.listener.onLoadCoin(val);
        }
    }
    public void removeCoin(int coin){
        SharedPreferences sharedPreferences = this.context.getSharedPreferences(context.getString(R.string.preference_file_key),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int val = sharedPreferences.getInt(this.coinKey,defValue);
        if(val == defValue){
            Log.e("SHARED", "SharedPreferences" + val);
            editor.putInt(this.coinKey, biasCoin);
            editor.commit();
            this.listener.onLoadCoin(biasCoin);
        }
        else{
            Log.e("SHARED", "SharedPreferences Another" + val);
            val -= coin;
            if(val < 0)
                val = 0;
            editor.putInt(this.coinKey, val);
            editor.commit();
            this.listener.onLoadCoin(val);
        }
    }


    public int getCoin(){
        SharedPreferences sharedPreferences = this.context.getSharedPreferences(context.getString(R.string.preference_file_key),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int val = sharedPreferences.getInt(this.coinKey,defValue);
        if(val == defValue){
            editor.putInt(this.coinKey, zeroCoin);
            editor.commit();
            return zeroCoin;
        }
        else{
            return val;
        }
    }


    public void addHalfCoin(){
        SharedPreferences sharedPreferences = this.context.getSharedPreferences(context.getString(R.string.preference_file_key),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int val = sharedPreferences.getInt(this.coinKey,defValue);
        if(val == defValue){
            Log.e("SHARED", "SharedPreferences" + val);
            editor.putInt(this.coinKey, biasCoin);
            editor.commit();
            this.listener.onLoadCoin(biasCoin);
        }
        else{
            Log.e("SHARED", "SharedPreferences Another" + val);
            val += this.halfCoin;
            editor.putInt(this.coinKey, val);
            editor.commit();
            this.listener.onLoadCoin(val);
        }
    }
    public void removeHalfCoin(int coin){
        SharedPreferences sharedPreferences = this.context.getSharedPreferences(context.getString(R.string.preference_file_key),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int val = sharedPreferences.getInt(this.coinKey,defValue);
        if(val == defValue){
            Log.e("SHARED", "SharedPreferences" + val);
            editor.putInt(this.coinKey, biasCoin);
            editor.commit();
            this.listener.onLoadCoin(biasCoin);
        }
        else{
            Log.e("SHARED", "SharedPreferences Another" + val);
            val -= this.halfCoin;
            if(val < 0)
                val = 0;
            editor.putInt(this.coinKey, val);
            editor.commit();
            this.listener.onLoadCoin(val);
        }
    }

    public void addFullCoin(){
        SharedPreferences sharedPreferences = this.context.getSharedPreferences(context.getString(R.string.preference_file_key),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int val = sharedPreferences.getInt(this.coinKey,defValue);
        if(val == defValue){
            Log.e("SHARED", "SharedPreferences" + val);
            editor.putInt(this.coinKey, biasCoin);
            editor.commit();
            this.listener.onLoadCoin(biasCoin);
        }
        else{
            Log.e("SHARED", "SharedPreferences Another" + val);
            val += this.fullCoin;
            editor.putInt(this.coinKey, val);
            editor.commit();
            this.listener.onLoadCoin(val);
        }
    }
    public void removeFullCoin(){
        SharedPreferences sharedPreferences = this.context.getSharedPreferences(context.getString(R.string.preference_file_key),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int val = sharedPreferences.getInt(this.coinKey,defValue);
        if(val == defValue){
            Log.e("SHARED", "SharedPreferences" + val);
            editor.putInt(this.coinKey, biasCoin);
            editor.commit();
            this.listener.onLoadCoin(biasCoin);
        }
        else{
            Log.e("SHARED", "SharedPreferences Another" + val);
            val -= this.fullCoin;
            if(val < 0)
                val = 0;
            editor.putInt(this.coinKey, val);
            editor.commit();
            this.listener.onLoadCoin(val);
        }
    }


    public void compareAndRemoveCoin(){
        SharedPreferences sharedPreferences = this.context.getSharedPreferences(context.getString(R.string.preference_file_key),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int val = sharedPreferences.getInt(this.coinKey,defValue);
        if(val == defValue){
            editor.putInt(this.coinKey, zeroCoin);
            editor.commit();
            this.listener.onLoadCoin(zeroCoin);
            this.listener.onNeedMoreCoin();
        }
        else{
            if(val >= fullCoin){
                val -= fullCoin;
                if(val < 0 )
                    this.listener.onNeedMoreCoin();
                else{
                    editor.putInt(this.coinKey, val);
                    editor.commit();
                    this.listener.onLoadCoin(val);
                    this.listener.onEnoughCoin();
                }
            }else{
                this.listener.onNeedMoreCoin();
                this.listener.onLoadCoin(val);
            }
        }
    }


    public void removeAndCompareCoin(CoinType type){
        SharedPreferences sharedPreferences = this.context.getSharedPreferences(context.getString(R.string.preference_file_key),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int val = sharedPreferences.getInt(this.coinKey,defValue);
        if(val == defValue){
            Log.e("SHARED", "SharedPreferences" + val);
            editor.putInt(this.coinKey, zeroCoin);
            editor.commit();
            this.listener.onLoadCoin(zeroCoin);
            this.listener.onNeedMoreCoin();
        }
        else{
            Log.e("SHARED", "SharedPreferences Another" + val);
            if(type == CoinType.FULL_COIN)
                val -= this.fullCoin;
            if(type == CoinType.HALF_COIN)
                val -= this.halfCoin;
            if(val < 0)
                val = 0;
            editor.putInt(this.coinKey, val);
            editor.commit();
            this.listener.onLoadCoin(val);
        }

    }




    public Context getContext(){
        return this.context;
    }
    public void setContext(Context context){
        this.context = context;
    }
    public OnCoinLibListener getListener(){
        return this.listener;
    }
    public void setListener(OnCoinLibListener listener){
        this.listener = listener;
    }




}
