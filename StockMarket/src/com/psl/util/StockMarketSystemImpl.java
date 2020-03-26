
/*
 * Author :Snehal Patil,Nagpur
 * Email  :snehalpatil2011@gmail.com
 * 
 * Disclaimer :The projects questions does not have any resemblance
 *  			with the actual exam.Though they are on the same pattern 
 *  			that I have experienced in practice assignmets.It contains the ".rtf file" 
 *  			which describes the problem statement .
 *  
 *  In case of violation of Any privacy or rights please inform
 *  
 *  
 *                "Practice makes a man perfect.................... and women too"
 */

package com.psl.util;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.psl.bean.Sector;
import com.psl.bean.Stock;

public class StockMarketSystemImpl implements StockMarketSystem 
{
//---------READING THE DATA HERE---------//
	public Map<Sector, List<Stock>> readData() 
	{
		Map<Sector, List<Stock>> map=new HashMap<Sector, List<Stock>>();
		
		for (Sector sector:Sector.values())
		{
			map.put(sector,new ArrayList<Stock>());//yha se hum data put kra re hain map ki key me 
		}
		
		List<Stock> listStock=new ArrayList<Stock>();
		
		Connection connection=null;
		Statement stmt=null;
		ResultSet rs=null;
		
		connection=new ConnectionManagerImpl().getConnection();
		
		try {
			stmt=connection.createStatement();
			rs=stmt.executeQuery("SELECT * FROM stockmarketdb s;");
			while (rs.next()) 
			{
				double totalProfit =0;
				double totalLoss=0;
				
				String stockName=rs.getString(1);
				Sector sectorType=Sector.valueOf(rs.getString(2).trim());
				double costPrice=rs.getDouble(3);
				double presentPrice=rs.getDouble(4);
				Date purchaseDate=rs.getDate(5);
				int numberOfShares=rs.getInt(6);
//				double totalProfit=0;
				
				double cost = (presentPrice-costPrice)*numberOfShares;
				
				
				if(cost>0)
				{
					 totalProfit=cost;
				}
				else if(cost<=0)
				{
					 totalLoss = cost;
				}
				
				listStock.add(new Stock(stockName, sectorType, costPrice, presentPrice, purchaseDate, numberOfShares, totalProfit,totalLoss));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally{
			try {
				rs.close();
				stmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		Set<Map.Entry<Sector, List<Stock>>> set=map.entrySet();
		for (Entry<Sector, List<Stock>> entry : set) {
			for (Stock stock:listStock) {
				if(stock.getSectorType().equals(entry.getKey())){
					map.get(entry.getKey()).add(stock);
				}
			}
		}
		
		return map;


	}

	//---------CALCULATING-THE-PROFIT-HERE--------//
	public void calculateProfit(Map<Sector, List<Stock>> map)
	{
		Set<Map.Entry<Sector, List<Stock>>> set=map.entrySet();
		for (Entry<Sector, List<Stock>> entry : set) {
			for(Stock stock:entry.getValue()){
			
				double cost = (stock.getPresentPrice()-stock.getCostPrice())*stock.getNumberOfShares();
				
				if(cost >=0)
				{
				stock.setTotalProfit(cost);
				}
				else if(cost <=0)
				{
					stock.setTotalLoss(cost);
				}
			}
		}

	}
 
    //------------GET-STOCK-SECTORWISE-IN-BY-DEFAULT-ACCENDING-ORDER-----------//	
	public List<Stock> getsStockSectorwise(Map<Sector, List<Stock>> map, Sector sectorType) 
	{
		
			
			Collections.sort(map.get(sectorType),new Comparator<Stock>()
			{
				@Override
				public int compare(Stock s1, Stock s2)
				{
					return s1.getPurchaseDate().compareTo(s2.getPurchaseDate());
				}
			});
			
			
			return map.get(sectorType);
		
	
	}

	//--------------STOCKS-TO-SELL--------------//
	public List<Stock> stockToSell(Map<Sector, List<Stock>> map)
	{
		long current=Calendar.getInstance().getTimeInMillis();
		List<Stock> returnList=new ArrayList<Stock>();
		Set<Map.Entry<Sector, List<Stock>>> set=map.entrySet();
		for (Entry<Sector, List<Stock>> entry : set) {
			for(Stock stock:entry.getValue()){
				if(stock.getTotalProfit()<1){
					
					long given=stock.getPurchaseDate().getTime();
					int numberOfDays=(int)((current-given)/(24*60*60*1000));
					int numberOfWeaks=(int)(numberOfDays/7);
				//	System.out.println(numberOfWeaks+"\t"+stock.getStockName());
					if(numberOfWeaks>50)
					{
						returnList.add(stock);
					}
				}
			}
		}
		
		return returnList;

	}

}
