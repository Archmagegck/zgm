package com.pms.app.web.supervisor;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pms.app.entity.Stock;
import com.pms.app.entity.TransitGoods;
import com.pms.app.entity.Warehouse;
import com.pms.app.service.PledgePurityService;
import com.pms.app.service.StockService;
import com.pms.app.service.StyleService;
import com.pms.app.service.TransitGoodsService;

@Controller
@RequestMapping(value = "/supervisor/transitGoods")
public class TransitGoodsController {
	
private Logger logger = LoggerFactory.getLogger(TransitGoodsController.class);
	
	@Autowired private TransitGoodsService transitGoodsService;
	@Autowired private StockService stockService;
	@Autowired private StyleService styleService;
	@Autowired private PledgePurityService pledgePurityService;
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model, Pageable pageable, String queryName, String queryValue) {
		model.addAttribute("queryName", queryName);
		model.addAttribute("queryValue", queryValue);
		model.addAttribute("page", transitGoodsService.findAllLike(pageable, queryName, queryValue));
		return "supervisor/transitGoods/list";
	}
	
	
	@RequestMapping(value = "/add")
	public String add(Model model){
		model.addAttribute("styleList", styleService.findAll());
		model.addAttribute("pledgePurityList", pledgePurityService.findAll());
		return "supervisor/transitGoods/add";
	}
	
	
	@RequestMapping(value = "/save")
	public String save(TransitGoods transitGoods, RedirectAttributes ra, HttpSession session){
		try {
			Stock stock = new Stock();
			stock.setStyle(transitGoods.getStyle());
			stock.setPledgePurity(transitGoods.getPledgePurity());
			stock.setSpecWeight(transitGoods.getSpecWeight());
			stock.setAmount(transitGoods.getAmount());
			stock.setSumWeight(stock.getAmount()*stock.getSpecWeight());
			stock.setCompany(transitGoods.getCompany());
			stock.setClosedTran(transitGoods.getClosedTran());
			stock.setDesc(transitGoods.getDesc());
			stock.setWarehouse((Warehouse)session.getAttribute("warehouse"));
			stock.setInStock(0);
			stockService.save(stock);
			
			transitGoods.setStock(stock);
			transitGoods.setState(0);
			transitGoodsService.save(transitGoods);
			ra.addFlashAttribute("messageOK", "保存成功！");
		} catch (Exception e) {
			ra.addFlashAttribute("messageErr", "保存失败！");
			logger.error("保存异常", e);
		}
		return "redirect:/supervisor/transitGoods/list";
	}
	
	
	@RequestMapping(value = "/in/{id}")
	public String in(@PathVariable("id")String id, HttpSession httpSession){
		httpSession.setAttribute("transitGoods", transitGoodsService.findById(id));
		return "supervisor/transitGoods/in";
	}
	
	
	@RequestMapping(value = "/inStock")
	public String inStock(String senderIdCard, HttpSession session, Model model, RedirectAttributes ra){
		TransitGoods transitGoods = (TransitGoods)session.getAttribute("transitGoods");
		session.removeAttribute("transitGoods");
		transitGoods.setDate(new Date());
		transitGoods.setState(1);
				
		//修改在途物质和对应的库存信息的存储地点
		transitGoods.setWarehouse((Warehouse)session.getAttribute("warehouse"));
		Stock stock = transitGoods.getStock();
		stock.setWarehouse((Warehouse)session.getAttribute("warehouse"));
		stock.setInStock(1);
		
		Map<String, Stock> stockMap = stockService.findStockKeyMapByWarehouseId(((Warehouse)session.getAttribute("warehouse")).getId());	//获得库存的Map
		String key = stock.getKey();	//获得库存的Key
		if(stockMap.isEmpty())
			stockMap.put(key, stock);
		else{			
			if(stockMap.get(key) == null){
				stockMap.put(key, stock);
			}
			else{
				stock.add(stock);
			}
		}	
		
		transitGoodsService.save(transitGoods);
		stockService.save(stockMap);
		
		
		ra.addFlashAttribute("messageOK", "保存成功！");
		return "redirect:/supervisor/transitGoods/list";
	}
	
}
