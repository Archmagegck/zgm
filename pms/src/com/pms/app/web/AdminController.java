package com.pms.app.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pms.app.entity.Admin;
import com.pms.app.entity.Delegator;
import com.pms.app.entity.Supervisor;
import com.pms.app.entity.Warehouse;
import com.pms.app.service.AdminService;
import com.pms.app.service.DelegatorService;
import com.pms.app.service.SupervisionCustomerService;
import com.pms.app.service.SupervisorService;
import com.pms.app.service.WarehouseService;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

	@Autowired private AdminService adminService;
	@Autowired private SupervisorService supervisorService;
	@Autowired private DelegatorService delegatorService;
	@Autowired private WarehouseService warehouseService;
	@Autowired private SupervisionCustomerService supervisionCustomerService;

	@RequestMapping(value = { "" })
	public String admin() {
		return "login/login";
	}

	@RequestMapping(value = "/top")
	public String top() {
		return "main/top";
	}

	@RequestMapping(value = "/left")
	public String left() {
		return "main/left";
	}

	@RequestMapping(value = "/right")
	public String right(Model model, Pageable pageable, String queryName, String queryValue) {
		return "main/right";
	}

	@RequestMapping(value = "/login")
	public String login(RedirectAttributes ra, String username, String password, Integer type, HttpSession session) {
		if(type == null)
			return "redirect:/admin";
		switch (type) {
		case 1: // 监管经理
			List<Admin> adminList = adminService.findByUsernameAndPassword(username, password);
			if (!adminList.isEmpty()) {
				if (adminList.size() == 1) {
					Admin admin = adminList.get(0);
					session.setAttribute("type", type);
					session.setAttribute("user", admin);
					return "main/main";
				}
			}
			break;
		case 2: // 监管员
			List<Supervisor> supervisorList = supervisorService.findByUsernameAndPassword(username, password);
			if (!supervisorList.isEmpty()) {
				if (supervisorList.size() == 1) {
					Supervisor supervisor = supervisorList.get(0);
					if(supervisor.getIsUsed() == 1) {
						session.setAttribute("type", type);
						session.setAttribute("user", supervisor);
						session.setAttribute("supervisionCustomerCode", supervisionCustomerService.findBySupervisorId(supervisor.getId()).getCode());
						Warehouse warehouse = warehouseService.findWarehouseBySupervisorId(supervisor.getId());
						if(warehouse != null) {
							warehouse.getAddress();
							session.setAttribute("warehouse", warehouse);
							return "main/main";
						}
					}
					session.invalidate();
					ra.addFlashAttribute("message", "此监管员未分配仓库！");
					return "redirect:/admin";
				}
			}
			break;
		case 3: // 委托方
			List<Delegator> delegatorList = delegatorService.findByUsernameAndPassword(username, password);
			if (!delegatorList.isEmpty()) {
				if (delegatorList.size() == 1) {
					Delegator delegator = delegatorList.get(0);
					session.setAttribute("type", type);
					session.setAttribute("user", delegator);
					return "main/main";
				}
			}
			break;
		default:
			break;
		}
		session.invalidate();
		ra.addFlashAttribute("message", "账号或密码错误！");
		return "redirect:/admin";
	}

	@RequestMapping(value = "/exit")
	public String exit(HttpServletRequest request) {
		request.getSession().invalidate();
		return "login/login";
	}
}
