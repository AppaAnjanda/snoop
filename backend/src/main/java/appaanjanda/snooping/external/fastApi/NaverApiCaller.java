package appaanjanda.snooping.external.fastApi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
@Slf4j
public class NaverApiCaller {

    private final RestTemplate restTemplate;
    private static final String NAVER_URL = "http://j9d104a.p.ssafy.io:8000/naver";

    public void oneProductSearch(String productCode) {

        //  요청 url
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(NAVER_URL)
                .queryParam("query", productCode);

        String builderUrl = builder.toUriString();

        log.info(builderUrl);
        // 호출
        try {
            restTemplate.execute(builderUrl, HttpMethod.GET, null, null);
        } catch (Exception e) {
            throw e;
        }
    }
}
