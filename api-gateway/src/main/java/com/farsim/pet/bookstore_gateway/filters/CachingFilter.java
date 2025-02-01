@Component
@Order(4) // Runs early in the chain
public class CachingFilter implements WebFilter {
    @Autowired
    private ReactiveRedisTemplate<String, String> redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        if (!exchange.getRequest().getMethod().equals(HttpMethod.GET)) {
            return chain.filter(exchange); // Only cache GET requests
        }

        String userId = exchange.getRequest().getHeaders().getFirst("User-ID");
        String cacheKey = "cache:" + userId + ":" + exchange.getRequest().getURI().toString();

        return redisTemplate.opsForValue().get(cacheKey)
                .flatMap(cachedResponse -> {
                    ServerHttpResponse response = exchange.getResponse();
                    response.setStatusCode(HttpStatus.OK);
                    response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                    return response.writeWith(Mono.just(response.bufferFactory().wrap(cachedResponse.getBytes())));
                })
                .switchIfEmpty(chain.filter(exchange).then(Mono.defer(() -> {
                    ServerHttpResponse response = exchange.getResponse();
                    DataBufferFactory bufferFactory = response.bufferFactory();
                    return response.writeWith(DataBufferUtils.join(response.getBody()).map(dataBuffer -> {
                        byte[] bytes = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(bytes);
                        DataBufferUtils.release(dataBuffer);

                        // Store response in Redis (TTL: 5 minutes)
                        redisTemplate.opsForValue().set(cacheKey, new String(bytes), Duration.ofMinutes(5)).subscribe();
                        return bufferFactory.wrap(bytes);
                    }));
                })));
    }
}
