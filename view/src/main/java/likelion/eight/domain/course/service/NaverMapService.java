package likelion.eight.domain.course.service;

import likelion.eight.domain.course.model.map.NaverMapRes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;

@Service
public class NaverMapService {
    private final RestTemplate restTemplate;

    public NaverMapService() {
        this.restTemplate = new RestTemplate();
    }

    @Value("${naver.client.id}")
    private String NAVER_CLIENT_ID;

    @Value("${naver.client.secret}")
    private String NAVER_CLIENT_SECRET_ID;

    public NaverMapRes getGeocode(String address) throws UnsupportedEncodingException {
        HttpHeaders headers = new HttpHeaders();

        headers.add("X-NCP-APIGW-API-KEY-ID", NAVER_CLIENT_ID);
        headers.add("X-NCP-APIGW-API-KEY", NAVER_CLIENT_SECRET_ID);

        //네이버가 지정한 엔드포인트
        String urlTemplate = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=" + address;


        //네이버에 요청 보내고 response에 응답 받기
        ResponseEntity<NaverMapRes> response = restTemplate.exchange(
                urlTemplate,
                HttpMethod.GET,
                new HttpEntity<>(headers), //요청 헤더에 시크릿키 넣기
                NaverMapRes.class // 응답을 받을 타입 (dto)
        );

        return response.getBody();
    }
}
