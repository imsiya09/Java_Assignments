package com.psl.util; //implimentation of interface

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.psl.bean.Dish;
import com.psl.bean.Location;
import com.psl.bean.Order;

public class DominosPizzaDaoImpl implements DominosPizzaDao {

	@Override
	public void populateData(String dishFile, String locationFile, List<Dish> dishs, List<Location> locations) 
	{
		String locline;

		try 
		{
			BufferedReader location = new BufferedReader(new FileReader(locationFile));
			String arr[];

			do
			{
				locline = location.readLine();

				if (locline != null) 
				{
					arr = locline.split(",");
					locations.add(new Location(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]),Double.parseDouble(arr[2])));
				}
			} 
			while (locline != null);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}


		try 
		{
			BufferedReader dish = new BufferedReader(new FileReader(dishFile));

			String dishline;

			do 
			{
				String arr[];
				dishline = dish.readLine();

				if (dishline != null) 
				{
					arr = dishline.split(",");
					dishs.add(new Dish(Integer.parseInt(arr[0]), arr[1], Double.parseDouble(arr[2]),Double.parseDouble(arr[3])));
				}
			} 
			while (dishline != null);
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}

		for (Dish d : dishs) //dish ki list me locations ko add kra lia.
		{
			d.setList(locations);
		}
		 
	}

	@Override
	public void calculateLocationForDistance(List<Dish> dishs, List<Location> locations) 
	{
		List<Location> list = new ArrayList<Location>();

		for (Dish d : dishs) 
		{
			for (Location l : locations) 
			{
				double time = d.getTimeToCook() + l.getLocationTime();
				
				if (time <= 30) 
				{
					System.out.println("Dish :: \t"+ d.getDishName()+ 
										"\t deliver in \t"+ time + 
										"\t minutes in \t"+ l.getLocationCode()+ 
										"\t location" );
					list.add(l);
				}
			}
		}
		 
	}

	@Override
	public List<Order> calculateOrder(String orderFile, List<Dish> dishs, List<Location> locations) 
	{
		List<Order>orderFinal=new ArrayList<Order>();
		
		try {
			Scanner scanner=new Scanner(new File(orderFile));
			int dishID;
			int locCode;
			
			while(scanner.hasNext())
			{
				String data[]=scanner.nextLine().split(",");
				dishID=Integer.parseInt(data[0]);
				locCode=Integer.parseInt(data[1]);
				for(Dish d:dishs)
				{
					for(Location l:locations)
					{
						if(d.getDishId()==dishID && l.getLocationCode()==locCode)
					
						{
							double time =d.getTimeToCook()+l.getLocationTime();
							
							if(time<=30)
								{
								Order o=new Order(dishID,locCode);
								orderFinal.add(o);
								double cost=d.getCost()+l.getLocationDistance();
								o.setTotalCost(cost);
								}
							
						}
					}
				}
			}
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		return orderFinal;
	}

	@Override
	public void freeDelivery(List<Order> orders, List<Dish> dishs, List<Location> locations) 
	{
		for (Order o : orders) 
		{
			for (Dish d : dishs) 
			{
				for (Location l : locations) 
				{
					if (o.getDishId() == d.getDishId() && o.getLocationCode() == l.getLocationCode()) 
					{
						if (l.getLocationDistance() <= 10 && d.getCost() >= 200) 
						{
							System.out.println(o + " location dist= " + l.getLocationDistance() + " km");
						}
					}
				}
			}
		}

	}

}
