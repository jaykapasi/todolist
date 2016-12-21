package android.todo.com.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.todo.com.adapters.RecyclerListAdapter;
import android.todo.com.database.TodoTask;
import android.todo.com.helper.DBHelper;
import android.todo.com.helper.SimpleItemTouchHelperCallback;
import android.todo.com.listeneres.OnItemActionListener;
import android.todo.com.listeneres.OnItemUpdateListener;
import android.todo.com.todolist.R;
import android.todo.com.utils.AppConstants;
import android.todo.com.utils.Utilities;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jay on 19/12/16.
 */
public class HomeFragment extends ParentFragment implements OnItemActionListener, OnItemUpdateListener, SearchView.OnQueryTextListener {

    public static final String TAG = HomeFragment.class.getSimpleName();

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private RecyclerView mTaskRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private TextView mNoTaskTxtView;
    private SearchView mSearchView;
    private FloatingActionButton mAddTaskBtn;
    private RecyclerListAdapter mRecyclerViewAdapter;
    private ItemTouchHelper mItemTouchHelper;
    private List<TodoTask> mTodoTaskList = new ArrayList<>();
    private TextView mTitleTxt;
    private List<TodoTask> mSearchList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    protected void initialiseView(View view, Bundle savedInstanceState) {
        mTaskRecyclerView = (RecyclerView) view.findViewById(R.id.home_task_recycler_view);
        mNoTaskTxtView = (TextView) view.findViewById(R.id.home_no_task_txt);
        mAddTaskBtn = (FloatingActionButton) view.findViewById(R.id.home_add_task_btn);
        mSearchView = (SearchView) view.findViewById(R.id.home_search_view);
        mTitleTxt = (TextView) view.findViewById(R.id.home_title_txt);
        mLayoutManager = new LinearLayoutManager(getCurrActivity());
        mTaskRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerViewAdapter = new RecyclerListAdapter(getCurrActivity(), this);
        mTaskRecyclerView.setAdapter(mRecyclerViewAdapter);
        mTaskRecyclerView.setVisibility(View.VISIBLE);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mRecyclerViewAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mTaskRecyclerView);

    }

    @Override
    protected void bindListeners(View view) {
        mAddTaskBtn.setOnClickListener(this);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    mTitleTxt.setVisibility(View.GONE);
                    mSearchList.clear();
                } else {
                    mTitleTxt.setVisibility(View.VISIBLE);
                    mRecyclerViewAdapter.setTodoTaskList(mTodoTaskList);
                    mSearchView.onActionViewCollapsed();
                }
            }
        });
    }

    @Override
    public void onClicked(View v) {
        switch (v.getId()) {
            case R.id.home_add_task_btn:
                getCurrActivity().perform(AppConstants.NEW_TASK_SCREEN, null);
                break;
            case R.id.home_search_view:
                getCurrActivity().perform(AppConstants.SEARCH_TASK_SCREEN, null);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "OnResume");
        mTodoTaskList = DBHelper.getInstance().getDaoSession().getTodoTaskDao().loadAll();
        mRecyclerViewAdapter.setTodoTaskList(mTodoTaskList);
        updateUI();
    }

    private void updateUI() {
        if (mTodoTaskList.size() == 0) {
            mTaskRecyclerView.setVisibility(View.GONE);
            mNoTaskTxtView.setVisibility(View.VISIBLE);
        } else {
            mTaskRecyclerView.setVisibility(View.VISIBLE);
            mNoTaskTxtView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(TodoTask todoTask) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppConstants.KEY_TODOTASK, todoTask);
        getCurrActivity().perform(AppConstants.DETAIL_TASK_SCREEN, bundle);
    }

    @Override
    public void onItemDismiss(TodoTask todoTask) {
        DBHelper.getInstance().deleteTask(getCurrActivity(), todoTask);
        mTodoTaskList.remove(todoTask);
    }

    @Override
    public void onItemUpdate() {
        mTodoTaskList = DBHelper.getInstance().getDaoSession().getTodoTaskDao().loadAll();
        mRecyclerViewAdapter.setTodoTaskList(mTodoTaskList);
        updateUI();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mSearchList.clear();
        for (TodoTask todoTask : mTodoTaskList) {
            if (todoTask.contains(query)) {
                mSearchList.add(todoTask);
            }
        }
        if (mSearchList.size() == 0) Utilities.showToast(getView(), "No records found!");
        mRecyclerViewAdapter.setTodoTaskList(mSearchList);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) mSearchList.clear();
        return false;
    }
}
