package com.jeegem.lucene_maven;

import java.io.File;
import java.util.ArrayList;

/**
 * 得到一个文件夹里面的文件名
 * 
 * @author Daniel Dong
 *
 */

public class getFileName {
	
	private getFileName() {}

	public static ArrayList<String> getAllFileName(String path) {
			
		ArrayList<String> nameResults = new ArrayList<>();
		File file=new File(path);
		String test[];
		test=file.list();
		for(int i=0;i<test.length;i++)
		{
		nameResults.add(test[i]);
		//System.out.println(test[i]);
		}
		return nameResults; 
	}

}
