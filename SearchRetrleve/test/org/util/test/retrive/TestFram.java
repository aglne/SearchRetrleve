package org.util.test.retrive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.junit.Test;
import org.searchRetrleve.api.FullTextIndexParams;
import org.searchRetrleve.api.FullTextResult;
import org.searchRetrleve.api.FullTextSearchParams;
import org.searchRetrleve.api.ServerFactory;
import org.searchRetrleve.spi.FullSolrResult;
import org.searchRetrleve.spi.FullSolrService;

/** 
 * @author root  
 * @create 2016年3月30日 下午9:56:12
 * @version  1.0
 * 类说明 
 */
public class TestFram {
	
	FullSolrService fullSolr = null;
	
	public void beaginServer(){
		HashMap<String,String> params =new HashMap<String,String>();
		params.put("type", "solr");
		params.put("serverName", "test");
		params.put("url", "http://localhost:8080/solr");
		ServerFactory factory = new ServerFactory();
		fullSolr =(FullSolrService)factory.creaTextService(params);	
		
		fullSolr.setSeverName("test");
	}
	
	
	@Test
	public void testIndex(){		
		beaginServer();	
		FullTextIndexParams indexParams=new FullTextIndexParams();
		List<Map<String, Object>> indexData=new  ArrayList<Map<String, Object>>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		Map<String, Object> map3 = new HashMap<String, Object>();
		
		map1.put("id", "1");
		map1.put("name", "中国");
		map1.put("content", "城市上海");
		map1.put("desc", "天气好冷");
		
		map2.put("id", "2");
		map2.put("name", "广东");
		map2.put("content", "东莞");
		map2.put("desc", "天气好热吧");
		
		map3.put("id", "3");
		map3.put("name", "新加坡");
		map3.put("content", "首府");
		map3.put("desc", "大海之中的国度");
		
		
		indexData.add(map1);
		indexData.add(map2);
		indexData.add(map3);
		indexParams.setIndexData(indexData);
		
		fullSolr.doIndex(indexParams);
	}
	
	
	@Test
	public void testQuery(){		
		beaginServer();	
		FullTextResult result=new FullSolrResult();
		
		FullTextSearchParams fullSearchParams=new FullTextSearchParams();
		
		
		List<Map<String,String>> assignmetFields=new ArrayList<Map<String,String>>();
		Map<String,String> map = new HashMap<String, String>();
		map.put("name", "OR");
		map.put("desc", "OR");
		assignmetFields.add(map);
		fullSearchParams.setAssignFieldsList(assignmetFields);
		
		/**
		List<String> assignmetFields=new ArrayList<String>();
		assignmetFields.add("name");
		assignmetFields.add("desc");		
		fullSearchParams.setAssignmetFields(assignmetFields);
		**/
		String queryWord="天气";
		fullSearchParams.setQueryWord(queryWord);
		
		//排序
		Map<String, Boolean> sortmap =new HashMap<String, Boolean>();
		sortmap.put("id", false);
		fullSearchParams.setSortField(sortmap);
		
		//过滤
		Map<String, String> filterMap=new HashMap<String,String>();
		filterMap.put("desc", "好热");
		filterMap=null;
		fullSearchParams.setFilterField(filterMap);
		
		//分页
		fullSearchParams.setStartNum(0);
		//一页显示行数
		fullSearchParams.setPageNum(2);
		
		fullSearchParams.setIsFacet(true);
		String[] facet={"name"};
		fullSearchParams.setFacetField(facet);
		
		//设置显示域
		String[] viewFields = {"id","decs","name"};
		fullSearchParams.setViewFields(viewFields);
		
		//高亮
		String[] hightField={"desc","name"};
		fullSearchParams.setIsHighlighter(true);
		fullSearchParams.setHighlighterFields(hightField);
		fullSearchParams.setPreHighlighter("<em>");
		fullSearchParams.setPostHighlighter("</em>");
		fullSearchParams.setViewNum(10);
		
		result=fullSolr.doQuery(fullSearchParams);
		List list =result.getResultList();
		long num = result.getNumFound();
		System.out.println("total hit:"+num);
		
		for(int i=0;i<list.size();i++){
			System.out.println(list.get(i));
		}
		
		List<FacetField> facetList=result.getFacetList();
		for(FacetField facetField : facetList){
			System.out.println(facetField.getName());
			List<Count> counts=facetField.getValues();
			for(Count c : counts){
				System.out.println(c.getName()+":"+c.getCount());
			}		
		}			
	}
	
	

}
