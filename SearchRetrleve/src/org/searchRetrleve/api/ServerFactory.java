package org.searchRetrleve.api;

import java.util.Map;

import org.searchRetrleve.config.ConstanParams;
import org.searchRetrleve.config.StringUtil;
import org.searchRetrleve.spi.FullSolrService;

/** 
 * @author root  
 * @create 2016年1月5日 下午11:25:43
 * @version  1.0
 * 类说明 
 */
public class ServerFactory {
	
	
	public FullTextService  creaTextService(Map<String, String>  paramMap){
		FullTextService service=null;
				
		try {
			String serverName=paramMap.get("serverName");
			String type=paramMap.get("type");
			
			if("solr".equals(type)){
				
				String url=paramMap.get("url");
				
				if(StringUtil.isEmpty(url)){
					url=StringUtil.getPropertiesParams(ConstanParams.SOLR_URL, ConstanParams.PROPERTIES_FILE);
				}
				
				String className=FullSolrService.class.getName();
				Class<?> c=Class.forName(className);			
				service=(FullTextService) c.newInstance();				
				service.beginService(serverName,url);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		return service;
	}
	
	
	
	

}
