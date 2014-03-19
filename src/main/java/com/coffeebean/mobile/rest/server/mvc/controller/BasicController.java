package com.coffeebean.mobile.rest.server.mvc.controller;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.coffeebean.mobile.rest.server.mvc.model.Employee;
import com.coffeebean.mobile.rest.server.mvc.service.BasicService;

@Controller
@RequestMapping("/{uid}")
public class BasicController {
	@Resource
	private BasicService basicService;
	
	//private static final String XML_VIEW_NAME = "employees";
	
	@ResponseBody
	@RequestMapping(value="/hello.json")
	public String depositTracker(
			HttpServletRequest request){
		return "Hello";
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/employee/{id}.json")
	public ModelAndView getEmployee(@PathVariable String id) {
		Employee domain = new Employee();
		domain.setId(Long.parseLong(id));
		Employee fdbEmployee = basicService.selectDomain(domain, Employee.class, "id");
		return new ModelAndView("employees", "object", fdbEmployee);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/employee/{id}.json")
	public ModelAndView updateEmployee(
			HttpServletRequest request,
			@Valid Employee domain) {
		basicService.updateDomain(domain);
		return new ModelAndView("employees", "object", domain);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/employee.json")
	public ModelAndView addEmployee(
			HttpServletRequest request,
			@Valid Employee domain) {
		basicService.addDomain(domain);
		return new ModelAndView("employees", "object", domain);
	}
//	
//	@RequestMapping(method=RequestMethod.DELETE, value="/employee/{id}")
//	public ModelAndView removeEmployee(@PathVariable String id) {
//		employeeDS.remove(Long.parseLong(id));
//		List<Employee> employees = employeeDS.getAll();
//		EmployeeList list = new EmployeeList(employees);
//		return new ModelAndView(XML_VIEW_NAME, "employees", list);
//	}
	
	@RequestMapping(method=RequestMethod.GET, value="/employees.json")
	public ModelAndView getEmployees() {
		List<Employee> employees = new ArrayList<Employee>();
		
		Employee e1 = new Employee();
		e1.setId(1);
		e1.setName("Jack");
		e1.setEmail("jack@163,com");
		employees.add(e1);
		
		Employee e2 = new Employee();
		e2.setId(2);
		e2.setName("Jones");
		e2.setEmail("Jones@199,com");
		employees.add(e1);

		return new ModelAndView("employees", "employees", employees);
	}
}
