/**
 * File Name: LaunchEntry.java
 * Date: 2020-04-16 19:11:52
 */
package com.tzrcb.dispatch;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.json.JacksonFactory;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.Sqlite3Dialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;
import com.tzrcb.dispatch.controller.APIController;
import com.tzrcb.dispatch.controller.IndexController;
import com.tzrcb.dispatch.model.table.dispatch._DbMappingKit;
import com.tzrcb.dispatch.util.CommonUtils;
import me.belucky.easytool.config.JdbcConfig;
import me.belucky.easytool.jfinal.PropUtils;


/**
 * Description: JSON工具类
 * @author shenzulun
 * @date 2020-04-16
 * @version 1.0
 */
public class LaunchEntry extends JFinalConfig{
	protected static Logger log = LoggerFactory.getLogger(LaunchEntry.class);
	private static Prop prop;

	public void configConstant(Constants me) {
		loadConfig();
		me.setDevMode(prop.getBoolean("devMode", false));
		me.setMaxPostSize(1024 * 1024 * 1024);    //1G
		me.setViewType(ViewType.FREE_MARKER);
		me.setInjectDependency(true);
		// 配置对超类中的属性进行注入
		me.setInjectSuperClass(true);
		me.setJsonFactory(new JacksonFactory());
	}
	
	/**
	 * 载入配置文件
	 */
	static void loadConfig() {
		if (prop == null) {
			prop = PropKit.useFirstFound("sys-config.properties");
			PropUtils.initPropIgnore("", "sys-config.properties");
		}
	}
	
	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		me.add("/", IndexController.class);
		me.add("/api", APIController.class, "/api");
	}

	public void configEngine(Engine me) {
		
	}
	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
		
	}
	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		
	}
	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		//默认数据源
		DruidPlugin druid = createDruidPlugin(); 
		me.add(druid);
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druid);
		arp.setShowSql(prop.getBoolean("showSql", false));
		arp.setDialect(new Sqlite3Dialect());
		_DbMappingKit.mapping(arp);
		me.add(arp);
	}
	
	public static DruidPlugin createDruidPlugin() {	
		return createDruidPlugin("default");
	}
	
	/**
	 * 根据数据源名称创建Druid数据源
	 * @param dataSourceName
	 * @return
	 */
	public static DruidPlugin createDruidPlugin(String dataSourceName) {	
		loadConfig();
		JdbcConfig conf = PropUtils.getConfig(dataSourceName, "jdbc-config.properties", JdbcConfig.class);
		DruidPlugin druid = new DruidPlugin(conf.getUrl(), conf.getUsername(), conf.getPassword(), conf.getDriverClass()); 
		return druid;
	}
	
	/**
	 * 应用启动后执行事件
	 */
	@Override
	public void onStart(){
		CommonUtils.init();
		try {
			String indexUrl = PropKit.get("index_url");
			if(indexUrl != null && !"".equals(indexUrl)){
				Desktop.getDesktop().browse(new URI(indexUrl));
			}
		} catch (IOException e) {
			log.error("打开浏览器失败...",e);
		} catch (URISyntaxException e) {
			log.error("打开浏览器失败...",e);
		}
	}

	/**
	 * 应用停止前执行事件
	 */
	@Override
	public void onStop(){
		
	}
	
	public static void main(String[] args) {
		JFinal.start("WebContent", 10086, "/", 5);
	}
}
