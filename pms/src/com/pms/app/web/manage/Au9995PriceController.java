package com.pms.app.web.manage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pms.app.dao.PledgeConfigDao;
import com.pms.app.dao.StockDao;
import com.pms.app.entity.PurityPrice;
import com.pms.app.entity.SupervisionCustomer;
import com.pms.app.entity.vo.LowerMinPriceListShow;
import com.pms.app.service.PledgePurityService;
import com.pms.app.service.PurityPriceService;
import com.pms.app.service.SupervisionCustomerService;


@Controller
@RequestMapping(value = "/manage/au9995Price")
public class Au9995PriceController {
	
	private Logger logger = LoggerFactory.getLogger(Au9995PriceController.class);
	
	@Autowired private PurityPriceService au9995PriceService;
	@Autowired private PledgePurityService pledgePurityService;
	@Autowired private SupervisionCustomerService supervisionCustomerService;
	@Autowired private PledgeConfigDao pledgeConfigDao;
	@Autowired private StockDao stockDao;
	
	
	@InitBinder  
	public void initBinder(WebDataBinder binder) throws Exception {  
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
	    CustomDateEditor dateEditor = new CustomDateEditor(df, true);  
	    binder.registerCustomEditor(Date.class, dateEditor);      
	}  
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model, Pageable pageable, Date date) {
		model.addAttribute("date", (date != null) ? new DateTime(date).toString("yyyy-MM-dd") : "");
		model.addAttribute("page", au9995PriceService.findByDate(pageable, date));
		return "manage/au9995Price/list";
	}
	
	
	@RequestMapping(value = "/add")
	public String add(Model model, RedirectAttributes ra){
		if(au9995PriceService.hasDatePrice(new Date())) {
			ra.addFlashAttribute("messageOK", "今日价格已添加！");
			return "redirect:/manage/au9995Price/list";
		}
		model.addAttribute("pledgePurityList",pledgePurityService.findAllEq("type", "1"));
		System.out.printf("这是查询的信息"+pledgePurityService.findAll().toString());
		model.addAttribute("date", new DateTime().toString("yyyy-MM-dd"));
		return "manage/au9995Price/add";
	}
	
	
	@RequestMapping(value = "/save")
	public String save(Model model,PurityPrice Au9995Price,String pledgePurityId){
		List<LowerMinPriceListShow> lowerMinPriceListShows = new ArrayList<LowerMinPriceListShow>();
		try {
			Au9995Price.setPledgePurity(pledgePurityService.findById(pledgePurityId));
			au9995PriceService.save(Au9995Price);
			
			//1、所有监管客户
			
			List<SupervisionCustomer> supervisionCustomerList=supervisionCustomerService.findAll();
			for(SupervisionCustomer supervisionCustomer:supervisionCustomerList){
				//根据仓库查询监管客户ID,查询监管客户设置对象
				double levelPrice=pledgeConfigDao.findBySupervisionCustomerId(supervisionCustomer.getId()).get(0).getMinValue()*1.15;
				//获取仓库实时库存重量（g）,计算实时价值
				double price= stockDao.findSumWeightByWarehouseId(supervisionCustomer.getWarehouse().getId())*Au9995Price.getPrice();
				if(price<=levelPrice){
					LowerMinPriceListShow lowerMinPriceListShow=new LowerMinPriceListShow();
					lowerMinPriceListShow.setDelegator(supervisionCustomer.getDelegator());
					lowerMinPriceListShow.setSupervisionCustomer(supervisionCustomer);
					lowerMinPriceListShow.setWarehouse(supervisionCustomer.getWarehouse());
					lowerMinPriceListShows.add(lowerMinPriceListShow);
				}
			}
		} catch (Exception e) {
			
			logger.error("保存异常", e);
		}
		model.addAttribute("price", Au9995Price.getPrice());
		model.addAttribute("lowerMinPriceListShowList", lowerMinPriceListShows);
		return "manage/au9995Price/edit";
	}
	
	
	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable("id")String id, Model model){
		model.addAttribute("Au9995Price", au9995PriceService.findById(id));
		return "manage/au9995Price/edit";
	}
	

	@RequestMapping(value = "/delete")
	public String delete(String[] idGroup, RedirectAttributes ra){
		try {
			au9995PriceService.delete(idGroup);
			ra.addFlashAttribute("messageOK", "删除成功！");
		} catch (Exception e) {
			ra.addFlashAttribute("messageErr", "删除失败！");
			logger.error("删除异常", e);
		}
		return "redirect:/manage/au9995Price/list";
	}
	
	
	
}
