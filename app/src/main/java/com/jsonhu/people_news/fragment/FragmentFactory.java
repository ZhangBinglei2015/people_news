package com.jsonhu.people_news.fragment;

import android.util.Log;


public class FragmentFactory {

	public static BaseFragment getFragmentByTag(String tag) {
        Log.d("FragmentFactory", "new " + tag);
		if (tag.equals(HomePager.FRAGMENT_TAG)) {
            return new HomePager();
		}else if (tag.equals(MinePager.FRAGMENT_TAG)) {
			return new MinePager();
		}else if (tag.equals(HearingFragment.FRAGMENT_TAG)){
			return new HearingFragment();
		}
		else {
			return null;
		}
	}
}
