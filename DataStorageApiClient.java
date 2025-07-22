package com.example.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DataStorageApiClient {

    private static final Logger LOG = LoggerFactory.getLogger(DataStorageApiClient.class);
    private final String apiUrl;
    private final ObjectMapper objectMapper;

    public DataStorageApiClient(String apiUrl) {
        this.apiUrl = apiUrl;
        this.objectMapper = new ObjectMapper();
    }

    public boolean storeData(ProcessedMessage message) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(apiUrl);
            String json = objectMapper.writeValueAsString(message);
            StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            LOG.info("Sending data to API: {}", json);
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                int statusCode = response.getStatusLine().getStatusCode();
                String responseBody = EntityUtils.toString(response.getEntity());
                if (statusCode >= 200 && statusCode < 300) {
                    LOG.info("Successfully stored data. Response: {}", responseBody);
                    return true;
                } else {
                    LOG.error("Failed to store data. Status: {}, Response: {}", statusCode, responseBody);
                    return false;
                }
            }
        } catch (IOException e) {
            LOG.error("Error calling data storage API: {}", e.getMessage(), e);
            return false;
        }
    }
}


