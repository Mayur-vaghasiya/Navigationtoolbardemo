package com.example.navigationtoolbardemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.navigationtoolbardemo.interfaces.ChangeCurrentFragment;


public class MainActivity extends AppCompatActivity implements ChangeCurrentFragment, FragmentManager.OnBackStackChangedListener {

    private DrawerLayout mDrawerLayout;
    private String fragment_name, Tag;
    private Toolbar toolbar = null;
    private AppCompatTextView actv_header_name;
    private Activity activity = null;
    private ChangeCurrentFragment ChangeCurrentFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = MainActivity.this;
        ChangeCurrentFragment = (ChangeCurrentFragment) activity;//make object


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // getSupportActionBar().setDisplayShowHomeEnabled(true);

        actv_header_name = (AppCompatTextView) toolbar.findViewById(R.id.actv_header_name);

//        manageFragments(new FrgmentOne(), "Fragmentone");

        getSupportFragmentManager().addOnBackStackChangedListener(this);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(navigationItemSelectedListener);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment, new FrgmentOne()).commit();
        }

        navigationView.setCheckedItem(R.id.home);
    }

    NavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            mDrawerLayout.closeDrawer(GravityCompat.START);

            Fragment fragment = null;
            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.home:
                    fragment = new FrgmentOne();
                    break;
                case R.id.menus:
                    fragment = new FragmentTwo();
                    break;
            }

            ft.replace(R.id.fragment, fragment).commit();

            return true;
        }
    };

    @Override
    public void onBackPressed() {
        if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void manageFragments(Fragment fragmentName, String tag) {

        fragment_name = fragmentName.getClass().getName();
        Tag = tag;

        FragmentManager manager = getSupportFragmentManager();
        boolean fragment_popped = manager.popBackStackImmediate(fragment_name, 0);

        if (!fragment_popped) { //fragment not in back stack, create it.

//            //Directly Go to the deshboard
//            for (int i = manager.getBackStackEntryCount() - 1; i > 0; i--) {
//                if (!manager.getBackStackEntryAt(i).getName().equalsIgnoreCase(getString(R.string.dashboard))) {
//                    manager.popBackStack();
//                } else {
//                    break;
//                }
//            }

            FragmentTransaction ft = manager.beginTransaction();
            //ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_right );
            ft.add(R.id.fragment, fragmentName, tag);
            // ft.show(fragmentName);
            //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(fragment_name);
            ft.commit();


        }
    }

    @Override
    public void onBackStackChanged() {

        Object object = getSupportFragmentManager().findFragmentById(R.id.fragment);

        if (object != null) {

            ((Fragment) object).onResume();

        }
    }

    @Override
    public void onFragmentChangeListener(Fragment fragment, String tag) {

        manageFragments(fragment, tag);
    }

@Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        View view = getCurrentFocus();
        boolean ret = super.dispatchTouchEvent(event);

        if (view instanceof EditText) {
            View w = getCurrentFocus();
            int scrcoords[] = new int[2];
            w.getLocationOnScreen(scrcoords);
            float x = event.getRawX() + w.getLeft() - scrcoords[0];
            float y = event.getRawY() + w.getTop() - scrcoords[1];

            if (event.getAction() == MotionEvent.ACTION_UP
                    && (x < w.getLeft() || x >= w.getRight() || y < w.getTop() || y > w
                    .getBottom())) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus()
                        .getWindowToken(), 0);
            }
        }
        return ret;
    }
}
