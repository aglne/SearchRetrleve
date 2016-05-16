package org.searchRetrleve.solr;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

/** 
 * @author root  
 * @create 2015年12月28日 下午2:24:17
 * @version  1.0
 * 类说明 
 */
public class SolrUtil {

	
	public static void solrIndex(){
		try{
			String url="http://localhost:8080/solr";
			HttpSolrServer server=new HttpSolrServer(url);
			SolrInputDocument document1=new SolrInputDocument();
			SolrInputDocument document2=new SolrInputDocument();
			SolrInputDocument document3=new SolrInputDocument();
			
			document1.addField("id", "1");
			document1.addField("name", "中国");
			document1.addField("content", "城市上海");
			document1.addField("desc", "天气好冷");
			
			document2.addField("id", "2");
			document2.addField("name", "广东");
			document2.addField("content", "东莞");
			document2.addField("desc", "天气好热吧");
			
			document3.addField("id", "3");
			document3.addField("name", "新加坡");
			document3.addField("content", "首府");
			document3.addField("desc", "大海之中的国度");
			
			server.add(document1);
			server.add(document2);
			server.add(document3);
			server.commit();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public static void solrSearch(){
		try{
			String url="http://localhost:8080/solr";
			HttpSolrServer server=new HttpSolrServer(url);
			
			SolrQuery params=new SolrQuery("content:城市上海 ");
			
			//默认搜索域
//			params.set("df", "content");
			
			//显示域
			String[] field={"id","name","content","desc"};
			params.setFields(field);
			
			//高亮
			params.addHighlightField("content");
			params.setHighlight(true);
			params.setHighlightSimplePre("<em class=\"highlight\">");
			params.setHighlightSimplePost("</em>");
			
			QueryResponse response=server.query(params);	
			SolrDocumentList docList=response.getResults();
			Map<String, Map<String, List<String>>> map=response.getHighlighting();
			
			System.out.println(docList.getNumFound());
			for(SolrDocument doc : docList){
				System.out.println("name="+doc.get("name"));
				System.out.println("content="+doc.getFieldValue("content"));
				System.out.println("h1="+map.get(doc.getFieldValue("id")).get("content").get(0));
				System.out.println("desc="+doc.get("desc"));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public static void solrDelete(){
		try{
			String url="http://localhost:8080/solr";
			HttpSolrServer server=new HttpSolrServer(url);
		    server.deleteById("1");
		    server.commit();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}
