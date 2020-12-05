## Motivation
  To implement Kafka worker thread pool pattern which allows for decoupling of Kafka consumer threads from record processing.
  
## Implementation
    The implementation can be broken down into 4 different units, namely, record production unit, record consumption unit, and record processing unit, orchestration unit.
    
  ### Record production unit
    This unit produces records into Kafka.
    
    Classes 
      KProducer - Responsible for managing the lifecycle of a Kafka Producer instance and production of records into Kafka.
  
  ### Record consumption unit
    This unit is responsible for creating multiple consumers that belong to a consumer group and have them consume records from Kafka parallely.
    
    Classes
      KConsumer - Responsible for managing the lifecyle of a Kafka Consumer instance and consuming records from Kafa.
      KConsumerPool - Responsible for managing the lifecyle of an ExecutorService instance, initiates record consumption using KConsumer objects, and wakes up consumer objects.
      
  ### Record processing unit
    This unit is responsible for creating multiple worker threads and have them process the records consumed from Kafka.
    
    Classes
      KRecordProcessor - Responsible for processing the records. Business logic starts here.
      KRecordProcessorPool - Responsible for managing the lifecyle of an ExecutorService instance, and submission of consumed records to the worker thread pool. This holds the worker thread pool and acts as the decoupling entity between consumer threads and worker threads.
  
  ### Orchestration unit
    This unit is responsible for orchestrating the entire execution.
    
    Classes
      KDriver - This is the entry point of execution and drives the entire execution.
      
## TODO

    1. As of now, exceptions are not taken advantage of to properly control the flow of execution. Exceptions are just logged as and when they occur. This needs to be handled in a way that closes any open resources or thread pools if an exception occurs.
    
    2. Producer and consumer configurations are hardcoded in the code. This has to be moved to a properties file.
    
## Cons
   This is just a very basic implementation of Kafka usage. This does not do any fine tuning of Kafka. This implementation works with a single producer and 2 consumers belonging to the same consumer group and the topic "testtopic" only has 2 partitions.
