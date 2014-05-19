package com.pms.app.service;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.pms.app.dao.PledgePurityDao;
import com.pms.app.dao.PledgeRecordDao;
import com.pms.app.dao.PledgeRecordDetailDao;
import com.pms.app.dao.StockDao;
import com.pms.app.dao.StyleDao;
import com.pms.app.dao.SupervisionCustomerDao;
import com.pms.app.entity.Delegator;
import com.pms.app.entity.Inventory;
import com.pms.app.entity.InventoryDetail;
import com.pms.app.entity.PledgeConfig;
import com.pms.app.entity.PledgePurity;
import com.pms.app.entity.PledgeRecord;
import com.pms.app.entity.PledgeRecordDetail;
import com.pms.app.entity.Style;
import com.pms.app.entity.SupervisionCustomer;
import com.pms.app.entity.Supervisor;
import com.pms.app.entity.Warehouse;
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
	@Autowired private PledgePurityDao pledgePurityDao;
	@Autowired private PledgeRecordDao pledgeRecordDao;
	@Autowired private PledgeRecordDetailDao pledgeRecordDetailDao;

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
		
		PledgePurity pledgePurity = pledgePurityDao.findListByType(1).get(0);
		SupervisionCustomer supervisionCustomer = supervisionCustomerDao.findListByWarehouseId(warehouse.getId()).get(0);
		Delegator delegator = supervisionCustomer.getDelegator();
		PledgeRecord pledgeRecord = new PledgeRecord();
		String code = CodeUtils.getPledgeRecordCode(warehouse.getId());
		pledgeRecord.setCode(code);
		pledgeRecord.setDate(new Date());
		pledgeRecord.setDelegator(delegator);
		pledgeRecord.setPasscode(Encodes.encodeHex(Digests.md5(code.getBytes())));
		pledgeRecord.setSupervisionCustomer(supervisionCustomer);
		pledgeRecord.setWarehouse(warehouse);
		List<PledgeRecordDetail> pledgeRecordDetails = new ArrayList<PledgeRecordDetail>();
		
		double invSumWeight = 0.0;
		for (InventoryDetail inventoryDetail : inventoryDetailList) {
			invSumWeight += inventoryDetail.getWeight();
			
			inventoryDetail.setInventory(inventory);
			inventoryDetail.setDate(now);
			inventoryDetail.setWarehouse(warehouse);
			
			PledgeRecordDetail pledgeRecordDetail = new PledgeRecordDetail();
			pledgeRecordDetail.setPledgeRecord(pledgeRecord);
			pledgeRecordDetail.setStyle(inventoryDetail.getStyle());
			pledgeRecordDetail.setPledgePurity(pledgePurity);
			pledgeRecordDetail.setSumWeight(inventoryDetail.getWeight());
			pledgeRecordDetail.setStorage(warehouse.getAddress());
			
			pledgeRecordDetails.add(pledgeRecordDetail);
		}
		
		pledgeRecord.setSumWeight(invSumWeight);
		pledgeRecord.setPledgeRecordDetails(pledgeRecordDetails);
		
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
		
		
		pledgeRecordDao.save(pledgeRecord);
		
		
		Map<String, PledgeRecordDetail> pledgeRecordDetailMap = new HashMap<String, PledgeRecordDetail>();
		for (PledgeRecordDetail details : pledgeRecordDetails) {
			String key = details.getKey();
			PledgeRecordDetail pd = pledgeRecordDetailMap.get(key);
			if(pd == null) {
				pd = new PledgeRecordDetail(pledgeRecord, details.getStyle(), details.getSumWeight(), details.getStorage());
			} else {
				pd.addWeight(details.getSumWeight());
			}
			pledgeRecordDetailMap.put(key, pd);
		}
		
		pledgeRecordDetailDao.save(pledgeRecordDetailMap.values());
		
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
