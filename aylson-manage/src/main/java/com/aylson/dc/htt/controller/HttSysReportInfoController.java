package com.aylson.dc.htt.controller;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aylson.core.easyui.EasyuiDataGridJson;
import com.aylson.core.frame.controller.BaseController;
import com.aylson.dc.htt.search.HttSysReportInfoSearch;
import com.aylson.dc.htt.service.HttSysReportInfoService;
import com.aylson.dc.htt.vo.HttSysReportInfoVo;
import com.aylson.utils.DateUtil2;
import com.aylson.utils.StrUtil;

import net.sf.json.JSONObject;

/**
 * 统计报表数据查询
 * @author Minbo
 */
@Controller
@RequestMapping("/htt/httSysReportInfo")
public class HttSysReportInfoController extends BaseController {
	
	protected static final Logger logger = Logger.getLogger(HttSysReportInfoController.class);
	
	@Autowired
	private HttSysReportInfoService httSysReportInfoService;

	/**
	 * 后台-首页
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/htt/admin/httSysReportInfo/index";
	}
	
	/**
	 * 获取详细列表数据
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(HttSysReportInfoSearch httSysReportInfoSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			httSysReportInfoSearch.setIsPage(true);
			List<HttSysReportInfoList> list = new ArrayList<>();
			Map<String, Object> params = new HashMap<>();
			params.put("currentTime", httSysReportInfoSearch.getCurrentTime());
			for (int i = 0; i < httSysReportInfoSearch.getNum(); i++) {
				int j = 0;
				if(i > 0) {
					j = 0 - i;
				}
				HttSysReportInfoList httSysReportInfo = new HttSysReportInfoList();
				httSysReportInfo.setDateStr(DateUtil2.getCurrentDateByNum(j));;
				httSysReportInfo.setNewUserOfDay(this.getResult(j, 101));
				httSysReportInfo.setUserGoldOfDay(this.getResult(j, 102));
				httSysReportInfo.setUserBalanceOfDay(this.getResult(j, 103));
				httSysReportInfo.setUserWithdrawOfDay(this.getResult(j, 104));
				httSysReportInfo.setUserReadOfDay(this.getResult(j, 105));
				list.add(httSysReportInfo);
			}
			result.setTotal(Long.valueOf(list.size()));
			result.setRows(list);
			return result;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 获取顶部三栏数据
	 * @return list
	 */
	@RequestMapping(value = "/admin/listTop", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject listTop(){
		try{
			Map<String, Object> map = new HashMap<>();
			//今日用户金币余额
			map.put("todayGold", this.getResult(0, 102));
			//昨日用户金币余额
			map.put("yesterdayGold", this.getResult(-1, 102));
			//用户总金币余额
			map.put("allIncome", this.getResult(0, 106));
			//用户总零钱余额
			map.put("allBalance", this.getResult(0, 107));
			JSONObject json = JSONObject.fromObject(map);
			return json;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 获取折线图数据
	 * @return list
	 */
	@RequestMapping(value = "/admin/listMap", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject listMap(int num){
		try{
			Map<String, Object> map = new HashMap<>();
			//横坐标，统计七天的数据，含今天
			List<Object> list = new ArrayList<>();
			for (int i = 0; i < num; i++) {
				int j = 0;
				if(i > 0) {
					j = 0 - i;
				}
				list.add(DateUtil2.getCurrentDateByNum(j));
			}
			Collections.reverse(list);
			map.put("categories", list);
			
			//新增用户数
			list = new ArrayList<>();
			for (int i = 0; i < num; i++) {
				int j = 0;
				if(i > 0) {
					j = 0 - i;
				}
				String count = StrUtil.null2Str(this.getResult(j, 101));
				list.add(count.equals("")?"0":count);
			}
			Collections.reverse(list);
			map.put("value1", list);
			
			//用户金币余额
			list = new ArrayList<>();
			for (int i = 0; i < num; i++) {
				int j = 0;
				if(i > 0) {
					j = 0 - i;
				}
				String count = StrUtil.null2Str(this.getResult(j, 102));
				list.add(count.equals("")?"0":count);
			}
			Collections.reverse(list);
			map.put("value2", list);
			
			//用户零钱金额
			list = new ArrayList<>();
			for (int i = 0; i < num; i++) {
				int j = 0;
				if(i > 0) {
					j = 0 - i;
				}
				String count = StrUtil.null2Str(this.getResult(j, 103));
				list.add(count.equals("")?"0.0":count);
			}
			Collections.reverse(list);
			map.put("value3", list);
			
			//用户提现金额
			list = new ArrayList<>();
			for (int i = 0; i < num; i++) {
				int j = 0;
				if(i > 0) {
					j = 0 - i;
				}
				String count = StrUtil.null2Str(this.getResult(j, 104));
				list.add(count.equals("")?"0.0":count);
			}
			Collections.reverse(list);
			map.put("value4", list);
			
			//用户浏览新闻数
			list = new ArrayList<>();
			for (int i = 0; i < num; i++) {
				int j = 0;
				if(i > 0) {
					j = 0 - i;
				}
				String count = StrUtil.null2Str(this.getResult(j, 105));
				list.add(count.equals("")?"0":count);
			}
			Collections.reverse(list);
			map.put("value5", list);
			
			JSONObject json = JSONObject.fromObject(map);
			return json;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * @param day 0-6
	 * @param type 101=当日新增用户数, 102=用户金币余额, 103=用户零钱金额, 104=用户提现金额, 105=用户浏览新闻数, 106=用户总金币余额, 107=用户总零钱余额
	 * @return
	 */
	private String getResult(int day, int type) {
		Map<String, Object> params = new HashMap<>();
		params.put("currentTime", DateUtil2.getCurrentDateByNum(day));
		//格式化，保留三位小数，四舍五入
		DecimalFormat dFormat = new DecimalFormat("#0.000");
		HttSysReportInfoVo httSysReportInfoVo = new HttSysReportInfoVo();
		String result = "";
		switch (type) {
			case 101:
				result = this.httSysReportInfoService.selectNewUserOfDay(params).getValue();
				return StrUtil.null2Str(result).equals("")?"0":result;
			case 102:
				result = this.httSysReportInfoService.selectUserGoldOfDay(params).getValue();
				return StrUtil.null2Str(result).equals("")?"0":result;	
			case 103:
				httSysReportInfoVo = this.httSysReportInfoService.selectUserBalanceOfDay(params);
				if(!StrUtil.null2Str(httSysReportInfoVo.getValue()).equals("")) {
					httSysReportInfoVo.setValue(dFormat.format(Double.valueOf(httSysReportInfoVo.getValue())));
				}else {
					httSysReportInfoVo.setValue("0.0");
				}
				return httSysReportInfoVo.getValue();
			case 104:
				httSysReportInfoVo = this.httSysReportInfoService.selectUserWithdrawOfDay(params);
				if(!StrUtil.null2Str(httSysReportInfoVo.getValue()).equals("")) {
					httSysReportInfoVo.setValue(dFormat.format(Double.valueOf(httSysReportInfoVo.getValue())));
				}else {
					httSysReportInfoVo.setValue("0.0");
				}
				return httSysReportInfoVo.getValue();
			case 105:
				result = this.httSysReportInfoService.selectUserAllRead(params).getValue();
				return StrUtil.null2Str(result).equals("")?"0":result;
			case 106:
				result = this.httSysReportInfoService.selectAllGold().getValue();
				return StrUtil.null2Str(result).equals("")?"0":result;
			case 107:
				httSysReportInfoVo = this.httSysReportInfoService.selectAllBalance();
				if(!StrUtil.null2Str(httSysReportInfoVo.getValue()).equals("")) {
					httSysReportInfoVo.setValue(dFormat.format(Double.valueOf(httSysReportInfoVo.getValue())));
				}else {
					httSysReportInfoVo.setValue("0.0");
				}
				return httSysReportInfoVo.getValue();
			default:
				throw new RuntimeException("错误类型，请检查。day=" + day + ", type=" + type);
		}
	}
	
	public class HttSysReportInfoList implements Serializable{
		private static final long serialVersionUID = -79583709832163829L;
		
		private String dateStr;
		private String newUserOfDay;
		private String userGoldOfDay;
		private String userBalanceOfDay;
		private String userWithdrawOfDay;
		private String userReadOfDay;
		
		public String getDateStr() {
			return dateStr;
		}
		public void setDateStr(String dateStr) {
			this.dateStr = dateStr;
		}
		public String getNewUserOfDay() {
			return newUserOfDay;
		}
		public void setNewUserOfDay(String newUserOfDay) {
			this.newUserOfDay = newUserOfDay;
		}
		public String getUserGoldOfDay() {
			return userGoldOfDay;
		}
		public void setUserGoldOfDay(String userGoldOfDay) {
			this.userGoldOfDay = userGoldOfDay;
		}
		public String getUserBalanceOfDay() {
			return userBalanceOfDay;
		}
		public void setUserBalanceOfDay(String userBalanceOfDay) {
			this.userBalanceOfDay = userBalanceOfDay;
		}
		public String getUserWithdrawOfDay() {
			return userWithdrawOfDay;
		}
		public void setUserWithdrawOfDay(String userWithdrawOfDay) {
			this.userWithdrawOfDay = userWithdrawOfDay;
		}
		public String getUserReadOfDay() {
			return userReadOfDay;
		}
		public void setUserReadOfDay(String userReadOfDay) {
			this.userReadOfDay = userReadOfDay;
		}
	}
}
