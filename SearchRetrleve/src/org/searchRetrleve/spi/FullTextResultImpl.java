package org.searchRetrleve.spi;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.response.FacetField;
import org.searchRetrleve.api.FullTextResult;

/** 
 * @author root  
 * @create 2016年1月5日 下午10:10:28
 * @version  1.0
 * 类说明 
 */
public class FullTextResultImpl implements FullTextResult {

	@SuppressWarnings("rawtypes")
	public  List  resultList=new ArrayList();
	
	public  List<FacetField> facetList=new ArrayList<FacetField>();
	
	public long numFound;

	@Override
	public List getResultList() {
		return resultList;
	}

	@Override
	public void setResultList(List list) {
		resultList=list;
	}

	@Override
	public List getFacetList() {
		return facetList;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setFacetList(List list) {
		this.facetList=list;
	}

	@Override
	public long getNumFound() {
		return numFound;
	}

	@Override
	public void setNumFound(long num) {
		this.numFound=num;
	}

	
	
	
	

}
