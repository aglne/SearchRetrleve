package org.searchRetrleve.spi;

import org.searchRetrleve.api.FullTextIndexParams;
import org.searchRetrleve.api.FullTextResult;
import org.searchRetrleve.api.FullTextSearchParams;
import org.searchRetrleve.api.FullTextService;

/** 
 * @author root  
 * @create 2016年1月5日 上午12:44:24
 * @version  1.0
 * 类说明 
 */
public class FullTextServiceImpl  implements FullTextService{

	@Override
	public int beginService(String serverName) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int beginService(String serverName, String url) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	
	@Override
	public int endService(String serverName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void doIndex(FullTextIndexParams params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public FullTextResult doQuery(FullTextSearchParams params) {
		// TODO Auto-generated method stub
		return null;	
	}

	@Override
	public void preIndexMethod() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterIndexMethod() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateIndex() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preUpdateIndex() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterUpdateIndex() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteIndex() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preDeleteIndex() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterDeleteIndex() {
		// TODO Auto-generated method stub
		
	}

}
