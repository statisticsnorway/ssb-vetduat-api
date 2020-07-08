package no.ssb.api.ssbvetduatapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import no.ssb.api.ssbvetduatapi.repository.VetduatRestRepository;
import no.ssb.api.ssbvetduatapi.security.AccessTokenService;
import no.ssb.api.ssbvetduatapi.util.ResultStrings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@Service
public class SsbVetduatService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    VetduatRestRepository vetduatRestRepository;

    @Autowired
    AccessTokenService accessTokenService;

    final String emptyRestResult = "[]";

    public CompletableFuture<JsonNode> productInformationForCodes(String codeType, String codes) {
        CompletableFuture<JsonNode> future = new CompletableFuture<>();
        ArrayNode result = objectMapper.createArrayNode();

        log.info("accessTokenService er {} null", accessTokenService == null ? "" : " ikke ");
        String accessToken = accessTokenService.getAccessToken();
        if (accessToken == null || accessToken.length() == 0 ) {
            try {
                result.add(objectMapper.readTree(ResultStrings.errorResult(codeType, codes, "Noe feilet ved henting av accessToken")));
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

}
