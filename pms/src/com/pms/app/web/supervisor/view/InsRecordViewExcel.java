package com.pms.app.web.supervisor.view;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.joda.time.DateTime;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.pms.app.entity.InsRecord;
import com.pms.app.util.ServletUtils;

public class InsRecordViewExcel extends AbstractExcelView {

	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType(ServletUtils.EXCEL_TYPE);
		ServletUtils.setFileDownloadHeader(response, "入库单列表.xls");
		
		List<InsRecord> insRecordList = (List<InsRecord>) model.get("insRecordList");
		Date date = (Date) model.get("date");
		
		String[] title = { "序号", "入库单号", "总重量（g）", "存储地点", "入库时间"};
		int length = title.length - 1;
		
		Sheet s = workbook.createSheet("入库单");
		
		for (int i = 0; i < title.length; i++) {
			s.setColumnWidth(i, 35 * 256);// 设置列的宽度
		}
		
		Row r = null;
		Cell c = null;
		CellStyle cTitleCellStyle = getTitleCellStyle(workbook);
		CellStyle cMenuCellStyle = getMenuCellStyle(workbook);
		CellStyle cOtherCellStyle = getOtherCellStyle(workbook);
		CellStyle numCellStyle = getNum2Style2(workbook);
		
		s.addMergedRegion(new CellRangeAddress(0, 0, 0, length));
		r = s.createRow(0);
		c = r.createCell(0);
		c.setCellValue("入库单统计表");
		c.setCellStyle(cTitleCellStyle);
		for (int i = 1; i <= length; i++) {
			r.createCell(i).setCellStyle(cOtherCellStyle);
		}
		
		s.addMergedRegion(new CellRangeAddress(1, 1, 0, length));
		r = s.createRow(1);
		c = r.createCell(0);
		c.setCellValue(new DateTime(date).toString("yyyy年MM月dd日"));
		c.setCellStyle(cOtherCellStyle);
		for (int j = 1; j <= length; j++) {
			r.createCell(j).setCellStyle(cOtherCellStyle);
		}
		
		r = s.createRow(2);
		for (int m = 0; m <= length; m++) {
			c = r.createCell(m);
			c.setCellValue(title[m].toString());
			c.setCellStyle(cMenuCellStyle);
		}
		
		int listRow = 3;
		int i = 1;
		double sumWeight = 0.0;
		for(InsRecord insRecord : insRecordList) {
			r = s.createRow(listRow);
			c = r.createCell(0);
			c.setCellStyle(cOtherCellStyle);
			c.setCellValue(i + "");
			
			c = r.createCell(1);
			c.setCellStyle(cOtherCellStyle);
			c.setCellValue(insRecord.getCode());
			
			c = r.createCell(2);
			c.setCellStyle(numCellStyle);
			c.setCellValue(insRecord.getSumWeight());
			sumWeight += insRecord.getSumWeight();
			
			c = r.createCell(3);
			c.setCellStyle(cOtherCellStyle);
			c.setCellValue(insRecord.getWarehouse().getAddress());
			
			c = r.createCell(4);
			c.setCellStyle(cOtherCellStyle);
			c.setCellValue(new DateTime(insRecord.getDate()).toString("yyyy-MM-dd hh:mm:ss"));
			
			i++;
			listRow++;
		}
		
		r = s.createRow(listRow);
		c = r.createCell(0);
		c.setCellStyle(cOtherCellStyle);
		c.setCellValue("合计");
		
		c = r.createCell(1);
		c.setCellStyle(cOtherCellStyle);
		c.setCellValue(" ");
		
		c = r.createCell(2);
		c.setCellStyle(numCellStyle);
		c.setCellValue(sumWeight);
		
		c = r.createCell(3);
		c.setCellStyle(cOtherCellStyle);
		c.setCellValue(" ");
		
		c = r.createCell(4);
		c.setCellStyle(cOtherCellStyle);
		c.setCellValue(" ");
		
	}
	
	private static CellStyle getTitleCellStyle(Workbook workbook) {
		CellStyle cTitleCellStyle = workbook.createCellStyle();
		Font fTitleFont = workbook.createFont();
		fTitleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		fTitleFont.setFontHeightInPoints((short) 18);
		fTitleFont.setFontName("宋体");
		cTitleCellStyle.setFont(fTitleFont);
		cTitleCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cTitleCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		cTitleCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cTitleCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cTitleCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		return cTitleCellStyle;
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

	private static CellStyle getOtherCellStyle(Workbook workbook) {
		CellStyle cOtherCellStyle = workbook.createCellStyle();
		cOtherCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cOtherCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		cOtherCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cOtherCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cOtherCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		return cOtherCellStyle;
	}


	private static CellStyle getNum2Style2(Workbook workbook) {
		CellStyle cs = workbook.createCellStyle();
		Font f = workbook.createFont();
		cs.setFont(f);
		cs.setAlignment(CellStyle.ALIGN_CENTER);
		cs.setBorderTop(CellStyle.BORDER_THIN);
		cs.setBorderLeft(CellStyle.BORDER_THIN);
		cs.setBorderRight(CellStyle.BORDER_THIN);
		cs.setBorderBottom(CellStyle.BORDER_THIN);
		DataFormat format = workbook.createDataFormat();
		cs.setDataFormat(format.getFormat("0.00")); // 两位小数
		return cs;
	}

}
