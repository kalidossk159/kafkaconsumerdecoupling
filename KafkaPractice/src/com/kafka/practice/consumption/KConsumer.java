package com.kafka.practice.consumption;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

import com.kafka.practice.process.KRecordProcessorPool;

public class KConsumer implements Runnable{
	
	private KafkaConsumer<String, String> consumer;
	
	public KConsumer() {
		try {
			Properties props = new Properties();
		    props.setProperty("bootstrap.servers", "localhost:9092");
		    props.setProperty("group.id", "test");
		    props.setProperty("enable.auto.commit", "true");
		    props.setProperty("auto.commit.interval.ms", "1000");
		    props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		    props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		    consumer = new KafkaConsumer<>(props);
		    consumer.subscribe(Arrays.asList("testtopic"));
		    System.out.println("Consumer created successfully");
		}catch(Exception e) {
			System.out.println("Exception while consumer creation");
			e.printStackTrace();
		}
	}
	
	private void consume() {
		try {
			while(true) {
				ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
				for (ConsumerRecord<String, String> record : records) {
				    System.out.println(Thread.currentThread().getName() + " is going to submit record : " + record.value() + " polled from partition - " + record.partition());
				    KRecordProcessorPool.execute(record);
				}
			}
		}
		catch(WakeupException e) {
			System.out.println(Thread.currentThread().getName() + " successfully consumed records");
		}
		catch(Exception e) {
			System.out.println("Exception during consumption for " + Thread.currentThread().getName());
			e.printStackTrace();
		}
		finally {
			consumer.close();
			System.out.println("Consumer destroyed successfully for " + Thread.currentThread().getName());
		}
	}
	
	
	public void wakeup() {
		consumer.wakeup();
		System.out.println("Woke up the consumer successfully");
	}

	@Override
	public void run() {
		this.consume();
	}
}
