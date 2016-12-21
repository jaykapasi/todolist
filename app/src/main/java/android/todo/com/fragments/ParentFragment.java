package android.todo.com.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.todo.com.activities.ParentActivity;
import android.view.View;

public abstract class ParentFragment extends Fragment implements View.OnClickListener {

    public static String TAG = ParentFragment.class.getSimpleName();

    private boolean mIsOneItemClicked = false;
    public final int DISABLE_DURATION = 500;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialiseView(view, savedInstanceState);
        bindListeners(view);
        callInitialApi();
    }

    protected abstract void initialiseView(View view, Bundle savedInstanceState);

    protected abstract void bindListeners(View view);

    protected void callInitialApi() {
    }

    protected ParentActivity getCurrActivity() {
        return (ParentActivity) getActivity();
    }

    public abstract void onClicked(View v);

    @Override
    public void onClick(View v) {
        if (!mIsOneItemClicked) {
            onClicked(v);
            mIsOneItemClicked = true;

            v.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mIsOneItemClicked = false;
                }
            }, DISABLE_DURATION);
        }
    }

}
