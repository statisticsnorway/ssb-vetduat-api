package no.ssb.api.ssbvetduatapi.repository;

import com.microsoft.aad.adal4j.AuthenticationContext;
import com.microsoft.aad.adal4j.ClientCredential;
import no.ssb.api.ssbvetduatapi.util.ResultStrings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class VetduatRestRepository {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${vetduat.url}")
    private String vetduatUrl;

    @Value("${vetduat.endpoint.usersearch}")
    private String vetduatUsersearchEndpoint;

    @Value("${vetduat.auth.resource}")
    private String authResource;

    @Value("${vetduat.auth.authority}")
    private String authAuthority;

    @Value("${vetduat.auth.application.id}")
    private String applicationId;

    @Value("${vetduat.auth.client.secret}")
    private String clientSecret;

    private int timeoutInSeconds = 10;

    public String callVetDuAt(String code) {
        String result;
        String body = "{ \"gtin\" : " + code + " }";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(vetduatUrl+vetduatUsersearchEndpoint))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + getAccessToken())
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
//        logRequest(request);
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            log.info("respons: {}, status {}", response.body(), response.statusCode());
            if (response.statusCode() == HttpStatus.OK.value()) {
                result = response.body();
            } else {
                result = ResultStrings.emptyResult(Long.parseLong(code), -1, -1, "Noe feilet - status " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            log.error("something wrong in calling vetduat: {}", e.getMessage());
            result = ResultStrings.emptyResult(Integer.parseInt(code), "Noe feilet - " + e.getMessage());
        }
        log.info("result: {}", result);

        return result;
    }


    private String getAccessToken() {
        String tokenResult ="";
        final ExecutorService executorService = Executors.newSingleThreadExecutor();

        try {
            AuthenticationContext authContext =
                    new AuthenticationContext(authAuthority, true, executorService);
            ClientCredential clientCredential =
                    new ClientCredential(applicationId, clientSecret);
            tokenResult = authContext.acquireToken(authResource, clientCredential, null).get().getAccessToken();
        } catch (MalformedURLException | InterruptedException | ExecutionException e) {
            log.error(e.getMessage());
        }
        // ADAL includes an in memory cache, so this call will only send a message to the server if the cached token is expired.
//        log.info("tokenResult: {}", tokenResult);
        return tokenResult;

    }

//    private void logRequest(HttpRequest request) {
//        log.info("request: {}", request.toString());
//        log.info("request.body: {}", request.bodyPublisher().get().contentLength());
//        log.info("request.uri :{}", request.uri());
//        Set<String> requestHeaderKeys = request.headers().map().keySet();
//        requestHeaderKeys.forEach(k -> log.info(k + ": " + request.headers().allValues(k)));
//    }

}
