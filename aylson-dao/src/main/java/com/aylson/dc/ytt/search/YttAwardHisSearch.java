package com.aylson.dc.ytt.search;

import com.aylson.core.frame.search.BaseSearch;

public class YttAwardHisSearch  extends BaseSearch{

	private static final long serialVersionUID = -2219868499799737769L;
	
	//模糊查询
	private String phoneNumLike;		//手机号码

	public String getPhoneNumLike() {
		return phoneNumLike;
	}

	public void setPhoneNumLike(String phoneNumLike) {
		this.phoneNumLike = phoneNumLike;
	}
}
