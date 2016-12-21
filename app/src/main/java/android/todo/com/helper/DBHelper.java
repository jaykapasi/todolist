package android.todo.com.helper;

import android.os.AsyncTask;
import android.todo.com.TodoApplication;
import android.todo.com.activities.ParentActivity;
import android.todo.com.database.DaoMaster;
import android.todo.com.database.DaoSession;
import android.todo.com.database.TodoTask;
import android.todo.com.fragments.HomeFragment;
import android.todo.com.utils.Utilities;

/**
 * Created by jay on 20/12/16.
 */
public class DBHelper {

    private static DBHelper mInstance;
    private DaoSession mDaoSession;

    public static DBHelper getInstance() {
        if (mInstance == null) {
            mInstance = new DBHelper();
        }
        return mInstance;
    }

    private DBHelper() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(TodoApplication.getAppContext(), "Open Database", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        mDaoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public void deleteTask(final ParentActivity activity, final TodoTask todoTask) {

        class DeleteTodoTask extends AsyncTask<String, Void, Void> {

            @Override
            protected Void doInBackground(String... params) {
                getDaoSession().getTodoTaskDao().delete(todoTask);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (!(activity.getCurrentFragment() instanceof HomeFragment))
                    activity.onBackPressed();
                if (activity.getCurrentFragment() instanceof HomeFragment) {
                    Utilities.hideSoftKeyboard(activity);
                    ((HomeFragment) activity.getCurrentFragment()).onItemUpdate();
                }
            }
        }

        new DeleteTodoTask().execute();

    }

    public void insertOrReplaceNewTask(final ParentActivity activity, final TodoTask todoTask) {
        class InsertNewTask extends AsyncTask<String, Void, Void> {

            @Override
            protected Void doInBackground(String... params) {
                long value = getDaoSession().getTodoTaskDao().insertOrReplace(todoTask);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Utilities.hideSoftKeyboard(activity);
                activity.onBackPressed();
                if (activity.getCurrentFragment() instanceof HomeFragment) {
                    Utilities.hideSoftKeyboard(activity);
                    ((HomeFragment) activity.getCurrentFragment()).onItemUpdate();
                }
            }
        }

        new InsertNewTask().execute();
    }

}
