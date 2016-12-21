package android.todo.com.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.todo.com.database.TodoTask;
import android.todo.com.helper.DBHelper;
import android.todo.com.todolist.R;
import android.todo.com.utils.Utilities;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by jay on 19/12/16.
 */
public class NewTaskFragment extends ParentFragment {

    public static final String TAG = NewTaskFragment.class.getSimpleName();

    public static NewTaskFragment newInstance() {
        Bundle args = new Bundle();
        NewTaskFragment fragment = new NewTaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private ImageView mSaveImgView, mBackImgView;
    private EditText mTitleEdtTxt, mDescriptionEdtTxt;
    private String mTitle, mDescription;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task, container, false);
    }

    @Override
    protected void initialiseView(View view, Bundle savedInstanceState) {
        mSaveImgView = (ImageView) view.findViewById(R.id.task_save_img_view);
        mTitleEdtTxt = (EditText) view.findViewById(R.id.task_title_txt);
        mBackImgView = (ImageView) view.findViewById(R.id.task_back_img_view);
        mDescriptionEdtTxt = (EditText) view.findViewById(R.id.task_description_txt);
    }

    @Override
    protected void bindListeners(View view) {
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
                    TodoTask todoTask = new TodoTask();
                    todoTask.setTitle(mTitle);
                    todoTask.setDescription(mDescription);
                    todoTask.setCreated_date(System.currentTimeMillis() + "");
                    DBHelper.getInstance().insertOrReplaceNewTask(getCurrActivity(), todoTask);
                } else {
                    Utilities.showToast(getView(), "Title or description should not be empty.");
                }
                break;
            case R.id.task_back_img_view:
                Utilities.hideSoftKeyboard(getCurrActivity());
                getCurrActivity().onBackPressed();
                break;
        }
    }
}
