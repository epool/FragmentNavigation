package com.nearsoft.fragmentnavigation;

import android.support.v4.app.Fragment;

/**
 * Base fragment.
 * Created by epool on 1/13/16.
 */
public class BaseFragment extends Fragment {

    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

}
