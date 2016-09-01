package com.ihandy.a2014011384;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by lenovo on 2016/9/1.
 */
public class MainRunner {
    static public void run(Looper looper, Runnable runnable)
    {
        new Handler(looper).post(runnable);
    }
}
