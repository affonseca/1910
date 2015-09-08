package com.mobilelearning.game1910.serviceHandling.handlers;


import com.mobilelearning.game1910.serviceHandling.json.ClassArray;

/**
 * Created with IntelliJ IDEA.
 * User: AFFonseca
 * Date: 10-02-2015
 * Time: 15:14
 * To change this template use File | Settings | File Templates.
 */
public interface OtherClassesHandler {

    void onGettingOtherClassesSuccess(ClassArray response);

    void onGettingOtherClassesError(String error);

}
