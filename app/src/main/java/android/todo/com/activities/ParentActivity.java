package android.todo.com.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.todo.com.fragments.ParentFragment;
import android.todo.com.todolist.R;
import android.view.View;

import java.util.List;


public abstract class ParentActivity extends AppCompatActivity {

    private static final String TAG = ParentActivity.class.getSimpleName();

    // Fragment related: ContainerId, Currently Displayed Fragment, add/replace Animations.
    protected int mContainerID;
    protected int mFragEnterAnim, mFragExitAnim, mFragPopEnterAnim, mFragPopExitAnim;
    public static Activity currentActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentActivity = this;

        mFragEnterAnim = R.anim.slide_in_right;
        mFragExitAnim = R.anim.slide_out_left;
        mFragPopEnterAnim = R.anim.slide_in_left;
        mFragPopExitAnim = R.anim.slide_out_right;

    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        mContainerID = R.id.frag_container;

        // Enables drawing of Activity behind status bar.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    protected void removeAllFragments() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null)
                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
        }
    }

    protected void addFragment(ParentFragment fragment, String tag) {
        addFragment(fragment, tag, false,
                mFragEnterAnim, mFragExitAnim, mFragPopEnterAnim, mFragPopExitAnim);
    }

    protected void replaceFragment(ParentFragment fragment, String tag) {
        replaceFragment(fragment, tag, false,
                mFragEnterAnim, mFragExitAnim, mFragPopEnterAnim, mFragPopExitAnim);
    }

    protected void addFragment(ParentFragment fragment, String tag, boolean addToBackStack) {
        addFragment(fragment, tag, addToBackStack,
                mFragEnterAnim, mFragExitAnim, mFragPopEnterAnim, mFragPopExitAnim);
    }

    protected void replaceFragment(ParentFragment fragment, String tag, boolean addToBackStack) {
        replaceFragment(fragment, tag, addToBackStack,
                mFragEnterAnim, mFragExitAnim, mFragPopEnterAnim, mFragPopExitAnim);
    }

    protected void addFragment(ParentFragment fragment, String tag, boolean addToBackStack,
                               int enterAnim, int exitAnim, int popEnterAnim, int popExitAnim) {
//        mDisplayedFragment = fragment;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
                .add(mContainerID, fragment, tag);

        if (addToBackStack) transaction.addToBackStack(tag);
//        transaction.commit();
        transaction.commitAllowingStateLoss();
    }

    protected void replaceFragment(ParentFragment fragment, String tag, boolean addToBackStack,
                                   int enterAnim, int exitAnim, int popEnterAnim, int popExitAnim) {
//        mDisplayedFragment = fragment;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
                .replace(mContainerID, fragment, tag);

        if (addToBackStack) transaction.addToBackStack(tag);
//        transaction.commit();
        transaction.commitAllowingStateLoss();
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentActivity = this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        currentActivity = null;
    }


    // Function called by fragments to ask the activity to perform some action
    public void perform(int action, Bundle bundle) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Save the user's current game state
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public ParentFragment getCurrentFragment() {
        return (ParentFragment) getSupportFragmentManager().findFragmentById(mContainerID);
    }

}
