package org.searchRetrleve.lucene;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

/** 
 * @author root  
 * @create 2015年12月23日 下午3:39:37
 * @version  1.0
 * 类说明 
 */
public class IndexUtil {

	public static void createIndex(List<Map<String, Object>>  list,String indexPath){
		try{
			//Analyzer analyzer=new StandardAnalyzer(Version.LUCENE_4_9);
			Analyzer analyzer=new IKAnalyzer();
			Directory dir=FSDirectory.open(new File(indexPath));
			IndexWriterConfig config=new IndexWriterConfig(Version.LUCENE_4_9,analyzer);
			IndexWriter writer=new IndexWriter(dir,config);
			Document doc;
			for(Map<String, Object> map :list){
				doc=new Document();
				Set<String> set=map.keySet();
				Iterator<String> iter=set.iterator();
				while(iter.hasNext()){
					String key=iter.next();
					String value=(String) map.get(key);
					IndexableField field=new TextField(key, value, Field.Store.YES);
					
					doc.add(field);					
				}	
				writer.addDocument(doc);
			}
          
			writer.forceMerge(2);
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
			
			Query query=new TermQuery(new Term("id","100"));
	//		Query query=NumericRangeQuery.newIntRange("id", 1, 1, true, true);
			TopDocs topDocs=searcher.search(query, 10);
			
			int hits=topDocs.totalHits;
			System.out.println("hits:"+hits);
			ScoreDoc[] sDocs=topDocs.scoreDocs;
			
			for(ScoreDoc sDoc :sDocs){
				int docID=sDoc.doc;
				Document doc=searcher.doc(docID);
				System.out.println("id:"+doc.get("id")+"\ntitle:"+doc.get("name")+"\ncontent:"+doc.get("content"));			
			}
			
			dirReader.close();
			dir.close();
		}catch(Exception e){
			e.printStackTrace();
		}
				
	}
	
	
	public static void deleteIndex(String indexPath){
		try{
			Analyzer analyzer=new IKAnalyzer();
			Directory dir=FSDirectory.open(new File(indexPath));
			IndexWriterConfig config=new IndexWriterConfig(Version.LUCENE_4_9,analyzer);
			IndexWriter writer=new IndexWriter(dir,config);
			
	//		writer.deleteAll();
			writer.deleteDocuments(new Term("id","32"));
			
			writer.commit();
			writer.close();	
		}catch(Exception e){
			e.printStackTrace();
		}						
	}
	
	
	public static void updateIndex(String indexPath){
		try{
			Analyzer analyzer=new IKAnalyzer();
			Directory dir=FSDirectory.open(new File(indexPath));
			IndexWriterConfig config=new IndexWriterConfig(Version.LUCENE_4_9,analyzer);
			IndexWriter writer=new IndexWriter(dir,config);
			
			Document doc=new Document();
	        IndexableField field=new TextField("id", "100", Field.Store.YES);
	        doc.add(field);
			writer.updateDocument(new Term("id","32"),doc);
			
			writer.commit();
			writer.close();	
		}catch(Exception e){
			e.printStackTrace();
		}	
	
		
	}	
		
}
