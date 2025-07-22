package com.example.app;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;
import java.util.Properties;

@Component
public class MainApplication implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(MainApplication.class);

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private AvroSchemaUtils avroSchemaUtils;

    @Autowired
    private BusinessRulesEngine businessRulesEngine;

    @Autowired
    private DataStorageApiClient dataStorageApiClient;

    @Override
    public void run(String... args) throws Exception {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "spark-kafka-drools-app");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, appConfig.getKafkaBootstrapServers());
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, SpecificAvroSerde.class);
        props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, appConfig.getSchemaRegistryUrl());

        StreamsBuilder builder = new StreamsBuilder();

        // Configure Avro Serde for RawMessage
        final Map<String, String> serdeConfig = Collections.singletonMap(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, appConfig.getSchemaRegistryUrl());
        final SpecificAvroSerde<RawMessage> rawMessageSerde = new SpecificAvroSerde<>();
        rawMessageSerde.configure(serdeConfig, false);

        // 1. Read messages in Avro format from raw topic
        KStream<String, RawMessage> rawStream = builder.stream(
                appConfig.getKafkaRawTopic(),
                Consumed.with(Serdes.String(), rawMessageSerde)
        );

        // 2. Process the messages (deserialization and business rules will be applied here)
        KStream<String, ProcessedMessage> processedStream = rawStream.mapValues(rawMessage -> {
            LOG.info("Received raw message: {}", rawMessage);
            // Apply business rules
            ProcessedMessage processedMessage = businessRulesEngine.process(rawMessage);
            LOG.info("Processed message through rules: {}", processedMessage);
            return processedMessage;
        });

        // Configure Avro Serde for ProcessedMessage
        final SpecificAvroSerde<ProcessedMessage> processedMessageSerde = new SpecificAvroSerde<>();
        processedMessageSerde.configure(serdeConfig, false);

        // 3. Release events at every stage to another Kafka topic (processed topic)
        processedStream.to(
                appConfig.getKafkaProcessedTopic(),
                Produced.with(Serdes.String(), processedMessageSerde)
        );

        // 4. Call an API to store the newly created data
        processedStream.foreach((key, processedMessage) -> {
            boolean stored = dataStorageApiClient.storeData(processedMessage);
            if (stored) {
                LOG.info("Successfully stored data via API for message: {}", processedMessage.getId());
                // Optionally publish API storage success event to a Kafka topic
                // This would require another KStream.to() or a separate producer
            } else {
                LOG.error("Failed to store data via API for message: {}", processedMessage.getId());
            }
        });

        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();

        // Add shutdown hook to close the streams application gracefully.
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

        LOG.info("Kafka Streams application started. Consuming from topic: {}", appConfig.getKafkaRawTopic());
    }
}


