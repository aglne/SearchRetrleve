package org.searchRetrleve.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.searchRetrleve.config.ConstanParams;
import org.searchRetrleve.config.StringUtil;

/** 
 * @author root  
 * @create 2015年12月13日 下午9:10:42
 * @version  1.0
 * 类说明 
 */
public class ExtractUtil {

	
	public static List<String> geRules(String inputPath){
	    List<String> result=new ArrayList<String>();
		
	    File file=new File(inputPath);
	    
	    InputStreamReader reader=null;
	    BufferedReader br=null;
	    
	    String regex="[\u4e00-\u9fa5a-zA-Z0-9]*";
		try{
			reader=new InputStreamReader(new FileInputStream(file));
			br = new  BufferedReader(reader);
			String line="";
			while((line = br.readLine()) != null){
				String[] temp=line.split(ConstanParams.CHEN_SINGLE_SPACE);
	            String str="";
	            for(String rule : temp){
	            	str+=(regex+"/"+rule+ConstanParams.CHEN_SINGLE_SPACE);
	            }
	            str=str.substring(0,str.length()-1);
	            result.add(str);
			}
	
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if(br != null){
					br.close();
				}
				if(reader != null){
					reader.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 单个文本 过滤
	 * void
	 * @param rulesPath
	 * @param sourcePath
	 * @param outputPath
	 */
	public static void singleTextWord(String rulesPath,String sourcePath,String outputPath){
		
		try{
			String result="";
			List<String> rulesList=geRules(rulesPath);			
			String content=StringUtil.getLocalContent(sourcePath);
			for(String rules : rulesList){
				result+=StringUtil.getContentRegex(rules, content, "");
			}			
			StringUtil.writerFile(result, outputPath);		
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	/**
	 * 过滤特殊词
	 * void
	 * @param inputPath
	 * @param outputPath
	 */
	public static  void filterWord(String inputPath,String outputPath){
		try{
			String result="";
			File file=new File(inputPath);
			InputStreamReader in=new InputStreamReader(new FileInputStream(file));
			BufferedReader reader=new BufferedReader(in);
			String temp="";
			
			while((temp=reader.readLine()) != null){
				String[]  words=temp.split(ConstanParams.CHEN_SINGLE_SPACE);
				if(words.length > 1){
					result+=words+ConstanParams.CHEN_LINE;
				}else{
					String[] wordTemp=words[0].split("/");
					if(wordTemp.length != 1){
						result+=words+ConstanParams.CHEN_LINE;
					}						
				}				
			}
			StringUtil.writerFile(result, outputPath);
			reader.close();
			in.close();
		}catch(Exception e){
			e.printStackTrace();
		}
			
	}
	
	/**
	 * 去除重复
	 * void
	 * @param inputPath
	 * @param outputPath
	 */
	public static void deleteRepWord(String inputPath,String outputPath){
		
		try{
			List<String> resultList=new ArrayList<String>();
			String result="";
			File file=new File(inputPath);
			InputStreamReader in=new InputStreamReader(new FileInputStream(file));
			BufferedReader reader=new BufferedReader(in);
			String temp="";
			
			while((temp=reader.readLine()) != null){
				if(!resultList.contains(temp)){
					resultList.add(temp);
					result+=temp+ConstanParams.CHEN_LINE;
				}			
			}
			StringUtil.writerFile(result, outputPath);
			reader.close();
			in.close();
		}catch(Exception e){
			e.printStackTrace();
		}	
	
	}
	
	/**
	 * 完整性和非完整性过滤
	 * void
	 * @param inputPath
	 * @param outputPath
	 */
	public static void filterNonWord(String inputPath,String sourcePath,String outputPath){
		try{
			String result="";
			List<String> wordresult=StringUtil.getContentFromPath(inputPath);
			for(String words : wordresult){
				String content=StringUtil.getLocalContent(sourcePath);
				String[] sourceWord=content.split(ConstanParams.CHEN_SINGLE_SPACE);
				String[] splitword=words.split(ConstanParams.CHEN_SINGLE_SPACE);
				
				
				Set<String> leftSet=new HashSet<String>();
				Set<String> rightSet=new HashSet<String>();
				
				int left=0; //0为完整；1为不完整
				int right=0;
				
				int leftFrist=1;
				int rightLast=1;
				
				if(splitword.length == 1){
					for(int i=0;i<sourceWord.length;i++){
						if(splitword[0].equals(sourceWord[i])){
							if(i==0){
								leftFrist=0;
								rightSet.add(sourceWord[i+1]);
							}else if(i == sourceWord.length-1){
								rightLast=0;
								leftSet.add(sourceWord[i-1]);
							}else{
								rightSet.add(sourceWord[i+1]);
								leftSet.add(sourceWord[i-1]);
							}
						}
						if(leftFrist == 0){
							left=0;
						}else{
							if(leftSet.size() == 1){
								left=1;
							}else{
								left=0;
							}
						}
						
						if(rightLast == 0){
							right=0;
						}else{
							if(rightSet.size() == 1){
								right=1;
							}else{
								right=0;
							}
							
						}
						
					}
				}else if(splitword.length == 2){
					for(int i=0;i<sourceWord.length-1;i++){
						if(splitword[0].equals(sourceWord[i]) && splitword[1].equals(sourceWord[i+1])){
							if(i==0){
								leftFrist=0;
								rightSet.add(sourceWord[i+2]);
							}else if(i == sourceWord.length-1){
								rightLast=0;
								leftSet.add(sourceWord[i-1]);
							}else{
								rightSet.add(sourceWord[i+2]);
								leftSet.add(sourceWord[i-1]);
							}
						}
						if(leftFrist == 0){
							left=0;
						}else{
							if(leftSet.size() == 1){
								left=1;
							}else{
								left=0;
							}
						}
						
						if(rightLast == 0){
							right=0;
						}else{
							if(rightSet.size() == 1){
								right=1;
							}else{
								right=0;
							}							
						}						
					}
				}else if(splitword.length == 3){
					for(int i=0;i<sourceWord.length-2;i++){
						if(splitword[0].equals(sourceWord[i]) && splitword[1].equals(sourceWord[i+1])&& splitword[2].equals(sourceWord[i+2])){
							if(i==0){
								leftFrist=0;
								rightSet.add(sourceWord[i+3]);
							}else if(i == sourceWord.length-1){
								rightLast=0;
								leftSet.add(sourceWord[i-1]);
							}else{
								rightSet.add(sourceWord[i+3]);
								leftSet.add(sourceWord[i-1]);
							}
						}
						if(leftFrist == 0){
							left=0;
						}else{
							if(leftSet.size() == 1){
								left=1;
							}else{
								left=0;
							}
						}
						
						if(rightLast == 0){
							right=0;
						}else{
							if(rightSet.size() == 1){
								right=1;
							}else{
								right=0;
							}							
						}						
					}	
				}	
				result+=words+ConstanParams.CHEN_TABLE+left+ConstanParams.CHEN_TABLE+right+ConstanParams.CHEN_LINE;
			}
			StringUtil.writerFile(result, outputPath);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 对词频的统计
	 * void
	 * @param inputPath
	 * @param outputPath
	 */
	public static void getNumWord(String inputPath,String outputPath){
		try{
			String result="";
			File file=new File(inputPath);
			InputStreamReader in=new InputStreamReader(new FileInputStream(file));
			BufferedReader reader=new BufferedReader(in);
			String temp="";
			
			Map<String, Integer> map=new HashMap<String, Integer>();
			while((temp=reader.readLine()) != null){
				
				if(map.containsKey(temp)){
					Integer value=map.get(temp);
					map.remove(map);
					map.put(temp, value+1);
				}else{
					map.put(temp, 1);
				}					
			}			
			reader.close();
			in.close();
			
			Set<String> set=map.keySet();
			Iterator<String> iter=set.iterator();
			while(iter.hasNext()){
				String key=iter.next();
				Integer value=map.get(key);
				result+=key+ConstanParams.CHEN_TABLE+value+ConstanParams.CHEN_LINE;				
			}
		    StringUtil.writerFile(result, outputPath);
		}catch(Exception e){
			e.printStackTrace();
		}
	}	
	
	/**
	 * 词频排序
	 * void
	 * @param inputPath
	 * @param outputPath
	 */
	public static void getSortResultForStatics(String inputPath,String outputPath){
		try{
			String result="";
			File file=new File(inputPath);
			InputStreamReader in=new InputStreamReader(new FileInputStream(file));
			BufferedReader reader=new BufferedReader(in);
			String temp="";
			
			Map<String, Integer> map=new HashMap<String, Integer>();
			while((temp=reader.readLine()) != null){
				String[] temps=temp.split(ConstanParams.CHEN_TABLE);
			    map.put(temps[0], Integer.parseInt(temps[1]));					
			}
			Map resultMap=SortUtil.sortByValue(map);
			
			reader.close();
			in.close();
			
			Set<String> set=resultMap.keySet();
			
			Iterator iterator=set.iterator();
			while(iterator.hasNext()){
				String key=(String) iterator.next();
				Integer value=(Integer)resultMap.get(key);
				result +=key+ConstanParams.CHEN_TABLE+value+ConstanParams.CHEN_LINE;	
			}
			
			StringUtil.writerFile(result, outputPath);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
