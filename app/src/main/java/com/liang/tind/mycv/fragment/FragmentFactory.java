package com.liang.tind.mycv.fragment;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.util.SparseArray;

import com.liang.tind.mycv.Constant;
import com.liang.tind.mycv.R;

/**
 * Created by Sherlock on 2017/9/1.
 */

public class FragmentFactory {
    private static SparseArray<Fragment> mFragments = new SparseArray<>(8);

    public static Fragment getFragment(@IdRes int key) {
        if (mFragments == null) {
            mFragments = new SparseArray<>(8);
        }
        Fragment fragment = null;
        Bundle bundle  = null;
        switch (key) {
            case R.id.nav_cv:
                fragment = mFragments.get(key);
                if (fragment == null) {
                    fragment = new CvFragment();

                }
                break;
            case R.id.nav_githup:
                fragment = mFragments.get(key);
                if (fragment == null) {
                    fragment = new HomepageFragment();
                    bundle = new Bundle();
                    bundle.putString(Constant.IntentExtra.TAG_HOMEPAGE_FRAGMENT_URL,Constant.URL.GITHUP_URL);
                    fragment.setArguments(bundle);
                }
                break;
            case R.id.nav_csdn:
                fragment = mFragments.get(key);
                if (fragment == null) {
                    fragment = new HomepageFragment();
                    bundle = new Bundle();
                    bundle.putString(Constant.IntentExtra.TAG_HOMEPAGE_FRAGMENT_URL,Constant.URL.CSDN_URL);
                    fragment.setArguments(bundle);
                }
                break;
            case R.id.nav_jianshu:
                fragment = mFragments.get(key);
                if (fragment == null) {
                    fragment = new HomepageFragment();
                    bundle = new Bundle();
                    bundle.putString(Constant.IntentExtra.TAG_HOMEPAGE_FRAGMENT_URL,Constant.URL.JIAN_SHU_URL);
                    fragment.setArguments(bundle);
                }
                break;
            case R.id.nav_we_chat:
                fragment = mFragments.get(key);
                if (fragment == null) {
                    fragment = new WeChatFragment();
                }
                break;
        }
        mFragments.put(key, fragment);
        return fragment;
    }

    public static void onActivityDestroyed() {
        mFragments = null;
    }
}
