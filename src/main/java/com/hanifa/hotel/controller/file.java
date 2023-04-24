package com.hanifa.hotel.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class file {
	public static void main(String[] args) {
		int a=7;
		String c_name="hanifa";
		long phone =9876543210l;
			String s="customer name  :"+c_name+"\nCustomer phone :"+phone+"\n";
			String s6="_____________________________________________________________\n";
			String s7="                     Total=\n";
			String s1="S.NO   P.Name                       P.price   P.Qty   Total  \n";


			String res=s+s1+space(String.valueOf(5),7)+space("Rice",29)+space(String.valueOf(50.0),10)+space(String.valueOf(4),8)+"\n"+s6+s7+s6;
		try {
			FileWriter fileWriter=new FileWriter("d:\\bill\\"+a+".txt");
			fileWriter.write(res);
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	public static String space(String s,int n) {
		int length=n-s.length();
		for (int i = 0; i < length; i++) {
			s+=" ";
		}
		return s;
	}
//	public static String space1(int n) {
//		String s1="";
//		if(n<10) {
//			s1+=n+"      ";
//		}
//		else {
//			s1+=n+"     ";
//		}
//		return s1;
//	}
//	public static String space2(String s) {
//		int n=30-s.length();
//		String s1="";
//		for (int i = 1; i <n; i++) {
//			s1+=" ";
//		}
//		return s+s1;
//	}
//	public static String space3(double price) {
//		int count=0;
//		int price1=(int)price;
//		while(price1!=0) {
//			price1/=10;
//			count++;
//		}
//		int n=8-count;
//		String s="";
//		for (int i = 1; i <=n; i++) {
//			s+=" ";
//		}
//		return price+s;
//	}
//	public static String space4(int qty) {
//		String s="";
//		if(qty<10) {
//			s=qty+"       ";
//		}
//		else {
//			s=qty+"      ";
//		}
//		return s;
//	}
		
}
