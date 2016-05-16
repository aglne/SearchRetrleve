package org.util.test;

import org.junit.Test;
import org.searchRetrleve.solr.SolrUtil;

/** 
 * @author root  
 * @create 2015年12月28日 下午2:47:19
 * @version  1.0
 * 类说明 
 */
public class TestSolrUtil {

	
	@Test
	public void testSolrIndex(){
		SolrUtil.solrIndex();
	}
	
	
	@Test
	public void testSolrSearch(){
		SolrUtil.solrSearch();
	}
	
	
	@Test
	public void testSolrDelete(){
		SolrUtil.solrDelete();
	}
	
	@Test
	public void beginServer(){
		
	}
	
}
