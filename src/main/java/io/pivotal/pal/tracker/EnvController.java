package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EnvController {
    Map<String, String> envMap;

    public EnvController(@Value("${PORT}") String port, @Value("${MEMORY_LIMIT}") String memoryLimit, @Value("${CF_INSTANCE_INDEX}") String instanceIndex, @Value("${CF_INSTANCE_ADDR}") String instanceAddr) {
        envMap = new HashMap<>();

        envMap.put("PORT", port);
        envMap.put("MEMORY_LIMIT", memoryLimit);
        envMap.put("CF_INSTANCE_INDEX", instanceIndex);
        envMap.put("CF_INSTANCE_ADDR", instanceAddr);
    }

    @GetMapping("/env")
    public Map<String, String> getEnv() {
        return envMap;
    }
}
