@Component
@Order(1) // After logging
public class RateLimiterFilter implements WebFilter {
    @Autowired
    private RedisRateLimiter redisRateLimiter;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String ip = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
        if (redisRateLimiter.isBlocked(ip)) {
            exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }
}
