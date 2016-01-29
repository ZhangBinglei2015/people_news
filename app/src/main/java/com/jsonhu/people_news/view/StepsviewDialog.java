package com.jsonhu.people_news.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.SeekBar;

import com.anton46.stepsview.StepsView;
import com.jsonhu.people_news.R;
import com.jsonhu.people_news.utills.Utils;

/**
 * Created by lugang on 2016/1/28.
 */
public class StepsviewDialog extends Dialog {

    private View mView;
    private WindowManager.LayoutParams lp;
    private final String[] labels = {"Step 1", "Step 2", "Step 3", "Step 4", "Step 5"};
    private SeekBarChangedListener seekBarChanged;
    public StepsviewDialog(Context context) {
        super(context, R.style.AnimBottom);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mView = inflater.inflate(R.layout.step_view_dialog_layout, null);
        this.setCanceledOnTouchOutside(true);
        this.setContentView(mView);

        lp = getWindow().getAttributes();
        lp.gravity = Gravity.BOTTOM;
        lp.width = Utils.getDisplaySize((Activity) context).widthPixels;
        lp.height = Utils.getDisplaySize((Activity) context).heightPixels * 1 / 3;
        getWindow().setAttributes(lp);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        initView();
    }

    public void setSeekBarChanged(SeekBarChangedListener listener ){
        this.seekBarChanged = listener;
    }

    private void initView() {
        AppCompatSeekBar seekBar = (AppCompatSeekBar) findViewById(R.id.seekbar_font_size);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser){

                    seekBarChanged.onProgressChanged(progress);

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public interface SeekBarChangedListener{
        void onProgressChanged(int progress);
    }
}
