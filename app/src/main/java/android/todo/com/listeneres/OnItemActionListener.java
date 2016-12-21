package android.todo.com.listeneres;

import android.todo.com.database.TodoTask;

/**
 * Created by Jay on 12/21/2016.
 */

public interface OnItemActionListener {

    void onItemClick(TodoTask todoTask);

    void onItemDismiss(TodoTask todoTask);

}
