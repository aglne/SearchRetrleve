package org.util.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.searchRetrleve.util.ExtractUtil;
import org.searchRetrleve.util.SimilaryUtil;
import org.searchRetrleve.util.SortUtil;

/** 
 * @author root  
 * @create 2015年12月19日 下午11:15:32
 * @version  1.0
 * 类说明 
 */
public class TestSortUtil {

	
	@Test
	public void testSortByValue(){
		Map<String,Integer> map=new HashMap<String,Integer>();
		map.put("a", 2);
		map.put("t", 5);
		map.put("u", 1);
		map.put("a", 6);
		map.put("j", 4);
		map.put("k", 9);
		map.put("h", 3);
		
		
		Map result=SortUtil.sortByValue(map,true);
		
		
		System.out.println("============");
		Set<String> set=result.keySet();
		
		Iterator iterator=set.iterator();
		while(iterator.hasNext()){
			String key=(String) iterator.next();
			Integer value=(Integer)result.get(key);
			System.out.println(key+":"+value);		
		}	
	}

	
	
	@Test
	public void testGetMinDistance(){
		String s1="tet";
		String s2="testget";
		int result=SimilaryUtil.getMinDistance(s2, s1);
		System.out.println(result);		
	}	
	
	
	@Test
	public void testGgetSplitWord(){
		String inputPath="D:\\Eclipsework\\SearchRetrleve\\parser\\n388620854.txt";
		String outputPath="D:\\Eclipsework\\SearchRetrleve\\wordSet\\siglneText\\splitword.txt";
		SimilaryUtil.getSplitWord(inputPath, outputPath);
	}
	
	@Test
	public void testGetNumForWord(){
		String word="张高丽";
		String inputPath="D:\\Eclipsework\\SearchRetrleve\\wordSet\\siglneText\\splitword.txt";
		List<String> list=SimilaryUtil.getAllWord(inputPath);
		Double number=SimilaryUtil.getNumForWord(word, list);
		
		System.out.println(number);
	}
	
	
	@Test
	public void testGetTF(){
		String inputPath="D:\\Eclipsework\\SearchRetrleve\\wordSet\\siglneText\\splitword.txt";
		String outputPath="D:\\Eclipsework\\SearchRetrleve\\wordSet\\siglneText\\splitTF.txt";
		
		SimilaryUtil.getTF(inputPath, outputPath);
				
	}
		
	@Test
	public void testGetDF(){
		String inputPath="D:\\Eclipsework\\SearchRetrleve\\wordSet\\siglneText\\splitTF.txt";
		String outputPath="D:\\Eclipsework\\SearchRetrleve\\wordSet\\siglneText\\splitDF.txt";
		String inputPathAll="D:\\Eclipsework\\SearchRetrleve\\wordSet\\siglneText\\sortTF";
			
		SimilaryUtil.getDF(inputPath, inputPathAll, outputPath);
	}
	
	
	@Test
	public void testGetTFIdf(){
		String inputPath="D:\\Eclipsework\\SearchRetrleve\\wordSet\\siglneText\\splitDF.txt";
		String outputPath="D:\\Eclipsework\\SearchRetrleve\\wordSet\\siglneText\\wordTFIDF.txt";
		String inputPathAll="D:\\Eclipsework\\SearchRetrleve\\wordSet\\siglneText\\sortTF";
		long start=System.currentTimeMillis();
		System.out.println("start:"+start);			
		SimilaryUtil.getTFIdf(inputPath, inputPathAll, outputPath);	
		System.out.println(System.currentTimeMillis()-start);	
	}
	
}
