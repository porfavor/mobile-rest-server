package com.coffeebean.mobile.rest.server.mvc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.coffeebean.mobile.rest.server.mvc.model.Employee;

@Controller
@RequestMapping("/{uid}")
public class BasicController {
	@ResponseBody
	@RequestMapping(value="/hello.json")
	public String depositTracker(
			HttpServletRequest request){
		return "Hello";
	}
	
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
