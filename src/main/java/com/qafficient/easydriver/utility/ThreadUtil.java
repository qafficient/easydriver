package com.qafficient.easydriver.utility;

public class ThreadUtil {

    private ThreadUtil(){}

    public static void sleep(int seconds){
        try{
            Thread.sleep(seconds*1000);
        }catch (InterruptedException e){

        }
    }

    public static void sleep(long milliseconds){
        try{
            Thread.sleep(milliseconds);
        }catch (InterruptedException e){

        }
    }
}
