package com.jsonhu.people_news.utills;

import android.os.CountDownTimer;
import android.widget.Button;

/* 定义一个倒计时的内部类 */
public class TimeCount extends CountDownTimer {
	public static final long mMillisInFuture=120000;//120秒
	public static final long mCountDownInterval=1000;
	private Button mButton;
	
	//自定义时间
	public TimeCount(long millisInFuture, long countDownInterval,Button button) {
		super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
		this.mButton=button;
	}
	
	//默认时间
	public TimeCount(Button button){
		super(mMillisInFuture, mCountDownInterval);// 参数依次为总时长,和计时的时间间隔
		this.mButton=button;
	}

	@Override
	public void onFinish() {// 计时完毕时触发
		mButton.setText("重新获取验证码");
		mButton.setClickable(true);
	}

	@Override
	public void onTick(long millisUntilFinished) {// 计时过程显示
		mButton.setClickable(false);
		mButton.setText(millisUntilFinished / 1000 + "秒后重新获取");
	}
}