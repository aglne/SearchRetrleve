package org.util.test;

import org.junit.Test;

import org.searchRetrleve.lucene.IndexDemo;


/** 
 * @author root  
 * @create 2015年12月25日 下午11:19:36
 * @version  1.0
 * 类说明 
 */
public class TestIndexDemo {
	@Test
	public void TestCreateIndex(){		
		String indexPath="D:\\Eclipsework\\SearchRetrleve\\indexPath\\Demo";
		IndexDemo.createIndex(indexPath);	
	}
	
	@Test
	public void TestSearchIndex(){		
		String indexPath="D:\\Eclipsework\\SearchRetrleve\\indexPath\\Demo";	
		IndexDemo.searchIndex(indexPath);		
	}
	
	@Test
	public void TestPrefixQuery(){		
		String indexPath="D:\\Eclipsework\\SearchRetrleve\\indexPath\\Demo";	
		IndexDemo.prefixQuery(indexPath);		
	}
	
	@Test
	public void TestBooleanQuery(){		
		String indexPath="D:\\Eclipsework\\SearchRetrleve\\indexPath\\Demo";	
		IndexDemo.booleanQuery(indexPath);		
	}
	
	@Test
	public void TestWildcardQuery(){		
		String indexPath="D:\\Eclipsework\\SearchRetrleve\\indexPath\\Demo";	
		IndexDemo.wildcardQuery(indexPath);		
	}
	
	@Test
	public void TestPhraseQuery(){		
		String indexPath="D:\\Eclipsework\\SearchRetrleve\\indexPath\\Demo";	
		IndexDemo.phraseQuery(indexPath);		
	}
	
	
	@Test
	public void TestMultiPhraseQuery(){		
		String indexPath="D:\\Eclipsework\\SearchRetrleve\\indexPath\\Demo";	
		IndexDemo.multiPhaseQuery(indexPath);	
	}
	
	@Test
	public void TestRegexpQuery(){		
		String indexPath="D:\\Eclipsework\\SearchRetrleve\\indexPath\\Demo";	
		IndexDemo.regexpQuery(indexPath);	
	}
	
	
	@Test
	public void TestTermRangeQuery(){		
		String indexPath="D:\\Eclipsework\\SearchRetrleve\\indexPath\\Demo";	
		IndexDemo.termRangeQuery(indexPath);	
	}
	
	@Test
	public void TestDisjunctionRangeQuery(){		
		String indexPath="D:\\Eclipsework\\SearchRetrleve\\indexPath\\Demo";	
		IndexDemo.disjunctionQuery(indexPath);	
	}
	
	@Test
	public void TestMultiFieldQuery(){		
		String indexPath="D:\\Eclipsework\\SearchRetrleve\\indexPath\\Demo";	
		IndexDemo.multiFieldQueryParser(indexPath);	
	}
	
}
