package com.example.pinkbullmakeup.Service;

import com.example.pinkbullmakeup.GeminiModel.Content;
import com.example.pinkbullmakeup.GeminiModel.GeminiRequest;
import com.example.pinkbullmakeup.GeminiModel.GeminiResponse;
import com.example.pinkbullmakeup.GeminiModel.Part;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Service
public class ChatbotService {

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @Value("${gemini.api.base-url}")
    private String geminiApiUrl;

    @Value("${gemini.api.endpoint}")
    private String geminiApiEndpoint;

    private final RestTemplate restTemplate;

    public ChatbotService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String callGeminiApi(String userMessage) {
        GeminiRequest request = new GeminiRequest(
                List.of(new Content("user", List.of(new Part(userMessage))))
        );

        // Make synchronous POST request
        ResponseEntity<GeminiResponse> responseEntity = restTemplate.postForEntity(
                geminiApiUrl + geminiApiEndpoint + "?key=" + geminiApiKey,
                request,
                GeminiResponse.class
        );

        GeminiResponse response = responseEntity.getBody();

        if (response != null &&
                response.getCandidates() != null &&
                !response.getCandidates().isEmpty() &&
                response.getCandidates().get(0).getContent() != null &&
                response.getCandidates().get(0).getContent().getParts() != null &&
                !response.getCandidates().get(0).getContent().getParts().isEmpty()) {

            return response.getCandidates().get(0).getContent().getParts().get(0).getText();
        }

        return "No response from Gemini.";
    }
}
