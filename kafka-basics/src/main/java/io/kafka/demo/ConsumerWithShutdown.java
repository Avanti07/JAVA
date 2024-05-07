package io.kafka.demo;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class ConsumerWithShutdown {
    private  static  final Logger log = LoggerFactory.getLogger((ConsumerWithShutdown.class.getSimpleName()));
    public static void main(String[] args) {
        log.info("I am Kafka Consumer with graceful shutdown");

        String groupId = "my-java-application";
        //create Producer properties. create producer, send data, flush & close
        Properties properties = new Properties();

        // connect to localhost
            //properties.setProperty("bootstrap.servers","127.0.0.1:9092");

        //connect to upstash
        properties.setProperty("bootstrap.servers", "https://massive-husky-8223-us1-kafka.upstash.io:9092");
        properties.setProperty("security.protocol", "SASL_SSL");
        properties.setProperty("sasl.jaas.config", "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"bWFzc2l2ZS1odXNreS04MjIzJAh4Ojtg8QFTCNtTlpIAhkZIQj8OSUxhLbNs1sM\" password=\"YzgwMDk5OTgtM2EzZS00MjFlLWI1Y2YtNWMyMzdkODQ3Zjkw\";");
        properties.setProperty("sasl.mechanism", "SCRAM-SHA-256");

        //producer properties
        properties.setProperty("key.deserializer", StringDeserializer.class.getName());
        properties.setProperty("value.deserializer", StringDeserializer.class.getName());

        properties.setProperty("group.id", groupId);
        properties.setProperty("auto.offset.restet", "earliest");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);

        //get refernce to main thread
        final Thread mainThread = Thread.currentThread();

        //add shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(){
            //this will run by thread isf there is shutdown
            public void run() {
                log.info("Detected shutdown, exit by calling consumer.wakeup().....");
                consumer.wakeup();  ///exit while loop

                //next time when .poll is called then wakeup execption is called
                // when execption is thrown it wait for main thread to complete hence join() is called
                try {
                    mainThread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }finally {
                    consumer.close();
                }
            }
        });

        //subscribe topic
        consumer.subscribe(Arrays.asList("demo_java")); // can subs multiple

        try {
            while (true) {
                log.info("Polling");
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));

                for (ConsumerRecord<String, String> record : records) {
                    log.info("Key: " + record.key() + " Value: " + record.value());
                    log.info("Partition: " + record.partition() + " Offset: " + record.offset());
                }
            }
        }catch (WakeupException e){
            log.info("Shutting down consumer");
        }

//        try (var producer = new KafkaProducer<String, String>(props)) {
//        }  optional way to write above code

    }
}
