package appaanjanda.snooping.external.fastApi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
@Slf4j
public class CoupangCrawlingCaller {

    private final RestTemplate restTemplate;
    private static final String COUPANG_URL = "http://15.165.116.126:8000/naver/";

    public void oneProductSearch(String productCode) {

        //  요청 url
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(COUPANG_URL)
                .queryParam("query", productCode);
        String realUrl = builder.toUriString();



        // 호출
        try {
            restTemplate.execute(realUrl, HttpMethod.GET, null, null);
        } catch (Exception e) {
            throw e;
        }
    }
}
