package com.toniousli.wearablenotes.data;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

/**
 * Created by toni on 7/11/14.
 */
public class DatabaseInstance {

    private static DatabaseHelper helper;

    public static DatabaseHelper getInstance(Context context) {
        if(helper == null) {
            helper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
        }
        return helper;
    }

}
