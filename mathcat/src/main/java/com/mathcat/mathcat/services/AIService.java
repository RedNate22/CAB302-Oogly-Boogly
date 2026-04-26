package com.mathcat.mathcat.services;

import io.github.cdimascio.dotenv.Dotenv;
import java.net.URI;
import java.net.http.*;
import java.net.http.HttpRequest.BodyPublishers;
import com.google.gson.*;
import java.util.List;

public class AIService {

    private final String apiKey;
    private final HttpClient client = HttpClient.newHttpClient();
    public AIService() {
        String workingDir = System.getProperty("user.dir");
        String envDir = workingDir.endsWith("mathcat") ? workingDir : workingDir + "/mathcat";
        Dotenv dotenv = Dotenv.configure().directory(envDir).load();
        this.apiKey = dotenv.get("GROQ_API_KEY");
    }

    // Escapes special characters in a string to make it safe for JSON
    private String escapeJson(String input) {
        if (input == null) return "null";
        StringBuilder sb = new StringBuilder();
        for (char c : input.toCharArray()) {
            switch (c) {
                case '"' -> sb.append("\\\"");
                case '\\' -> sb.append("\\\\");
                case '\b' -> sb.append("\\b");
                case '\f' -> sb.append("\\f");
                case '\n' -> sb.append("\\n");
                case '\r' -> sb.append("\\r");
                case '\t' -> sb.append("\\t");
                default -> {
                    if (c < ' ') {
                        sb.append(String.format("\\u%04x", (int) c));
                    } else {
                        sb.append(c);
                    }
                }
            }
        }
        return sb.toString();
    }

    public String getHint(String questionContext, String userMessage, List<String[]> history) throws Exception {
        String url = "https://api.groq.com/openai/v1/chat/completions";

        String systemPrompt = "You are a concise math tutor using the Socratic method. "
                + "The student is working on this problem: " + questionContext + ". "
                + "RULES: "
                + "1. NEVER give the answer directly. "
                + "2. NEVER start with praise like 'Great', 'That's a great start', 'Good job' etc. "
                + "3. NEVER repeat a question you already asked. "
                + "4. Give ONE short, specific hint or question per response. "
                + "5. Build directly on exactly what the student just said. "
                + "6. If the student is correct, confirm it and ask the student to press next question. "
                + "7. Max 2 sentences per response.";

        // Build conversation history messages
        StringBuilder messagesArray = new StringBuilder();
        messagesArray.append("{\"role\": \"system\", \"content\": \"")
                .append(escapeJson(systemPrompt))
                .append("\"}");

        // Add previous messages from history
        for (String[] message : history) {
            messagesArray.append(", {\"role\": \"")
                    .append(message[0])
                    .append("\", \"content\": \"")
                    .append(escapeJson(message[1]))
                    .append("\"}");
        }

        // Add current user message
        messagesArray.append(", {\"role\": \"user\", \"content\": \"")
                .append(escapeJson(userMessage))
                .append("\"}");

        String body = """
            {
              "model": "llama-3.3-70b-versatile",
              "messages": [%s]
            }
            """.formatted(messagesArray.toString());

        System.out.println("History size: " + history.size());
        System.out.println("Body: " + body);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        // Check HTTP status code first before parsing JSON
        if (response.statusCode() != 200) {
            return "Error: Request failed with status code " + response.statusCode()
                    + ". Please check your API key.";
        }

        // Parse JSON response
        JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
        if (json.has("error")) {
            return "Error: " + json.getAsJsonObject("error").get("message").getAsString();
        }


        return json.getAsJsonArray("choices")
                .get(0).getAsJsonObject()
                .getAsJsonObject("message")
                .get("content").getAsString();
    }
}
