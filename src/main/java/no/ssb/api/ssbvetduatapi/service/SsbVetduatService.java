package no.ssb.api.ssbvetduatapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.microsoft.aad.adal4j.AuthenticationContext;
import com.microsoft.aad.adal4j.ClientCredential;
import no.ssb.api.ssbvetduatapi.repository.VetduatRestRepository;
import no.ssb.api.ssbvetduatapi.util.ResultStrings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class SsbVetduatService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    VetduatRestRepository vetduatRestRepository;

    @Value("${vetduat.auth.resource}")
    private String authResource;

    @Value("${vetduat.auth.authority}")
    private String authAuthority;

    @Value("${vetduat.auth.application.id}")
    private String applicationId;

    @Value("${vetduat.auth.client.secret}")
    private String clientSecret;


    final String emptyRestResult = "[]";

    public CompletableFuture<JsonNode> productInformationForCodes(String codeType, String codes) {
        CompletableFuture<JsonNode> future = new CompletableFuture<>();
        ArrayNode result = objectMapper.createArrayNode();

        String accessToken = getAccessToken();
        if (accessToken == null || accessToken.length() == 0 ) {
            try {
                result.add(objectMapper.readTree(ResultStrings.emptyResult(codeType, codes, "Noe feilet ved henting av accessToken")));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {

            Arrays.stream(codes.split(",")).parallel().forEach(code -> {
                try {
                    String codeInfo = vetduatRestRepository.callVetDuAt(codeType, code, accessToken);
                    log.info("code: {}, codeInfor: {}", code, codeInfo);
                    JsonNode node = objectMapper.readTree(
                            codeInfo.equals(emptyRestResult) ?
                                    ResultStrings.emptyResult(codeType, code, "Finner ikke hos vetduat") :
                                    codeInfo);
                    log.info("node: {}", node);
                    if (node.isArray()) {
                        node.forEach(result::add);
                    } else {
                        result.add(node);
                    }
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
        }
        log.info("return result: {}", result);
        future.complete(result);
        return future;
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
            log.error("Feil ved henting av accessToken: {}", e.getMessage());
        }
        // ADAL includes an in memory cache, so this call will only send a message to the server if the cached token is expired.
//        log.info("tokenResult: {}", tokenResult);
        return tokenResult;

    }
}
