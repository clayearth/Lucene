package com.jeegem.XML;

import java.io.File;
import java.util.ArrayList;

import com.jeegem.XML.TextWriter;
import com.jeegem.XML.XMLReader;

public class FolderIterator {
	
	public static File[] getFiles(String folderPath){
		File files = new File(folderPath);
		File[] r =  null;
		if(files.isFile()){
			return files.listFiles();
		}
		if(files.isDirectory()&&files.canRead()){
			r = files.listFiles();
			return r;
		}
		return r;
	}
	public static void main(String[] args) {
		File [] fs = FolderIterator.getFiles("C:/Users/筱钰/Documents/【【【【【【结果】】】】】】/Part018");
		ArrayList<String> result = new ArrayList<>();
		for(File f:fs){
			result.addAll(XMLReader.getContAndAttrsInOrder(f, "QUESTION"));
		}
		System.out.println(result);
		
		TextWriter.writeTXT(result, "D:/100.txt");
	}
}
