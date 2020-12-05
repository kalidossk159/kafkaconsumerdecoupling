package com.kafka.practice;

import java.util.Scanner;

import com.kafka.practice.consumption.KConsumerPool;
import com.kafka.practice.process.KRecordProcessorPool;
import com.kafka.practice.production.KProducer;

//TODO: Handle exceptions for all the classes
public class KDriver {

	public static void init() {
		KProducer.init();
		KConsumerPool.init(2);
		KRecordProcessorPool.init(2);
	}
	
	public static void execute() {
		KProducer.produce();
		KConsumerPool.startConsumption();
	}
	
	public static void waitForUserInput() {
		Scanner sc = new Scanner(System.in);
		sc.nextLine();
		sc.close();
	}
	
	public static void main(String[] args){
		init();
		execute();
		waitForUserInput();
		destroy();
	}
	
	public static void destroy(){
		KProducer.destroy();
		KConsumerPool.destroy();
		KRecordProcessorPool.destroy();
	}
}
