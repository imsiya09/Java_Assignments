package com.psl.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.psl.bean.Dish;
import com.psl.bean.Location;
import com.psl.bean.Order;
import com.psl.util.DominosPizzaDaoImpl;

public class ClientClass {
	public static void main(String[] args) {
		DominosPizzaDaoImpl obj = new DominosPizzaDaoImpl();

		List<Dish> dishList = new ArrayList<Dish>();
		List<Location> locationList = new ArrayList<Location>();
		List<Order> listOrder = new ArrayList<Order>();
		Scanner sc = new Scanner(System.in);

		int choice = 0;

		do {

			System.out.println("    ********** MENU **********    ");
			System.out.println("1 . List Of Dishes & Locations where they available..!!");
			System.out.println("2 . Delivery Locations With In 30 Minutes");
			System.out.println("3 . CalculateOrder & Cost");
			System.out.println("4 . Free Delivery Location ");
			System.out.println("8 . Exit");
			System.out.println("");
			System.out.println("enter condition value");

			choice = sc.nextInt();

			switch (choice) {

			case 1 :
					System.out.println("List of dish and available Locations \n");
					obj.populateData("dish.txt", "location.txt", dishList, locationList);
					System.out.println(dishList.toString());
					break;

			case 2 :
					System.out.println("Order Delivered in less then 30 minutes..");
					obj.populateData("dish.txt", "location.txt", dishList, locationList);
					obj.calculateLocationForDistance(dishList, locationList);
					break;

			case 3 :
					obj.populateData("dish.txt", "location.txt", dishList, locationList);
					listOrder = obj.calculateOrder("order.txt", dishList, locationList);
					System.out.println(listOrder.toString());
					break;
				
			case 4 :
					obj.populateData("dish.txt", "location.txt", dishList, locationList);
					listOrder = obj.calculateOrder("order.txt", dishList, locationList);
					obj.freeDelivery(listOrder, dishList, locationList);
				    break;

			case 8:
					System.exit(0);
					break;

			default:
					System.out.println("Please enter valid choice..");
					break;

			}

		} while (choice != 8);

	}
}
