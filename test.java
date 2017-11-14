package com.demo.controller;

import java.util.List;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class test {


    public static void main(String[] args) {
    	new test().calculateSeats();
//    	new test().connectMangoDB();
    }
    
	class Seats{
		int seats;
		public void setSeats(int seats){
			this.seats = seats;
		}
		public int getSeats(){
			return seats;
		}
	}
    
    public void calculateSeats(){
    	double result = 0, floorResult, result2 = 0, finalResult = 0, vote = 0;
    	DecimalFormat df = new DecimalFormat("#.#");
    	double totalVote = 0;
    	int i=1;
    	Seats seat = new Seats();
    	
    	Map<String, Double> myMap = new TreeMap<String, Double>();
    	myMap.put("a",  15000.0);
    	myMap.put("b",  6667.0);
    	myMap.put("c",  3125.0);
    	myMap.put("d",  6459.0);
    	seat.setSeats(15);
    	int seats = seat.getSeats();
    	Map<String, String> output = new HashMap<String, String>();
    	
    	for (Entry<String, Double> e : sortByDoubleValue(myMap).entrySet()) {
    		totalVote = (Double) e.getValue() + totalVote;
    	}
    	for (Entry<String, Double> e : sortByDoubleValue(myMap).entrySet()) {
    		if(i == 1){
    			 vote = (Double) e.getValue();
    			 result = (double) (seats * vote/ totalVote);
    			 floorResult = Math.floor(Double.parseDouble(df.format(result)));
    			 result2 = result - floorResult;
           		 finalResult = Double.parseDouble(df.format(result)) - Double.parseDouble(df.format(result2));
    		}
    		else{
    		 vote = (Double) e.getValue();
    		 result = (double) (vote * seats / totalVote) + Double.parseDouble(df.format(result2));
   	    	 floorResult = Math.floor(Double.parseDouble(df.format(result)));
   	    	 result2 = result - floorResult;
   	    	 finalResult = Double.parseDouble(df.format(result)) - Double.parseDouble(df.format(result2));			
    		}
    		output.put((String) e.getKey(), new DecimalFormat("#").format(Math.ceil(finalResult)));
    		i++;
    	}

    	Map<String, String> treeMap = new TreeMap<String, String>(output);
    	System.out.print("Seats: " );
    				printMap(sortByStringValue(treeMap));
    	}
    private static Map<String, Double> sortByDoubleValue(Map<String, Double> unsortMap) {

        List<Entry<String, Double>> list =
                new LinkedList<Map.Entry<String, Double>>(unsortMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> o1,
                               Map.Entry<String, Double> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        Map<String, Double> sortedMap = new LinkedHashMap<String, Double>();
        for (Entry<String, Double> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
    private static Map<String, String> sortByStringValue(Map<String, String> unsortMap) {

        List<Entry<String, String>> list =
                new LinkedList<Map.Entry<String, String>>(unsortMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
            public int compare(Map.Entry<String, String> o1,
                               Map.Entry<String, String> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        Map<String, String> sortedMap = new LinkedHashMap<String, String>();
        for (Entry<String, String> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
    public static <K, V> void printMap(Map<K, V> map) {
    	int i=1;
        for (Map.Entry<K, V> entry : map.entrySet()) {
        	if(i == map.size()){
                System.out.print( entry.getKey()
        				+ " => " + entry.getValue());
        	}
        	else
        	{
                System.out.print( entry.getKey()
        				+ " => " + entry.getValue() + ", ");
        	}
        	i++;
        }
    }
    
    public void connectMangoDB(){
    
    			MongoClient mongo = new MongoClient( "localhost" , 27017 ); 
    			MongoDatabase database = mongo.getDatabase("local");
    		      System.out.println(database);  
    	
    		      
    		      MongoCollection<Document> collection = database.getCollection("pangdb"); 
    		      System.out.println("Collection myCollection selected successfully "); 
    		      Document document = new Document("title", "MongoDB") 
    		    	      .append("id", 1)
    		    	      .append("description", "database") 
    		    	      .append("likes", 100) 
    		    	      .append("url", "http://www.tutorialspoint.com/mongodb/") 
    		    	      .append("by", "tutorials point");  
    		    	      collection.insertOne(document); 
    		    	      System.out.println("Document inserted successfully");     
    }	
}
