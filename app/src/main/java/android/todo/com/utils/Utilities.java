package android.todo.com.utils;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.text.format.DateFormat;
import android.todo.com.TodoApplication;
import android.todo.com.activities.ParentActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by jay on 20/12/16.
 */
public class Utilities {

    public static void showToast(View view, String data) {
        if (view != null) {
            Snackbar.make(view, data, Snackbar.LENGTH_SHORT).show();
        } else {
            Toast.makeText(TodoApplication.getAppContext(), data, Toast.LENGTH_SHORT).show();
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        View focusView = activity.getCurrentFocus();
        if (focusView != null)
            inputMethodManager.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
    }

    public static String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }
}
