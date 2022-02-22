package vn.cmc.du21.orderservice.common;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import vn.cmc.du21.orderservice.common.restful.StandardResponse;
import vn.cmc.du21.orderservice.presentation.internal.response.ProductResponse;

import javax.servlet.http.HttpServletRequest;

public class APIGetDetailProduct {
    public static StandardResponse<ProductResponse> getDetailProduct (HttpServletRequest request, long productId){
        final String uri = "http://192.168.66.125:8200/api/v1.0/product/get-detail-product/" + String.valueOf(productId);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<StandardResponse<ProductResponse>> requestt = new HttpEntity<>(new StandardResponse<ProductResponse>());
        ResponseEntity<StandardResponse<ProductResponse>> responsee = restTemplate
                .exchange(uri, HttpMethod.GET, requestt, new ParameterizedTypeReference<StandardResponse<ProductResponse>>() {
                });

        StandardResponse<ProductResponse> productResponse = responsee.getBody();
        return productResponse;
    }
}
