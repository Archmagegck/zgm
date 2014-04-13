package com.pms.app.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pms.app.dao.StockDao;
import com.pms.app.dao.SupervisionCustomerDao;
import com.pms.app.entity.Delegator;
import com.pms.app.entity.Stock;
import com.pms.app.entity.SupervisionCustomer;

@Service
public class DailyStockService {
	
	@Autowired SupervisionCustomerDao supervisionCustomerDao;
	@Autowired StockDao stockDao;

	public Map<SupervisionCustomer, List<Stock>>  queryByDelegatorAndDate(String delegatorId, String supervisionCustomerId) {
		Map<SupervisionCustomer, List<Stock>> stockMap = new HashMap<SupervisionCustomer, List<Stock>>();
		if(StringUtils.hasText(supervisionCustomerId)) {
			int has = 0;
			if(StringUtils.hasText(delegatorId)){
				List<SupervisionCustomer> supervisionCustomerList = supervisionCustomerDao.findListByDelegatorId(delegatorId);
				for (SupervisionCustomer s : supervisionCustomerList) {
					if(s.getId().equals(supervisionCustomerId)) {
						has = 1;
					}
				}
			} else {
				has = 1;
			}
			if(has == 1) {
				SupervisionCustomer supervisionCustomer = supervisionCustomerDao.findOne(supervisionCustomerId);
				String warehouseId = supervisionCustomer.getWarehouse().getId();
				List<Stock> stockList = stockDao.findByWarehouseId(warehouseId);
				if(!stockList.isEmpty())
					stockMap.put(supervisionCustomer, stockList);
			}
			
		} else {
			List<SupervisionCustomer> supervisionCustomerList = supervisionCustomerDao.findListByDelegatorId(delegatorId);
			for (SupervisionCustomer supervisionCustomer : supervisionCustomerList) {
				String warehouseId = supervisionCustomer.getWarehouse().getId();
				List<Stock> stockList = stockDao.findByWarehouseId(warehouseId);
				if(!stockList.isEmpty())
					stockMap.put(supervisionCustomer, stockList);
			}
		}
		return stockMap;
	}

	public File generalRecordFile(Delegator delegator, String supervisionCustomerId) throws Exception {
		Map<SupervisionCustomer, List<Stock>> map = queryByDelegatorAndDate(delegator.getId(), supervisionCustomerId);
		Workbook wb = new HSSFWorkbook();
		Sheet s = wb.createSheet("库存表");
		String[] title = { "款式大类", "标明成色", "标明规格重量", "存储地点", "数量", "总重" };
		for (int i = 0; i < 6; i++) {
			s.setColumnWidth(i, 12 * 256);// 设置列的宽度
		}
		Row r = null;
		Cell c = null;
		CellStyle cMenuCellStyle = getMenuCellStyle(wb);
		CellStyle cTitleCellStyle = getTitleCellStyle(wb);
		CellStyle cOtherCellStyle = getOtherCellStyle(wb);

		s.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
		r = s.createRow(0);
		c = r.createCell(0);
		c.setCellValue("库存表");
		c.setCellStyle(cTitleCellStyle);
		
		r = s.createRow(1);
		c = r.createCell(0);
		c.setCellValue("委托方:" + delegator.getName());
		
		c = r.createCell(4);
		c.setCellValue("日期:" + new DateTime().toString("yyyy-MM-dd"));
		
		r = s.createRow(2);
		r = s.createRow(3);
		
		int row = 4;
		double allSumAmount = 0;
		double allSumWeight = 0;
		
		for(Map.Entry<SupervisionCustomer, List<Stock>> entry : map.entrySet()) {
			SupervisionCustomer customer = entry.getKey();
			List<Stock> stockList = entry.getValue();
			
			r = s.createRow(row);
			c = r.createCell(0);
			c.setCellValue("监管客户:" + customer.getName());
			row++;
			
			r = s.createRow(row);
			for (int m = 0; m < 6; m++) {
				c = r.createCell(m);
				c.setCellValue(title[m].toString());
				c.setCellStyle(cMenuCellStyle);
			}
			row++;
			
			double sumAmount = 0;
			double sumWeight = 0;
			
			for (Stock stock : stockList) {
				r = s.createRow(row);
				c = r.createCell(0);
				c.setCellStyle(cOtherCellStyle);
				c.setCellValue(stock.getStyle().getName());
				
				c = r.createCell(1);
				c.setCellStyle(cOtherCellStyle);
				c.setCellValue(stock.getPledgePurity().getName());
				
				c = r.createCell(2);
				c.setCellStyle(cOtherCellStyle);
//				c.setCellValue(stock.getSpecWeight());
				
				c = r.createCell(3);
				c.setCellStyle(cOtherCellStyle);
				String address = stock.getWarehouse().getAddress();
//				if(stock.getInStock() == 0) {
//					address = "在途";
//				}
				c.setCellValue(address);
				
				c = r.createCell(4);
				c.setCellStyle(cOtherCellStyle);
//				c.setCellValue(stock.getAmount());
				
				c = r.createCell(5);
				c.setCellStyle(cOtherCellStyle);
				c.setCellValue(stock.getSumWeight());
				
//				sumAmount = sumAmount + stock.getAmount();
				sumWeight = sumWeight + stock.getSumWeight();
				allSumAmount = allSumAmount + sumAmount;
				allSumWeight = allSumWeight + sumWeight;
				
				row++;
			}
			r = s.createRow(row);
			c = r.createCell(0);
			c.setCellStyle(cOtherCellStyle);
			c.setCellValue("合计");
			c = r.createCell(1);
			c.setCellStyle(cOtherCellStyle);
			c.setCellValue("");
			c = r.createCell(2);
			c.setCellStyle(cOtherCellStyle);
			c.setCellValue("");
			c = r.createCell(3);
			c.setCellStyle(cOtherCellStyle);
			c.setCellValue("");
			c = r.createCell(4);
			c.setCellStyle(cOtherCellStyle);
			c.setCellValue(sumAmount);
			c = r.createCell(5);
			c.setCellStyle(cOtherCellStyle);
			c.setCellValue(sumWeight);
			row++;
			
			r = s.createRow(row);
			row++;
			r = s.createRow(row);
			row++;
			
		}
		
		r = s.createRow(row);
		c = r.createCell(0);
		c.setCellStyle(cOtherCellStyle);
		c.setCellValue("合计");
		c = r.createCell(1);
		c.setCellStyle(cOtherCellStyle);
		c.setCellValue("");
		c = r.createCell(2);
		c.setCellStyle(cOtherCellStyle);
		c.setCellValue("");
		c = r.createCell(3);
		c.setCellStyle(cOtherCellStyle);
		c.setCellValue("");
		c = r.createCell(4);
		c.setCellStyle(cOtherCellStyle);
		c.setCellValue(allSumAmount);
		c = r.createCell(5);
		c.setCellStyle(cOtherCellStyle);
		c.setCellValue(allSumWeight);
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
