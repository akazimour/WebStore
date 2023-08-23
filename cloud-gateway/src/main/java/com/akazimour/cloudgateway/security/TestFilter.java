package com.akazimour.cloudgateway.security;
import com.auth0.jwt.algorithms.Algorithm;
import hu.webuni.tokenlib.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.DefaultJwtSignatureValidator;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import io.netty.handler.codec.base64.Base64Decoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

@Component
public class TestFilter implements GatewayFilter {
    @Autowired
    JwtService jwtService;

    @Value("${hu.webuni.tokenlib.keypaths.public:#{null}}")
    private String pathToPemWithPublicKey;

    private Algorithm alg = Algorithm.HMAC256("AUTH");

    private String secretKey = "AUTH";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        final List<String> apiEndpoints = List.of("/api/user/login");
        ServerHttpRequest request = (ServerHttpRequest) exchange.getRequest();

        Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpoints.stream()
                .noneMatch(uri -> r.getURI().getPath().contains(uri));

        if (isApiSecured.test(request)) {
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            } else if (exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                String token = request.getHeaders().getOrEmpty("Authorization").get(0);
                String[] parts = token.split(" ");
                if(parts.length!=2 || !"Bearer".equals(parts[0])){
                    throw new RuntimeException("Incorrect Auth structure!");
                }
                token = token.substring(7);
                UserDetails userDetails = jwtService.parseJwt(token);
                String username = userDetails.getUsername();
                System.out.println(username);
                String password = userDetails.getPassword();
                System.out.println(password);
                Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
                for (GrantedAuthority ga : authorities){
                    GrantedAuthority ga1 = ga;
                    System.out.println(ga1);

                }

            }



//            String string = decode.toString();
//            System.out.println(string);


//            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), sa.getJcaName());
//            String tokenWithoutSignature = chunks[0] + "." + chunks[1];
//            String signature = chunks[2];
//            DefaultJwtSignatureValidator validator = new DefaultJwtSignatureValidator(sa, secretKeySpec);
//
//            if (!validator.isValid(tokenWithoutSignature, signature)) {
//                try {
//                    throw new Exception("Could not verify JWT token integrity!");
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
            }




        return chain.filter(exchange);
    }
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(alg.getSigningKeyId());
        return Keys.hmacShaKeyFor(keyBytes);
    }



}