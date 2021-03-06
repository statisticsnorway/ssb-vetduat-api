package no.ssb.api.ssbvetduatapi.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import no.ssb.api.ssbvetduatapi.service.SsbVetduatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;


@Api("ssb-vetduat-api")
@RestController
@RequestMapping("")
public class SsbVetduatController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final String HEADER_APIKEY = "api_key";

    @Value("${api-keys}")
    private List<String> acceptedApiKeys;

    @Value("${vetduat.default.codeType}")
    private String defaultCodeType;

    @Autowired
    SsbVetduatService ssbVetduatService;

    final ObjectMapper objectMapper = new ObjectMapper();

    @ApiOperation(value = "Hent produktinformasjon fra vetduat.no")
    @GetMapping("/produktinfo/{codes}")
    @ResponseBody
    public JsonNode getProductInformation(@RequestHeader HttpHeaders header,
                                          @PathVariable String codes, HttpServletResponse resp) {
        return getProductInformationForCodes(header, defaultCodeType, codes, resp);
    }

    @ApiOperation(value = "Hent produktinformasjon fra vetduat.no")
    @GetMapping("/produktinfo/{codeType}/{codes}")
    @ResponseBody
    public JsonNode getProductInformation(@RequestHeader HttpHeaders header, @PathVariable String codeType,
                                          @PathVariable String codes, HttpServletResponse resp) {
        return getProductInformationForCodes(header, codeType, codes, resp);
    }

    private JsonNode getProductInformationForCodes(@RequestHeader HttpHeaders header, @PathVariable String codeType, @PathVariable String codes, HttpServletResponse resp) {
        AtomicReference<JsonNode> produktInfo = new AtomicReference<>();

        Instant start = Instant.now();

        if (!authorizeRequest(header)) {
            resp.setStatus(HttpStatus.UNAUTHORIZED.value());
            return objectMapper.createObjectNode().put("resultat", "Autentisering feilet");
        }

        ssbVetduatService.productInformationForCodes(codeType, codes)
                .orTimeout(10, TimeUnit.SECONDS)
                .thenAccept(result -> {
                    if (result == null || result.isMissingNode()) {
                        resp.setStatus(HttpStatus.BAD_REQUEST.value());
                        produktInfo.set(objectMapper.createObjectNode().put("resultat", "Noe feilet"));
                    } else {
                        produktInfo.set(result);
                    }
                });
//        log.info("returner {}", produktInfo.get());
        log.info("ferdig behandlet {} , brukt tid: {}", codes, Duration.between(start, Instant.now()).toMillis());
        return produktInfo.get();
    }

    private boolean authorizeRequest(HttpHeaders header) {
        return header.get(HEADER_APIKEY) != null && acceptedApiKeys.contains(Objects.requireNonNull(header.get(HEADER_APIKEY)).get(0));
    }
}
