@Component
@Order(5) // Last
public class ResponseHeaderFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            exchange.getResponse().getHeaders().add("X-Correlation-ID", UUID.randomUUID().toString());
        }));
    }
}
