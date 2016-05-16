package org.searchRetrleve.lucene;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.DisjunctionMaxQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MultiPhraseQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.RegexpQuery;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.Version;
import org.apache.solr.schema.DateField;
import org.wltea.analyzer.lucene.IKAnalyzer;

/** 
 * @author root  
 * @create 2015年12月25日 下午11:13:10
 * @version  1.0
 * 类说明 
 */
public class IndexDemo {
	private static Analyzer analyzer=new IKAnalyzer();
	
	public static void createIndex(String indexPath){
		try{
			//Analyzer analyzer=new StandardAnalyzer(Version.LUCENE_4_9);
			
			Directory dir=FSDirectory.open(new File(indexPath));
			IndexWriterConfig config=new IndexWriterConfig(Version.LUCENE_4_9,analyzer);
			IndexWriter writer=new IndexWriter(dir,config);
			
			Document doc1=new Document();
			IndexableField id1=new IntField("id", 1, Field.Store.YES);
			IndexableField date1=new StringField("date","2013年7月",Field.Store.YES);
			IndexableField name1=new StringField("name", "俄罗斯总理梅德韦杰夫抵京访华", Field.Store.YES);
			IndexableField content1=new TextField("content", "俄罗斯联邦政府总理梅德韦杰夫22日凌晨抵达北京", Field.Store.YES);
			
			Document doc2=new Document();
			IndexableField id2=new IntField("id", 2, Field.Store.YES);
			IndexableField date2=new StringField("date","2014年8月",Field.Store.YES);
			IndexableField name2=new StringField("name", "北京", Field.Store.YES);
			IndexableField content2=new TextField("content", "开始对中国进行为期两天的正式访问", Field.Store.YES);
			
			Document doc3=new Document();
			IndexableField id3=new IntField("id", 3, Field.Store.YES);
			IndexableField date3=new StringField("date","2015年9月",Field.Store.YES);
			IndexableField name3=new StringField("name", "美国副总统展开访华行程", Field.Store.YES);
			IndexableField content3=new TextField("content", "并会晤国家元首及总要官员", Field.Store.YES);
			
			
			doc1.add(id1);
			doc1.add(name1);
			doc1.add(date1);
			doc1.add(content1);
			doc2.add(id2);
			doc2.add(date2);
			doc2.add(name2);
			doc2.add(content2);
			doc3.add(id3);
			doc3.add(date3);
			doc3.add(name3);
			doc3.add(content3);
			
			
			writer.addDocument(doc1);
			writer.addDocument(doc2);
			writer.addDocument(doc3);
		//	writer.forceMerge(3);
			writer.commit();			
			writer.close();		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public static void searchIndex(String  indexPath){
		
		try{
			
			Directory dir=FSDirectory.open(new File(indexPath));
			DirectoryReader dirReader=DirectoryReader.open(dir);
			IndexSearcher searcher=new IndexSearcher(dirReader);
			
			Query query=new TermQuery(new Term("content","俄罗斯"));
	//		Query query=NumericRangeQuery.newIntRange("id", 1, 1, true, true);
			
			String preTag="<font color = \"red\" >";
			String postTag="</font>";
			Formatter formatter=new SimpleHTMLFormatter(preTag, postTag);
			Scorer fragmentScorer=new QueryScorer(query);
			Highlighter highlighter=new Highlighter(formatter, fragmentScorer);
			Fragmenter fragmenter=new SimpleFragmenter(50);
			highlighter.setTextFragmenter(fragmenter);
			
			TopDocs topDocs=searcher.search(query, 10);
			
			int hits=topDocs.totalHits;
			System.out.println("hits:"+hits);
			ScoreDoc[] sDocs=topDocs.scoreDocs;
			
			for(ScoreDoc sDoc :sDocs){
				int docID=sDoc.doc;
				Document doc=searcher.doc(docID);
				String id=highlighter.getBestFragment(analyzer, "id", doc.get("id"));				
				id=(id==null?doc.get("id"):id);
				
				String name=highlighter.getBestFragment(analyzer, "id", doc.get("name"));
				name=(name==null?doc.get("name"):name);
				
				String content=highlighter.getBestFragment(analyzer, "id", doc.get("content"));
				content=(content==null?doc.get("content"):content);
				
				System.out.println("id:"+id+"\ntitle:"+name+"\ncontent:"+content);			
			}
			
			dirReader.close();
			dir.close();
		}catch(Exception e){
			e.printStackTrace();
		}
				
	}
	
	
	public static void booleanQuery(String  indexPath){
		
		try{
			Directory dir=FSDirectory.open(new File(indexPath));
			IndexReader dirReader=DirectoryReader.open(dir);
			IndexSearcher searcher=new IndexSearcher(dirReader);
			
			Query query1=new TermQuery(new Term("content","俄罗斯"));
		//	Query query2=new TermQuery(new Term("content","总理"));
	        BooleanQuery query=new BooleanQuery();
			query.add(query1, BooleanClause.Occur.MUST);
	        
			TopDocs topDocs=searcher.search(query, 10);
			
			int hits=topDocs.totalHits;
			System.out.println("hits:"+hits);
			ScoreDoc[] sDocs=topDocs.scoreDocs;
			
			for(ScoreDoc sDoc :sDocs){
				int docID=sDoc.doc;
				Document doc=searcher.doc(docID);
				String id= doc.get("id");								
				String name=doc.get("name");				
				String content=doc.get("content");
				
				System.out.println("id:"+id+"\ntitle:"+name+"\ncontent:"+content);			
			}
			
			dirReader.close();
			dir.close();
		}catch(Exception e){
			e.printStackTrace();
		}
				
	}
	
	
	public static void wildcardQuery(String  indexPath){
		
		try{
			Directory dir=FSDirectory.open(new File(indexPath));
			IndexReader dirReader=DirectoryReader.open(dir);
			IndexSearcher searcher=new IndexSearcher(dirReader);
			
			WildcardQuery query=new WildcardQuery(new Term("content","北京*"));
	      
			TopDocs topDocs=searcher.search(query, 10);
			
			int hits=topDocs.totalHits;
			System.out.println("hits:"+hits);
			ScoreDoc[] sDocs=topDocs.scoreDocs;
			
			for(ScoreDoc sDoc :sDocs){
				int docID=sDoc.doc;
				Document doc=searcher.doc(docID);
				String id= doc.get("id");								
				String name=doc.get("name");				
				String content=doc.get("content");
				
				System.out.println("id:"+id+"\ntitle:"+name+"\ncontent:"+content);			
			}
			
			dirReader.close();
			dir.close();
		}catch(Exception e){
			e.printStackTrace();
		}
				
	}
	
	
	public static void phraseQuery(String  indexPath){
		
		try{
			Directory dir=FSDirectory.open(new File(indexPath));
			IndexReader dirReader=DirectoryReader.open(dir);
			IndexSearcher searcher=new IndexSearcher(dirReader);
			
			PhraseQuery query=new PhraseQuery();
			query.add(new Term("content","总理"));
	        query.add(new Term("content","北京"));	        
	        query.setSlop(9);
			
			TopDocs topDocs=searcher.search(query, 10);
			
			int hits=topDocs.totalHits;
			System.out.println("hits:"+hits);
			ScoreDoc[] sDocs=topDocs.scoreDocs;
			
			for(ScoreDoc sDoc :sDocs){
				int docID=sDoc.doc;
				Document doc=searcher.doc(docID);
				String id= doc.get("id");								
				String name=doc.get("name");				
				String content=doc.get("content");
				
				System.out.println("id:"+id+"\ntitle:"+name+"\ncontent:"+content);			
			}
			
			dirReader.close();
			dir.close();
		}catch(Exception e){
			e.printStackTrace();
		}
				
	}
	
	
	
	public static void prefixQuery(String  indexPath){
		
		try{
			Directory dir=FSDirectory.open(new File(indexPath));
			IndexReader dirReader=DirectoryReader.open(dir);
			IndexSearcher searcher=new IndexSearcher(dirReader);
			
	        PrefixQuery query=new PrefixQuery(new Term("content","俄罗斯"));
			//Query query=new TermQuery(new Term("content","俄罗斯"));
			
			TopDocs topDocs=searcher.search(query, 10);
			
			int hits=topDocs.totalHits;
			System.out.println("hits:"+hits);
			ScoreDoc[] sDocs=topDocs.scoreDocs;
			
			for(ScoreDoc sDoc :sDocs){
				int docID=sDoc.doc;
				Document doc=searcher.doc(docID);
				String id= doc.get("id");								
				String name=doc.get("name");				
				String content=doc.get("content");
				
				System.out.println("id:"+id+"\ntitle:"+name+"\ncontent:"+content);			
			}
			
			dirReader.close();
			dir.close();
		}catch(Exception e){
			e.printStackTrace();
		}
				
	}
	
	public static void multiPhaseQuery(String  indexPath){
		
		try{
			Directory dir=FSDirectory.open(new File(indexPath));
			IndexReader dirReader=DirectoryReader.open(dir);
			IndexSearcher searcher=new IndexSearcher(dirReader);
			
	        //PrefixQuery query=new PrefixQuery(new Term("content","俄罗斯"));
			MultiPhraseQuery query=new MultiPhraseQuery();
			Term term1=new Term("content","俄罗斯");
			Term term2=new Term("content","总理");
			Term[] term={term1,term2};
			query.add(term);
 			
			TopDocs topDocs=searcher.search(query, 10);
			
			int hits=topDocs.totalHits;
			System.out.println("hits:"+hits);
			ScoreDoc[] sDocs=topDocs.scoreDocs;
			
			for(ScoreDoc sDoc :sDocs){
				int docID=sDoc.doc;
				Document doc=searcher.doc(docID);
				String id= doc.get("id");								
				String name=doc.get("name");				
				String content=doc.get("content");
				
				System.out.println("id:"+id+"\ntitle:"+name+"\ncontent:"+content);			
			}
			
			dirReader.close();
			dir.close();
		}catch(Exception e){
			e.printStackTrace();
		}
				
	}
	
	
	public static void  regexpQuery(String  indexPath){
		
		try{
			Directory dir=FSDirectory.open(new File(indexPath));
			IndexReader dirReader=DirectoryReader.open(dir);
			IndexSearcher searcher=new IndexSearcher(dirReader);
			
	        RegexpQuery query=new RegexpQuery(new Term("name","俄(.*)华"));
 			//RegexpQuery query=new RegexpQuery(new Term("content","俄(.*)斯"));
	        
	        
			TopDocs topDocs=searcher.search(query, 10);
			
			int hits=topDocs.totalHits;
			System.out.println("hits:"+hits);
			ScoreDoc[] sDocs=topDocs.scoreDocs;
			
			for(ScoreDoc sDoc :sDocs){
				int docID=sDoc.doc;
				Document doc=searcher.doc(docID);
				String id= doc.get("id");								
				String name=doc.get("name");				
				String content=doc.get("content");
				
				System.out.println("id:"+id+"\ntitle:"+name+"\ncontent:"+content);			
			}
			
			dirReader.close();
			dir.close();
		}catch(Exception e){
			e.printStackTrace();
		}
				
	}
	
	public static void  termRangeQuery(String  indexPath){
		
		try{
			Directory dir=FSDirectory.open(new File(indexPath));
			IndexReader dirReader=DirectoryReader.open(dir);
			IndexSearcher searcher=new IndexSearcher(dirReader);
			
			BytesRef lowerTerm=new BytesRef("2013年6月");
			BytesRef upperTerm=new BytesRef("2015年9月");
			
	        TermRangeQuery query=new TermRangeQuery("date",lowerTerm,upperTerm,true,true);
 			//RegexpQuery query=new RegexpQuery(new Term("content","俄(.*)斯"));
	        
	        
			TopDocs topDocs=searcher.search(query, 10);
			
			int hits=topDocs.totalHits;
			System.out.println("hits:"+hits);
			ScoreDoc[] sDocs=topDocs.scoreDocs;
			
			for(ScoreDoc sDoc :sDocs){
				int docID=sDoc.doc;
				Document doc=searcher.doc(docID);
				String id= doc.get("id");
				String date=doc.get("date");
				String name=doc.get("name");				
				String content=doc.get("content");
				
				System.out.println("id:"+id+"\ndate:"+date+"\ntitle:"+name+"\ncontent:"+content);			
			}
			
			dirReader.close();
			dir.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
		
	public static void  disjunctionQuery(String  indexPath){
			
			try{
				Directory dir=FSDirectory.open(new File(indexPath));
				IndexReader dirReader=DirectoryReader.open(dir);
				IndexSearcher searcher=new IndexSearcher(dirReader);
				
				DisjunctionMaxQuery query=new DisjunctionMaxQuery(0.1f);   
		        query.add(new TermQuery(new Term("content","中国")));
				
				TopDocs topDocs=searcher.search(query, 10);
				
				int hits=topDocs.totalHits;
				System.out.println("hits:"+hits);
				ScoreDoc[] sDocs=topDocs.scoreDocs;
				
				for(ScoreDoc sDoc :sDocs){
					int docID=sDoc.doc;
					Document doc=searcher.doc(docID);
					String id= doc.get("id");
					String date=doc.get("date");
					String name=doc.get("name");				
					String content=doc.get("content");
					
					System.out.println("id:"+id+"\ndate:"+date+"\ntitle:"+name+"\ncontent:"+content);			
				}
				
				dirReader.close();
				dir.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		
		
	}
	
	
	public static void  multiFieldQueryParser(String  indexPath){
		
		try{
			Directory dir=FSDirectory.open(new File(indexPath));
			IndexReader dirReader=DirectoryReader.open(dir);
			IndexSearcher searcher=new IndexSearcher(dirReader);
			
			String[] fields={"name","content"};
			Map<String,Float> boosts=new HashMap<String, Float>();
			boosts.put("name", 1.0f);
			boosts.put("content", 20.0f);
			MultiFieldQueryParser queryParser=new MultiFieldQueryParser(Version.LUCENE_4_9, fields, analyzer, boosts);
			Query query=queryParser.parse("北京");
			
			
			TopDocs topDocs=searcher.search(query, 10);
			
			int hits=topDocs.totalHits;
			System.out.println("hits:"+hits);
			ScoreDoc[] sDocs=topDocs.scoreDocs;
			
			for(ScoreDoc sDoc :sDocs){
				int docID=sDoc.doc;
				Document doc=searcher.doc(docID);
				String id= doc.get("id");
				String date=doc.get("date");
				String name=doc.get("name");				
				String content=doc.get("content");
				
				System.out.println("id:"+id+"\ndate:"+date+"\ntitle:"+name+"\ncontent:"+content);			
			}
			
			dirReader.close();
			dir.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	
	}
	
	
}
