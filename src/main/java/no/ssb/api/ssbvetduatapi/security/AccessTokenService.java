package no.ssb.api.ssbvetduatapi.security;

import com.microsoft.aad.adal4j.AuthenticationContext;
import com.microsoft.aad.adal4j.ClientCredential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class AccessTokenService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${vetduat.auth.resource}")
    private String authResource;

    @Value("${vetduat.auth.authority}")
    private String authAuthority;

    @Value("${vetduat.auth.application.id}")
    private String applicationId;

    @Value("${vetduat.auth.client.secret}")
    private String clientSecret;

    public String getAccessToken() {
        String tokenResult ="";
        final ExecutorService executorService = Executors.newSingleThreadExecutor();

        try {
            AuthenticationContext authContext =
                    new AuthenticationContext(authAuthority, true, executorService);
            ClientCredential clientCredential =
                    new ClientCredential(applicationId, clientSecret);
            tokenResult = authContext.acquireToken(authResource, clientCredential, null).get().getAccessToken();
        } catch (MalformedURLException | InterruptedException | ExecutionException e) {
            log.error("Feil ved henting av accessToken: {}", e.getMessage());
        }
        // ADAL includes an in memory cache, so this call will only send a message to the server if the cached token is expired.
//        log.info("tokenResult: {}", tokenResult);
        return tokenResult;
    }

}
