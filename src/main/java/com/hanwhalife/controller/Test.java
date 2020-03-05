package com.hanwhalife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
public class Oauth2AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
 
    @Autowired
    private DataSource dataSource;
 
    // 현재 client secret 부분을 암호화 하고있지 않기 때문에 임시적으로 passworkEncoder를 사용하지 않는다.
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }
 
    // oauth client detail 테이블을 사용하여 유저를 조회 하므로 다음과 같이 client 는 db를 바라보게 세팅한다.
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource).passwordEncoder(passwordEncoder());
    }
 
    // config endpoint 설정
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
 
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
 
        tokenEnhancerChain.setTokenEnhancers(
                Arrays.asList(tokenEnhancer(), accessTokenConverter()));
 
        endpoints
                .tokenStore(tokenStore())
                .tokenEnhancer(tokenEnhancerChain);
    }
 
    // custom으로 외부에서 client와 secret을 등록하기 위해서 필요한 서비스를 위해 열어준다.
    @Bean
    @Primary
    public JdbcClientDetailsService jdbcClientDetailsService(DataSource dataSource) {
        return new JdbcClientDetailsService(dataSource);
    }
 
 
    // tokenstore 에서 jwtTokenStore 를 사용하고 암호화를 진행단.
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }
 
    // jwt 토큰을 키로 검증한다. 키는 여기서 지정해도 되지만 보통 file로 보관한다. 이 키는 resource server와 auth server가 동일해야 한다.
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("waug_secret");
        return converter;
    }
 
    // 토큰에 추가적인 정보를 넣어서 저장한다.
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new WaugTokenEnhancer();
    }
 
     
    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }
 
}