/**
 * File Name: ConstConfig.java
 * Date: 2020-04-20 15:48:41
 */
package com.tzrcb.dispatch.config;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import com.tzrcb.dispatch.model.table.dispatch.RouteConfig;
import com.tzrcb.dispatch.model.table.dispatch.ServiceConfig;

/**
 * Description: 常量配置
 * @author shenzulun
 * @date 2020-04-20
 * @version 1.0
 */
public class ConstConfig {
	/**
	 * 路由配置MAP
	 */
	private static ConcurrentMap<String, RouteConfig> routeMap = new ConcurrentHashMap<String, RouteConfig>();
	/**
	 * 服务配置MAP
	 */
	private static ConcurrentMap<String, ServiceConfig> serviceMap = new ConcurrentHashMap<String, ServiceConfig>();
	
	/**
	 * 初始化
	 */
	public static void init() {
		List<RouteConfig> routes = RouteConfig.dao.findAll();
		ConcurrentMap<String, RouteConfig> routeMap1 = new ConcurrentHashMap<String, RouteConfig>();
		for(RouteConfig route : routes) {
			routeMap1.put(route.getTransNo() + "-" + route.getSource() + "-" + route.getTarget(), route);
		}
		routeMap = routeMap1;
		
		List<ServiceConfig> services = ServiceConfig.dao.findAll();
		ConcurrentMap<String, ServiceConfig> serviceMap1 = new ConcurrentHashMap<String, ServiceConfig>();
		for(ServiceConfig service : services) {
			serviceMap1.put(service.getRouteNo(), service);
		}
		serviceMap = serviceMap1;
		
	}
	
	public static RouteConfig getRoute(String transNo) {
		return routeMap.get(transNo);
	}
	
	public static ServiceConfig getService(String routeNo) {
		return serviceMap.get(routeNo);
	}
	
}
