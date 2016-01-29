package com.jsonhu.people_news.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jsonhu.people_news.R;
import com.jsonhu.people_news.widget.ExpandableTextView;

/**
 * Created by lugang on 2016/1/28.
 */
public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.CommentListHolder>{
    private final SparseBooleanArray mCollapsedStatus;
    private Context context;
    private final String[] sampleStrings;
    public CommentListAdapter(Context ctx){
        this.context = ctx;
        mCollapsedStatus = new SparseBooleanArray();
        sampleStrings = context.getResources().getStringArray(R.array.sampleStrings);
    }

    @Override
    public CommentListAdapter.CommentListHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view  = LayoutInflater.from(context).inflate(R.layout.item_comment_layout,parent,false);

        return new CommentListHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentListAdapter.CommentListHolder holder, int position) {

        holder.expand_text_view.setText(sampleStrings[position], mCollapsedStatus, position);

    }

    @Override
    public int getItemCount() {
        return sampleStrings.length;
    }


    class CommentListHolder extends RecyclerView.ViewHolder{

        ExpandableTextView expand_text_view;
        TextView expandable_text;
        ImageButton expand_collapse;
        public CommentListHolder(View itemView) {
            super(itemView);

            expand_text_view = (ExpandableTextView) itemView.findViewById(R.id.expand_text_view);
            expandable_text = (TextView) itemView.findViewById(R.id.expandable_text);
            expand_collapse = (ImageButton) itemView.findViewById(R.id.expand_collapse);

            expand_collapse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    expand_text_view.expandAndCollapse();
                }
            });

            expandable_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //弹出popwindow

                    Log.i("dddd","==================");

//                    showUpPopWindow(v);
                }
            });
        }
    }

    private void showUpPopWindow(View v) {
        View contentView = View.inflate(context,R.layout.comment_pop_layout,null);
        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupWidth = contentView.getMeasuredWidth();
        int popupHeight = contentView.getMeasuredHeight();
        int[] location = new int[2];

        popupWindow.setTouchable(true);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.i("mengdd", "onTouch : ");

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        v.getLocationOnScreen(location);

        popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, (location[0] + v.getWidth() / 2) - popupWidth / 2, location[1] - popupHeight);
    }
}
