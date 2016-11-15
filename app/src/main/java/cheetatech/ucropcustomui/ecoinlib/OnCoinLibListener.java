package cheetatech.ucropcustomui.ecoinlib;

/**
 * Created by Erkan.Guzeler on 15.11.2016.
 */

public interface OnCoinLibListener {
    void onNeedMoreCoin();
    void onEnoughCoin();
    void onLoadCoin(int coin);
}
