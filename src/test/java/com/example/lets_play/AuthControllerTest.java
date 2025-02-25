package com.example.lets_play;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class AuthControllerTest {

    public static void main(String[] args) {
        SSLUtil.disableSSLVerification(); 
        testRegisterEndpoint();
        testLoginEndpoint();
    }

    public static void testLoginEndpoint() {
        try {
            URL url = new URL("https://localhost:8443/api/auth/login");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String jsonInputString = "{\"email\": \"admin@mail.com\", \"password\": \"password\"}";

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                System.out.println("Test de login réussi !");
            } else {
                System.err.println("Échec du test de login. Code de réponse : " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void testRegisterEndpoint() {
        try {
            URL url = new URL("https://localhost:8443/api/auth/register");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
    
            String jsonInputString = "{\"name\": \"Test\", \"email\": \"test3@example.com\", \"password\": \"password\", \"role\": \"user\"}";
    
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
    
            int responseCode = connection.getResponseCode();
            if (responseCode == 201) {
                System.out.println("Test d'enregistrement réussi !");
            } else {
                System.err.println("Échec du test d'enregistrement. Code de réponse : " + connection.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}