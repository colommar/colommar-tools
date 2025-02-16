package com.github.ollama;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api")
public class OllamaController {

    @Value("${ollama.api.url}") // 从配置文件读取Ollama服务的URL
    private String ollamaApiUrl;

    private final RestTemplate restTemplate;

    public OllamaController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/ollama")
    public String generate(@RequestBody OllamaRequest request) {
        try {
            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // 封装请求体
            HttpEntity<OllamaRequest> entity = new HttpEntity<>(request, headers);

            // 发起请求到本地的 Ollama 服务
            ResponseEntity<String> response = restTemplate.exchange(
                    ollamaApiUrl,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            // 返回响应内容
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error during model execution: " + e.getMessage();
        }
    }
}
