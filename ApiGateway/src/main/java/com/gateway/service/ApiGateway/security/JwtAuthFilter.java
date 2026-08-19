package com.gateway.service.ApiGateway.security;

import io.jsonwebtoken.Jwts;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.PublicKey;
import java.util.List;

@Component
public class JwtAuthFilter implements GlobalFilter, Ordered {

    private final JwtKeyUtil keyUtil;
    private PublicKey publicKey;

    // public routes — no token required (can't require a token to GET a token)
    private static final List<String> OPEN = List.of(
            "/api/user/login",
            "/api/user/register"
    );

    public JwtAuthFilter(JwtKeyUtil keyUtil) {
        this.keyUtil = keyUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        // 1. public route? let it straight through
        if (OPEN.stream().anyMatch(path::startsWith)) {
            return chain.filter(exchange);
        }

        // 2. read the Authorization header
        String authHeader = exchange.getRequest()
                .getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        // 3. missing or malformed -> 401
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return reject(exchange);
        }

        String token = authHeader.substring(7);   // strip "Bearer "

        try {
            // 4. verify signature + expiry with the PUBLIC key
            if (publicKey == null) publicKey = keyUtil.loadPublicKey();

            var claims = Jwts.parser()
                    .verifyWith(publicKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            String role = String.valueOf(claims.get("role"));

// admin-only routes: only ROLE_ADMIN may pass
            if (isAdminRoute(exchange.getRequest().getMethod(), path) && !"ROLE_ADMIN".equals(role)) {
                return forbidden(exchange);   // 403 — authenticated, but not allowed
            }
            // 5. valid -> forward the identity downstream as headers, then continue
            ServerHttpRequest mutated = exchange.getRequest().mutate()
                    .header("X-User-Id", claims.getSubject())
                    .header("X-User-Roles", role)
                    .build();

            return chain.filter(exchange.mutate().request(mutated).build());

        } catch (Exception e) {
            e.printStackTrace();   // your "see the error" lesson — log before hiding
            return reject(exchange);   // bad signature / expired / tampered -> 401
        }
    }

    private Mono<Void> reject(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();   // reactive way to "stop here"
    }

    @Override
    public int getOrder() {
        return -1;   // run early, before routing
    }
    private boolean isAdminRoute(HttpMethod method, String path) {
        // POST /api/hotel/addHotel is admin-only
        if (HttpMethod.POST.equals(method) && path.startsWith("/api/hotel/addHotel")) {
            return true;
        }
        // (add more admin routes here later, e.g. deletes)
        return false;
    }

    private Mono<Void> forbidden(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);   // 403, not 401
        return exchange.getResponse().setComplete();
    }
}