package org.util.test;

import java.util.List;

import org.junit.Test;
import org.searchRetrleve.config.StringUtil;
import org.searchRetrleve.parser.HtmlLocalParser;
import org.searchRetrleve.util.HtmlUtil;
import org.searchRetrleve.util.MD5Util;

/** 
 * @author root  
 * @create 2015年12月9日 下午4:37:18
 * @version  1.0
 * 类说明 
 */
public class TestHtmlUtil {

	@Test
	public void testGetHtmlContent(){
		String addressUrl="http://news.sohu.com/s2007/newsreview/";
		String encoding="GBK";
		String result=HtmlUtil.getHtmlContent(addressUrl, encoding);
		System.out.println(result);
			
	}
	
	@Test
	public void testStringUtil(){
		
		//<h1 itemprop="headline">报告称中国明年真实工资将增长8% 领先全球</h1>		
		String regex="<h1 itemprop=\"headline\">(.*)</h1>";
		String url="http://business.sohu.com/20151209/n430502341.shtml";
		String content=HtmlUtil.getHtmlContent(url);
		String result=StringUtil.getContentRegex(regex, content);
		System.out.println(result);
			
	}
	
	@Test
	public void testGetAllPath(){
		String filePath="D:\\Eclipsework\\Heritrix\\jobs\\sohu-20151208032340501\\mirror";
		List<String> fileList=new StringUtil(filePath).fileList;
		System.out.println(fileList.size());
		
	}
	
	@Test
	public void testSingleParseNews(){
		HtmlLocalParser hlp=new HtmlLocalParser();
		String input="D:\\Eclipsework\\Heritrix\\jobs\\"
				+ "sohu-20151208032340501\\mirror\\news.sohu.com\\20151208\\n430185010.shtml";
		String content=StringUtil.getLocalContent(input);
		
		//hlp.singleParseNews(content);		
	}
	
	@Test
	public void testGetContentRegex(){
		String regex="[\u4e00-\u9fa5a-zA-Z0-9]*/n(.?)";
		String content="张高丽/nr 会 见/v 新加波/ns 副/b 总理/n 彰武/nr";
		String splitMark="";
		
		String result=StringUtil.getContentRegex(regex, content, splitMark);
		
		System.out.println(result);
		
	}
	
	@Test
	public void testGetParams(){
		String reslut="";
		String fileName="jdbc.properties";
		reslut=StringUtil.getPropertiesParams("jdbc.url", fileName);
		System.out.println(""+reslut);
	}
	
	
	/**
	 * 测试MD5
	 * void
	 */
	@Test
	public void testMD5(){
		String str="System.out.println(str);";
		System.out.println(str);	
		MD5Util md5Util=new MD5Util();
		String result=md5Util.getMD5ofStr(str);
		System.out.println(result+"\t"+result.length());
		System.out.println("===========");
		String  md=md5Util.getMD5ofStr(md5Util.getMD5ofStr(result));
		System.out.println(md+"==md\t"+md.length());
		convertMD5(convertMD5(result));
	}
	
	
	public String convertMD5(String str){
		char[] ch=str.toCharArray();
		for(int i=0;i<ch.length;i++){
			ch[i]=(char) (ch[i] ^ 't');
		}
		System.out.println(new String(ch));
		return new String(ch);
	}
	
}
