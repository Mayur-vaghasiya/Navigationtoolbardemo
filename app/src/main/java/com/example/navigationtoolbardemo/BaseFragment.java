package com.example.navigationtoolbardemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.navigationtoolbardemo.interfaces.ChangeCurrentFragment;


/**
 * Created by peacock on 24/1/17.
 */

public class BaseFragment extends Fragment {

    private MainActivity activity;
    private ChangeCurrentFragment ChangeCurrentFragment;
    private Toolbar toolbar = null;
    private AppCompatTextView actv_header_name;

    private ChangeCurrentFragment changeCurrentFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (MainActivity) getActivity();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        changeCurrentFragment = (ChangeCurrentFragment) context;//make object
    }

    public ChangeCurrentFragment onChangeCurrentFragment() {
        return changeCurrentFragment;
    }


    public Activity getBaseActivity() {

        return activity;

    }


    public ActionBar getBaseSupportActionBar() {

        return activity.getSupportActionBar();

    }


    public void onRestoreInstanceState(Bundle savedInstanceState) {

    }

}
