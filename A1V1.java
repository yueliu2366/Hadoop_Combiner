/*
Author      : Yue Liu
Organization: Northeastern University
Date        : Feb. 3, 2015
*/
import java.io.File;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class A1V1 {
	public static boolean isFloat(String s){
		try
		   {
		      Float.parseFloat(s);
		      return true;
		   }
		   catch(NumberFormatException e)
		   {
		      return false;
		   }
	}
	
	
	public static void main(String args[]) {
		try { 	
			long startTime=System.currentTimeMillis(); 		
			File writename = new File(System.getProperty("user.dir") + "/a1v1output"); 
			writename.createNewFile(); 
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));
			
			String pathname = System.getProperty("user.dir");
			pathname = pathname + "/purchases4.txt";

			File filename = new File(pathname); 
			
			
			InputStreamReader reader = new InputStreamReader(
					new FileInputStream(filename)); 
			BufferedReader br = new BufferedReader(reader); 
			String line = "";
			line = br.readLine();
			HashSet<String> hs = new HashSet();
			ArrayList<String> keyLs = new ArrayList<String>();
			while (line != null) {				
				String[] oneLine = line.toString().split("	");
				if(oneLine.length < 5)
					{line = br.readLine();continue;};
				String tmp = oneLine[0];
				if (tmp.equals("Date"))
				    {line = br.readLine();continue;};
				if(!isFloat(oneLine[4]))
				    {line = br.readLine();continue;}; 
				//  System.out.println(oneLine[3]);
			    if(hs.contains(oneLine[3]))
			    	{line = br.readLine();continue;}
			    else{
			    	hs.add(oneLine[3]);
			    	keyLs.add(oneLine[3]);
			    }
			   
				line = br.readLine();
			}
			System.out.println(keyLs + " "+"size is :"+keyLs.size());
			 for(String key : keyLs){
			    	ArrayList<Float> tm = new ArrayList<Float>();
			        reader = new InputStreamReader(new FileInputStream(filename)); 
					br = new BufferedReader(reader); 
					line = "";
					line = br.readLine();
			    	//System.out.println(key);
					while (line != null) {				
						String[] oneLine = line.toString().split("	");
						if(oneLine.length < 5)
							{line = br.readLine();continue;};
						String tmp = oneLine[0];
						if (tmp.equals("Date"))
						    {line = br.readLine();continue;};
						if(!isFloat(oneLine[4]))
						    {line = br.readLine();continue;};
					    if(key.equals(oneLine[3]))
					    	tm.add(Float.parseFloat(oneLine[4]));
					    else
					        {line = br.readLine(); continue;}
					    

						line = br.readLine(); 
						
					}
			    	Collections.sort(tm);
			    	Float median;
			    	int len = tm.size();
			    	if (len % 2 == 0)
						median = (tm.get(len / 2) + tm.get(len / 2 - 1)) / 2;
					else
						median = tm.get(len / 2);
			    	System.out.println(key+"	"+ median);
			    	out.write(key+"	"+ median + "\r\n");
			    }
			
			out.flush(); 
			out.close();
			long endTime=System.currentTimeMillis();
			
			System.out.println("time: " + (endTime - startTime) + "ms");  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}