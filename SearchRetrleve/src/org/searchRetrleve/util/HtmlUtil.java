package org.searchRetrleve.util;

import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.searchRetrleve.config.ConstanParams;
import org.searchRetrleve.config.StringUtil;

/** 
 * @author root  
 * @create 2015年12月9日 下午3:14:56
 * @version  1.0
 * 类说明 
 */
public class HtmlUtil {

	/*
	 * 获取网页内容
	 */
	public static String getHtmlContent(String addressUrl,String encoding){
		String result="";
		try{
			if(StringUtil.isEmpty(addressUrl)){
				return "";
			}			
			URL url=new URL(addressUrl);
			InputStreamReader  input=new InputStreamReader(url.openStream(),encoding);
			
			BufferedReader reader=new BufferedReader(input);
			String temp="";
			while((temp=reader.readLine())!= null){
				result+=temp+ConstanParams.CHEN_LINE;								
			}					
		}catch (Exception e){
			e.printStackTrace();
		}				
		return result;
	}
	
	/*
	 * 获取网页内容
	 */
	public static String getHtmlContent(String addressUrl){
		String result="";
		if(StringUtil.isEmpty(addressUrl)){
			return result;
		}	
		try {
			URL url=new URL(addressUrl);
			String code=getEncoding(url);
			if(StringUtil.isEmpty(code)){
				code="utf-8";
			}			
			result=getHtmlContent(addressUrl, code);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}		
		
		return result;
	}
	
	/**
	 * 获取网页编码
	 */
	public static String getEncoding(URL url){
		String result="";
		try{
			CodepageDetectorProxy codepage=CodepageDetectorProxy.getInstance();
			codepage.add(JChardetFacade.getInstance());
			result=codepage.detectCodepage(url).name();
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 在线获取
	 * String
	 * @param addressUrl
	 * @param encoding
	 * @return
	 */
	public static String getHtmlParser(String addressUrl,String encoding){
		String result="";
		if(StringUtil.isEmpty(addressUrl)){
			return result;
		}
		
		if(StringUtil.isEmpty(encoding)){
			encoding="utf-8";
		}
		
		try{
			Parser parser=new Parser(addressUrl);
			parser.setEncoding(encoding);
		    
			NodeFilter nodeFilter=new AndFilter(new TagNameFilter("h1"), 
					new HasAttributeFilter("itemprop", "headline"));
			NodeList nodeList=parser.extractAllNodesThatMatch(nodeFilter);
			parser.reset();
			Node node=nodeList.elementAt(0);
			
			result=node.toPlainTextString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	
}
