package com.kafka.practice.production;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class KProducer {
	
	private static Producer<String, String> producer;
	
	public static void init() {
		try {
			Properties props = new Properties();
			props.put("bootstrap.servers", "localhost:9092");
			props.put("acks", "all");
			props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
			props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
			producer = new KafkaProducer<>(props);
			System.out.println("Producer created successfully");
		}
		catch(Exception e) {
			System.out.println("Exception while producer creation");
			e.printStackTrace();
		}
	}
	
	public static void produce() {
		try {
			System.out.println("Going to produce records");
			for(int i = 1 ; i <= 20 ; i++) {
				producer.send(new ProducerRecord<String, String>("testtopic", 0, "Partition - 0", Integer.toString(i)));
			}
			for(int i = 21 ; i <= 40 ; i++) {
				producer.send(new ProducerRecord<String, String>("testtopic", 1, "Partition - 1", Integer.toString(i)));
			}
			System.out.println("Produced records successfully");
		}
		catch(Exception e) {
			System.out.println("Exception while record production" );
			e.printStackTrace();
		}
	}
	
	public static void destroy() {
		try {
			producer.close();
			System.out.println("Producer destroyed successfully");
		}
		catch(Exception e) {
			System.out.println("Exception while producer destruction");
			e.printStackTrace();
		}
	}

}
