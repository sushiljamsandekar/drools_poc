package com.example.app;

import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class BusinessRulesEngine {

    private static final Logger LOG = LoggerFactory.getLogger(BusinessRulesEngine.class);
    private KieContainer kieContainer;

    public BusinessRulesEngine() {
        try {
            loadRules();
        } catch (Exception e) {
            LOG.error("Error initializing BusinessRulesEngine: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to initialize Drools KieContainer", e);
        }
    }

    private void loadRules() throws IOException {
        KieServices ks = KieServices.Factory.get();
        KieFileSystem kfs = ks.newKieFileSystem();

        // Load DRL files from resources/rules directory
        // In a real application, you might load from a configurable path or a KIE Server
        Collection<File> drlFiles = Files.walk(new File(getClass().getClassLoader().getResource("rules").getFile()).toPath())
                .filter(Files::isRegularFile)
                .map(java.nio.file.Path::toFile)
                .collect(Collectors.toList());

        if (drlFiles.isEmpty()) {
            LOG.warn("No DRL files found in src/main/resources/rules. Business rules will not be applied.");
        } else {
            for (File drlFile : drlFiles) {
                LOG.info("Loading DRL file: {}", drlFile.getName());
                kfs.write(ResourceFactory.newFileResource(drlFile));
            }
        }

        KieBuilder kb = ks.newKieBuilder(kfs).buildAll();

        if (kb.getResults().hasMessages(Message.Level.ERROR)) {
            throw new RuntimeException("Build Errors:\n" + kb.getResults().toString());
        }

        kieContainer = ks.newKieContainer(ks.getRepository().getDefaultReleaseId());
        LOG.info("Drools KieContainer initialized successfully.");
    }

    public ProcessedMessage process(RawMessage rawMessage) {
        KieSession kieSession = kieContainer.newKieSession();
        ProcessedMessage processedMessage = new ProcessedMessage(
                rawMessage.getId(), rawMessage.getTimestamp(), rawMessage.getPayload(), "initial", 0.0, false);

        kieSession.insert(processedMessage);
        kieSession.fireAllRules();
        kieSession.dispose(); // Dispose session to free up resources

        LOG.info("Processed message through rules: {}", processedMessage);
        return processedMessage;
    }
}


