package com.jsonhu.people_news.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.jsonhu.people_news.R;
import com.jsonhu.people_news.fragment.BaseFragment;
import com.jsonhu.people_news.fragment.FragmentFactory;
import com.jsonhu.people_news.fragment.HearingFragment;
import com.jsonhu.people_news.fragment.HomePager;
import com.jsonhu.people_news.base.BaseActivity;
import com.jsonhu.people_news.fragment.BaseFragment;
import com.jsonhu.people_news.fragment.MinePager;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends BaseActivity implements
        OnClickListener {

    private static String TAG = MainActivity.class.getSimpleName();

    private Button mBt0, mBt1, mBt2;
    private static int mCurrntTabInt = -1;
    private static String mCurrentFragmentTag;
    private static BaseFragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");


        setContentView(R.layout.activity_main);
        initViews();

        switchTabChoosed(0);
        switchContent(HomePager.FRAGMENT_TAG);

    }

    private void initViews() {
        mBt0 = (Button) findViewById(R.id.tab_button_0);
        mBt1 = (Button) findViewById(R.id.tab_button_1);
        mBt2 = (Button) findViewById(R.id.tab_button_2);

        mBt0.setOnClickListener(this);
        mBt1.setOnClickListener(this);
        mBt2.setOnClickListener(this);

    }

    @Override
    public void onClick(View arg0) {

        switch (arg0.getId()) {

            case R.id.tab_button_0:
                switchTabChoosed(0);
                switchContent(HomePager.FRAGMENT_TAG);
                break;
            case R.id.tab_button_1:
                switchTabChoosed(1);
                switchContent(HearingFragment.FRAGMENT_TAG);
                break;
            case R.id.tab_button_2:
                switchTabChoosed(2);
                switchContent(MinePager.FRAGMENT_TAG);
                break;

            default:
                break;
        }
    }

    public void switchTabChoosed(int tab) {
        mCurrntTabInt = tab;
        switch (tab) {
            case 0:
                mBt0.setSelected(true);
                mBt1.setSelected(false);
                mBt2.setSelected(false);
                break;
            case 1:
                mBt0.setSelected(false);
                mBt1.setSelected(true);
                mBt2.setSelected(false);
                break;
            case 2:
                mBt0.setSelected(false);
                mBt1.setSelected(false);
                mBt2.setSelected(true);
                break;
            default:
                break;
        }
    }

    private BaseFragment toFragment;

    public void switchContent(String tag) {
        Log.d(TAG, "switchContent tag " + tag);

        mCurrentFragmentTag = tag;

        toFragment = (BaseFragment) getSupportFragmentManager()
                .findFragmentByTag(tag);

        if (toFragment != null
                && tag.equals(HearingFragment.FRAGMENT_TAG)) {
            if (toFragment.isVisible()) {
                FragmentTransaction fmt = getSupportFragmentManager()
                        .beginTransaction();
                fmt.remove(toFragment);
                toFragment = null;
            }
        }

        if ((toFragment != null) && tag.equals(HearingFragment.FRAGMENT_TAG)) {
            if (toFragment.isVisible()) {
                FragmentTransaction fmt = getSupportFragmentManager()
                        .beginTransaction();
                fmt.remove(toFragment);
                toFragment = null;
            }
        }



        if (toFragment == null) {
            Log.d(TAG, "toFragment == null " + tag);
            toFragment = FragmentFactory.getFragmentByTag(tag);
            if (toFragment == null) {
                throw new NullPointerException(
                        "you should create a new Fragment by Tag" + tag);
            }

            FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction();
            fragmentTransaction
                    .add(R.id.lay_content_container, toFragment, tag);
            if (mCurrentFragment != null) {
                fragmentTransaction.hide(mCurrentFragment);
            }
            fragmentTransaction.commit();
            mCurrentFragment = toFragment;
        } else {
            if (mCurrentFragment == toFragment) {
                return;
            }
            if (!toFragment.isAdded()) {
                Log.d(TAG, "!toFragment.isAdded() " + tag);
                FragmentTransaction fmt = getSupportFragmentManager()
                        .beginTransaction();
                if (mCurrentFragment != null) {
                    fmt.hide(mCurrentFragment);
                }
                fmt.add(R.id.lay_content_container, toFragment, tag);
                fmt.commit();
                mCurrentFragment = toFragment;
            } else {
                Log.d(TAG, "toFragment.isAdded() " + tag);
                if (toFragment.isHidden()) {
                    Log.d(TAG,
                            "toFragment.isHidden() " + tag + toFragment.getId());
                }
                FragmentTransaction fmt = getSupportFragmentManager()
                        .beginTransaction();
                if (mCurrentFragment != null) {
                    Log.d(TAG, "mCurrentFragment != null "
                            + mCurrentFragment.getTag());
                    fmt.hide(mCurrentFragment);
                } else {
                    Log.d(TAG, "mCurrentFragment == null ");
                }
                fmt.show(toFragment).commit();
                mCurrentFragment = toFragment;
            }
        }

    }


    private static Boolean isQuit = false;
    private Timer timer = new Timer();

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!isQuit) {
                isQuit = true;
                Toast.makeText(getBaseContext(),
                        R.string.back_more_quit, Toast.LENGTH_LONG).show();
                TimerTask task = null;
                task = new TimerTask() {
                    @Override
                    public void run() {
                        isQuit = false;
                    }
                };
                timer.schedule(task, 2000);
            } else {
                finish();
                System.exit(0);
            }
        }
        return false;
    }


    @Override
    protected void onDestroy() {
        Log.v(TAG, "onDestroy");
        mCurrentFragmentTag = null;
        mCurrntTabInt = -1;
        super.onDestroy();
    }

    @Override
    public void finish() {
        Log.v(TAG, "finish");
        mCurrentFragmentTag = null;
        mCurrntTabInt = -1;
        super.finish();
    }


}