package dbc;

/**
 * Created by Tangqh on 2017/10/26.
 */

public interface CallBackListener {
    void onFinish(String info);
    void onError(Exception e);
}
