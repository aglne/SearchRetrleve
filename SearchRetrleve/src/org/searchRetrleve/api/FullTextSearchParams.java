package org.searchRetrleve.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
 * @author root  
 * @create 2016年1月5日 上午12:42:41
 * @version  1.0
 * 类说明 
 */
public class FullTextSearchParams {

	//搜索关键词
	private String queryWord="";
	
	//指定搜索域
	//指定搜索域Map中： 第一个String为域名   例：name,desc等
	//             第二个String为关系   例：OR  AND等
	List<String>  assignmetFields=new ArrayList<String>();
	List<Map<String, String>> assignFieldsList = new ArrayList<Map<String, String>>();
	
	
	//显示域		
	private	String[] viewFields;	
	
	//是否高亮
	private Boolean isHighlighter=true;		
			
	//高亮域		
	private String[]  highlighterFields;	
	
	//高亮前缀
	private String preHighlighter="<em class=\"highlighter\"> ";
	
	//高亮后缀
	private String postHighlighter="</em>";
	
	//排序域   String需要排序的域   boolean值为 flase为降序  true为升序
	private Map<String, Boolean>  sortField=new HashMap<String, Boolean>();
	
	//过滤域
	private Map<String, String> filterField=new HashMap<String, String>();
	
	//开始行
	private  int startNum=0;
	
	//一页显示多少行
	private  int pageNum=20;
	
	//是否进行统计
	private  Boolean isFacet=false;
	
	//统计域
	private  String[] facetField;

	//结果返回数
	private  long numFound;
	
	//显示字数
	private  int viewNum;
	
	
	public String getQueryWord() {
		return queryWord;
	}

	public void setQueryWord(String queryWord) {
		this.queryWord = queryWord;
	}

	public List<String> getAssignmetFields() {
		return assignmetFields;
	}

	public void setAssignmetFields(List<String> assignmetFields) {
		this.assignmetFields = assignmetFields;
	}
	
	public List<Map<String, String>> getAssignFieldsList() {
		return assignFieldsList;
	}

	public void setAssignFieldsList(List<Map<String, String>> assignFieldsList) {
		this.assignFieldsList = assignFieldsList;
	}

	public String[] getViewFields() {
		return viewFields;
	}

	public void setViewFields(String[] viewFields) {
		this.viewFields = viewFields;
	}

	public Boolean getIsHighlighter() {
		return isHighlighter;
	}

	public void setIsHighlighter(Boolean isHighlighter) {
		this.isHighlighter = isHighlighter;
	}

	public String[] getHighlighterFields() {
		return highlighterFields;
	}

	public void setHighlighterFields(String[] highlighterFields) {
		this.highlighterFields = highlighterFields;
	}

	public String getPreHighlighter() {
		return preHighlighter;
	}

	public void setPreHighlighter(String preHighlighter) {
		this.preHighlighter = preHighlighter;
	}

	public String getPostHighlighter() {
		return postHighlighter;
	}

	public void setPostHighlighter(String postHighlighter) {
		this.postHighlighter = postHighlighter;
	}

	public Map<String, Boolean> getSortField() {
		return sortField;
	}

	public void setSortField(Map<String, Boolean> sortField) {
		this.sortField = sortField;
	}

	public Map<String, String> getFilterField() {
		return filterField;
	}

	public void setFilterField(Map<String, String> filterField) {
		this.filterField = filterField;
	}

	public int getStartNum() {
		return startNum;
	}

	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public Boolean getIsFacet() {
		return isFacet;
	}

	public void setIsFacet(Boolean isFacet) {
		this.isFacet = isFacet;
	}

	public String[] getFacetField() {
		return facetField;
	}

	public void setFacetField(String[] facetField) {
		this.facetField = facetField;
	}

	public long getNumFound() {
		return numFound;
	}

	public void setNumFound(long numFound) {
		this.numFound = numFound;
	}

	public int getViewNum() {
		return viewNum;
	}

	public void setViewNum(int viewNum) {
		this.viewNum = viewNum;
	}
	
	
	
	
}
