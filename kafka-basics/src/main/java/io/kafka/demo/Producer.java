package io.kafka.demo;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.LoggerFactory;

import org.slf4j.Logger;

import java.util.Properties;

public class Producer {
    private  static  final Logger log = LoggerFactory.getLogger((Producer.class.getSimpleName()));
    public static void main(String[] args) {
        log.info("I am Kafka Producer");

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
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());

        //create Producer
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        //create producer record - this will go to demo java topic and value is hello world
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("demo_java", "hello world");

        //send data
        producer.send(producerRecord);

        producer.flush();
        producer.close();

//        try (var producer = new KafkaProducer<String, String>(props)) {
//        }  optional way to write above code

    }
}
