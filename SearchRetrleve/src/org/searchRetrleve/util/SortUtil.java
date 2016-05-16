package org.searchRetrleve.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** 
 * @author root  
 * @create 2015年12月16日 下午11:46:09
 * @version  1.0
 * 类说明 
 */
public class SortUtil {
	
	public static Map sortByValue(Map map){
		Map result=sortByValue(map,true);
		return result;
	}
	
	
	/**
	 * 排序
	 * Map
	 * @param map
	 * @param isDec
	 * @return
	 */
	public static Map sortByValue(Map map,boolean isDec){
		Map result=new LinkedHashMap();
		
		List list=new LinkedList(map.entrySet());
		Collections.sort(list, new Comparator(){

			public int compare(Object o1, Object o2) {
				if(isDec){
					return ((Comparable)(((Map.Entry)o2).getValue())).compareTo(((Map.Entry)o1).getValue());
				}else{
					return ((Comparable)(((Map.Entry)o1).getValue())).compareTo(((Map.Entry)o2).getValue());
				}
			}

		});
		
		for(Iterator iter=list.iterator();iter.hasNext();){
			Map.Entry entry=(Map.Entry)iter.next();
			result.put(entry.getKey(), entry.getValue());
		}
		
		return result;
	}

}
