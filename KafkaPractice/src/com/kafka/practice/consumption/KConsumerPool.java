package com.kafka.practice.consumption;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class KConsumerPool {
	
	private static ExecutorService consumerPool;
	private static List<KConsumer> consumers = new LinkedList<>();
	
	public static void init(int noOfConsumers){
		try {
			consumerPool = Executors.newFixedThreadPool(noOfConsumers);
			for(int i = 1 ; i <= noOfConsumers ; i++) {
				consumers.add(new KConsumer());
			}
			System.out.println("Consumer pool created successfully");
		}
		catch(Exception e) {
			System.out.println("Exception while consumer pool creation");
			e.printStackTrace();
		}
		
	}
	
	public static void startConsumption(){
		try {
			for(KConsumer consumer : consumers) {
				consumerPool.execute(consumer);
			}
			System.out.println("Record consumption initiated successfully");
		}
		catch(Exception e) {
			System.out.println("Exception while record consumption initiation");
			e.printStackTrace();
		}
	}
	
	public static void destroy(){
		try {
			for(KConsumer consumer : consumers) {
				consumer.wakeup();
			}
			consumerPool.awaitTermination(500, TimeUnit.MILLISECONDS);
			System.out.println("Consumer pool destroyed successfully");
		}
		catch(Exception e) {
			System.out.println("Exception while consumer pool destruction");
			e.printStackTrace();
		}
	}
}
