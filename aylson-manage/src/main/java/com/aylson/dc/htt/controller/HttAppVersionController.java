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
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.dc.htt.search.HttAppVersionSearch;
import com.aylson.dc.htt.service.HttAppVersionService;
import com.aylson.dc.htt.vo.HttAppVersionVo;
import com.aylson.dc.sys.common.SessionInfo;
import com.aylson.utils.DateUtil2;
import com.aylson.utils.UUIDUtils;

/**
 * APK渠道版本配置
 * @author Minbo
 */
@Controller
@RequestMapping("/htt/httAppVersion")
public class HttAppVersionController extends BaseController {
	
	protected static final Logger logger = Logger.getLogger(HttAppVersionController.class);

	@Autowired
	private HttAppVersionService httAppVersionService;
	
	/**
	 * 后台-首页
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/htt/admin/httAppVersion/index";
	}
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(HttAppVersionSearch httAppVersionSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			httAppVersionSearch.setIsPage(true);
			List<HttAppVersionVo> list = this.httAppVersionService.getList(httAppVersionSearch);
			result.setTotal(this.httAppVersionService.getRowCount(httAppVersionSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 后台-添加页面
	 * @param httAppVersionVo
	 * @return
	 */
	@RequestMapping(value = "/admin/toAdd", method = RequestMethod.GET)
	public String toAdd(HttAppVersionVo httAppVersionVo) {
		this.request.setAttribute("httAppVersionVo", httAppVersionVo);
		return "/jsp/htt/admin/httAppVersion/add";
	}
	
	/**
	 * 后台-添加保存
	 * @param httAppVersionVo
	 * @return
	 */
	@RequestMapping(value = "/admin/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(HttAppVersionVo httAppVersionVo) {
		Result result = new Result();
		try{
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
			httAppVersionVo.setId(UUIDUtils.create());
			String cTime = DateUtil2.getCurrentLongDateTime();
			httAppVersionVo.setCreatedBy(sessionInfo.getUser().getUserName() + "/" + sessionInfo.getUser().getRoleName());
			httAppVersionVo.setCreateDate(cTime);
			httAppVersionVo.setUpdateDate(cTime);
			Boolean flag = this.httAppVersionService.add(httAppVersionVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 后台-编辑页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/admin/toEdit", method = RequestMethod.GET)
	public String toEdit(String id) {
		if(id != null){
			HttAppVersionVo httAppVersionVo = this.httAppVersionService.getById(id);
			this.request.setAttribute("httAppVersionVo", httAppVersionVo);
		}
		return "/jsp/htt/admin/httAppVersion/add";
	}
	
	/**
	 * 后台-编辑保存
	 * @param httAppVersionVo
	 * @return
	 */
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(HttAppVersionVo httAppVersionVo) {
		Result result = new Result();
		try {
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
			httAppVersionVo.setUpdatedBy(sessionInfo.getUser().getUserName() + "/" + sessionInfo.getUser().getRoleName());
			httAppVersionVo.setUpdateDate(DateUtil2.getCurrentLongDateTime());
			Boolean flag = this.httAppVersionService.edit(httAppVersionVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 根据id删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/admin/deleteById", method = RequestMethod.POST)
	@ResponseBody
	public Result deleteById(String id) {
		Result result = new Result();
		try{
			Boolean flag = this.httAppVersionService.deleteById(id);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "删除成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "删除失败");
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
}
