package com.kafka.practice.process;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class KRecordProcessor implements Runnable{
	
	private ConsumerRecord<String, String> record;
	
	KRecordProcessor(ConsumerRecord<String, String> record) {
		this.record = record;
	}
	
	private void processRecord() {
		System.out.printf("Thread name = %s, Partition=%d, Value = %s%n", Thread.currentThread().getName(), record.partition(), record.value());
		//Record Processing logic
	}

	@Override
	public void run() {
		this.processRecord();
	}
	
}
