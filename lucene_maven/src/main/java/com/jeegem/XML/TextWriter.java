package com.jeegem.XML;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class TextWriter {

	/**
	 * @return int 写了多少行
	 * @usage	读取一个
	 */
	public static int writeLabeledNewText(File inputTxt, ArrayList<String> msgToAppend){

		int rowCount = 0;
		
        FileInputStream fis = null;//文件输入流
        FileOutputStream fos = null;//文件输出流
        InputStreamReader isr = null;
        OutputStreamWriter osw = null;
        BufferedReader br = null;
        BufferedWriter bw =null;
        try {
            String preLine = "";
            String msg2Append = "";
            String newText = "";
            fis = new FileInputStream(inputTxt);// 读入本地文件
            fos = new FileOutputStream("D:\\outcome.txt");//写入本地文件
 
            isr = new InputStreamReader(fis);// InputStreamReader
            osw = new OutputStreamWriter(fos,"UTF-8");//写入文件编码格式为UTF-8
 
            br = new BufferedReader(isr);// 从字符输入流中读取文件中的内容
            bw = new BufferedWriter(osw);//将字符输出流写入到文件中
            
            String strb= "";//定义一个可变类型的String
 
            while ((preLine = br.readLine()) != null) {
            	msg2Append = msgToAppend.get(rowCount);
                strb+=preLine+'\t'+msg2Append+"\r\n";//当读取的一行不为空时,把读到的str和str1进行连接并把值赋给str2
                
                /**
                 *  windows -->   \r\n

    				Linux         -->   \r

    				mac         -->   \n
                 */
                rowCount++;
            }
            newText = strb.toString();
            System.out.println(newText);// 打印出str2
            bw.write(newText);//写入文件
            return rowCount;
 
        } catch (FileNotFoundException e) {
            System.out.println("找不到指定文件");
            return -1;
        } catch (IOException e) {
            System.out.println("读取/写入文件失败");
            return -1;
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
	
	public static int writeTXT(ArrayList<String> rows,String target) {
		
        FileOutputStream fos = null;//文件输出流
        OutputStreamWriter osw = null;
        BufferedWriter bw =null;
        
        try {
            String row =  null;
            int rowCount = 0;
            fos = new FileOutputStream(target);//写入本地文件
 
            osw = new OutputStreamWriter(fos,"UTF-8");//写入文件编码格式为UTF-8
 
            bw = new BufferedWriter(osw);//将字符输出流写入到文件中
            
            String strb= "";//定义一个可变类型的String
 
            Iterator<String> iter = rows.iterator();
            while(iter.hasNext()) {
                strb+= iter.next()+"\r\n";//当读取的一行不为空时,把读到的str和str1进行连接并把值赋给str2
                rowCount++;
            }
            String txt = strb.toString();
            System.out.println(txt);// 打印出str2
            bw.write(txt);//写入文件
            return rowCount;
 
        } catch (FileNotFoundException e) {
            System.out.println("找不到指定文件");
            return -1;
        } catch (IOException e) {
            System.out.println("读取/写入文件失败");
            return -1;
        } finally {
            try {
                bw.close();
                osw.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        
		
	}
	
	public static void main(String[] args) {
		ArrayList<String> s = new ArrayList<>();
		s.add("haha");
		s.add("heheasdasdsa");
		writeLabeledNewText(new File("D:/1.txt"), s);
	}
}
