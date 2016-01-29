package com.jsonhu.people_news.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsonhu.people_news.R;

/**
 * 标题导航栏 actionbar
 * 
 * @author lanyj
 * 
 */
public class TopBarView extends LinearLayout {

	private LinearLayout mTopBack;
	public TextView mTvTitle;
	public TextView mTvRight;
	private Activity mActivity;

	public TopBarView(Context context) {
		super(context);
	}

	public TopBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.top_bar_view, this, true);
		mTopBack = (LinearLayout) this.findViewById(R.id.top_back_btn);
		mTvTitle = (TextView) this.findViewById(R.id.top_title);
		mTvRight = (TextView) this.findViewById(R.id.top_right_tv);
		mTopBack.setOnClickListener(onClickListener);
		mTvRight.setOnClickListener(onClickListener);
	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.top_back_btn:
						mActivity.finish();
				break;
			case R.id.top_right_tv:

				break;
			}
		}
	};

	public void setActivity(Activity activity) {
		this.mActivity = activity;
	}

	public void setTitle(String title) {
		mTvTitle.setText(title);
	}

	public void setRightText(String text) {
		mTvRight.setText(text);
	}

	public void setRightVisible(Boolean type) {
		if (type == true) {
			mTvRight.setVisibility(VISIBLE);

		} else {

			mTvRight.setVisibility(GONE);
		}
	}

}
