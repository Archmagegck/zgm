package com.pms.app.web.manage;


import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pms.app.entity.Admin;
import com.pms.app.entity.InsRecordDetail;
import com.pms.app.entity.Inventory;
import com.pms.app.entity.Warehouse;
import com.pms.app.entity.PurityPrice;
import com.pms.app.entity.vo.InOutsRecord;
import com.pms.app.entity.vo.InventoryShow;

import com.pms.app.service.InventoryDetailService;
import com.pms.app.service.InventoryService;
import com.pms.app.service.WarehouseService;
import com.pms.app.service.PurityPriceService;
import com.pms.app.util.DateUtils;



@Controller
@RequestMapping(value = "/manage/dailyInventoryRecord")
public class DailyInventoryRecordController {
	private Logger logger = LoggerFactory.getLogger(DailyInventoryRecordController.class);
	
	@Autowired WarehouseService warehouseService;
	@Autowired InventoryService inventoryService;
	@Autowired PurityPriceService purityPriceService;
	@Autowired JavaMailSender javaMailSender;
	
	
	@InitBinder  
	public void initBinder(WebDataBinder binder) throws Exception {  
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
	    CustomDateEditor dateEditor = new CustomDateEditor(df, true);  
	    binder.registerCustomEditor(Date.class, dateEditor);
	}  
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model, String warehouseId, Date date) {
		if(date == null) 
			date = new Date();
		DateTime dateTime = new DateTime(date);
		model.addAttribute("date", dateTime.toString("yyyy-MM-dd"));
		model.addAttribute("warehouseList", warehouseService.findAll());
		model.addAttribute("warehouseId", warehouseId);
		List<InventoryShow> inventoryShowList = new ArrayList<InventoryShow>();
		double price=0.0;
		if(!purityPriceService.findListByDate(date).isEmpty()){
			price=purityPriceService.findListByDate(date).get(0).getPrice();
		}else{
			price=0.0;
		}
		if(warehouseId==null||warehouseId.equals("")){
			
			List<Inventory> inventoryList=inventoryService.findByDateBetween((new DateUtils().dateToDayBegin(date)),(new DateUtils().dateToDayEnd(date)));
			//List<Inventory> inventoryList=inventoryService.findByDate(date);
			for(Inventory inventory : inventoryList){
				InventoryShow inventoryShow=new InventoryShow();
				inventoryShow.setWarehouse(inventory.getWarehouse());
				inventoryShow.setInventoryWeight(inventory.getSumWeight());
				inventoryShow.setInventoryPrice(price*inventory.getSumWeight());
				inventoryShowList.add(inventoryShow);
			}
		}else{
			List<Inventory> inventoryList=inventoryService.findByWarehouseIdAndDateBetween(warehouseId, (new DateUtils().dateToDayBegin(date)),(new DateUtils().dateToDayEnd(date)));
			for(Inventory inventory : inventoryList){
				InventoryShow inventoryShow=new InventoryShow();
				inventoryShow.setWarehouse(inventory.getWarehouse());
				inventoryShow.setInventoryWeight(inventory.getSumWeight());
				inventoryShow.setInventoryPrice(price*inventory.getSumWeight());
				inventoryShowList.add(inventoryShow);
			}
		}

		model.addAttribute("inventoryShows",inventoryShowList);
//		model.addAttribute("inventoryStatisticsList",inventoryDetailService.findWarehouseListDate(warehouseService.findById(warehouseId),date));
		model.addAttribute("purityPrice",purityPriceService.findListByDate(date));
		//inventoryDetailService.findb
		//model.addAttribute("dailyInventoryRecordList", dailyInventoryRecordService.findPageByQuery(warehouseId, date));
		return "manage/dailyInventoryRecord/list";
	}
	

}
