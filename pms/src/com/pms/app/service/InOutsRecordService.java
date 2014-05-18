package com.pms.app.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pms.app.dao.InsRecordDao;
import com.pms.app.dao.InsRecordDetailDao;
import com.pms.app.dao.OutsRecordDao;
import com.pms.app.dao.OutsRecordDetailDao;
import com.pms.app.dao.SupervisionCustomerDao;
import com.pms.app.entity.Delegator;
import com.pms.app.entity.InsRecord;
import com.pms.app.entity.InsRecordDetail;
import com.pms.app.entity.OutsRecord;
import com.pms.app.entity.OutsRecordDetail;
import com.pms.app.entity.PledgePurity;
import com.pms.app.entity.SupervisionCustomer;
import com.pms.app.entity.vo.InOutsRecord;
import com.pms.app.util.DateUtils;

@Service
public class InOutsRecordService {

	@Autowired InsRecordDetailDao insRecordDetailDao;
	@Autowired InsRecordDao insRecordDao;
	@Autowired OutsRecordDetailDao outsRecordDetailDao;
	@Autowired OutsRecordDao outsRecordDao;
	@Autowired SupervisionCustomerDao supervisionCustomerDao;
//查询所有出库入库单，并根据监管客户进行合并汇总。	（by：cyy）
	public Map<SupervisionCustomer, List<InOutsRecord>> queryByDelegatorAndSuperCustomerAndDateBetween(String delegatorId, String supervisionCustomerId, Date beginDate, Date endDate){
		Map<SupervisionCustomer, List<InOutsRecord>> inoutsRecordMap = new HashMap<SupervisionCustomer, List<InOutsRecord>>();
		//根据监管客户id查询
		if(StringUtils.hasText(supervisionCustomerId)) {
			SupervisionCustomer supervisionCustomer = supervisionCustomerDao.findOne(supervisionCustomerId);
			List<InOutsRecord> inOutsRecordList = new ArrayList<InOutsRecord>();
			//查询入库记录
			//List<InsRecordDetail> insRecordDetails = insRecordDetailDao.findAll(getInsSpec(delegatorId, supervisionCustomerId, beginDate, endDate));
			List<InsRecord> insRecords = insRecordDao.findAll(getInssSpec(delegatorId, supervisionCustomerId, beginDate, endDate));
			for (InsRecord insRecord : insRecords) {
				InOutsRecord inOutsRecord =new InOutsRecord();
				inOutsRecord.setMethod("入库");
				inOutsRecord.setDate(insRecord.getDate());
				inOutsRecord.setSumWeight(insRecord.getSumWeight());
				inOutsRecord.setSpecWeight(insRecord.getId());//存储入库记录id
				inOutsRecord.setPledgePurity(insRecord.getAttach());//存储入库记录扫描文件路径
				
				inOutsRecordList.add(inOutsRecord);			
			}
			//查询出库记录
			//List<OutsRecordDetail> outsRecordDetails = outsRecordDetailDao.findAll(getOutsSpec(delegatorId, supervisionCustomerId, beginDate, endDate));
			List<OutsRecord> outsRecords = outsRecordDao.findAll(getOutssSpec(delegatorId, supervisionCustomerId, beginDate, endDate));
			
			for (OutsRecord outsRecord : outsRecords) {
				InOutsRecord inOutsRecord =new InOutsRecord();
				inOutsRecord.setMethod("出库");
				inOutsRecord.setDate(outsRecord.getDate());
				inOutsRecord.setSumWeight(outsRecord.getSumWeight());
				inOutsRecord.setSpecWeight(outsRecord.getId());//存储入库记录id
				inOutsRecord.setPledgePurity(outsRecord.getAttach());//存储入库记录扫描文件路径
				
				inOutsRecordList.add(inOutsRecord);	
			}
			Collections.sort(inOutsRecordList);
			if(!inOutsRecordList.isEmpty())
				inoutsRecordMap.put(supervisionCustomer, inOutsRecordList);
			
		}else{
			List<SupervisionCustomer> supervisionCustomerList = supervisionCustomerDao.findListByDelegatorId(delegatorId);
			for (SupervisionCustomer supervisionCustomer : supervisionCustomerList) {
				
				List<InOutsRecord> inOutsRecordList = new ArrayList<InOutsRecord>();				
				String scId = supervisionCustomer.getId();
				//入库查询
				List<InsRecord> insRecords = insRecordDao.findAll(getInssSpec(scId, beginDate, endDate));				
				for (InsRecord insRecord : insRecords) {
					InOutsRecord inOutsRecord =new InOutsRecord();
					inOutsRecord.setMethod("入库");
					inOutsRecord.setDate(insRecord.getDate());
					inOutsRecord.setSumWeight(insRecord.getSumWeight());
					inOutsRecord.setSpecWeight(insRecord.getId());//存储入库记录id
					inOutsRecord.setPledgePurity(insRecord.getAttach());//存储入库记录扫描文件路径
					
					inOutsRecordList.add(inOutsRecord);							
				}
				//出库查询
				List<OutsRecord> outsRecords = outsRecordDao.findAll(getOutssSpec(scId, beginDate, endDate));
				for (OutsRecord outsRecord : outsRecords) {
					InOutsRecord inOutsRecord =new InOutsRecord();
					inOutsRecord.setMethod("出库");
					inOutsRecord.setDate(outsRecord.getDate());
					inOutsRecord.setSumWeight(outsRecord.getSumWeight());
					inOutsRecord.setSpecWeight(outsRecord.getId());//存储入库记录id
					inOutsRecord.setPledgePurity(outsRecord.getAttach());//存储入库记录扫描文件路径
					
					inOutsRecordList.add(inOutsRecord);	
				}
				Collections.sort(inOutsRecordList);
				if(!inOutsRecordList.isEmpty())
					inoutsRecordMap.put(supervisionCustomer, inOutsRecordList);
			}
			
		}
		return inoutsRecordMap;
	}
	//查询器构造：入库——根据监管客户id不为空
	public Specification<InsRecord> getInssSpec(final String delegatorId, final String supervisionCustomerId, final Date beginDate, final Date endDate) {
		Specification<InsRecord> specificationIns = new Specification<InsRecord>() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public Predicate toPredicate(Root<InsRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (StringUtils.hasText(delegatorId)) {
					Path expression = root.get("delegator");
					expression = expression.get("id");
					predicates.add(cb.equal(expression, delegatorId));
				}
				if (StringUtils.hasText(supervisionCustomerId)) {
					Path expression = root.get("supervisionCustomer");
					expression = expression.get("id");
					predicates.add(cb.equal(expression, supervisionCustomerId));
				}
				if (!StringUtils.isEmpty(beginDate)) {
					Path expression = root.get("date");
					predicates.add(cb.greaterThanOrEqualTo(expression, DateUtils.dateToDayBegin(beginDate)));
				}
				if (!StringUtils.isEmpty(endDate)) {
					Path expression = root.get("date");
					predicates.add(cb.lessThanOrEqualTo(expression, DateUtils.dateToDayEnd(endDate)));
				}
				query.orderBy(cb.desc(root.get("date")));
				if (predicates.size() > 0) {
					return cb.and(predicates.toArray(new Predicate[predicates.size()]));
				}
				return cb.conjunction();
			}
		};
		return specificationIns;
	}	
	//查询器构造： 出库——根据监管客户id不为空
	public Specification<OutsRecord> getOutssSpec(final String delegatorId, final String supervisionCustomerId, final Date beginDate, final Date endDate) {
		Specification<OutsRecord> specificationIns = new Specification<OutsRecord>() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public Predicate toPredicate(Root<OutsRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (StringUtils.hasText(delegatorId)) {
					Path expression = root.get("delegator");
					expression = expression.get("id");
					predicates.add(cb.equal(expression, delegatorId));
				}
				if (StringUtils.hasText(supervisionCustomerId)) {
					Path expression = root.get("supervisionCustomer");
					expression = expression.get("id");
					predicates.add(cb.equal(expression, supervisionCustomerId));
				}
				if (!StringUtils.isEmpty(beginDate)) {
					Path expression = root.get("date");
					predicates.add(cb.greaterThanOrEqualTo(expression, DateUtils.dateToDayBegin(beginDate)));
				}
				if (!StringUtils.isEmpty(endDate)) {
					Path expression = root.get("date");
					predicates.add(cb.lessThanOrEqualTo(expression, DateUtils.dateToDayEnd(endDate)));
				}
				query.orderBy(cb.desc(root.get("date")));
				if (predicates.size() > 0) {
					return cb.and(predicates.toArray(new Predicate[predicates.size()]));
				}
				return cb.conjunction();
			}
		};
		return specificationIns;
	}
	//查询器：入库——没有选择监管客户
	public Specification<InsRecord> getInssSpec(final String supervisionCustomerId, final Date beginDate, final Date endDate) {
		Specification<InsRecord> specificationIns = new Specification<InsRecord>() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public Predicate toPredicate(Root<InsRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (StringUtils.hasText(supervisionCustomerId)) {
					Path expression = root.get("supervisionCustomer");
					expression = expression.get("id");
					predicates.add(cb.equal(expression, supervisionCustomerId));
				}
				if (!StringUtils.isEmpty(beginDate)) {
					Path expression = root.get("date");
					predicates.add(cb.greaterThanOrEqualTo(expression, DateUtils.dateToDayBegin(beginDate)));
				}
				if (!StringUtils.isEmpty(endDate)) {
					Path expression = root.get("date");
					predicates.add(cb.lessThanOrEqualTo(expression, DateUtils.dateToDayEnd(endDate)));
				}
				query.orderBy(cb.desc(root.get("date")));
				if (predicates.size() > 0) {
					return cb.and(predicates.toArray(new Predicate[predicates.size()]));
				}
				return cb.conjunction();
			}
		};
		return specificationIns;
	}
	//查询器：出库——没有选择监管客户
	public Specification<OutsRecord> getOutssSpec(final String supervisionCustomerId, final Date beginDate, final Date endDate) {
		Specification<OutsRecord> specificationIns = new Specification<OutsRecord>() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public Predicate toPredicate(Root<OutsRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (StringUtils.hasText(supervisionCustomerId)) {
					Path expression = root.get("supervisionCustomer");
					expression = expression.get("id");
					predicates.add(cb.equal(expression, supervisionCustomerId));
				}
				if (!StringUtils.isEmpty(beginDate)) {
					Path expression = root.get("date");
					predicates.add(cb.greaterThanOrEqualTo(expression, DateUtils.dateToDayBegin(beginDate)));
				}
				if (!StringUtils.isEmpty(endDate)) {
					Path expression = root.get("date");
					predicates.add(cb.lessThanOrEqualTo(expression, DateUtils.dateToDayEnd(endDate)));
				}
				query.orderBy(cb.desc(root.get("date")));
				if (predicates.size() > 0) {
					return cb.and(predicates.toArray(new Predicate[predicates.size()]));
				}
				return cb.conjunction();
			}
		};
		return specificationIns;
	}

//查询出所有成为类型为“type=1”的出入库明细，并根据监管客户进行合并汇总，按照款式分条显示。 （by:wzz）
	public Map<SupervisionCustomer, List<InOutsRecord>> queryByDelegatorAndDateBetween(String delegatorId, String supervisionCustomerId, Date beginDate, Date endDate) {
		Map<SupervisionCustomer, List<InOutsRecord>> inoutsRecordMap = new HashMap<SupervisionCustomer, List<InOutsRecord>>();
		if(StringUtils.hasText(supervisionCustomerId)) {
			SupervisionCustomer supervisionCustomer = supervisionCustomerDao.findOne(supervisionCustomerId);
			List<InOutsRecord> inOutsRecordList = new ArrayList<InOutsRecord>();
			List<InsRecordDetail> insRecordDetails = insRecordDetailDao.findAll(getInsSpec(delegatorId, supervisionCustomerId, beginDate, endDate));
			for (InsRecordDetail insRecordDetail : insRecordDetails) {
				PledgePurity pledgePurity = insRecordDetail.getPledgePurity();
				if(pledgePurity != null) {
					if(pledgePurity.getType() == 1) 
						inOutsRecordList.add(new InOutsRecord(insRecordDetail));
				}
			}
			List<OutsRecordDetail> outsRecordDetails = outsRecordDetailDao.findAll(getOutsSpec(delegatorId, supervisionCustomerId, beginDate, endDate));
			for (OutsRecordDetail outsRecordDetail : outsRecordDetails) {
				PledgePurity pledgePurity = outsRecordDetail.getPledgePurity();
				if(pledgePurity != null) {
					if(pledgePurity.getType() == 1) 
						inOutsRecordList.add(new InOutsRecord(outsRecordDetail));
				}
			}
			Collections.sort(inOutsRecordList);
			if(!inOutsRecordList.isEmpty())
				inoutsRecordMap.put(supervisionCustomer, inOutsRecordList);
		} else {
			List<SupervisionCustomer> supervisionCustomerList = supervisionCustomerDao.findListByDelegatorId(delegatorId);
			for (SupervisionCustomer supervisionCustomer : supervisionCustomerList) {
				List<InOutsRecord> inOutsRecordList = new ArrayList<InOutsRecord>();
				String scId = supervisionCustomer.getId();
				List<InsRecordDetail> insRecordDetails = insRecordDetailDao.findAll(getInsSpec(scId, beginDate, endDate));
				for (InsRecordDetail insRecordDetail : insRecordDetails) {
					PledgePurity pledgePurity = insRecordDetail.getPledgePurity();
					if(pledgePurity != null) {
						if(pledgePurity.getType() == 1) 
							inOutsRecordList.add(new InOutsRecord(insRecordDetail));
					}
				}
				List<OutsRecordDetail> outsRecordDetails = outsRecordDetailDao.findAll(getOutsSpec(scId, beginDate, endDate));
				for (OutsRecordDetail outsRecordDetail : outsRecordDetails) {
					PledgePurity pledgePurity = outsRecordDetail.getPledgePurity();
					if(pledgePurity != null) {
						if(pledgePurity.getType() == 1) 
							inOutsRecordList.add(new InOutsRecord(outsRecordDetail));
					}
				}
				Collections.sort(inOutsRecordList);
				if(!inOutsRecordList.isEmpty())
					inoutsRecordMap.put(supervisionCustomer, inOutsRecordList);
			}
		}
		return inoutsRecordMap;
	}

	public Specification<InsRecordDetail> getInsSpec(final String supervisionCustomerId, final Date beginDate, final Date endDate) {
		Specification<InsRecordDetail> specificationIns = new Specification<InsRecordDetail>() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public Predicate toPredicate(Root<InsRecordDetail> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (StringUtils.hasText(supervisionCustomerId)) {
					Path expression = root.get("supervisionCustomer");
					expression = expression.get("id");
					predicates.add(cb.equal(expression, supervisionCustomerId));
				}
				if (!StringUtils.isEmpty(beginDate)) {
					Path expression = root.get("date");
					predicates.add(cb.greaterThanOrEqualTo(expression, DateUtils.dateToDayBegin(beginDate)));
				}
				if (!StringUtils.isEmpty(endDate)) {
					Path expression = root.get("date");
					predicates.add(cb.lessThanOrEqualTo(expression, DateUtils.dateToDayEnd(endDate)));
				}
				query.orderBy(cb.desc(root.get("date")));
				if (predicates.size() > 0) {
					return cb.and(predicates.toArray(new Predicate[predicates.size()]));
				}
				return cb.conjunction();
			}
		};
		return specificationIns;
	}
	
	
	public Specification<OutsRecordDetail> getOutsSpec(final String supervisionCustomerId, final Date beginDate, final Date endDate) {
		Specification<OutsRecordDetail> specificationIns = new Specification<OutsRecordDetail>() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public Predicate toPredicate(Root<OutsRecordDetail> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (StringUtils.hasText(supervisionCustomerId)) {
					Path expression = root.get("supervisionCustomer");
					expression = expression.get("id");
					predicates.add(cb.equal(expression, supervisionCustomerId));
				}
				if (!StringUtils.isEmpty(beginDate)) {
					Path expression = root.get("date");
					predicates.add(cb.greaterThanOrEqualTo(expression, DateUtils.dateToDayBegin(beginDate)));
				}
				if (!StringUtils.isEmpty(endDate)) {
					Path expression = root.get("date");
					predicates.add(cb.lessThanOrEqualTo(expression, DateUtils.dateToDayEnd(endDate)));
				}
				query.orderBy(cb.desc(root.get("date")));
				if (predicates.size() > 0) {
					return cb.and(predicates.toArray(new Predicate[predicates.size()]));
				}
				return cb.conjunction();
			}
		};
		return specificationIns;
	}
	
	
	public Specification<InsRecordDetail> getInsSpec(final String delegatorId, final String supervisionCustomerId, final Date beginDate, final Date endDate) {
		Specification<InsRecordDetail> specificationIns = new Specification<InsRecordDetail>() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public Predicate toPredicate(Root<InsRecordDetail> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (StringUtils.hasText(delegatorId)) {
					Path expression = root.get("delegator");
					expression = expression.get("id");
					predicates.add(cb.equal(expression, delegatorId));
				}
				if (StringUtils.hasText(supervisionCustomerId)) {
					Path expression = root.get("supervisionCustomer");
					expression = expression.get("id");
					predicates.add(cb.equal(expression, supervisionCustomerId));
				}
				if (!StringUtils.isEmpty(beginDate)) {
					Path expression = root.get("date");
					predicates.add(cb.greaterThanOrEqualTo(expression, DateUtils.dateToDayBegin(beginDate)));
				}
				if (!StringUtils.isEmpty(endDate)) {
					Path expression = root.get("date");
					predicates.add(cb.lessThanOrEqualTo(expression, DateUtils.dateToDayEnd(endDate)));
				}
				query.orderBy(cb.desc(root.get("date")));
				if (predicates.size() > 0) {
					return cb.and(predicates.toArray(new Predicate[predicates.size()]));
				}
				return cb.conjunction();
			}
		};
		return specificationIns;
	}
	
	
	public Specification<OutsRecordDetail> getOutsSpec(final String delegatorId, final String supervisionCustomerId, final Date beginDate, final Date endDate) {
		Specification<OutsRecordDetail> specificationIns = new Specification<OutsRecordDetail>() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public Predicate toPredicate(Root<OutsRecordDetail> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (StringUtils.hasText(delegatorId)) {
					Path expression = root.get("delegator");
					expression = expression.get("id");
					predicates.add(cb.equal(expression, delegatorId));
				}
				if (StringUtils.hasText(supervisionCustomerId)) {
					Path expression = root.get("supervisionCustomer");
					expression = expression.get("id");
					predicates.add(cb.equal(expression, supervisionCustomerId));
				}
				if (!StringUtils.isEmpty(beginDate)) {
					Path expression = root.get("date");
					predicates.add(cb.greaterThanOrEqualTo(expression, DateUtils.dateToDayBegin(beginDate)));
				}
				if (!StringUtils.isEmpty(endDate)) {
					Path expression = root.get("date");
					predicates.add(cb.lessThanOrEqualTo(expression, DateUtils.dateToDayEnd(endDate)));
				}
				query.orderBy(cb.desc(root.get("date")));
				if (predicates.size() > 0) {
					return cb.and(predicates.toArray(new Predicate[predicates.size()]));
				}
				return cb.conjunction();
			}
		};
		return specificationIns;
	}

	public File generalRecordFile(Delegator delegator,  String supervisionCustomerId, Date beginDate, Date endDate)  throws Exception {
		Map<SupervisionCustomer, List<InOutsRecord>> map = this.queryByDelegatorAndDateBetween(delegator.getId(), supervisionCustomerId, beginDate, endDate);
		Workbook wb = new HSSFWorkbook();
		Sheet s = wb.createSheet("出入库明细报表");
		String[] title = { "日期", "操作类型", "款式大类", "标明成色", "标明重量规格", "数量", "总重" };
		for (int i = 0; i < 7; i++) {
			s.setColumnWidth(i, 12 * 256);// 设置列的宽度
		}
		Row r = null;
		Cell c = null;
		CellStyle cMenuCellStyle = getMenuCellStyle(wb);
		CellStyle cTitleCellStyle = getTitleCellStyle(wb);
		CellStyle cOtherCellStyle = getOtherCellStyle(wb);

		s.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
		r = s.createRow(0);
		c = r.createCell(0);
		c.setCellValue("日常出货统计表");
		c.setCellStyle(cTitleCellStyle);
//		for (int i = 1; i <= 6; i++) {
//			r.createCell(i).setCellStyle(cOtherCellStyle);
//		}
		
		r = s.createRow(1);
		c = r.createCell(0);
		c.setCellValue("委托方:" + delegator.getName());
		
		r = s.createRow(2);
		c = r.createCell(0);
		c.setCellValue("起始日期:" + new DateTime(beginDate).toString("yyyy-MM-dd"));
		
		c = r.createCell(4);
		c.setCellValue("结束日期:" + new DateTime(endDate).toString("yyyy-MM-dd"));
		
		r = s.createRow(3);
		
		int row = 4;
		double allInSumAmount = 0;
		double allOutSumAmount = 0;
		double allInSumWeight = 0;
		double allOutSumWeight = 0;
				
		for(Map.Entry<SupervisionCustomer, List<InOutsRecord>> entry : map.entrySet()) {
			SupervisionCustomer customer = entry.getKey();
			List<InOutsRecord> outsRecordList = entry.getValue();
			r = s.createRow(row);
			c = r.createCell(0);
			c.setCellValue("监管客户:" + customer.getName());
			row++;
			
			r = s.createRow(row);
			for (int m = 0; m < 7; m++) {
				c = r.createCell(m);
				c.setCellValue(title[m].toString());
				c.setCellStyle(cMenuCellStyle);
			}
			row++;
			
			double sumInAmount = 0;
			double sumInWeight = 0;
			double sumOutAmount = 0;
			double sumOutWeight = 0;
			for (InOutsRecord inOutsRecord : outsRecordList) {
				r = s.createRow(row);
				c = r.createCell(0);
				c.setCellStyle(cOtherCellStyle);
				c.setCellValue(inOutsRecord.getDateStr());
				
				c = r.createCell(1);
				c.setCellStyle(cOtherCellStyle);
				c.setCellValue(inOutsRecord.getMethod());
				
				c = r.createCell(2);
				c.setCellStyle(cOtherCellStyle);
				c.setCellValue(inOutsRecord.getStyle());
				
				c = r.createCell(3);
				c.setCellStyle(cOtherCellStyle);
				c.setCellValue(inOutsRecord.getPledgePurity());
				
				c = r.createCell(4);
				c.setCellStyle(cOtherCellStyle);
				c.setCellValue(inOutsRecord.getSpecWeight());
				
				c = r.createCell(5);
				c.setCellStyle(cOtherCellStyle);
				c.setCellValue(inOutsRecord.getAmount());
				
				c = r.createCell(6);
				c.setCellStyle(cOtherCellStyle);
				c.setCellValue(inOutsRecord.getSumWeight());
				
				if("入库".equals(inOutsRecord.getMethod())){
					sumInAmount = sumInAmount + inOutsRecord.getAmount();
					sumInWeight = sumInWeight + inOutsRecord.getSumWeight();
					allInSumAmount = allInSumAmount + sumInAmount;
					allInSumWeight = allInSumWeight + sumInWeight;
				}
				if("出库".equals(inOutsRecord.getMethod())){
					sumOutAmount = sumOutAmount + inOutsRecord.getAmount();
					sumOutWeight = sumOutWeight + inOutsRecord.getSumWeight();
					allOutSumAmount = allOutSumAmount + sumOutAmount;
					allOutSumWeight = allOutSumWeight + sumOutWeight;
				}
				row++;
			}
			
			r = s.createRow(row);
			s.addMergedRegion(new CellRangeAddress(row, row + 1, 0, 0));
			c = r.createCell(0);
			c.setCellStyle(cOtherCellStyle);
			c.setCellValue("合计");
			c = r.createCell(1);
			c.setCellStyle(cOtherCellStyle);
			c.setCellValue("入库");
			c = r.createCell(2);
			c.setCellStyle(cOtherCellStyle);
			c.setCellValue("");
			c = r.createCell(3);
			c.setCellStyle(cOtherCellStyle);
			c.setCellValue("");
			c = r.createCell(4);
			c.setCellStyle(cOtherCellStyle);
			c.setCellValue("");
			c = r.createCell(5);
			c.setCellStyle(cOtherCellStyle);
			c.setCellValue(sumInAmount);
			c = r.createCell(6);
			c.setCellStyle(cOtherCellStyle);
			c.setCellValue(sumInWeight);
			row++;
			
			r = s.createRow(row);
			c = r.createCell(0);
			c.setCellStyle(cOtherCellStyle);
			c = r.createCell(1);
			c.setCellStyle(cOtherCellStyle);
			c.setCellValue("出库");
			c = r.createCell(2);
			c.setCellStyle(cOtherCellStyle);
			c.setCellValue("");
			c = r.createCell(3);
			c.setCellStyle(cOtherCellStyle);
			c.setCellValue("");
			c = r.createCell(4);
			c.setCellStyle(cOtherCellStyle);
			c.setCellValue("");
			c = r.createCell(5);
			c.setCellStyle(cOtherCellStyle);
			c.setCellValue(sumOutAmount);
			c = r.createCell(6);
			c.setCellStyle(cOtherCellStyle);
			c.setCellValue(sumOutWeight);
			row++;
			
			r = s.createRow(row);
			row++;
			r = s.createRow(row);
			row++;
		}
			
		
		r = s.createRow(row);
		s.addMergedRegion(new CellRangeAddress(row, row + 1, 0, 0));
		c = r.createCell(0);
		c.setCellStyle(cOtherCellStyle);
		c.setCellValue("合计");
		c = r.createCell(1);
		c.setCellStyle(cOtherCellStyle);
		c.setCellValue("入库");
		c = r.createCell(2);
		c.setCellStyle(cOtherCellStyle);
		c.setCellValue("");
		c = r.createCell(3);
		c.setCellStyle(cOtherCellStyle);
		c.setCellValue("");
		c = r.createCell(4);
		c.setCellStyle(cOtherCellStyle);
		c.setCellValue("");
		c = r.createCell(5);
		c.setCellStyle(cOtherCellStyle);
		c.setCellValue(allInSumAmount);
		c = r.createCell(6);
		c.setCellStyle(cOtherCellStyle);
		c.setCellValue(allInSumWeight);
		row++;
		
		r = s.createRow(row);
		c = r.createCell(0);
		c.setCellStyle(cOtherCellStyle);
		c = r.createCell(1);
		c.setCellStyle(cOtherCellStyle);
		c.setCellValue("出库");
		c = r.createCell(2);
		c.setCellStyle(cOtherCellStyle);
		c.setCellValue("");
		c = r.createCell(3);
		c.setCellStyle(cOtherCellStyle);
		c.setCellValue("");
		c = r.createCell(4);
		c.setCellStyle(cOtherCellStyle);
		c.setCellValue("");
		c = r.createCell(5);
		c.setCellStyle(cOtherCellStyle);
		c.setCellValue(allOutSumAmount);
		c = r.createCell(6);
		c.setCellStyle(cOtherCellStyle);
		c.setCellValue(allOutSumWeight);
		row++;
		
		String tempDir =  System.getProperty("java.io.tmpdir");
		File file = new File(tempDir + "\\" + UUID.randomUUID() + ".xls");
		FileOutputStream fileOut = new FileOutputStream(file);
	    wb.write(fileOut);
	    fileOut.close();
	    
	    return file;
	}

	
	private static CellStyle getTitleCellStyle(Workbook workbook) {
		CellStyle cTitleCellStyle = workbook.createCellStyle();
		Font fTitleFont = workbook.createFont();
		fTitleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		fTitleFont.setFontHeightInPoints((short) 18);
		fTitleFont.setFontName("宋体");
		cTitleCellStyle.setFont(fTitleFont);
		cTitleCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
//		cTitleCellStyle.setBorderTop(CellStyle.BORDER_THIN);
//		cTitleCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
//		cTitleCellStyle.setBorderRight(CellStyle.BORDER_THIN);
//		cTitleCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		return cTitleCellStyle;
	}
	
	private static CellStyle getOtherCellStyle(Workbook workbook) {
		CellStyle cOtherCellStyle = workbook.createCellStyle();
		cOtherCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cOtherCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		cOtherCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cOtherCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cOtherCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		return cOtherCellStyle;
	}
	
	private static CellStyle getMenuCellStyle(Workbook workbook) {
		CellStyle cMenuCellStyle = workbook.createCellStyle();
		Font infoFont = workbook.createFont();
		infoFont.setFontName("宋体");
		cMenuCellStyle.setFont(infoFont);
		cMenuCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cMenuCellStyle.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		cMenuCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		cMenuCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cMenuCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cMenuCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cMenuCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		cMenuCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cMenuCellStyle.setWrapText(true);
		return cMenuCellStyle;
	}
	

}
