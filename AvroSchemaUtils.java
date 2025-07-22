package com.example.app;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.avro.specific.SpecificRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
public class AvroSchemaUtils {

    private static final Logger LOG = LoggerFactory.getLogger(AvroSchemaUtils.class);

    private final AppConfig appConfig;

    public AvroSchemaUtils(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public SpecificAvroSerde<RawMessage> getRawMessageSerde() {
        final Map<String, String> serdeConfig = Collections.singletonMap(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, appConfig.getSchemaRegistryUrl());
        final SpecificAvroSerde<RawMessage> rawMessageSerde = new SpecificAvroSerde<>();
        rawMessageSerde.configure(serdeConfig, false);
        return rawMessageSerde;
    }

    public SpecificAvroSerde<ProcessedMessage> getProcessedMessageSerde() {
        final Map<String, String> serdeConfig = Collections.singletonMap(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, appConfig.getSchemaRegistryUrl());
        final SpecificAvroSerde<ProcessedMessage> processedMessageSerde = new SpecificAvroSerde<>();
        processedMessageSerde.configure(serdeConfig, false);
        return processedMessageSerde;
    }
}


