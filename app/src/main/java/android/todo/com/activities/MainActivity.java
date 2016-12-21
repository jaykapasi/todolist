package android.todo.com.activities;

import android.os.Bundle;
import android.todo.com.database.TodoTask;
import android.todo.com.fragments.HomeFragment;
import android.todo.com.fragments.NewTaskFragment;
import android.todo.com.fragments.TaskDetailFragment;
import android.todo.com.todolist.R;
import android.todo.com.utils.AppConstants;

/**
 * Created by jay on 19/12/16.
 */
public class MainActivity extends ParentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        launchHomeScreen();

    }

    @Override
    public void perform(int action, Bundle bundle) {
        super.perform(action, bundle);
        switch (action) {
            case AppConstants.NEW_TASK_SCREEN:
                launchNewTaskScreen();
                break;
            case AppConstants.DETAIL_TASK_SCREEN:
                launchTaskDetailScreen((TodoTask) bundle.getParcelable(AppConstants.KEY_TODOTASK));
                break;
        }
    }

    private void launchTaskDetailScreen(TodoTask todoTask) {
        addFragment(TaskDetailFragment.newInstance(todoTask), TaskDetailFragment.TAG, true);
    }

    private void launchHomeScreen() {
        replaceFragment(HomeFragment.newInstance(), HomeFragment.TAG, false, 0, 0, 0, 0);
    }

    private void launchNewTaskScreen() {
        addFragment(NewTaskFragment.newInstance(), NewTaskFragment.TAG, true);
    }
}
