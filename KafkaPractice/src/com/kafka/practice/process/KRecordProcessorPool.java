package com.kafka.practice.process;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class KRecordProcessorPool {
	
	private static ExecutorService recordProcessorPool;
	
	public static void init(int noOfThreads){
		try {
			recordProcessorPool = Executors.newFixedThreadPool(noOfThreads);
			System.out.println("Record processors created successfully");
		}
		catch(Exception e) {
			System.out.println("Exception while record processors creation");
			e.printStackTrace();
		}
	}
	
	public static void execute(ConsumerRecord<String, String> record) {
		try {
			recordProcessorPool.execute((new KRecordProcessor(record)));
		}
		catch(Exception e) {
			System.out.println("Exception while record submission");
			e.printStackTrace();
		}	
	}
	
	public static void destroy(){
		try {
			recordProcessorPool.shutdown();
			System.out.println("Record processors successfully shut down");
		}
		catch(Exception e) {
			System.out.println("Exception while record processors shut down");
			e.printStackTrace();
		}
	}
}
