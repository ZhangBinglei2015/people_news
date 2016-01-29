package com.jsonhu.people_news.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsonhu.people_news.R;
import com.jsonhu.people_news.model.ChannelItem;

import org.w3c.dom.Text;


public class ChannelAdapter extends BaseAdapter {
    /** TAG*/
    private final static String TAG = "DragAdapter";
    /** 是否显示底部的ITEM */
    private boolean isItemShow = false;
    private Context context;
    /** 控制的postion */
    private int holdPosition;
    /** 是否改变 */
    private boolean isChanged = false;
    /** 是否可见 */
    boolean isVisible = true;
    /** 可以拖动的列表（即用户选择的频道列表） */
    public List<ChannelItem> channelList;
    /** 要删除的position */
    public int remove_position = -1;
    private boolean delView = false;

    public ChannelAdapter(Context context, List<ChannelItem> channelList) {
        this.context = context;
        this.channelList = channelList;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return channelList == null ? 0 : channelList.size();
    }

    @Override
    public ChannelItem getItem(int position) {
        // TODO Auto-generated method stub
        if (channelList != null && channelList.size() != 0) {
            return channelList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.navigate_cotent, null);
        TextView item_text = (TextView) convertView.findViewById(R.id.navigate_content_right);
        TextView content_left = (TextView) convertView.findViewById(R.id.navigate_content_left);
//            ImageView icon_new = (ImageView) convertView.findViewById(R.id.icon_new);


        /**/

        ChannelItem channel = getItem(position);
        content_left.setText(channel.getUnfancy());
        item_text.setText(channel.getName());
      if ((position == 0) || (position == 1)){
//			item_text.setTextColor(context.getResources().getColor(R.color.black));
            content_left.setEnabled(false);
        }
        if (isChanged && (position == holdPosition) && !isItemShow) {
            content_left.setText("");
            content_left.setSelected(true);
            content_left.setEnabled(true);
            isChanged = false;
        }
        if (!isVisible && (position == -1 + channelList.size())) {
            content_left.setText("");
            content_left.setSelected(true);
            content_left.setEnabled(true);
        }
        if(remove_position == position){
            content_left.setText("");
        }
        Log.i("convertView","list.size()="+channelList.size()+",channel="+channel.toString());
        if(delView){
//            icon_new.setVisibility(View.VISIBLE);
        }else{
//            icon_new.setVisibility(View.GONE);
        }
        return convertView;
    }
    public class ViewHolder{
        TextView item_text,content_left;
    }
    public void showDel(boolean b) {
        this.delView = b;
        this.notifyDataSetChanged();
    }
    /** 添加频道列表 */
    public void addItem(ChannelItem channel) {
        channelList.add(channel);
        notifyDataSetChanged();
    }

    /** 拖动变更频道排序 */
    public void exchange(int dragPostion, int dropPostion) {
        holdPosition = dropPostion;
        ChannelItem dragItem = getItem(dragPostion);
        Log.d(TAG, "startPostion=" + dragPostion + ";endPosition=" + dropPostion);
        if (dragPostion < dropPostion) {
            channelList.add(dropPostion + 1, dragItem);
            channelList.remove(dragPostion);
        } else {
            channelList.add(dropPostion, dragItem);
            channelList.remove(dragPostion + 1);
        }
        isChanged = true;
        notifyDataSetChanged();
    }

    /** 获取频道列表 */
    public List<ChannelItem> getChannnelLst() {
        return channelList;
    }
    public void updateItem(int position,int selected){
        channelList.get(position).setSelected(selected);
    }
    /** 设置删除的position */
    public void setRemove(int position) {
        remove_position = position;
        notifyDataSetChanged();
    }

    /** 删除频道列表 */
    public void remove() {
        channelList.remove(remove_position);
        remove_position = -1;
        notifyDataSetChanged();
    }
    private void setFonts(final TextView textView){
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                textView.setTypeface((Typeface)msg.obj);
            }
        };
        new Thread(){
            @Override
            public void run() {
                super.run();
                Typeface fontFace = Typeface.createFromAsset(context.getAssets(),
                        "fonts/FZCSK.TTF");
                Message message = Message.obtain();
                message.obj = fontFace;
                handler.dispatchMessage(message);
            }
        }.start();
    }
    /** 设置频道列表 */
    public void setListDate(List<ChannelItem> list) {
        channelList = list;
    }

    /** 获取是否可见 */
    public boolean isVisible() {
        return isVisible;
    }

    /** 设置是否可见 */
    public void setVisible(boolean visible) {
        isVisible = visible;
    }
    /** 显示放下的ITEM */
    public void setShowDropItem(boolean show) {
        isItemShow = show;
    }
}