package org.searchRetrleve.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** 
 * @author root  
 * @create 2016年1月5日 上午12:38:36
 * @version  1.0
 * 类说明 
 */
public class FullTextIndexParams {

	//索要索引的数据
	private List<Map<String,Object>>  indexData=new  ArrayList<Map<String,Object>>();
	
	//索引路径
	private String indexPath="";

	public List<Map<String, Object>> getIndexData() {
		return indexData;
	}

	public void setIndexData(List<Map<String, Object>> indexData) {
		this.indexData = indexData;
	}

	public String getIndexPath() {
		return indexPath;
	}

	public void setIndexPath(String indexPath) {
		this.indexPath = indexPath;
	}
	
}
