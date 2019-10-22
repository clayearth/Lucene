/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
//package org.apache.lucene.demo;
package com.jeegem.lucene_maven;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;

//import org.apache.lucene.analysis.Analyzer;
//import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer; //采用中文分词器
//import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

//import com.jeegem.XML.XMLReader;

/**
 * 内置一个函数searchQuestion:
 * 输入为问题一个字符串（分/未分词均可），
 * 返回一个ArrayList动态数组 ，
 * 返回数组每个元素结构为文章编号+tab+绝对得分
 * 
 * @author Daniel Dong, Frank Zhao 
 * 
 * */

public class SearchFiles {

  private SearchFiles() {}
  
  /**
 * @param line
 * @param fileName
 * @return
 * @throws Exception
 */
public static ArrayList<String> searchSentences(String line,String fileName,int nameLoc) throws Exception {
	  //输入问题=line，以及文件名，检索文件名所在文件夹下的分句文件，返回“文章编号+tab+绝对得分”动态数组

	ArrayList<String> results = new ArrayList<String>();
	
    String index = "C:\\Users\\danie\\Desktop\\lucene测试\\newcomeon\\newcomeon\\"+fileName+".0.xml"+"\\index";
    //System.out.println("【search Sentences “"+line+"” from index " +index +"】");
    String field = "contents";
    String queries = null;
//    int repeat = 0;
    boolean raw = false;
    String queryString = null;
    int hitsPerPage = 10;
      try {
    	    IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
    	    IndexSearcher searcher = new IndexSearcher(reader);
    	    //Analyzer analyzer = new StandardAnalyzer();
    	    SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();



    	    BufferedReader in = null;
    	    in = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.US_ASCII));
    	    QueryParser parser = new QueryParser(field, analyzer);
    	//  String line = queryString != null ? queryString : in.readLine();
    	    	
    	     
//    	    String line = "北京青团";
    	      line = line.trim();
    	      Query query = parser.parse(line);
    	      
//    	      System.out.println("Searching for: " + query.toString(field));
//    	            
//    	      if (repeat > 0) {                           // repeat & time as benchmark
//    	        Date start = new Date();
//    	        for (int i = 0; i < repeat; i++) {
//    	          searcher.search(query, 100);
//    	        }
//    	        Date end = new Date();
//    	        System.out.println("Time: "+(end.getTime()-start.getTime())+"ms");
//    	      }

    	      results = doPagingSearch(nameLoc ,in, searcher, query, hitsPerPage, raw, queries == null && queryString == null);

//    	      if (queryString != null) {
//    	        break;
//    	      }
    	    reader.close();
	} catch (Exception e) {
		// TODO: handle exception
		return new ArrayList<>();
	}

	return results;
}
  
  public static ArrayList<String> searchQuestion(String line,int nameLoc) throws Exception {
	  //返回“文章编号+tab+绝对得分”动态数组

	ArrayList<String> results = new ArrayList<String>();
	  
    String index = "C:\\Users\\danie\\Desktop\\lucene测试\\index";
    String field = "contents";
    String queries = null;
//    int repeat = 0;
    boolean raw = false;
    String queryString = null;
    int hitsPerPage = 10;
      
    IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
    IndexSearcher searcher = new IndexSearcher(reader);
    //Analyzer analyzer = new StandardAnalyzer();
    SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();

    BufferedReader in = null;
    in = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.US_ASCII));
    QueryParser parser = new QueryParser(field, analyzer);
//  String line = queryString != null ? queryString : in.readLine();
    	
     
//    String line = "北京青团";
      line = line.trim();
      Query query = parser.parse(line);
      
//     System.out.println("Searching for: " + query.toString(field));
//            
//      if (repeat > 0) {                           // repeat & time as benchmark
//        Date start = new Date();
//        for (int i = 0; i < repeat; i++) {
//          searcher.search(query, 100);
//        }
//        Date end = new Date();
//        System.out.println("Time: "+(end.getTime()-start.getTime())+"ms");
//      }

      results = doPagingSearch( nameLoc ,in, searcher, query, hitsPerPage, raw, queries == null && queryString == null);

//      if (queryString != null) {
//        break;
//      }
    reader.close();
	return results;
}
  /**
   * This demonstrates a typical paging search scenario, where the search engine presents 
   * pages of size n to the user. The user can then go to the next page if interested in
   * the next hits.
   * 
   * When the query is executed for the first time, then only enough results are collected
   * to fill 5 result pages. If the user wants to page beyond this limit, then the query
   * is executed another time and all hits are collected.
 * @return 
   * 
   */
  public static ArrayList<String> doPagingSearch(int nameLoc,BufferedReader in, IndexSearcher searcher, Query query, 
                                     int hitsPerPage, boolean raw, boolean interactive) throws IOException {
 
	  ArrayList<String> myResults = new ArrayList<>();
    // Collect enough docs to show 5 pages
    TopDocs results = searcher.search(query, 5 * hitsPerPage);
    //System.out.println("【doPagingSearch:  " + results.totalHits+"】");
    ScoreDoc[] hits = results.scoreDocs;
    
    int numTotalHits = Math.toIntExact(results.totalHits.value);
    
    int start = 0;
    int end = Math.min(numTotalHits, hitsPerPage);
        
      if (end > hits.length) {
//        System.out.println("Only results 1 - " + hits.length +" of " + numTotalHits + " total matching documents collected.");
//        System.out.println("Collect more (y/n) ?");
 //       String line = in.readLine();

        hits = searcher.search(query, numTotalHits).scoreDocs;
      }
      
      end = Math.min(hits.length, start + hitsPerPage);
      
      for (int i = start; i < hits.length; i++) {//TODO 这里的10 可能会出现找不到十篇文章的错误
//        if (raw) {                              // output raw format
//          System.out.println("doc="+hits[i].doc+" score="+hits[i].score);    
//          continue;
//        }
    	//System.out.println("【hitslength" +hits.length+"】");
    	//System.out.println("【doPagingSearch" +hits[i] +"】");
        Document doc = searcher.doc(hits[i].doc);
        String path = doc.get("path"); //获得文章的绝对路径
        String tempPath1 = ""; //去格式变量1
        String tempPath2 = ""; //去格式变量2
        String tempResult = ""; //写入结果ArrayList的元素
        if (path != null) {
//         System.out.println((i+1) + ". " + path);
          //String headLine = XMLReader.getContent(path, "HEADLINE");
          //System.out.println(path);//打印1 绝对地址
          tempPath1 += path.split("\\\\")[nameLoc]; //TODO 这里的7是由绝对路径确定的，可能会出现问题
          tempPath2 += tempPath1.split("\\.")[0]; //TODO 这里的0是由文章命名格式确定的，可能会出现问题
          tempResult += tempPath2; //TODO 匹配文章格式
//          tempResult += path;
        	
//          String title = doc.get("title");
//          if (title != null) {
//            System.out.println("   Title: " + doc.get("title"));
//          }
          //System.out.println("Score: " + hits[i].score);//打印2 得分
          tempResult += "\t" + hits[i].score;
          
        } else {
          System.out.println((i+1) + ". " + "No path for this document");
        }
        
        myResults.add(tempResult);
        //System.out.println("【" +myResults+"】");
        tempPath1 = null;  //标准归0化
        tempPath2 = null;
        tempResult = null;
      }            
      return myResults; //写入结果ArrayList中
  }
}
