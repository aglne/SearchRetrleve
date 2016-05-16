package org.util.test;

import java.util.List;

import javax.print.DocFlavor.STRING;

import org.junit.Test;
import org.searchRetrleve.config.StringUtil;
import org.searchRetrleve.util.ExtractUtil;
import org.searchRetrleve.util.IctclasUtil;
import org.searchRetrleve.util.SpiltWordUtil;

/** 
 * @author root  
 * @create 2015年12月10日 下午9:36:58
 * @version  1.0
 * 类说明 
 */
public class TestSplitWord {

	
	@Test
	public void testIKSplit(){
		
		String str="参考消息网12月8日报道 外媒援引泰国媒体12月2日报道说，中国和泰国之间几十亿美元的铁路项目由于存在重大分歧而暂缓";
		String result=SpiltWordUtil.ikSplit(str);
		System.out.println(result);
				
	}
	
	
	@Test
	public void testIctclasSplit(){
		String str="随后 温 总理 就 离开 了 舟曲县 城 ， 预计 温 总理 今天 下午 就 回到 北京 。 以上 就 是 今天 上午 的 最新 动态 ";
		String result=IctclasUtil.ictclasSplit(str);
		System.out.println(result);				
	}
	
	@Test
	public void testGeRules(){
		String  str="D:\\Eclipsework\\SearchRetrleve\\src\\rules.txt";
		List<String> list=ExtractUtil.geRules(str);
		for(String s : list){
			System.out.println(s);
		}
	
	}		
	
	
	@Test
	public void testSingleTextWord(){
		String rulesPath="D:\\Eclipsework\\SearchRetrleve\\src\\rules.txt";
		String sourcePath="D:\\Eclipsework\\SearchRetrleve\\ictclassplit";
		String outputPath="D:\\Eclipsework\\SearchRetrleve\\wordSet";
		
		StringUtil sUtil=new StringUtil(sourcePath);
		List<String> listNames=sUtil.fileList;
		
		for(String fileName : listNames){
			long start=System.currentTimeMillis();
			//String source=StringUtil.getLocalContent(fileName);
			String f=StringUtil.getFileName(fileName);
			System.out.println(fileName);
			ExtractUtil.singleTextWord(rulesPath, fileName, outputPath+"/"+f+".txt");
			long end=System.currentTimeMillis();
			System.out.println(""+(start-end));
			
		}
		
		String result="";
		
		System.out.println(result);
		
	}
	
	@Test
	public void testDeleteRepWord(){
		String inputPath="D:\\Eclipsework\\SearchRetrleve\\wordSet\\n388620854.txt";
		String outputPath="D:\\Eclipsework\\SearchRetrleve\\wordSet\\1.txt";
        
		ExtractUtil.deleteRepWord(inputPath, outputPath);	
	}	
	
	
	@Test
	public void testFilterNonWord(){
		String inputPath="D:\\Eclipsework\\SearchRetrleve\\wordSet\\n388620854.txt";
		String sourcePath="D:\\Eclipsework\\SearchRetrleve\\ictclassplit\\n388620854.txt";
		String outputPath="D:\\Eclipsework\\SearchRetrleve\\wordSet\\2.txt";
        
		ExtractUtil.filterNonWord(inputPath, sourcePath, outputPath);	
	}
	
	
	@Test
	public void testGetNumWord(){
		String inputPath="D:\\Eclipsework\\SearchRetrleve\\wordSet\\n388620854.txt";
		String outputPath="D:\\Eclipsework\\SearchRetrleve\\wordSet\\3.txt";
		ExtractUtil.getNumWord(inputPath, outputPath);
	}
	
	@Test
	public void testGetSortResultForStatics(){
		String inputPath="D:\\Eclipsework\\SearchRetrleve\\wordSet\\3.txt";
		String outputPath="D:\\Eclipsework\\SearchRetrleve\\wordSet\\4.txt";
		ExtractUtil.getSortResultForStatics(inputPath, outputPath);
	}
	
	
}
