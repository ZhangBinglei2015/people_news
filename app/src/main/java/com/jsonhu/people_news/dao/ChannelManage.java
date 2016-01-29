package com.jsonhu.people_news.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.database.SQLException;
import android.util.Log;

import com.jsonhu.people_news.db.SQLHelper;
import com.jsonhu.people_news.model.ChannelItem;


public class ChannelManage {
	public static ChannelManage channelManage;
	/**
	 * 默认的用户选择频道列表
	 * */
	public static List<ChannelItem> defaultUserChannels;
	/**
	 * 默认的其他频道列表
	 * */
	public static List<ChannelItem> defaultOtherChannels;

	public static List<ChannelItem> defaultCateGoryChannels;
	private ChannelDao channelDao;
	/** 判断数据库中是否存在用户数据 */
	private boolean userExist = false;
	static {
		defaultUserChannels = new ArrayList<ChannelItem>();
		defaultOtherChannels = new ArrayList<ChannelItem>();
		defaultCateGoryChannels = new ArrayList<>();

		defaultUserChannels.add(new ChannelItem(30, "热点", 30, 1,"闻",0));
		defaultUserChannels.add(new ChannelItem(31, "锐度", 31, 1,"评",0));
		defaultUserChannels.add(new ChannelItem(32, "问政", 32, 1,"问",0));
		defaultUserChannels.add(new ChannelItem(45, "播报", 45, 1,"听",0));
		defaultUserChannels.add(new ChannelItem(33, "版面", 33, 1,"报",0));
		defaultUserChannels.add(new ChannelItem(34, "公益", 34, 1,"帮",0));
		defaultUserChannels.add(new ChannelItem(35, "镜头", 35, 1,"图",0));
		defaultUserChannels.add(new ChannelItem(36, "影像", 36, 1,"视",0));

		defaultUserChannels.add(new ChannelItem(37, "财经", 37, 1,"财",2));
		defaultUserChannels.add(new ChannelItem(38, "社会", 38, 1,"社",2));
		defaultUserChannels.add(new ChannelItem(39, "文化", 39, 1,"文",2));
		defaultUserChannels.add(new ChannelItem(40, "教育", 40, 1,"教",2));
		defaultUserChannels.add(new ChannelItem(41, "健康", 41, 1,"健",2));



		defaultOtherChannels.add(new ChannelItem(1, "北京", 1, 0,"京",1));
		defaultOtherChannels.add(new ChannelItem(2, "上海", 2, 0,"沪",1));
		defaultOtherChannels.add(new ChannelItem(3, "广东", 3, 0, "粤",1));
		defaultOtherChannels.add(new ChannelItem(4, "内蒙古", 4, 0,"蒙",1));
		defaultOtherChannels.add(new ChannelItem(5, "山西", 5, 0, "晋",1));
		defaultOtherChannels.add(new ChannelItem(6, "宁夏", 6, 0, "宁",1));
		defaultOtherChannels.add(new ChannelItem(7, "桂林", 7, 0, "桂",1));

		defaultOtherChannels.add(new ChannelItem(8, "安徽", 1, 0, "皖",1));
		defaultOtherChannels.add(new ChannelItem(9, "河北", 2, 0, "冀",1));
		defaultOtherChannels.add(new ChannelItem(10, "浙江", 3, 0, "浙",1));
		defaultOtherChannels.add(new ChannelItem(11, "福建", 4, 0, "闽",1));
		defaultOtherChannels.add(new ChannelItem(12, "江西", 5, 0, "赣",1));
		defaultOtherChannels.add(new ChannelItem(13, "山东", 6, 0, "鲁",1));
		defaultOtherChannels.add(new ChannelItem(14, "辽宁", 7, 0, "辽",1));
		defaultOtherChannels.add(new ChannelItem(15, "黑龙江", 8, 0, "黑",1));
		defaultOtherChannels.add(new ChannelItem(16, "海南", 9, 0, "琼",1));
		defaultOtherChannels.add(new ChannelItem(17, "江西", 10, 0, "湘",1));
		defaultOtherChannels.add(new ChannelItem(18, "湖北", 11, 0, "鄂",1));



		defaultOtherChannels.add(new ChannelItem(19, "四川", 12, 0, "川",1));
		defaultOtherChannels.add(new ChannelItem(20, "云南", 13, 0, "云",1));
		defaultOtherChannels.add(new ChannelItem(21, "西藏", 14, 0, "藏",1));
		defaultOtherChannels.add(new ChannelItem(22, "陕西", 15, 0, "陕",1));
		defaultOtherChannels.add(new ChannelItem(23, "甘肃", 16, 0, "甘",1));
		defaultOtherChannels.add(new ChannelItem(24, "青海", 17, 0, "青",1));
		defaultOtherChannels.add(new ChannelItem(25, "新疆", 18, 0, "新",1));
		defaultOtherChannels.add(new ChannelItem(26, "吉林", 19, 0, "吉",1));
		defaultOtherChannels.add(new ChannelItem(27, "山西", 20, 0, "豫",1));
		defaultOtherChannels.add(new ChannelItem(28, "江苏", 21, 0, "苏",1));
		defaultOtherChannels.add(new ChannelItem(29, "重庆", 22, 0, "渝",1));


		defaultCateGoryChannels.add(new ChannelItem(41, "科技", 41, 0,"科",2));
		defaultCateGoryChannels.add(new ChannelItem(42, "军事", 42, 0,"军",2));
		defaultCateGoryChannels.add(new ChannelItem(43, "汽车", 43, 0,"车",2));
		defaultCateGoryChannels.add(new ChannelItem(44, "房产", 44, 0,"房",2));


	}

	private ChannelManage(SQLHelper paramDBHelper) throws SQLException {
		if (channelDao == null)
			channelDao = new ChannelDao(paramDBHelper.getContext());
		// NavigateItemDao(paramDBHelper.getDao(NavigateItem.class));
		return;
	}

	/**
	 * 初始化频道管理类
	 * @throws SQLException
	 */
	public static ChannelManage getManage(SQLHelper dbHelper)throws SQLException {
		if (channelManage == null)
			channelManage = new ChannelManage(dbHelper);
		return channelManage;
	}

	/**
	 * 清除所有的频道
	 */
	public void deleteAllChannel() {
		channelDao.clearFeedTable();
	}
	/**
	 * 获取其他的频道
	 * @return 数据库存在用户配置 ? 数据库内的用户选择频道 : 默认用户选择频道 ;
	 */
	public List<ChannelItem> getUserChannel() {
		Object cacheList = channelDao.listCache(SQLHelper.SELECTED + "= ?",new String[] { "1" });
		if (cacheList != null && !((List) cacheList).isEmpty()) {
			userExist = true;
			List<Map<String, String>> maplist = (List) cacheList;
			int count = maplist.size();
			List<ChannelItem> list = new ArrayList<ChannelItem>();
			for (int i = 0; i < count; i++) {
				ChannelItem navigate = new ChannelItem();
				navigate.setId(Integer.valueOf(maplist.get(i).get(SQLHelper.ID)));
				navigate.setName(maplist.get(i).get(SQLHelper.NAME));
				navigate.setUnfancy(maplist.get(i).get(SQLHelper.UNFANCY));
				navigate.setOrderId(Integer.valueOf(maplist.get(i).get(SQLHelper.ORDERID)));
				navigate.setSelected(Integer.valueOf(maplist.get(i).get(SQLHelper.SELECTED)));
				navigate.setType(Integer.valueOf(maplist.get(i).get(SQLHelper.TYPE)));
				list.add(navigate);

				Log.i("获取其他的频道",navigate.toString());
			}

			return list;
		}
		initDefaultChannel();
		return defaultUserChannels;
	}

	/**
	 * 获取其他的频道
	 * @return 数据库存在用户配置 ? 数据库内的其它频道 : 默认其它频道 ;
	 */
	public List<ChannelItem> getOtherChannel() {
		Object cacheList = channelDao.listCache(SQLHelper.SELECTED + "= ?" ,new String[] { "0" });
		List<ChannelItem> list = new ArrayList<ChannelItem>();
		if (cacheList != null && !((List) cacheList).isEmpty()){
			List<Map<String, String>> maplist = (List) cacheList;
			int count = maplist.size();
			for (int i = 0; i < count; i++) {
				ChannelItem navigate= new ChannelItem();
				navigate.setId(Integer.valueOf(maplist.get(i).get(SQLHelper.ID)));
				navigate.setName(maplist.get(i).get(SQLHelper.NAME));
				navigate.setUnfancy(maplist.get(i).get(SQLHelper.UNFANCY));
				navigate.setOrderId(Integer.valueOf(maplist.get(i).get(SQLHelper.ORDERID)));
				navigate.setSelected(Integer.valueOf(maplist.get(i).get(SQLHelper.SELECTED)));
				navigate.setType(Integer.valueOf(maplist.get(i).get(SQLHelper.TYPE)));
				list.add(navigate);
			}
			return list;
		}
		if(userExist){
			return list;
		}
		cacheList = defaultOtherChannels;
		return (List<ChannelItem>) cacheList;
	}

	/**
	 * 获取其他的频道
	 * @return 数据库存在用户配置 ? 数据库内的其它频道 : 默认其它频道 ;
	 */
	public List<ChannelItem> getCateGoryChannel() {
		Object cacheList = channelDao.listCache(SQLHelper.SELECTED + "= ?" ,new String[] { "0" });
		List<ChannelItem> list = new ArrayList<ChannelItem>();
		if (cacheList != null && !((List) cacheList).isEmpty()){
			List<Map<String, String>> maplist = (List) cacheList;
			int count = maplist.size();
			for (int i = 0; i < count; i++) {
				ChannelItem navigate= new ChannelItem();
				navigate.setId(Integer.valueOf(maplist.get(i).get(SQLHelper.ID)));
				navigate.setName(maplist.get(i).get(SQLHelper.NAME));
				navigate.setUnfancy(maplist.get(i).get(SQLHelper.UNFANCY));
				navigate.setOrderId(Integer.valueOf(maplist.get(i).get(SQLHelper.ORDERID)));
				navigate.setSelected(Integer.valueOf(maplist.get(i).get(SQLHelper.SELECTED)));
				navigate.setType(Integer.valueOf(maplist.get(i).get(SQLHelper.TYPE)));
				list.add(navigate);
			}
			return list;
		}
		if(userExist){
			return list;
		}
		cacheList = defaultCateGoryChannels;
		return (List<ChannelItem>) cacheList;
	}

	/**
	 * 保存用户频道到数据库
	 * @param userList
	 */
	public void saveUserChannel(List<ChannelItem> userList) {
		for (int i = 0; i < userList.size(); i++) {
			ChannelItem channelItem = (ChannelItem) userList.get(i);
			channelItem.setOrderId(i);
			channelItem.setSelected(Integer.valueOf(1));
			channelDao.addCache(channelItem);
		}
	}

	/**
	 * 保存其他频道到数据库
	 * @param otherList
	 */
	public void saveOtherChannel(List<ChannelItem> otherList) {
		for (int i = 0; i < otherList.size(); i++) {
			ChannelItem channelItem = (ChannelItem) otherList.get(i);
			channelItem.setOrderId(i);
			channelItem.setSelected(Integer.valueOf(0));
			channelDao.addCache(channelItem);
		}
	}

	/**
	 * 保存其他频道到数据库
	 * @param otherList
	 */
	public void saveCateGoryChannel(List<ChannelItem> otherList) {
		for (int i = 0; i < otherList.size(); i++) {
			ChannelItem channelItem = (ChannelItem) otherList.get(i);
			channelItem.setOrderId(i);
			channelItem.setSelected(Integer.valueOf(0));

			channelDao.addCache(channelItem);
		}
	}

	/**
	 * 初始化数据库内的频道数据
	 */
	public void initDefaultChannel(){
		Log.d("deleteAll", "deleteAll="+defaultUserChannels.size());
		deleteAllChannel();
		saveUserChannel(defaultUserChannels);
		saveOtherChannel(defaultOtherChannels);
	}
}
