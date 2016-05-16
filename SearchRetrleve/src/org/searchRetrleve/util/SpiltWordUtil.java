package org.searchRetrleve.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.searchRetrleve.config.ConstanParams;
import org.searchRetrleve.config.StringUtil;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

/** 
 * @author root  
 * @create 2015年12月10日 下午9:21:48
 * @version  1.0
 * 类说明 
 */
public class SpiltWordUtil {

	/**
	 * ik分词
	 * String
	 * @param text
	 * @return
	 */
	public static String ikSplit(String text,String mark){
		String  result="";
		if(StringUtil.isEmpty(text)){
			return result;
		}
		
		try{
			byte[] bt=text.getBytes();
			InputStream in=new ByteArrayInputStream(bt);
			Reader reader=new InputStreamReader(in);
			IKSegmenter iks=new IKSegmenter(reader, true);
			
			Lexeme lexeme;
			
			while((lexeme=iks.next()) != null){
				result+=lexeme.getLexemeText()+mark;
			}		
		}catch(Exception e){
			e.printStackTrace();
		}	
		return result;
	}
	
	
	public static String ikSplit(String text){
		return ikSplit(text,ConstanParams.CHEN_SPACE);
	}
	
	
	
}
