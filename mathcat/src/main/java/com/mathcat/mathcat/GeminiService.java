package com.mathcat.mathcat;


import io.github.cdimascio.dotenv.Dotenv;
import java.net.URI;
import java.net.http.*;
import java.net.http.HttpRequest.BodyPublishers;
import com.google.gson.*;
import com.google.gson.JsonArray;

public class GeminiService {

    private final String apiKey;
    private final HttpClient client = HttpClient.newHttpClient();

    public GeminiService() {
        Dotenv dotenv = Dotenv.configure().directory("mathcat").load();
        this.apiKey = dotenv.get("GEMINI_API_KEY");
    }

    public String getHint(String questionContext, String userMessage) throws Exception {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/"
                + "gemini-2.5-flash-lite:generateContent?key=" + apiKey;

        String systemPrompt = "You are a math tutor using the Socratic method. " +
                "The student is working on this problem: " + questionContext + ". " +
                "NEVER give the answer directly. Instead, ask guiding questions " +
                "and give step-by-step hints that help the student think through " +
                "the problem themselves. Keep responses concise and encouraging.";

        String body = String.format("""
                {
                  "contents": [
                    {"role": "user", "parts": [{"text": "%s\\n\\nStudent says: %s"}]}
                  ]
                }
                """, systemPrompt.replace("\"", "'"), userMessage.replace("\"", "'"));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        // Parse response using Gson

        JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();

// Check for errors first
        if (json.has("error")) {
            return "Error: " + json.getAsJsonObject("error").get("message").getAsString();
        }

        JsonObject candidate = json.getAsJsonArray("candidates")
                .get(0).getAsJsonObject();

        JsonObject content = candidate.getAsJsonObject("content");
        JsonArray parts = content.getAsJsonArray("parts");

// Find the text part (skip thoughtSignature parts)
        for (int i = 0; i < parts.size(); i++) {
            JsonObject part = parts.get(i).getAsJsonObject();
            if (part.has("text")) {
                return part.get("text").getAsString();
            }
        }

        return "No response received.";
    }
}