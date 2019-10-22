package com.jeegem.lucene_maven;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.jeegem.lucene_maven.SearchFiles;

/**
 *  实现输入输出接口功能，
 *  可在代码中更改输入输出文档路径，
 *  将查询获得的参数写入输出文档
 * 
 * @author Daniel Dong, Frank Zhao
 *
 */

public class QuestionSearchLoop {
	
	public static ArrayList<String> searchOneQuestions(String Question) throws Exception { 
		//输入问题，返回五篇匹配度最高文章名
		ArrayList<String> topFileNames = new ArrayList<>();

				ArrayList<String> machtedFiles = SearchFiles.searchQuestion(Question,6);
				//给出五篇
				for(int i=0;i<5&&i<machtedFiles.size();i++){//TODO 此处的10为选取最高得分的规则数量
					topFileNames.add(machtedFiles.get(i).split("\t")[0]);
					//System.out.println("最匹配的文章名："+machtedFiles.get(i).split("\t")[0]);
					}
		return topFileNames;
	}
	

	public static ArrayList<String> searchAllQuestions(File inputTxt) throws Exception {//目前不使用
		
		ArrayList<String> topFileNames = new ArrayList<>();

		//Integer rowCount = 0;
		FileInputStream fis = null;// 文件输入流
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			String preLine = "";
			//String msg2Append = "";
			fis = new FileInputStream(inputTxt);// 读入本地文件
			isr = new InputStreamReader(fis);// InputStreamReader
			br = new BufferedReader(isr);// 从字符输入流中读取文件中的内容

			boolean flag = true; //第一行读取数据会出现问题，跳过第一行
			while ((preLine = br.readLine()) != null) {
				//System.out.println(preLine.split("\t")[1]);
				ArrayList<String> myResults = SearchFiles.searchQuestion(preLine.split("\t")[1],6);
				//检索整句
				//String oriTxt = preLine.split("\t")[0]; //tab分词
				for(Integer i=0;i<5;i++){
					topFileNames.add(myResults.get(i).split("\t")[0]);
					}
			}
			if(flag) flag = false; //去除第一行的问题
		}

		finally {
			try {
				br.close();
				isr.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return topFileNames;
	}
	
	public static void main(String[] args) { //目前不使用
		try {
			searchAllQuestions(new File("C:\\Users\\danie\\Desktop\\lucene测试\\food_data_test\\question_data.txt"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
