package com.jsonhu.people_news.model;

import java.io.Serializable;

/**
 * ITEM的对应可序化队列属性
 *  */
public class ChannelItem implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -6465237897027410019L;
	/**
	 * 栏目对应ID
	 *  */
	public Integer id;
	/**
	 * 栏目对应NAME
	 *  */
	public String name;
	/**
	 * 栏目在整体中的排序顺序  rank
	 *  */
	public Integer orderId;
	/**
	 * 栏目是否选中
	 *  */
	public Integer selected;

	public String unfancy;

	public int type;

	public String getUnfancy() {
		return unfancy;
	}

	public void setUnfancy(String unfancy) {
		this.unfancy = unfancy;
	}

	public ChannelItem() {
	}

	public ChannelItem(int id, String name, int orderId,int selected,String unfancy,int type) {
		this.id = Integer.valueOf(id);
		this.name = name;
		this.orderId = Integer.valueOf(orderId);
		this.selected = Integer.valueOf(selected);
		this.unfancy = unfancy;
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getId() {
		return this.id.intValue();
	}

	public String getName() {
		return this.name;
	}

	public int getOrderId() {
		return this.orderId.intValue();
	}

	public Integer getSelected() {
		return this.selected;
	}

	public void setId(int paramInt) {
		this.id = Integer.valueOf(paramInt);
	}

	public void setName(String paramString) {
		this.name = paramString;
	}

	public void setOrderId(int paramInt) {
		this.orderId = Integer.valueOf(paramInt);
	}

	public void setSelected(Integer paramInteger) {
		this.selected = paramInteger;
	}

	@Override
	public String toString() {
		return "ChannelItem{" +
				"id=" + id +
				", name='" + name + '\'' +
				", orderId=" + orderId +
				", selected=" + selected +
				", unfancy='" + unfancy + '\'' +
				", type=" + type +
				'}';
	}
}