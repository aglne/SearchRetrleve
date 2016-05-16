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
	
	//搜索
	public FullTextResult doQuery(FullTextSearchParams params);
	
}
