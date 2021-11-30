package alpoo.http;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;

/**
 * Handler for the HTTP requests
 */
public class HTTPRequestHandler {
    /**
     * Makes a request for the newsletter endpoint
     * 
     * @param url   URL of the newsletter endpoint
     * @param name  Name of the user to be registered for the newsletter
     * @param email Email of the user to be registered for the newsletter
     * @throws Exception Exception thrown by the HTTP client
     */
    public static void makeNewsletterRequest(String url, String name, String email) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        String requestURL = String.format("%s/mail?name=%s&email=%s", url, name, email);

        HttpRequest request = HttpRequest
                .newBuilder(URI.create(requestURL))
                .build();

        client.send(request, BodyHandlers.ofString());
    }

    /**
     * Makes a request for the newsletter endpoint
     * 
     * @param name  Name of the user to be registered for the newsletter
     * @param email Email of the user to be registered for the newsletter
     * @throws Exception Exception thrown by the HTTP client
     */
    public static void makeNewsletterRequest(String name, String email) throws Exception {
        makeNewsletterRequest("http://localhost:9090", name, email);
    }
}
