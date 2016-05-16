package org.util.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.searchRetrleve.config.StringUtil;
import org.searchRetrleve.lucene.IndexUtil;

/** 
 * @author root  
 * @create 2015年12月23日 下午4:11:16
 * @version  1.0
 * 类说明 
 */
public class TestIndexUtil {
	
	@Test
	public void TestCreateIndex(){		
		String indexPath="D:\\Eclipsework\\SearchRetrleve\\indexPath";
		
		String sourcePath="D:\\Eclipsework\\SearchRetrleve\\parser";
		List<Map<String, Object>>  list=new ArrayList<Map<String,Object>>();
		StringUtil su=new StringUtil(sourcePath);
		Map<String, Object> map=null;
		List<String> filename=su.fileList;
		
		int id=0;
		for(String file : filename){
			System.out.println("filename:"+file);
			map=new HashMap<String, Object>();
			String name=StringUtil.getFileName(file);
			String content=StringUtil.getLocalContent(file);
			map.put("id", ++id+"");
			map.put("name", name);
			map.put("content", content);
			list.add(map);			
		}
		
		IndexUtil.createIndex(list,indexPath);	
	}
	
	@Test
	public void TestSearchIndex(){		
		String indexPath="D:\\Eclipsework\\SearchRetrleve\\indexPath";	
		IndexUtil.searchIndex(indexPath);		
	}
	
	@Test
	public void TestDeleteIndex(){		
		String indexPath="D:\\Eclipsework\\SearchRetrleve\\indexPath";	
		IndexUtil.deleteIndex(indexPath);		
	}
	
	@Test
	public void TestUpdateIndex(){		
		String indexPath="D:\\Eclipsework\\SearchRetrleve\\indexPath";	
		IndexUtil.updateIndex(indexPath);		
	}
	
	
}
