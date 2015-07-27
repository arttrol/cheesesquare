/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.support.android.designlibdemo;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 */
public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private CheeseListFragment f1;
    private CheeseListFragment f2;
    private CheeseListFragment f3;
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private MyCoordinatorLayout coordinatorLayout;
    private ViewPager viewPager;
    private Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        f1 = new CheeseListFragment();
        f2 = new CheeseListFragment();
        f3 = new CheeseListFragment();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                Log.e(TAG, "onOffsetChanged() called with " + "appBarLayout = [" + appBarLayout + "], i = [" + i + "]");
            }
        });
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }
        coordinatorLayout = (MyCoordinatorLayout) findViewById(R.id.main_content);
        coordinatorLayout.setListener(this);


////        AppBarLayout.ScrollingViewBehavior
//coordinatorLayout.
////        AppBarLayout.ScrollingViewBehavior scrollingViewBehavior = new AppBarLayout.ScrollingViewBehavior(this, null);
////        scrollingViewBehavior.setOverlayTop(50);
////        scrollingViewBehavior.layoutDependsOn(coordinatorLayout, viewPager, null);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(f1, "Category 1");
        adapter.addFragment(f2, "Category 2");
        adapter.addFragment(f3, "Category 3");
        viewPager.setAdapter(adapter);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    private static final String TAG = "MainActivity";
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void checkActionBarPosition() {
        Log.e(TAG, "checkActionBarPosition " + toolbar.getTranslationY() + "  " + appBarLayout.getY()
        + " " + coordinatorLayout.getScrollY() + " " + coordinatorLayout.getScrollY());

        if (-appBarLayout.getY() > appBarLayout.getTotalScrollRange() / 2) {
//            coordinatorLayout.dispatchNestedFling(0, 300f, true);
//            coordinatorLayout.dispatchNestedPreFling(0, 300f);
            aaa(adapter.getRecyclerView(viewPager.getCurrentItem()), 500f);
//            coordinatorLayout.onNestedPreFling(adapter.getRecyclerView(viewPager.getCurrentItem()), 0, -300f);
//            appBarLayout.setY(-appBarLayout.getTotalScrollRange());
        } else {
//            appBarLayout.setY(0);
            aaa(adapter.getRecyclerView(viewPager.getCurrentItem()), -500f);
        }
    }

    private void aaa(View target, float velocityY){
        boolean handled = false;
        int childCount = coordinatorLayout.getChildCount();

//        for(int i = 0; i < childCount; ++i) {
//            View view = coordinatorLayout.getChildAt(i);
            View view = appBarLayout;
            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams)view.getLayoutParams();
//            if(lp.isNestedScrollAccepted()) {
                CoordinatorLayout.Behavior viewBehavior = lp.getBehavior();
                if(viewBehavior != null) {
                    handled |= viewBehavior.onNestedFling(coordinatorLayout, view, target, 0, velocityY, true);
                }
//            }
//        }

        if(handled) {
//            coordinatorLayout.dispatchOnDependentViewChanged(true);
        }
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<CheeseListFragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(CheeseListFragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        public RecyclerView getRecyclerView(int pos){
            return getItem(pos).getRV();
        }

        @Override
        public CheeseListFragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
