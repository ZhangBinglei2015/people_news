package com.jsonhu.people_news.factory;

import java.util.HashMap;
import java.util.Map;

import com.jsonhu.people_news.fragment.BaseFragment;
import com.jsonhu.people_news.fragment.HearingFragment;

/**
 * Created by lugang on 2016/1/20.
 */
public class FragmentFactory {

    public final static int HAERING_FRAGMENT = 1;
    private final static Map<Integer,BaseFragment> fragmentMap = new HashMap<Integer,BaseFragment>();

    public static BaseFragment create(int flag){
        BaseFragment fragment = fragmentMap.get(flag);
        if (fragment == null){
            switch (flag){

                case HAERING_FRAGMENT:
                    fragment = new HearingFragment();
                    break;

                default:
                    break;
            }

            fragmentMap.put(flag,fragment);
        }

        return fragment;
    }
}
