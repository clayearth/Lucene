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

import com.jeegem.lucene_maven.QuestionSearchLoop;
import com.jeegem.lucene_maven.SearchFiles;

/**
 *  实现输入输出接口功能，
 *  可在代码中更改输入输出文档路径，
 *  将查询获得的参数写入输出文档
 * 
 * @author Daniel Dong, Frank Zhao
 *
 */

public class lucene_precisoon {
	public static void searchAllQuestions(File inputTxt) throws Exception {

		//Integer rowCount = 0;
		FileInputStream fis = null;// 文件输入流
		FileOutputStream fos = null;// 文件输出流
		InputStreamReader isr = null;
		OutputStreamWriter osw = null;
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			String row = "";
			//String msg2Append = "";
			//String newText = "";
			fis = new FileInputStream(inputTxt);// 读入本地文件
			fos = new FileOutputStream("C:\\Users\\danie\\Desktop\\lucene测试\\food_data_test\\580test660_outcome.txt");// 写入本地文件

			isr = new InputStreamReader(fis);// InputStreamReader
			osw = new OutputStreamWriter(fos, "UTF-8");// 写入文件编码格式为UTF-8

			br = new BufferedReader(isr);// 从字符输入流中读取文件中的内容
			bw = new BufferedWriter(osw);// 将字符输出流写入到文件中

			//String strb = "";// 定义一个可变类型的String
			
			FileInputStream myFis = null;//读取最高匹配度句子文件输入流
			InputStreamReader myIsr = null;
			BufferedReader myBr = null;

			/**
			 * String outTxt = "";
			 * 
			 */
			//bw.write("文件编号\t问题\t问题分词\t问题搜索排名\t问题查询得分\t问题最高得分\t分词搜索排名\t分词查询得分\t分词最高得分\r\n"); //书写参数
			
			//boolean flag = true; //第一行读取数据会出现问题，跳过第一行
			int answerTag = 0;
			while ((row = br.readLine()) != null) {
				
				//String fileName = row.split("\t")[1].split("_")[0];//TODO 每一行分离出所在文章名称，所在第二列
				String question = row.split("\t")[1];//TODO 每一行分离出问题，所在为第三列
				String answer = row.split("\t")[2];//TODO 每一行分离出答案，所在第四列
				System.out.println(question);
				
				ArrayList<String> topFileNames = QuestionSearchLoop.searchOneQuestions(question);//获得最高匹配的五篇文章的文章名
				
				answerTag = 0;
				int everyNumOfFile = 1;//每篇文章读取前多少的句子

				for(int i=0;i<topFileNames.size();i++) {//循环五次，从每篇文章中寻找句子
//					
//					//int everyFileTopScore[] = new int[5];
//					
					//String everyFileName = topFileNames.get(i);//获得每一次循环的文章名，如“10899”
					ArrayList<String> topSentences = SearchFiles.searchSentences(question, topFileNames.get(i),9);//TODO 此处的9由文章的绝对路径确定
					
					if(topSentences.isEmpty()) {
						System.out.println("【No Sentences For "+question+"】");
						continue;
					}
					//System.out.println("【top sentences:" +topSentences +"】");
					
					int maxNo = topSentences.size();
					
					for(int j = 0; j< everyNumOfFile; j++) {//循环
						
						if(maxNo<=j) break;
						int numOfMostLikeQuestionSentence = Integer.valueOf(topSentences.get(j).split("\t")[0]);//文章中匹配度排第j的句子编号
					    
//					//ArrayList<String> nearSentences = new ArrayList<String>();
//					
//					double topScore = Double.valueOf(topSentences.get(0).split("\t")[1]);//获得最高绝对得分
//					double nowScore = topScore;//TODO 规则：当前句子相对得分比0.5大，计数加一
//					int allCounts = 0;//总计数，precision分母
//					int rightCounts = 0;//正确计数，，precision分子
//					
//					for(int j = 0;nowScore/topScore>0.5 && j < topSentences.size();j++) {
//						
//						myFis = new FileInputStream("C:\\Users\\danie\\Desktop\\lucene测试\\newcomeon\\newcomeon\\"
//								+topFileNames.get(i)+".0.xml"+"\\data\\"+ topSentences.get(j).split("\t")[0] +".txt");// 读入本地文件
//						myIsr = new InputStreamReader(myFis);// InputStreamReader
//						myBr = new BufferedReader(myIsr);// 从字符输入流中读取文件中的内容
//						
//						String highScoreSentences = myBr.readLine();//得分》0.5的每个句子
//						
//						nowScore = Double.valueOf(topSentences.get(j).split("\t")[1]);
//						if(highScoreSentences.indexOf(answer)>=0) rightCounts++;
//						allCounts++;
//					}
//					double precision = rightCounts*1.0/allCounts;//precision准确率
//					bw.write(row+"\t"+topFileNames.get(i)+".0.xml\t"+allCounts+"\t"+precision+ "\r\n");
//				}
//			}
					
					myFis = new FileInputStream("C:\\Users\\danie\\Desktop\\lucene测试\\newcomeon\\newcomeon\\"
							+topFileNames.get(i)+".0.xml"+"\\data\\"+ numOfMostLikeQuestionSentence +".txt");// 读入本地文件
					
					myIsr = new InputStreamReader(myFis);// InputStreamReader
					myBr = new BufferedReader(myIsr);// 从字符输入流中读取文件中的内容
					
//					String mostLikeQuestionText = myBr.readLine();//读取文章中最像问题的句子
					
//					//得到文件数量
//					File folder = new File("C:\\Users\\danie\\Desktop\\lucene测试\\newcomeon\\newcomeon\\"
//							+topFileNames.get(i)+".0.xml"+"\\data");
//					int maxNo = (folder.list().length)-2;
					//System.out.println("【Max no:" + maxNo +"】");
					
					//answerTag = 0;//标注句子中是否存在answer
					
					//for(int point=-10;point<=10;point++) {//定义规则，从什么什么范围检索
//					for(int point=-1;point<=maxNo;point++) {
//						answerTag = 0;
//						//int pointName = numOfMostLikeQuestionSentence + point;
//						int pointName = point;
//
////						if(pointName<-1) {
////							pointName=-1;//判断句子标号是否越下界
////							continue;
////						}
////						if(pointName>maxNo) {
////							pointName=maxNo;//上界
////							continue;
////						}
					

//						myFis = new FileInputStream("C:\\Users\\danie\\Desktop\\lucene测试\\newcomeon\\newcomeon\\"
//								+topFileNames.get(i)+".0.xml"+"\\data\\"+ pointName +".txt");// 读入本地文件
//						myIsr = new InputStreamReader(myFis);// InputStreamReader
//						myBr = new BufferedReader(myIsr);// 从字符输入流中读取文件中的内容
						
						String tempText = myBr.readLine();//读取文章中最像问题的句子的前后句子
						int existInString = tempText.indexOf(answer);
						if(existInString>=0) answerTag ++;//如果存在则标1
//						
//						int fileNumOfTop = 0;//如果这个句子在定义的前十篇规则中出现了，则输出排名
//						int flagOfFileNum = 0;//在检索规则中检索到与否标为0/1
//						
//						if(fileName.equals(everyFileName)) {
//							fileNumOfTop = i+1;
//						}
//						else {
//							fileNumOfTop = 0;
//						}
//						
//						if(fileNumOfTop!=0) {
//							flagOfFileNum = 1;
//						}
////
//						if(answerTag==1) {
//						bw.write(row+"\t"+topFileNames.get(i)+".0.xml\t"+flagOfFileNum+ "\t"+ fileNumOfTop +"\t"+ point
//								+"\t"+tempText+"\t"+answerTag+"\r\n");
//						if(flagOfFileNum==1) {
//						bw.write(row+"\t"+topFileNames.get(i)+".0.xml\t"+flagOfFileNum+ "\t"+ fileNumOfTop +"\t"+ "\r\n");
//						break;
//						}				
					}
					
					//break;
				}
				bw.write(row + "\t"+ answerTag*1.0/(4*everyNumOfFile) + "\r\n");
			}
//					}
//				}	
				//if(flag) flag = false; //去除第一行的问题
				 
//			}
//			newText = strb.toString();
//			System.out.println(newText);// 打印出str2
//			bw.write(newText);// 写入文件
	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("找不到指定文件!!!");

		} catch (IOException e) {
			System.out.println("读取/写入文件失败");
		} finally {
			try {
				System.out.println("【问题处理完毕！】");
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
