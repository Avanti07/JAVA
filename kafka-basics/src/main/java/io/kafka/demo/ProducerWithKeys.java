package io.kafka.demo;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerWithKeys {
    private  static  final Logger log = LoggerFactory.getLogger((ProducerWithKeys.class.getSimpleName()));
    public static void main(String[] args) {
        log.info("I am Kafka Producer With CallBack");

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

        for (int j =0 ; j< 2; j++){
            for (int i =0 ; i< 10; i++){

                String topic = "first_topic";
                String key = "id_" + i;
                String value = "hello world "+ i;

                //create producer record - this will go to demo java topic and value is hello world
                ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, key, value);

                //send data
                producer.send(producerRecord, new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata metadata, Exception e){
                        if(e==null){
                            log.info("Key: " + key +  "| Partition: " + metadata.partition());
                        }
                        else{
                            log.error("Erroe while producing custom message", e);
                        }
                    }
                });

            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        producer.flush();
        producer.close();
}}
