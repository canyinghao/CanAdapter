package com.canyinghao.canadapterdemo.ui;

import android.os.Bundle;

import com.canyinghao.canadapterdemo.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;


/**
 * Created by canyinghao on 16/1/21.
 */
public class MainActivity extends AppCompatActivity {



    Toolbar toolbar;

    TabLayout tabLayout;

    ViewPager viewPager;

    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        toolbar.setTitle(R.string.app_name);

        if (viewPager != null) {
            setupViewPager(viewPager);
        }


        tabLayout.setupWithViewPager(viewPager);

    }


    private void setupViewPager(ViewPager viewPager) {
        adapter = new Adapter(getSupportFragmentManager());

        String[] strs = getResources().getStringArray(R.array.array_list);


        ListViewFragment listViewFragment = new ListViewFragment();
        RVListFragment rvListFragment = new RVListFragment();
        RVGridFragment rvGridFragment = new RVGridFragment();
        ERVGridFragment ervGridFragment = new ERVGridFragment();
        RVHFGridFragment1 rvhfGridFragment = new RVHFGridFragment1();


        adapter.addFragment(listViewFragment, strs[0]);
        adapter.addFragment(rvListFragment, strs[1]);
        adapter.addFragment(rvGridFragment, strs[2]);
        adapter.addFragment(ervGridFragment, strs[3]);
        adapter.addFragment(rvhfGridFragment, strs[4]);

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
    }


    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
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
