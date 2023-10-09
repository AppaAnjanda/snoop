package appaanjanda.snooping.external.fastApi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class NaverApiCaller {

    private final RestTemplate restTemplate;
    private static final String NAVER_URL = "http://j9d104a.p.ssafy.io:8000/naver";

    public void oneProductSearch(String productCode) {

        //  요청 url
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject body = new JSONObject();
        body.put("keyword", productCode);

        HttpEntity<String> entity = new HttpEntity<>(body.toString(), headers);

        // 호출
        try {
            ResponseEntity<Object> response = restTemplate.exchange(NAVER_URL, HttpMethod.POST, entity, Object.class);
            log.info(response.getStatusCode().toString());
        } catch (Exception e) {
            throw e;
        }
    }
}
