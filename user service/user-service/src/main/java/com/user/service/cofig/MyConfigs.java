package com.user.service.cofig;

import com.user.service.cofig.interceptor.RestTemplateInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MyConfigs {

//    Without interceptor
//    @Bean
//    @LoadBalanced // While using MicroService name in calling URL registered on Eureka server we must use this annotation
//    public RestTemplate restTemplate(){
//        return new RestTemplate();
//    }

    @Autowired
    ClientRegistrationRepository clientRegistrationRepository;
    @Autowired
    OAuth2AuthorizedClientRepository auth2AuthorizedClientRepository;

//    With Interceptor

    @Bean
    @LoadBalanced // While using MicroService name in calling URL registered on Eureka server we must use this annotation
    public RestTemplate restTemplate(){

        RestTemplate restTemplate=new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptors=new ArrayList<>();
        interceptors.add(new RestTemplateInterceptor(manager(clientRegistrationRepository,auth2AuthorizedClientRepository)));
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }


//    Declare the bean of OAuth2AuthorizedClientManager for Feing client Interceptor

    @Bean
    public OAuth2AuthorizedClientManager manager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository auth2AuthorizedClientRepository
    ){
        OAuth2AuthorizedClientProvider provider= OAuth2AuthorizedClientProviderBuilder.builder().clientCredentials().build();
        DefaultOAuth2AuthorizedClientManager defaultOAuth2AuthorizedClientManager=new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository,auth2AuthorizedClientRepository);
        defaultOAuth2AuthorizedClientManager.setAuthorizedClientProvider(provider);
        return defaultOAuth2AuthorizedClientManager;
    }
}
