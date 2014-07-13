package com.pms.app.service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pms.app.dao.InventoryDao;
import com.pms.app.dao.InventoryDetailDao;
import com.pms.app.dao.PledgeConfigDao;
import com.pms.app.dao.PledgeRecordDao;
import com.pms.app.dao.PledgeRecordDetailDao;
import com.pms.app.dao.StockDao;
import com.pms.app.dao.StyleDao;
import com.pms.app.dao.SupervisionCustomerDao;
import com.pms.app.entity.Delegator;
import com.pms.app.entity.ExtendedCheck;
import com.pms.app.entity.ExtendedCheckDetail;
import com.pms.app.entity.InsCheck;
import com.pms.app.entity.InsCheckDetail;
import com.pms.app.entity.Inventory;
import com.pms.app.entity.InventoryCheck;
import com.pms.app.entity.InventoryCheckDetail;
import com.pms.app.entity.InventoryDetail;
import com.pms.app.entity.PledgeConfig;
import com.pms.app.entity.PledgeRecord;
import com.pms.app.entity.PledgeRecordDetail;
import com.pms.app.entity.Stock;
import com.pms.app.entity.Style;
import com.pms.app.entity.SupervisionCustomer;
import com.pms.app.entity.Supervisor;
import com.pms.app.entity.Warehouse;
import com.pms.app.entity.reference.CheckMethod;
import com.pms.app.util.CodeUtils;
import com.pms.app.util.DateUtils;
import com.pms.app.util.Digests;
import com.pms.app.util.Encodes;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;
import com.pms.base.service.ServiceException;

@Service
public class InventoryService extends BaseService<Inventory, String> {

	@Autowired private InventoryDao inventoryDao;
	@Autowired private InventoryDetailDao inventoryDetailDao;
	@Autowired private StockDao stockDao;
	@Autowired private StyleDao styleDao;
	@Autowired private PledgeConfigDao pledgeConfigDao;
	@Autowired private SupervisionCustomerDao supervisionCustomerDao;
//	@Autowired private PledgePurityDao pledgePurityDao;
	@Autowired private PledgeRecordDao pledgeRecordDao;
	@Autowired private PledgeRecordDetailDao pledgeRecordDetailDao;
	@Autowired InventoryCheckService inventoryCheckService;
	@Autowired InsCheckService insCheckService;
	@Autowired ExtendedCheckService extendedCheckService;

	@Override
	protected BaseDao<Inventory, String> getEntityDao() {
		return inventoryDao;
	}
	
	@Transactional
	public void save(Warehouse warehouse, SupervisionCustomer supervisionCustomer, String supName, List<InventoryDetail> inventoryDetails) {
		Inventory inventory = new Inventory();
		inventory.setWarehouse(warehouse);
		inventory.setCode(CodeUtils.getInventoryCode(supervisionCustomer.getCode()));
		inventory.setSupName(supName);
		List<InventoryDetail> inventoryDetailList = new ArrayList<InventoryDetail>();
		for (InventoryDetail inventoryDetail : inventoryDetails) {
			inventoryDetail.setInventory(inventory);
			inventoryDetailList.add(inventoryDetail);
		}
		inventoryDao.save(inventory);
		inventoryDetailDao.save(inventoryDetailList);
	}

	public void save(List<InventoryDetail> inventoryDetailList, Warehouse warehouse, Supervisor supervisor) {
		Date now = new Date();
		Inventory inventory = new Inventory();
		inventory.setWarehouse(warehouse);
		inventory.setCode(CodeUtils.getInventoryCode(warehouse.getId()));
		inventory.setSupName(supervisor.getName());
		inventory.setDate(now);
		
//		PledgePurity pledgePurity = pledgePurityDao.findListByType(1).get(0);
		SupervisionCustomer supervisionCustomer = supervisionCustomerDao.findListByWarehouseId(warehouse.getId()).get(0);
		Delegator delegator = supervisionCustomer.getDelegator();
		
		double invSumWeight = 0.0;
		for (InventoryDetail inventoryDetail : inventoryDetailList) {
			invSumWeight += inventoryDetail.getWeight();
			
			inventoryDetail.setInventory(inventory);
			inventoryDetail.setDate(now);
			inventoryDetail.setWarehouse(warehouse);
		}
		
		inventory.setSumWeight(invSumWeight);
		double stockSumWeight = stockDao.findSumWeightByWarehouseId(warehouse.getId());//实时库存总量
		
		PledgeConfig config = pledgeConfigDao.findBySupervisionCustomerId(supervisionCustomer.getId()).get(0);
		double range = config.getIeRange();
		if(Math.abs(stockSumWeight - invSumWeight) / stockSumWeight > (range/100)){
			throw new ServiceException("盘存不一致，已超出盘存误差范围!");
		}
		
		//（盘存总重量为a,实时库存重量为b,盘存误差为c%）
		// 判断if((1-c/100)*b <a<(1+c/100) ) 盘存通过
//		if(invSumWeight <= ((1-range/100)*stockSumWeight) || (invSumWeight >= (1+range/100))) {
//			throw new ServiceException("盘存不一致，已超出盘存误差范围!");
//		}
		
		inventoryDao.save(inventory);
		inventoryDetailDao.save(inventoryDetailList);
		
		List<InsCheckDetail> insCheckDetails = new ArrayList<InsCheckDetail>();
		InsCheck insCheck = insCheckService.findByWarehouseDateDay(warehouse.getId(), now);//入库检测
		if(insCheck != null)  insCheckDetails = insCheck.getInsCheckDetails();
		
		List<InventoryCheckDetail> inventoryDetails = new ArrayList<InventoryCheckDetail>();
		InventoryCheck inventoryCheck = inventoryCheckService.findByWarehouseDateDay(warehouse.getId(), now);
		if(inventoryCheck != null) inventoryDetails = inventoryCheck.getInventoryCheckDetails();
		
		List<ExtendedCheckDetail> extendedCheckDetails = new ArrayList<ExtendedCheckDetail>();
		ExtendedCheck extendedCheck = extendedCheckService.findByWarehouseDateDay(warehouse.getId(), now);//超期检测
		if(extendedCheck != null) extendedCheckDetails = extendedCheck.getExtendedCheckDetails();
		
		PledgeRecord pledgeRecord = new PledgeRecord();
		String code = CodeUtils.getPledgeRecordCode(warehouse.getId());
		pledgeRecord.setCode(code);
		pledgeRecord.setDate(new Date());
		pledgeRecord.setDelegator(delegator);
		pledgeRecord.setPasscode(Encodes.encodeHex(Digests.md5(code.getBytes())));
		pledgeRecord.setSupervisionCustomer(supervisionCustomer);
		pledgeRecord.setWarehouse(warehouse);
		List<PledgeRecordDetail> pledgeRecordDetails = new ArrayList<PledgeRecordDetail>();
		List<Stock> stockList = stockDao.findByWarehouseId(warehouse.getId());
		for (Stock stock : stockList) {
			PledgeRecordDetail pledgeRecordDetail = new PledgeRecordDetail();
			pledgeRecordDetail.setPledgeRecord(pledgeRecord);
			pledgeRecordDetail.setStyle(stock.getStyle());
			pledgeRecordDetail.setPledgePurity(stock.getPledgePurity());
			pledgeRecordDetail.setSumWeight(stock.getSumWeight());
			pledgeRecordDetail.setStorage(warehouse.getAddress());
			
			pledgeRecordDetails.add(pledgeRecordDetail);
		}
		
		for (PledgeRecordDetail pledgeRecordDetail : pledgeRecordDetails) {
			double gpWeight = 0.0;
			double rjWeight = 0.0;
			for (InventoryCheckDetail inv : inventoryDetails) {
				if(inv.getStyle().getId().equals(pledgeRecordDetail.getStyle().getId())) {
					gpWeight += inv.getWeight();
				}
			}
			for (InsCheckDetail insCheckDetail : insCheckDetails) {
				if(insCheckDetail.getStyle().getId().equals(pledgeRecordDetail.getStyle().getId())) {
					if(insCheckDetail.getCheckMethod() == CheckMethod.Spectrum) {
						gpWeight += insCheckDetail.getCheckWeight();
					}
					if(insCheckDetail.getCheckMethod() == CheckMethod.Dissolve) {
						rjWeight += insCheckDetail.getCheckWeight();
					}
				}
			}
			for (ExtendedCheckDetail extendedCheckDetail : extendedCheckDetails) {
				if(extendedCheckDetail.getStyle().getId().equals(pledgeRecordDetail.getStyle().getId())) {
					if(extendedCheckDetail.getCheckMethod() == CheckMethod.Spectrum) {
						gpWeight += extendedCheckDetail.getCheckWeight();
					}
					if(extendedCheckDetail.getCheckMethod() == CheckMethod.Dissolve) {
						rjWeight += extendedCheckDetail.getCheckWeight();
					}
				}
			}
			double stockWeight = 1;
			for(Stock stock : stockList) {
				if(stock.getStyle().getId().equals(pledgeRecordDetail.getStyle().getId())) {
					stockWeight = stock.getSumWeight();
				}
			}
			
			pledgeRecordDetail.setSpectrumRate(new BigDecimal(gpWeight / stockWeight).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue());
			pledgeRecordDetail.setDissolveRate(new BigDecimal(rjWeight / stockWeight).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue());
		}
		
		pledgeRecord.setSumWeight(stockSumWeight);
		pledgeRecord.setPledgeRecordDetails(pledgeRecordDetails);
		pledgeRecordDao.save(pledgeRecord);
		
		pledgeRecordDetailDao.save(pledgeRecordDetails);
		
	}
	
	
	public List<Inventory> findByWarehouseIdAndDateBetween(String warehouseId, Date dateBegin, Date dateEnd) {
		
 		return inventoryDao.findByWarehouseIdAndDateBetween(warehouseId, dateBegin, dateEnd);
		
	}
	
	
	public List<Inventory> findByDateBetween(Date dateBegin, Date dateEnd) {
		
 		return inventoryDao.findByDateBetween(dateBegin, dateEnd);
		
	}
	
	public Inventory findByDateDay(Date date) {
		List<Inventory> inventories = inventoryDao.findByDateBetween(DateUtils.dateToDayBegin(date), DateUtils.dateToDayEnd(date));
		if(inventories.isEmpty()) return null;
		return inventories.get(0);
	}
	
	public Inventory findByWarehouseDateDay(String warehouseId, Date date) {
		List<Inventory> inventories = inventoryDao.findByWarehouseIdAndDateBetween(warehouseId, DateUtils.dateToDayBegin(date), DateUtils.dateToDayEnd(date));
		if(inventories.isEmpty()) return null;
		return inventories.get(0);
	}
	
	public List<InventoryDetail> paresExcel(InputStream inputStream) {
		List<InventoryDetail> inventoryDetails = new ArrayList<InventoryDetail>();
		Workbook workbook = null;
		try {
			workbook = WorkbookFactory.create(inputStream);
		} catch (Exception e) {
			throw new ServiceException("错误的文件格式!", e);
		}
		try {
			Sheet sheet =  workbook.getSheetAt(0);
			int maxRow = sheet.getLastRowNum();
			for (int i = 1; i <= maxRow; i++) {
				InventoryDetail inventoryDetail = new InventoryDetail();
				
				Row row = sheet.getRow(i);
				Cell cellTray = row.getCell(0);
				if(cellTray == null)
					continue;
				int trayNo = Integer.parseInt(getCellStr(cellTray));
				inventoryDetail.setTrayNo(trayNo);
				
				Cell cellStyle = row.getCell(1);
				if(cellStyle == null)
					continue;
				String styleName = getCellStr(cellStyle).trim();
				styleName = styleName.trim().replace("?", "").replace(" ", "");
				List<Style> styles = styleDao.findListByName(styleName);
				if(styles.isEmpty()) {
					throw new ServiceException("第" + (i + 1) + "行不存在款式 '" + styleName + "'");
				} else {
					inventoryDetail.setStyle(styles.get(0));
				}
				
				Cell cellWeight = row.getCell(2);
				if(cellWeight == null)
					continue;
				double weight = Double.parseDouble(getCellStr(cellWeight));
				inventoryDetail.setWeight(weight);
				
				inventoryDetails.add(inventoryDetail);
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return inventoryDetails;
	}
	
	public String getCellStr(Cell cell) {
		String str = "";
		if(cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				double strNumer = cell.getNumericCellValue();
				DecimalFormat df = new DecimalFormat("0");
				str = df.format(strNumer);
				break;
			case Cell.CELL_TYPE_STRING:
				str = cell.getStringCellValue();
				break;
			default:
				str = cell.toString();
				break;
			}
		}
		return str;
	}

}
