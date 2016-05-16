package org.searchRetrleve.parser;

import java.util.List;

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
 * @create 2015年12月9日 下午11:16:18
 * @version  1.0
 * 类说明 
 */
public class HtmlLocalParser {

	
	public void parserNews(String inputPath,String ouputPath){
		
		try{
		   StringUtil util=new StringUtil(inputPath);
		   List<String> allPath=util.fileList;
		   
		   for(String path : allPath){
			   String htmlContent=StringUtil.getLocalContent(path);
			   String filename=StringUtil.getFileName(path);
			   singleParseNews(htmlContent,ouputPath+"/"+filename+".txt");
		   }		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
	}
	
	
	public void singleParseNews(String htmlContent,String outputPath){
		
		try{
			Parser parser=Parser.createParser(htmlContent, "gb2312");
			NodeFilter titleFilter=new AndFilter(new TagNameFilter("h1"), new HasAttributeFilter("itemprop", "headline"));
			NodeList titleList=parser.parse(titleFilter);
			Node titleNode=titleList.elementAt(0);
			String title="";
			if(titleNode != null){					
				title=titleNode.toPlainTextString();
			}		
			parser.reset();
			
			
			NodeFilter dateFilter=new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "time"));
			NodeList dateList=parser.parse(dateFilter);
			Node dateNode=dateList.elementAt(0);
			String date="";
			if(dateNode != null){
				date=dateNode.toPlainTextString();
			}		
			parser.reset();
			
			
			NodeFilter sourceFilter=new AndFilter(new TagNameFilter("span"), new HasAttributeFilter("itemprop", "name"));
			NodeList sourceList=parser.parse(sourceFilter);
			Node sourceNode=sourceList.elementAt(0);
			String source="";
			if(sourceNode != null){
				source=sourceNode.toPlainTextString();
			}		
			parser.reset();
			
			
			NodeFilter contentFilter=new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("itemprop", "articleBody"));
			NodeList contentList=parser.parse(contentFilter);
			Node contentNode=contentList.elementAt(0);
			String content="";
			if(contentNode != null){
				content=contentNode.toPlainTextString();
			}					
			parser.reset();
			
			String result=title+ConstanParams.CHEN_LINE+date+ConstanParams.CHEN_LINE
					+source+ConstanParams.CHEN_LINE+content;
			StringUtil.writerFile(result, outputPath);
			
//			System.out.println(title);
//			System.out.println(date);
//			System.out.println(source);
//			System.out.println(content);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
	}
	
	
	
}
