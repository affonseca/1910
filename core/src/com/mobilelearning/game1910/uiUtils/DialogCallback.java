package com.mobilelearning.game1910.uiUtils;

/**
 * Created with IntelliJ IDEA.
 * User: AFFonseca
 * Date: 22-02-2015
 * Time: 15:00
 * To change this template use File | Settings | File Templates.
 */
public interface DialogCallback {
    public static final int BOX_END = 0;
    public static final int ALL_END = 1;
    public static final int CLICKED_AFTER_END = 2;

    public void onEvent(int type, DialogBox source);
}
