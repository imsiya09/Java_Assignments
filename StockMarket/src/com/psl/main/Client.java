
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



package com.psl.main;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.psl.bean.Sector;
import com.psl.bean.Stock;
import com.psl.util.StockMarketSystemImpl;

public class Client
{

	public static void main(String[] args) 
	{
		StockMarketSystemImpl impl=new StockMarketSystemImpl();
		Map<Sector, List<Stock>> map=impl.readData();
		Set<Map.Entry<Sector, List<Stock>>> set=map.entrySet();
		
		//---------READING-THE-DATA-HERE---------//
		for (Entry<Sector, List<Stock>> entry : set) {
			for(Stock stock:entry.getValue()){
				//System.out.println(stock.getStockName()+"\t"+stock.getSectorType()+"\t"+stock.getCostPrice()+"\t"+stock.getPresentPrice()+"\t"+stock.getNumberOfShares()+"\t"+stock.getTotalProfit());
			System.out.println(stock);
			}
		}
		
		
		//----------CALCULATING-THE-PROFIT-HERE-------//
		System.out.println("\n\n");
		impl.calculateProfit(map);
		for (Entry<Sector, List<Stock>> entry : set) 
		{
			System.out.println("...................."+entry.getKey());
			for(Stock stock:entry.getValue())
			{
				System.out.println(stock.getCostPrice()+"\t"+stock.getPresentPrice()+"\t"+stock.getNumberOfShares()+"\t"+stock.getTotalProfit());
			}
		}	
		
		
       //----------GET-STOCK-SECTORWIS-IN-BY-DEFAULT-ACCENDING-ORDER------//
		System.out.println("\n\n");
		List<Stock>  list=impl.getsStockSectorwise(map,Sector.Infotech);
		
		for (Stock stock : list) 
		{
			System.out.println(stock.getSectorType()+"\t"+stock.getPurchaseDate());
		}
		
		
		//--------STOCK-TO-SELL--------//
		System.out.println("\n\n");
		List<Stock>  list1=impl.stockToSell(map);
		System.out.println(list1);
		
	}
	
}