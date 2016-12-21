package android.todo.com;

import android.app.Application;
import android.content.Context;

/**
 * Created by jay on 20/12/16.
 */


public class TodoApplication extends Application {

    private static final String TAG = TodoApplication.class.getSimpleName();
    private static TodoApplication mInstance;
    private static Context mAppContext;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mAppContext = getApplicationContext();
    }

    public static TodoApplication getInstance() {
        return mInstance;
    }

    public static Context getAppContext() {
        return mAppContext;
    }
}
