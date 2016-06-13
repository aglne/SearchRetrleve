package org.searchRetrleve.spi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.search.SortField;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.searchRetrleve.api.FullTextIndexParams;
import org.searchRetrleve.api.FullTextResult;
import org.searchRetrleve.api.FullTextSearchParams;
import org.searchRetrleve.config.ConstanParams;
import org.searchRetrleve.config.StringUtil;

/** 
 * @author root  
 * @create 2016年1月5日 下午4:39:58
 * @version  1.0
 * 类说明 
 */
public class FullSolrService  extends FullTextServiceImpl {

	public Map<String,SolrServer>  solrServerMap=new HashMap<String, SolrServer>();
	
	
	public String severName;
	
	public String getSeverName() {
		return severName;
	}

	public void setSeverName(String severName) {
		this.severName = severName;
	}

	@Override
	public void preIndexMethod() {
		// TODO Auto-generated method stub
		super.preIndexMethod();
	}

	@Override
	public void afterIndexMethod() {
		// TODO Auto-generated method stub
		super.afterIndexMethod();
	}

	@Override
	public void updateIndex() {
		// TODO Auto-generated method stub
		super.updateIndex();
	}

	@Override
	public void preUpdateIndex() {
		// TODO Auto-generated method stub
		super.preUpdateIndex();
	}

	@Override
	public void afterUpdateIndex() {
		// TODO Auto-generated method stub
		super.afterUpdateIndex();
	}

	@Override
	public void deleteIndex() {
		// TODO Auto-generated method stub
		super.deleteIndex();
	}

	@Override
	public void preDeleteIndex() {
		// TODO Auto-generated method stub
		super.preDeleteIndex();
	}

	@Override
	public void afterDeleteIndex() {
		// TODO Auto-generated method stub
		super.afterDeleteIndex();
	}

	@Override
	public int beginService(String serverName) {
		SolrServer solrServer=solrServerMap.get(serverName);
		if(solrServer == null){
			solrServer = beginServer();
			solrServerMap.put(serverName, solrServer);
			return 1;
		}		
		return -1;
	}

	@Override
	public int beginService(String serverName, String url) {
		if(StringUtil.isEmpty(url)){
			return -1;
		}	
		SolrServer solrServer=solrServerMap.get(serverName);
		if(solrServer == null){
			solrServer = beginServer(url);
			solrServerMap.put(serverName, solrServer);
			return 1;
		}		
		return -1;
	}

	
	@Override
	public int endService(String serverName) {
		return super.endService(serverName);
	}

	@Override
	public void doIndex(FullTextIndexParams params) {
		long startIndexTime = System.currentTimeMillis();	
		preIndexMethod();
		long endIndexTime = System.currentTimeMillis();
		System.out.println("before index time"+(endIndexTime-startIndexTime)+" ms ");
		try{
			List<SolrInputDocument> docList=new ArrayList<SolrInputDocument>();
			List<Map<String, Object>> indexDataMap=params.getIndexData();
			
			SolrInputDocument document=null;
			for(Map<String, Object> dataMap : indexDataMap){
				document=new SolrInputDocument();
				Set<String> set=dataMap.keySet();
				Iterator<String> iter=set.iterator();
				while(iter.hasNext()){
					String key=iter.next();
					Object value=dataMap.get(key);
				    document.addField(key, value);
				}
				docList.add(document);
			}
			
			this.solrServerMap.get(severName).add(docList);
			this.solrServerMap.get(severName).commit();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		long startAfterIndexTime = System.currentTimeMillis();
		afterIndexMethod();
		long endAfterIndexTime = System.currentTimeMillis();
		System.out.println("after index time"+(endAfterIndexTime-startAfterIndexTime)+" ms ");
	}

	@Override
	public FullTextResult doQuery(FullTextSearchParams params) {
		FullTextResult result=new FullSolrResult();
		try{
			String queryWord=params.getQueryWord();
			
			if(StringUtil.isEmpty(queryWord)){
				return null;
			}
			
		    List<String> assignmetFields = params.getAssignmetFields();
		    List<Map<String, String>> assignFields = params.getAssignFieldsList();   
		    String  queryStr="";
		    String  lastValue = "";
		    if(assignmetFields != null && assignmetFields.size() > 0){		    	
		    	for(String field : assignmetFields){
		    		queryStr += field+":"+queryWord+"  OR  ";
		    	}		    	
		    	int endIndex = queryStr.lastIndexOf("  OR  ");
			    queryStr = queryStr.substring(0, endIndex);		    	
		    }else if(assignFields != null && assignFields.size() > 0){
		    	for(Map<String, String> assigField : assignFields ){
			    	Set<String> set = assigField.keySet();
			    	Iterator<String> it = set.iterator();		    	
			    	while(it.hasNext()){
			    		String key = it.next();
			    		String value = assigField.get(key);		    		
			    		queryStr += key+":"+queryWord+ConstanParams.CHEN_SPACE+value+ConstanParams.CHEN_SPACE;		    		
			    		lastValue = value;
			    	}	    		    	
			    }		    	    		    
			    int endIndex = queryStr.lastIndexOf(ConstanParams.CHEN_SPACE+lastValue+ConstanParams.CHEN_SPACE);
			    queryStr = queryStr.substring(0, endIndex);
		    }else{
		    	queryStr = queryWord;	    	
		    }

		    System.out.println("queryStr=="+queryStr);
		    SolrQuery query=new SolrQuery();
		    query.setQuery(queryStr);
		     	    
		    //设置显示域
		    String[] viewFields = params.getViewFields();
		    query.setFields(viewFields);
		    
		    //高亮
		    boolean  isHightlight = params.getIsHighlighter();
		    String[] hightlightFields = params.getHighlighterFields();
		    //高亮前后缀
		    if(isHightlight && hightlightFields != null && hightlightFields.length>0){
		    	query.setHighlight(true);
		    	query.setHighlightSimplePre(params.getPreHighlighter());
		    	query.setHighlightSimplePost(params.getPostHighlighter());
		    	query.setHighlightFragsize(params.getViewNum());
		    }
		    
		    //分页
		    query.setStart(params.getStartNum());
		    //一页显示行数
		    query.setRows(params.getPageNum());
		    
		    //统计
		    boolean isFact=params.getIsFacet();
		    String[] facetField=params.getFacetField();
		    if(isFact && facetField != null && facetField.length >0){
		    	query.addFacetField(facetField);
		    }
		    
		    
		    //排序
		    Map<String, Boolean>  sortField=params.getSortField();   
		    if(sortField != null){
		    	Set<String> set=sortField.keySet();
		    	Iterator<String> it=set.iterator();
		    	while(it.hasNext()){
		    		String key=it.next();
		    		Boolean value=sortField.get(key);
		    		if(value){
		    			query.addSort(key, ORDER.asc);
		    		}else{
		    			query.addSort(key, ORDER.desc);
		    		}			    		
		    	}	    		    	
		    }
		    
		    //过滤
		    Map<String, String> filterMap=params.getFilterField();
		    if(filterMap!=null){
		        StringBuilder buff=new StringBuilder();
		    	Set<String> set=filterMap.keySet();
		    	Iterator<String> iter=set.iterator();
		    	while(iter.hasNext()){
		    		String key=iter.next();
		    		String value=filterMap.get(key);
		    		buff.append(key+":"+value);
		    		buff.append("-中国-");
		    	}
		    	String[] filterFields=buff.toString().split("-中国-");
		    	query.addFilterQuery(filterFields);
		    }
		   	    
		    QueryResponse response=this.solrServerMap.get(severName).query(query);
		    SolrDocumentList list=response.getResults();
		    result.setNumFound(list.getNumFound());
		    
		    
		    SolrDocument document = new SolrDocument();
		    SolrDocumentList hightList = new SolrDocumentList();
		    
		    //高亮结果
		    if(isHightlight && hightlightFields != null && hightlightFields.length>0){
		    	Map<String,Map<String,List<String>>> map = response.getHighlighting();
		    	for(int i=0;i<list.size();i++){		    		
		    		for(int j=0;j<hightlightFields.length;j++){
		    			document=list.get(i);
		    			
		    			if(map!=null && map.get(document.getFieldValue("id"))!=null && map.get(document.getFieldValue("id")).get(hightlightFields[j])!=null){
		    				document.setField(hightlightFields[j], map.get(document.getFieldValue("id")).get(hightlightFields[j]).get(0));
		    			}else{
		    				document.setField(hightlightFields[j], document.getFieldValue(hightlightFields[j]));
		    			}
		    		}
		    		hightList.add(document);
		    	}
		    	result.setResultList(hightList);
		    }else{
		    	result.setResultList(list);
		    }    
		    
		    if(isFact && facetField != null && facetField.length >0){
		    	List<FacetField> facetList=response.getFacetFields();
		    	result.setFacetList(facetList);	    	
		    }
		    
		    
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	public SolrServer  beginServer(){
		SolrServer solrServer=null;
		try{
			String url=StringUtil.getPropertiesParams(ConstanParams.SOLR_URL, ConstanParams.PROPERTIES_FILE);
			solrServer=new HttpSolrServer(url);
		}catch(Exception e){
			e.printStackTrace();
		}		
		return solrServer;		
	}
	
	public SolrServer  beginServer(String url){
		
		SolrServer solrServer=null;
		try{
			solrServer=new HttpSolrServer(url);
		}catch(Exception e){
			e.printStackTrace();
		}		
		return solrServer;		
	}
	
	
	
	
}
