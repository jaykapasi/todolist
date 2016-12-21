package android.todo.com.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.todo.com.database.TodoTask;
import android.todo.com.helper.DBHelper;
import android.todo.com.todolist.R;
import android.todo.com.utils.AppConstants;
import android.todo.com.utils.Utilities;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by jay on 19/12/16.
 */
public class TaskDetailFragment extends ParentFragment {

    public static final String TAG = TaskDetailFragment.class.getSimpleName();

    public static TaskDetailFragment newInstance(TodoTask todoTask) {
        Bundle args = new Bundle();
        args.putParcelable(AppConstants.KEY_TODOTASK, todoTask);
        TaskDetailFragment fragment = new TaskDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private EditText mTitleEdtTxt, mDescriptionEdtTxt;
    private TodoTask mTodotask;
    private ImageView mDeleteImgView, mSaveImgView, mBackImgView;
    private String mTitle, mDescription;
    private TextView mTitleTxt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task, container, false);
    }

    @Override
    protected void initialiseView(View view, Bundle savedInstanceState) {
        mTodotask = getArguments().getParcelable(AppConstants.KEY_TODOTASK);

        mTitleEdtTxt = (EditText) view.findViewById(R.id.task_title_txt);
        mDescriptionEdtTxt = (EditText) view.findViewById(R.id.task_description_txt);
        mSaveImgView = (ImageView) view.findViewById(R.id.task_save_img_view);
        mDeleteImgView = (ImageView) view.findViewById(R.id.task_delete_img_view);
        mBackImgView = (ImageView) view.findViewById(R.id.task_back_img_view);
        mTitleTxt = (TextView) view.findViewById(R.id.task_screen_title_txt);

        mTitleTxt.setText("Task Detail");
        mDeleteImgView.setVisibility(View.VISIBLE);

        mTitleEdtTxt.setText(mTodotask.getTitle());
        mDescriptionEdtTxt.setText(mTodotask.getDescription());
    }

    @Override
    protected void bindListeners(View view) {
        mDeleteImgView.setOnClickListener(this);
        mSaveImgView.setOnClickListener(this);
        mBackImgView.setOnClickListener(this);
    }

    @Override
    public void onClicked(View v) {
        switch (v.getId()) {
            case R.id.task_save_img_view:
                mTitle = mTitleEdtTxt.getText().toString();
                mDescription = mDescriptionEdtTxt.getText().toString();
                if (!TextUtils.isEmpty(mTitle) || !TextUtils.isEmpty(mDescription)) {
                    mTodotask.setDescription(mDescription);
                    mTodotask.setTitle(mTitle);
                    mTodotask.setCreated_date(System.currentTimeMillis() + "");
                    DBHelper.getInstance().insertOrReplaceNewTask(getCurrActivity(), mTodotask);

                } else {
                    Utilities.showToast(getView(), "Title or description should not be empty.");
                }
                break;
            case R.id.task_delete_img_view:
                DBHelper.getInstance().deleteTask(getCurrActivity(), mTodotask);
                break;
            case R.id.task_back_img_view:
                Utilities.hideSoftKeyboard(getCurrActivity());
                getCurrActivity().onBackPressed();
                break;
        }
    }
}
