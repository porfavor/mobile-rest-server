package com.coffeebean.mobile.rest.server.core.ext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class HandlerInterceptorAdapterEx extends HandlerInterceptorAdapter {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//		HandlerMethod method = (HandlerMethod) handler;
//		Object controller = method.getBean();
//		
//		if (controller instanceof LoginController 
//				|| controller instanceof AdminController 
//				|| controller instanceof ServiceController
//				|| controller instanceof WebController
//				|| controller instanceof SysController) {
//			return true;
//		}
//
//		ShopUserVo shopUser = (ShopUserVo) request.getSession().getAttribute("shopUser");
//		SysUser sysUser = (SysUser) request.getSession().getAttribute("sysUser");
//		
//		String header = request.getHeader("X-Requested-With");
//		boolean isAjax = false;
//		if (null != header && header.equals("XMLHttpRequest")) {
//			isAjax = true;
//		}
//		if (isAjax && shopUser == null&&sysUser ==null) {
//			throw new BizException("401", "登录超时,请重新登陆.");
//		}
//		if (shopUser == null&&sysUser ==null) {
//			response.sendRedirect(request.getContextPath() + "/site/index.html");
//			return false;
//		}
//
//		int urlId = shopUser==null?sysUser.getId():shopUser.getId();
//		String[] pieces = request.getRequestURI().split("/");
//		String uid = pieces[2];
//		if (!(urlId + "").equals(uid)) {
//			throw new BizException("403", "非法请求.");
//		}
//		
//		ShopExpireCheck ann = method.getMethodAnnotation(ShopExpireCheck.class);
//		if(ann!=null){
//			String shopId = request.getParameter("shopId");
//			if(shopId!=null){
//				Shop domain = new Shop();
//				domain.setId(Integer.valueOf(shopId));
//				
//				Shop fdb = ContextHolder.getBean(QueryFacadeService.class).getSimpleDomain(domain, Shop.class, "id");
//				if(fdb==null){
//					throw new BizException("不存在的店铺:"+shopId);
//				}
//				Calendar cal = Calendar.getInstance();
//				cal.set(Calendar.HOUR_OF_DAY, 0);
//				cal.set(Calendar.MINUTE, 0);
//				cal.set(Calendar.SECOND, 0);
//				if(fdb.getExpireDate()==null){
//					throw new BizException("店铺:"+fdb.getName()+",没有设置过期时间");
//				}
//				if(fdb.getExpireDate().getTime()<cal.getTime().getTime()){
//					throw new BizException("店铺:"+fdb.getName()+"已于"+com.clothing.shop.mvc.util.DateUtils.formatDate(fdb.getExpireDate(), "yyyy-MM-dd")+"过期");
//				}
//			}
//		}
		
		return true;
	}

	/**
	 * This implementation is empty.
	 */
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

}
