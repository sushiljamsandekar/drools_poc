
package com.example.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {

    @Value("${kafka.bootstrap.servers}")
    private String kafkaBootstrapServers;

    @Value("${kafka.raw.topic}")
    private String kafkaRawTopic;

    @Value("${kafka.processed.topic}")
    private String kafkaProcessedTopic;

    @Value("${schema.registry.url}")
    private String schemaRegistryUrl;

    @Value("${api.storage.url}")
    private String apiStorageUrl;

    @Value("${kafka.deserialized.topic}")
    private String kafkaDeserializedTopic;

    @Value("${kafka.business.rule.processed.topic}")
    private String kafkaBusinessRuleProcessedTopic;

    @Value("${kafka.api.storage.success.topic}")
    private String kafkaApiStorageSuccessTopic;

    @Value("${kafka.error.topic}")
    private String kafkaErrorTopic;

    public String getKafkaBootstrapServers() {
        return kafkaBootstrapServers;
    }

    public String getKafkaRawTopic() {
        return kafkaRawTopic;
    }

    public String getKafkaProcessedTopic() {
        return kafkaProcessedTopic;
    }

    public String getSchemaRegistryUrl() {
        return schemaRegistryUrl;
    }

    public String getApiStorageUrl() {
        return apiStorageUrl;
    }

    public String getKafkaDeserializedTopic() {
        return kafkaDeserializedTopic;
    }

    public String getKafkaBusinessRuleProcessedTopic() {
        return kafkaBusinessRuleProcessedTopic;
    }

    public String getKafkaApiStorageSuccessTopic() {
        return kafkaApiStorageSuccessTopic;
    }

    public String getKafkaErrorTopic() {
        return kafkaErrorTopic;
    }
}


