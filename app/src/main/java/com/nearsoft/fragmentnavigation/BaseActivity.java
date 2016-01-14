package com.nearsoft.fragmentnavigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.HashSet;
import java.util.Set;

/**
 * Base activity.
 * Created by epool on 1/13/16.
 */
public class BaseActivity extends AppCompatActivity {

    public static final String ROOT = "ROOT";

    private FragmentManager mFragmentManager;
    private Set<Integer> mFragmentIndexesToBackToRoot = new HashSet<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFragmentManager = getSupportFragmentManager();
    }

    /**
     * Presents the fragment passed as parameter.
     *
     * @param fragment       Fragment to show.
     * @param addToBackStack Indicates if add to back stack or not.
     */
    public void goToFragment(Fragment fragment, boolean addToBackStack) {
        goToFragment(fragment, addToBackStack, false);
    }

    /**
     * Presents the fragment passed as parameter.
     *
     * @param fragment       Fragment to show.
     * @param addToBackStack Indicates if add to back stack or not.
     * @param goToRoot       If true the back behavior will pop all the fragments in the stack.
     */
    public void goToFragment(Fragment fragment, boolean addToBackStack, boolean goToRoot) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.flContainer, fragment);
        if (addToBackStack) {
            // If the back stack entry count is 0 it means the fragment will be the root/first.
            transaction
                    .addToBackStack(mFragmentManager.getBackStackEntryCount() == 0 ? ROOT : null);
        }
        transaction.commit();

        if (goToRoot) mFragmentIndexesToBackToRoot.add(mFragmentManager.getBackStackEntryCount());
    }

    /**
     * Pops all fragments from the back stack including the ROOT fragment.
     */
    public void goToRootFragment() {
        getSupportFragmentManager().popBackStack(ROOT, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void onBackPressed() {
        int currentFragmentIndex = mFragmentManager.getBackStackEntryCount() - 1;
        for (Integer fragmentIndexToBackToRoot : mFragmentIndexesToBackToRoot) {
            if (currentFragmentIndex == fragmentIndexToBackToRoot) {
                goToRootFragment();
                mFragmentIndexesToBackToRoot.clear();
                return;
            }
        }
        super.onBackPressed();
    }
}
