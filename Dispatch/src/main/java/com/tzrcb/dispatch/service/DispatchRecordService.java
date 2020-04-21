/**
 * File Name: DispatchRecordService.java
 * Date: 2020-04-20 08:43:43
 */
package com.tzrcb.dispatch.service;

import com.jfinal.plugin.activerecord.Page;
import com.tzrcb.dispatch.dto.CommonSearchDTO;
import com.tzrcb.dispatch.model.table.dispatch.DispatchRecord;
import me.belucky.easytool.util.StringUtils;

/**
 * Description: 服务分发记录表服务类
 * @author shenzulun
 * @date 2020-04-20
 * @version 1.0
 */
public class DispatchRecordService {
	
	private DispatchRecord dao = new DispatchRecord().dao();
	
	/**
	 * 保存报文的交易记录
	 * @param DispatchRecord
	 */
	public void save(DispatchRecord DispatchRecord) {
		DispatchRecord.save();
	}
	
	/**
	 * 更新
	 * @param DispatchRecord
	 */
	public void update(DispatchRecord DispatchRecord) {
		DispatchRecord.update();
	}
	
	/**
	 * 分页查询，默认倒序
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<DispatchRecord> paginate(int pageNumber, int pageSize) {
		return dao.paginate(pageNumber, pageSize, "select *", "from T_DISPATCH_RECORD order by id desc");
	}
	
	/**
	 * 分页查询
	 * @param commonSearchDTO
	 * @return
	 */
	public Page<DispatchRecord> paginate(CommonSearchDTO commonSearchDTO) {
		Page<DispatchRecord> result = null;
		int pageNumber = commonSearchDTO.getPageNumber();
		int pageSize = commonSearchDTO.getPageSize();
		String orderBy = commonSearchDTO.getOrderBy();
		String orderDir = commonSearchDTO.getOrderDir();
		if(StringUtils.isBlank(orderBy)) {
			orderBy = "id";
			orderDir = "desc";
		}
		if(StringUtils.isBlank(orderDir)) {
			orderDir = "desc";
		}
		String fromSql = "from T_DISPATCH_RECORD order by " + orderBy + " " + orderDir;
		String queryValue = commonSearchDTO.getQueryValue();
		if(StringUtils.isBlank(queryValue)) {
			result = dao.paginate(pageNumber, pageSize, "select *", fromSql);
		}else {
			fromSql = new StringBuffer("from T_DISPATCH_RECORD where ")
							.append(" trans_no like ? or ")
							.append(" serno like ? or ")
							.append(" code like ? or ")
							.append(" message like ? or ")
							.append(" request_msg like ? or ")
							.append(" response_msg like ?  ")
							.append(" order by ")
							.append(orderBy)
							.append(" ")
							.append(orderDir).toString();
			String condValue = "%" + queryValue + "%";
			result = dao.paginate(pageNumber, pageSize, "select *", fromSql, condValue, condValue, condValue, condValue, condValue, condValue);
		}
		return result;
	}
	
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 */
	public DispatchRecord findById(int id) {
		return dao.findById(id);
	}
	
	/**
	 * 根据ID删除
	 * @param id
	 */
	public void deleteById(int id) {
		dao.deleteById(id);
	}
	
	/**
	 * 查询交易记录
	 * @param source
	 * @param serno
	 * @return
	 */
	public DispatchRecord queryDispatchRecordBySerno(String source, String serno) {
		return dao.findFirst("select * from T_DISPATCH_RECORD where source=? and serno=? and code='0000' order by id desc", source, serno);
	}
}
