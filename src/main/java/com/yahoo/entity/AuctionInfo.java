package com.yahoo.entity;

public class AuctionInfo {
	private String name = "";
	private String productURL = "";
	private String productPrice = "";
	private String remainTime = "";
	private String identify = "";
	
	/**
	 * @return the identify
	 */
	public String getIdentify() {
		return identify;
	}
	/**
	 * @param identify the identify to set
	 */
	public void setIdentify(String identify) {
		this.identify = identify;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the productURL
	 */
	public String getProductURL() {
		return productURL;
	}
	/**
	 * @param productURL the productURL to set
	 */
	public void setProductURL(String productURL) {
		this.productURL = productURL;
	}
	/**
	 * @return the productPrice
	 */
	public String getProductPrice() {
		return productPrice;
	}
	/**
	 * @param productPrice the productPrice to set
	 */
	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}
	/**
	 * @return the remainTime
	 */
	public String getRemainTime() {
		return remainTime;
	}
	/**
	 * @param remainTime the remainTime to set
	 */
	public void setRemainTime(String remainTime) {
		this.remainTime = remainTime;
	}
}
