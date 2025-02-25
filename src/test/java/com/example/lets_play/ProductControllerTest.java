package com.example.lets_play;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

public class ProductControllerTest {

    public static void main(String[] args) {
        SSLUtil.disableSSLVerification(); 
        testGetAllProducts();
        testGetProductById();
    }

    public static void testGetAllProducts() {
        try {
            URL url = new URL("https://localhost:8443/api/products");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String response = br.lines().collect(Collectors.joining());
                    System.out.println("Réponse de /api/products : " + response);
                }
            } else {
                System.err.println("Échec du test de récupération des produits. Code de réponse : " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void testGetProductById() {
        try {
            URL url = new URL("https://localhost:8443/api/products/67b8a7ff00e5a414dfaeb9c5");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
    
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String response = br.lines().collect(Collectors.joining());
                    System.out.println("Réponse de /api/products/1 : " + response);
                }
            } else {
                System.err.println("Échec du test de récupération du produit. Code de réponse : " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   
}