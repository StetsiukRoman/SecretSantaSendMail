package com.stetsiuk;

import java.util.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

public class ThreadShuffleMails {

	public static void printList(String msg, List<String[]> l){
		System.out.println(msg);
		for(String[] s : l){
			System.out.println("Name: "+ s[0] + " Email:"+ s[1]);	
		}
	}
	
	public static void main(String[] args) {
		
		 List<String[]> weList = new ArrayList();
		 String csvFile = "file_with_names_and_emails.csv"; //name,email
			BufferedReader br = null;
			String line = "";
			String cvsSplitBy = ",";
		int N = 0;
		 
			try {
				br = new BufferedReader(new FileReader(csvFile));
				while ((line = br.readLine()) != null) {
					String[] data = line.split(cvsSplitBy);
					weList.add(data);
					N++;
				}
				
				printList("Before", weList);
				Collections.shuffle(weList);
				printList("after", weList);	
				
			for(int i=0; i<N-1;i++){	
				Thread sendMail = new Thread(new EmailRunnable(weList.get(i+1)[0],"SantaThread", weList.get(i)[1]));
				sendMail.start();
			}
			Thread sendMail = new Thread(new EmailRunnable(weList.get(0)[0], "SantaThread",weList.get(N-1)[1]));
			sendMail.start();
					
			
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

	}

}
