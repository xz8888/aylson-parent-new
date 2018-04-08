package com.aylson.dc.htt.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aylson.core.easyui.EasyuiDataGridJson;
import com.aylson.core.frame.controller.BaseController;
import com.aylson.dc.htt.search.HttBalanceHisSearch;
import com.aylson.dc.htt.service.HttBalanceHisService;
import com.aylson.dc.htt.vo.HttBalanceHisVo;

/**
 * 零钱兑换记录
 * @author Minbo
 */
@Controller
@RequestMapping("/htt/balanceHis")
public class HttBalanceHisController extends BaseController {
	
	protected static final Logger logger = Logger.getLogger(HttBalanceHisController.class);

	@Autowired
	private HttBalanceHisService balanceHisService;
	
	/**
	 * 后台-首页
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/htt/admin/balanceHis/index";
	}
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(HttBalanceHisSearch balanceHisSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			balanceHisSearch.setIsPage(true);
			List<HttBalanceHisVo> list = this.balanceHisService.getList(balanceHisSearch);
			result.setTotal(this.balanceHisService.getRowCount(balanceHisSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return null;
		}
	}
}
