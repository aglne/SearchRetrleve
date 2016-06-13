package org.searchRetrleve.api;
/** 
 * @author root  
 * @create 2016年1月5日 上午12:23:50
 * @version  1.0
 * 类说明 
 */
public interface FullTextService {
	
	//启动服务
	public int  beginService(String serverName);
	
	public int  beginService(String serverName,String url);
	//结束服务
	public int endService(String serverName);
	
	//索引
	public void doIndex(FullTextIndexParams params);
	
	//索引之前要做的事
	public  void preIndexMethod();
	
	//索引之后要做的事
	public  void afterIndexMethod();
	
	//修改索引
	public  void updateIndex();
	
	//修改索引之前
	public  void preUpdateIndex();
	
	//修改索引之后
	public  void afterUpdateIndex();
	
	//删除索引
	public  void deleteIndex();
	
	//删除索引之前
	public  void preDeleteIndex();
	
	//删除索引之后
	public  void afterDeleteIndex();
	
	//搜索
	public FullTextResult doQuery(FullTextSearchParams params);
	
}
