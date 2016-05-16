package org.searchRetrleve.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * @author root  
 * @create 2015年12月9日 下午5:13:07
 * @version  1.0
 * 类说明 
 */
public class StringUtil {

	
	public StringUtil(String filePath){
		getAllPath(filePath);
	}
	
	
	/**
	 * 判断字符为空
	 * boolean
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		if(null == str || "".equals(str)){
		  return true;	
		}				
		return false;
	}
	
	/**
	 * 判断字符不为空
	 * boolean
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str){
		if(null != str && !"".equals(str)){
		  return true;	
		}				
		return false;
	}
	
	/**
	 * 利用正则表达式，进行匹配单个内容并获取
	 * String
	 * @param regex
	 * @param content
	 * @return
	 */
	public static String getContentRegex(String regex,String content){
		String result="";
		if(isEmpty(regex) || isEmpty(content)){
			return "";
		}
		
		try{
			Pattern pattern=Pattern.compile(regex);
			Matcher matcher=pattern.matcher(content);
			if(matcher.find()){
				result=matcher.group(1);
			}			
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 利用正则表达式，进行匹配多个内容并获取
	 * String
	 * @param regex
	 * @param content
	 * @param splitMark
	 * @return
	 */
	public static String getContentRegex(String regex,String content,String splitMark){
		String result="";
		if(isEmpty(regex) || isEmpty(content)){
			return "";
		}
		
		if(isEmpty(splitMark)){
			splitMark=ConstanParams.CHEN_LINE;
		}
		try{
			Pattern pattern=Pattern.compile(regex);
			Matcher matcher=pattern.matcher(content);
			while(matcher.find()){
				result+=matcher.group()+ConstanParams.CHEN_LINE;
			}			
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	
	public List<String> fileList=new ArrayList<String>();
	/**
	 * 获取文件路径
	 * void
	 * @param filePath
	 */
	public void getAllPath(String filePath){
		try{
			File dir=new File(filePath);
			File[] files=dir.listFiles();
			
			for(File file : files){
				if(file.isDirectory()){
					getAllPath(file.getAbsolutePath());
				}else{
			//		System.out.println(file.getAbsolutePath());
					fileList.add(file.getAbsolutePath());					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
    /**
     * 读取文件内容
     * String
     * @param inputPath
     * @return
     */
	public static String getLocalContent(String inputPath){
		String result="";
		
		if(isEmpty(inputPath)){
			return "";
		}
		try{
			InputStreamReader reader=new InputStreamReader(new FileInputStream(inputPath),"gb2312");
			BufferedReader buffer=new BufferedReader(reader);
			
			String str="";
			while((str=buffer.readLine()) != null){
				result+=(str+ConstanParams.CHEN_LINE);
			}
			buffer.close();
		    reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 将内存字符写入到文件
	 * boolean
	 * @param str
	 * @param outputPath
	 * @return
	 */
	public static boolean  writerFile(String str,String outputPath){
		boolean flag=false;
		
		if(isEmpty(outputPath)){
			return flag;
		}
		
		OutputStreamWriter output=null;
		BufferedWriter bw=null;
		try{
			output=new OutputStreamWriter(new FileOutputStream(outputPath));
			bw=new BufferedWriter(output);
			bw.write(str);
			flag=true;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(bw != null){
					bw.close();
				}				
				if(output != null){
					output.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}			
		}	
		return flag;
	}
	
	/**
	 * 获取文件名
	 * String
	 * @param path
	 * @return
	 */
	public static String getFileName(String path){
		String filename="";
		if(isEmpty(path)){
			return filename;
		}
		
		if(path.contains("/")){
			filename=path.substring(path.lastIndexOf("/")+1, path.lastIndexOf("."));			
		}else if(path.contains("\\")){
			filename=path.substring(path.lastIndexOf("\\")+1, path.lastIndexOf("."));
		}else{
			filename=path;
		}		
		return filename;
	}
	
	/**
	 * 根据txt文件路径,获取内容
	 * List<String>
	 * @param inputPath
	 * @return
	 */
	public static List<String>  getContentFromPath(String inputPath){
		List<String> result=new ArrayList<String>();
		try{
			File file=new File(inputPath);
			InputStreamReader in=new InputStreamReader(new FileInputStream(file));
			BufferedReader br=new BufferedReader(in);
			String temp="";
			while((temp=br.readLine())!=null){
				result.add(temp);
			}
			
			if(br != null){
				br.close();
			}
			if(in != null){
				in.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}		
		
		return result;
	}
	
	/**
	 * 获取属性文件参数
	 * String
	 * @param key
	 * @param fileName
	 * @return
	 */
	public static String getPropertiesParams(String key, String fileName){
		String result="";
		
		if(isEmpty(fileName) || isEmpty(key)){
			return result;
		}
		try{
			Properties properties=loadProperties(fileName);
			result=properties.getProperty(key);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 加载属性文件
	 * Properties
	 * @param fileName
	 * @return
	 */
	public static Properties loadProperties(String fileName){
		Properties properties=new Properties();
		try{
			ClassLoader classLoader=Thread.currentThread().getContextClassLoader();
			if(classLoader == null){
				classLoader=StringUtil.class.getClassLoader();
			}		
			InputStream in=classLoader.getResourceAsStream(fileName);
			properties.load(in);	
		}catch(Exception e){
			e.printStackTrace();
		}		
		return properties;
	}
	
	
	
}
