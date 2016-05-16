package org.searchRetrleve.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.searchRetrleve.config.ConstanParams;
import org.searchRetrleve.config.StringUtil;

/** 
 * @author root  
 * @create 2015年12月20日 下午4:49:21
 * @version  1.0
 * 类说明 
 */
public class SimilaryUtil {

	
	/**
	 * 最小编辑距离
	 * int
	 * @param target
	 * @param source
	 * @return
	 */
	public static int getMinDistance(String target,String source){
		
		int result=0;
		int n=target.length();
		int m=source.length();
		int[][] distance=new int[n+1][m+1];
		distance[0][0]=0;
		
		for(int i=1;i<=n;i++){
			distance[i][0]=distance[i-1][0]+ins_cost(target.charAt(i-1));
		}
				
		for(int j=1;j<=m;j++){
			distance[0][j]=distance[0][j-1]+ins_cost(target.charAt(j-1));
		}
	
		for(int i=1;i<=n;i++){
			for(int j=1;j<=m;j++){
				int insert=distance[i-1][j]+ins_cost(target.charAt(i-1));
				int update=distance[i-1][j-1]+update_cost(target.charAt(i-1), source.charAt(j-1));
				int delete=distance[i][j-1]+del_cost(source.charAt(j-1));
				distance[i][j]=min(insert,min(update, delete));
			}
		}
		result=distance[n][m];
		
		return result;
	}	
		
	private static int min(int d1,int d2){
		return d1<d2?d1:d2;
	}
	
	private static int ins_cost(char c){
		return 1;
	}
	
	private static int del_cost(char c){
		return 1;
	}
	
	private static int update_cost(char c1,char c2){
		return c1!=c2?2:0;
	}
	
	
	/**
	 * 分词
	 * void
	 * @param inputPath
	 * @param outputPath
	 */
	public static void getSplitWord(String inputPath,String  outputPath){
		String content=StringUtil.getLocalContent(inputPath);
		String splitResult=SpiltWordUtil.ikSplit(content,ConstanParams.CHEN_LINE);
		StringUtil.writerFile(splitResult, outputPath);

	}
	
	/**
	 * 获取所有的词
	 * List<String>
	 * @param inputPath
	 * @return
	 */
	public static List<String> getAllWord(String inputPath){
		
		List<String> result=new ArrayList<String>();
		try{
			File file=new File(inputPath);
			InputStreamReader in=new InputStreamReader(new FileInputStream(file));
			BufferedReader reader=new BufferedReader(in);
			String temp="";
			
			while((temp=reader.readLine()) != null){
				result.add(temp);
			}
			reader.close();
			in.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	
	public static List<String> getAllWord(String inputPath,int row){
		
		List<String> result=new ArrayList<String>();
		try{
			File file=new File(inputPath);
			InputStreamReader in=new InputStreamReader(new FileInputStream(file));
			BufferedReader reader=new BufferedReader(in);
			String temp="";
			
			while((temp=reader.readLine()) != null){
				String temps[] =temp.split(ConstanParams.CHEN_TABLE);
				if(row > temps.length-1){
					result.add(temps[temps.length-1]);			
				}else{
					result.add(temps[row]);
				}
			}
			reader.close();
			in.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 获取文件中单个词出现的次数
	 * int
	 * @param word
	 * @param list
	 * @return
	 */
	public static Double getNumForWord(String word,List<String> list){
		Double number=0.0;
		
		for(String temp : list){
			if(temp.equals(word)){
				number++;
			}
		}		
		return number;
	}	
	
	/**
	 * 计算TF值
	 * void
	 * @param inputPath
	 * @param outputPath
	 */
	public static void getTF(String inputPath,String outputPath){
		String result="";
	    List<String> list=getAllWord(inputPath);
	    
	    Double allSize=Double.valueOf(list.size());
	    for(String word : list){
	    	Double number=getNumForWord(word, list);
	    	Double tf=number/allSize;
	    	result +=word+ConstanParams.CHEN_TABLE+tf+ConstanParams.CHEN_LINE;
	    }
	    StringUtil.writerFile(result, outputPath);	
	}

	/**
	 * 对文件内容添加到map
	 * Map<String,Double>
	 * @param inputPath
	 * @return
	 */
	public static Map<String, Double>  getMapForFile(String inputPath){		
		Map<String, Double> resultMap=new HashMap<String, Double>();
		try{
			File file=new File(inputPath);
			InputStreamReader in=new InputStreamReader(new FileInputStream(file));
			BufferedReader reader=new BufferedReader(in);
			String temp="";
			
			while((temp=reader.readLine()) != null){
				String[] word=temp.split(ConstanParams.CHEN_TABLE);
				if(word.length > 1){
					resultMap.put(word[0],Double.valueOf(word[1]));
				}	
			}
			reader.close();
			in.close();
		}catch(Exception e){
			e.printStackTrace();
		}	
		return resultMap;
	}
	
	/**
	 * 计算DF
	 * void
	 * @param inputPath
	 * @param inputPathAll
	 * @param outputPath
	 */
	public static void getDF(String inputPath,String inputPathAll,String outputPath){
		StringUtil sUtil=new StringUtil(inputPathAll);
		List<String> list=sUtil.fileList;
		String result="";
		try{
			File file=new File(inputPath);
			InputStreamReader in=new InputStreamReader(new FileInputStream(file));
			BufferedReader reader=new BufferedReader(in);
			String temp="";
			while((temp=reader.readLine())!=null){
				Double df=0.0;
				String[] line=temp.split(ConstanParams.CHEN_TABLE);
				
				for(String filename : list){
					String content=StringUtil.getLocalContent(filename);
					if(content.contains(line[0])){
						df++;
					}				
				}				
				result += (temp+ConstanParams.CHEN_TABLE+df+ConstanParams.CHEN_LINE);
			}			
			reader.close();
			in.close();
			StringUtil.writerFile(result, outputPath);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void getTFIdf(String inputPath,String inputPathAll,String outputPath){
		String result="";
		StringUtil sUtil=new StringUtil(inputPathAll);
		List<String> list=sUtil.fileList;
		Double size=Double.valueOf(list.size());
		
		try{
			File file=new File(inputPath);
			InputStreamReader in=new InputStreamReader(new FileInputStream(file));
			BufferedReader reader=new BufferedReader(in);
			String temp="";
			while((temp=reader.readLine())!=null){
				Double tfidf=0.0;
				String[] line=temp.split(ConstanParams.CHEN_TABLE);
				System.out.println(line.length+":="+line[0]+":="+line[1]);
				Double idf=Math.log(size/Double.valueOf(line[2]));
				tfidf=Double.valueOf(line[1])*idf;			
				result += (temp+ConstanParams.CHEN_TABLE+tfidf+ConstanParams.CHEN_LINE);
			}			
			reader.close();
			in.close();
			StringUtil.writerFile(result, outputPath);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public static void getSimilary(String inputPath1,String inputPath2,String outputPath){
		Double result=0.0;
		try{
			List<String> listA=getAllWord(inputPath1,1);
			List<String> listB=getAllWord(inputPath2,1);
			
			int len=0;
			if(listA.size() > listB.size()){
				len=listB.size();
			}else{
				len=listA.size();
			}
			
			Map<String,Double> map1=getMapForFile(inputPath1);
			Map<String,Double> map2=getMapForFile(inputPath2);
			Double fn1=0.0;
			Double fn2=0.0;
			Double fz=0.0;
			Double fm=0.0;
			
			Set<String> set1=map1.keySet();
			Iterator<String> iter1=set1.iterator();
			while(iter1.hasNext()){
				Double value=map1.get(iter1.next());
				fn1=value*value;
			}
			
			Set<String> set2=map2.keySet();
			Iterator<String> iter2=set2.iterator();
			while(iter1.hasNext()){
				Double value=map2.get(iter2.next());
				fn2=value*value;
			}
			
		    fm=Math.sqrt(fn1)+Math.sqrt(fn2);
		    
		    for(int i=0;i<len;i++){
		    	Double v1=Double.valueOf(listA.get(i));
		    	Double v2=Double.valueOf(listB.get(i));
		    	fz+=(v1*v2);
		    }
		    
		    result=fz/fm;
		    System.out.println(result);
		}catch(Exception e){
			e.printStackTrace();
		}
	
	}
		
}
