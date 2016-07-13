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
	
	//删除索引 by  ids
	private  List<String> ids=new ArrayList<String>();
	
	//删除索引by   id
	private  String id;
	
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

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
