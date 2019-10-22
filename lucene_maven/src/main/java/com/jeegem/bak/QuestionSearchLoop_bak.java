package com.jeegem.bak;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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

public class QuestionSearchLoop_bak {
	public static void searchAllQuestions(File inputTxt) throws Exception {

		//Integer rowCount = 0;
		FileInputStream fis = null;// 文件输入流
		FileOutputStream fos = null;// 文件输出流
		InputStreamReader isr = null;
		OutputStreamWriter osw = null;
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			String preLine = "";
			//String msg2Append = "";
			String newText = "";
			fis = new FileInputStream(inputTxt);// 读入本地文件
			fos = new FileOutputStream("C:\\Users\\danie\\Desktop\\lucene测试\\food_data_test\\580_660_outcome.txt");// 写入本地文件

			isr = new InputStreamReader(fis);// InputStreamReader
			osw = new OutputStreamWriter(fos, "UTF-8");// 写入文件编码格式为UTF-8

			br = new BufferedReader(isr);// 从字符输入流中读取文件中的内容
			bw = new BufferedWriter(osw);// 将字符输出流写入到文件中

			String strb = "";// 定义一个可变类型的String

			/**
			 * String outTxt = "";
			 * 
			 */
			bw.write("文件编号\t问题\t问题分词\t问题搜索排名\t问题查询得分\t问题最高得分\t分词搜索排名\t分词查询得分\t分词最高得分\r\n"); //书写参数
			
			boolean flag = true; //第一行读取数据会出现问题，跳过第一行
			while ((preLine = br.readLine()) != null) {
				System.out.println(preLine.split("\t")[1]);
				ArrayList<String> myResults = SearchFiles.searchQuestion(preLine.split("\t")[1],6);
				//检索整句
				//String judegeNames = ""; //用于每次储存比对文件名
				//Integer numOfList = 0;
				String oriTxt = preLine.split("\t")[0]; //tab分词
				for(Integer i=0;i<50;i++){
					//numOfList = 0;
					//先定义一个规则，判断是不是
					if(oriTxt.equals(myResults.get(i).split("\t")[0])) 
					{//匹配到文章
						bw.write(preLine+"\t"+ /**1或者零*/ Integer.toString(i+1)+"\t"+  //排名
						myResults.get(i).split("\t")[1]+ "\t"+ 	//查询绝对得分
						myResults.get(0).split("\t")[1] + "\t"); //最高得分
						//searchQuestion结果有没有和oriTxt原文档相同的
						break;
					}
					if(i==49 && (!flag)) bw.write(preLine+"\t0\t0\t0\t");//未查询到
				}
				if(flag) flag = false; //去除第一行的问题
				
				/**
				 *   或者采用一次性输出的方式
				 * outTxt += preLine+"\t"+ 1或者零 +"\r\n";
				 * outputStream.write(preLine+"\t"+ 1或者零 +"\r\n")
				 */
				
			ArrayList<String> myResultsCut = SearchFiles.searchQuestion(preLine.split("\t")[2],6);
			System.out.println(preLine.split("\t")[2]);
			//检索分好词的句子
				for(Integer i=0;i<50;i++){
					if(oriTxt.equals(myResultsCut.get(i).split("\t")[0])) 
					{//匹配到文章
						bw.write(Integer.toString(i+1)+"\t"+  //排名
						myResultsCut.get(i).split("\t")[1]+ "\t"+ //查询绝对得分
						myResultsCut.get(0).split("\t")[1] + "\r\n"); //最高得分
						//searchQuestion结果有没有和oriTxt原文档相同的
						break;
					}
					if(i==49 && (!flag)) bw.write("0"+"\t"+"0\t0\t"+"\r\n");//未查询到
				}
				if(flag) flag = false; //去除第一行的问题
				 
			}
			newText = strb.toString();
			System.out.println(newText);// 打印出str2
			bw.write(newText);// 写入文件

		} catch (FileNotFoundException e) {
			System.out.println("找不到指定文件");
		} catch (IOException e) {
			System.out.println("读取/写入文件失败");
		} finally {
			try {
				br.close();
				isr.close();
				fis.close();
				bw.close();
				osw.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		try {
			searchAllQuestions(new File("C:\\Users\\danie\\Desktop\\lucene测试\\food_data_test\\580test660.txt"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
