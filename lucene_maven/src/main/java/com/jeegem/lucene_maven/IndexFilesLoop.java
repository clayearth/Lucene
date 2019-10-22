package com.jeegem.lucene_maven;

import java.util.ArrayList;
import com.jeegem.lucene_maven.getFileName;
import com.jeegem.lucene_maven.IndexFiles;

public class IndexFilesLoop {
	
	public static void IndexAllFiles(String path) throws Exception {
		ArrayList<String> allNames =  getFileName.getAllFileName(path);
		for(Integer i=0;i<allNames.size();i++) {
			IndexFiles.IndexMaking(allNames.get(i),path);
			System.out.println(allNames.get(i)+" is successfully indexed!");
		}
	}
	
	public static void main(String[] args) {
		
		try {
			IndexAllFiles("C:\\Users\\danie\\Desktop\\lucene测试\\newcomeon\\newcomeon");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
		
	}
}

