package com.jsonhu.people_news.fragment;

import android.view.View;

import android.content.Intent;
import android.view.LayoutInflater;

import com.jsonhu.people_news.R;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.Toast;


import com.jsonhu.people_news.activity.ChannelActivity;
import com.jsonhu.people_news.view.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;


/**
 * 空白页面
 */
public class HomePager extends BaseFragment implements View.OnClickListener {
    public static String FRAGMENT_TAG = HomePager.class.getSimpleName();
private PagerSlidingTabStrip mTabs;
	private ViewPager mPager;
	private MyPagerAdapter mPagerAdapter;
	private List<PagerItem> mPagers = new ArrayList<PagerItem>();
	private int[] icon_res = {R.mipmap.ic_launcher,R.mipmap.ic_launcher, R.mipmap.ic_launcher,
			R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,
			R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,
			R.mipmap.ic_launcher,R.mipmap.ic_launcher};
	private int FRAGMENT_COUNT = icon_res.length;
	private View v;

	@Override
	protected void lazyLoad() {

	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public View initView(LayoutInflater inflater) {
		v = View.inflate(getActivity(), R.layout.act_home_pager, null);
		initView(v);
		init();
		Toast.makeText(getActivity(), "init", Toast.LENGTH_LONG).show();
		return v;
	}

	@Override
	public void loadData() {

	}

	private void init() {

		/*mPagers.add(new PagerItem("普通", new HearingFragment()));
		mPagers.add(new PagerItem("分页", new HearingFragment()));
		mPagers.add(new PagerItem("刷新", new HearingFragment()));*/

		for (int i = 0; i < FRAGMENT_COUNT; i++) {
			mPagers.add(new PagerItem("",new HearingFragment()));
		}
		mPager.setOffscreenPageLimit(0);
		mPagerAdapter.notifyDataSetChanged();
		mTabs.notifyDataSetChanged();
	}
	private void initView(View view) {
		mTabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
		mPager = (ViewPager) view.findViewById(R.id.pager);
		ImageView icon_add = (ImageView) view.findViewById(R.id.icon_add);
		icon_add.setOnClickListener(this);
		mTabs.setShouldExpand(true);
		mTabs.setTabPaddingLeftRight(5);
		mTabs.setTextColor(Color.WHITE);
		mTabs.setTextSize(18);
		mTabs.setIndicatorColor(Color.TRANSPARENT);
		mTabs.setIndicatorHeight(0);
		mTabs.setUnderlineHeight(0);
		mTabs.setDividerPadding(0);
		mPagerAdapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
		mPager.setAdapter(mPagerAdapter);
		mTabs.setViewPager(mPager);
	}
	@Override
	public void onClick(View v) {

		switch (v.getId()){
			case R.id.icon_add:
				Intent intent = new Intent(getActivity(), ChannelActivity.class);
				startActivity(intent);
				/*ChannelWindow channelWindow = new ChannelWindow(getActivity());
				channelWindow.show();*/
				break;
		}
	}
	private class MyPagerAdapter extends FragmentStatePagerAdapter implements PagerSlidingTabStrip.IconTabProvider {

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public CharSequence getPageTitle(int position) {

			return "";
		}

		@Override
		public Fragment getItem(int position) {
			try {
				return mPagers.get(position).fragment;
			} catch (IndexOutOfBoundsException e) {
			}
			return null;
		}

		@Override
		public int getCount() {
			return FRAGMENT_COUNT;
		}

		@Override
		public int getPageIconResId(int position) {
			return icon_res[position];
		}
	}
	private class PagerItem {

		private String title;
		private Fragment fragment;

		public PagerItem(String title, Fragment fragment) {
			this.title = title;
			this.fragment = fragment;
		}

	}




}

