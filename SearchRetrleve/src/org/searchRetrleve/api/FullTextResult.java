package org.searchRetrleve.api;

import java.util.List;

/** 
 * @author root  
 * @create 2016年1月5日 上午12:37:30
 * @version  1.0
 * 类说明 
 */
public interface FullTextResult {	
	
	public List  getResultList();
	
	public void  setResultList(List list);
	
	public List  getFacetList();
	
	public void  setFacetList(List list);
	
    public long getNumFound();
	
    public void setNumFound(long  num);
}
