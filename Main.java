package com.exam;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class Main {
	private static String floatToString(double value) {
		DecimalFormat df = new DecimalFormat("0.00");
	    String val=df.format(value);
	    return val;  
	}
	public static String salesTaxCalculation(double billAmount) {
		  double taxAmount = 0.0;
	        if (billAmount <= 1000) {
	            taxAmount = billAmount * 0.125;
	        } else if (billAmount > 1000 && billAmount <= 2500) {
	            taxAmount = billAmount * 0.10;
	        } else {
	            taxAmount = billAmount * 0.075;
	        }
	        String tax = floatToString(taxAmount);
	        return tax;
	}
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter Customer Name:");
		String cusname=sc.nextLine();
		System.out.println("Enter number of items the customer purchases:");
		int n=Integer.parseInt(sc.nextLine());
		List<OrderedItem> orderedItemList=new ArrayList<>();
		
		for (int i=0;i<n;i++) {
			System.out.println("Enter name and quantity (comma(,)separate format) of purchased item no "+(i+1));
			String arr=sc.nextLine();
			String[] item = arr.split(",");
			String name=item[0];
			String qty=item[1];
			if (ItemData.isAvailable(name)) {
				SnackItem s=ItemData.getItem(name);
				String Rate=s.getRate();
				String discountRate=s.getDiscountRate();
				String discountQty=s.getDiscountQty();
				Double discountAmount=0.0;
				Double payAmount=Double.parseDouble(Rate)*Double.parseDouble(qty);
				
				if (Double.parseDouble(qty)>=Double.parseDouble(discountQty)) {
					
					discountAmount=payAmount * (Double.parseDouble(discountRate)/100);
					payAmount= payAmount-discountAmount;
				}

				OrderedItem orderItem=new OrderedItem(name,Rate,qty,floatToString(discountAmount),floatToString(payAmount) );
				orderedItemList.add(orderItem);	
			}
			
			else {
				System.out.println("Item is not Available");
			}
			
		}
		DateFormat formatter = new SimpleDateFormat("dd-MM-yy");
		Calendar obj = Calendar.getInstance();
		String date = formatter.format(obj.getTime());
		System.out.println("\nCustomer Name:"+cusname+"\t\t\tDate:"+date);
		System.out.println("\n");
		String output=String.format("%-10s %-10s %-10s %-10s %-10s %-10s", "ITEM" ,"RATE","QUANTITY","PRICE","DISCOUNT","AMOUNT");
		System.out.println(output);
		Double billAmount=0.0;
		for (OrderedItem d:orderedItemList) {
			String name=d.getItemName();
			String rat=d.getRate();
			String qt=d.getOrderQty();
			SnackItem s=ItemData.getItem(name);
			Double price=Double.parseDouble(s.getRate())*Double.parseDouble(qt);
			String disc=d.getDiscountAmount();
			String amount=d.getPayAmount();
			billAmount+= Double.parseDouble(amount);
			String out=String.format("%-10s %-10s %-10s %-10s %-10s %-10s", name,rat,qt,price,disc,amount);
			System.out.println(out);
		}
		String tax=salesTaxCalculation(billAmount);
		Double total=billAmount+Double.parseDouble(tax);
		System.out.println("\n");
		System.out.println("\t\t\t\t Bill Amount:"+billAmount);
		System.out.println("\t\t\t         Add: Sales Tax:"+tax);
		System.out.println("\t\t\t\t Final Amount:"+total);
	
		
	}

}
