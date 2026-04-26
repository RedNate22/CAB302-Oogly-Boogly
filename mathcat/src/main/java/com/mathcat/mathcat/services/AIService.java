package com.mathcat.mathcat.services;

import io.github.cdimascio.dotenv.Dotenv;
import java.net.URI;
import java.net.http.*;
import java.net.http.HttpRequest.BodyPublishers;
import com.google.gson.*;
import java.util.List;

/**
 * Service class responsible for communicating with the Groq AI API.
 * Uses the "I do, We do, You do" teaching method to guide students
 * through math problems without giving away the answer directly.
 * Maintains conversation history to provide context-aware hints.
 */
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

    /**
     * Sends a hint request to the Groq AI API using the I do, We do, You do teaching method.
     * Builds a full conversation history to maintain context across multiple hints.
     * @param questionContext the math problem the student is working on
     * @param userMessage the student's latest message or question
     * @param history the full conversation history as a list of role/content pairs
     * @return a Socratic hint from the AI to guide the student without giving the answer
     * @throws Exception if the HTTP request fails or the response cannot be parsed
     */
    public String getHint(String questionContext, String userMessage, List<String[]> history) throws Exception {
        String url = "https://api.groq.com/openai/v1/chat/completions";

        String systemPrompt = "You are a concise math tutor using the 'I do, We do, You do' teaching method. "
                + "The student is working on this problem: " + questionContext + ". "
                + "Follow these steps in order across the conversation: "
                + "STEP 1 - I DO: First, solve a SIMILAR but DIFFERENT example problem out loud, narrating each step simply. Do NOT use the actual question. "
                + "STEP 2 - WE DO: Then solve another similar example TOGETHER by asking the student to complete each step with your guidance. "
                + "STEP 3 - YOU DO: Finally, ask the student to try the ACTUAL question on their own using what they have learned. "
                + "RULES: "
                + "1. NEVER give the answer to the actual question directly. "
                + "2. Keep each response concise, max 3 sentences. "
                + "3. Track which step you are on and progress naturally through the steps. "
                + "4. Be encouraging but not overly praising.";

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
