package com.example.lets_play;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class UserControllerTest {

    public static void main(String[] args) {
        SSLUtil.disableSSLVerification(); 
        testCreateUser();
    }

    public static void testCreateUser() {
        try {
            URL url = new URL("https://localhost:8443/api/users");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String jsonInputString = "{\"name\": \"testuser2\", \"email\": \"test2@example.com\", \"password\": \"password\", \"role\": \"user\"}";

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                System.out.println("Test de création d'utilisateur réussi !");
            } else {
                System.err.println("Échec du test de création d'utilisateur. Code de réponse : " + connection.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
