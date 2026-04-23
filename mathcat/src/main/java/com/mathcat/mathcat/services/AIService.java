package com.mathcat.mathcat.services;

import io.github.cdimascio.dotenv.Dotenv;
import java.net.URI;
import java.net.http.*;
import java.net.http.HttpRequest.BodyPublishers;
import com.google.gson.*;

public class AIService {

        private final String apiKey;
        private final HttpClient client = HttpClient.newHttpClient();

        public AIService() {
                Dotenv dotenv = Dotenv.configure().directory("mathcat").load();
                this.apiKey = dotenv.get("GROQ_API_KEY");
        }

        public String getHint(String questionContext, String userMessage) throws Exception {
                String url = "https://api.groq.com/openai/v1/chat/completions";

                String systemPrompt = "You are a concise math tutor using the Socratic method. "
                                + "The student is working on this problem: " + questionContext
                                + ". " + "RULES: " + "1. NEVER give the answer directly. "
                                + "2. NEVER start with praise like 'Great', 'That's a great start', 'Good job' etc. "
                                + "3. NEVER repeat a question you already asked. "
                                + "4. Give ONE short, specific hint or question per response. "
                                + "5. Build directly on exactly what the student just said. "
                                + "6. If the student is correct, confirm it and ask the student to press next question. "
                                + "7. Max 2 sentences per response.";

                String body = String.format("""
                                {
                                  "model": "llama-3.3-70b-versatile",
                                  "messages": [
                                    {"role": "system", "content": "%s"},
                                    {"role": "user", "content": "%s"}
                                  ]
                                }
                                """, systemPrompt.replace("\"", "'"),
                                userMessage.replace("\"", "'"));

                HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                                .header("Content-Type", "application/json")
                                .header("Authorization", "Bearer " + apiKey)
                                .POST(BodyPublishers.ofString(body)).build();

                HttpResponse<String> response =
                                client.send(request, HttpResponse.BodyHandlers.ofString());

                // Check for errors
                JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
                if (json.has("error")) {
                        return "Error: " + json.getAsJsonObject("error").get("message")
                                        .getAsString();
                }

                return json.getAsJsonArray("choices").get(0).getAsJsonObject()
                                .getAsJsonObject("message").get("content").getAsString();
        }
}
