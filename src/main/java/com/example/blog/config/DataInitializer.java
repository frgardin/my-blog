package com.example.blog.config;

import com.example.blog.model.Article;
import com.example.blog.repository.ArticleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {
    
    private final ArticleRepository articleRepository;
    
    public DataInitializer(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }
    
    @Override
    public void run(String... args) throws Exception {
        if (articleRepository.count() == 0) {
            initializeSampleArticles();
        }
    }
    
    private void initializeSampleArticles() {
        Article article1 = new Article();
        article1.setTitle("Understanding Spring Boot Autoconfiguration");
        article1.setSlug("understanding-spring-boot-autoconfiguration");
        article1.setSummary("Deep dive into how Spring Boot's autoconfiguration mechanism works and how to leverage it effectively in your applications.");
        article1.setContent("""
# Understanding Spring Boot Autoconfiguration

Spring Boot's autoconfiguration is one of its most powerful features, but how does it work under the hood? In this article, we'll explore the mechanism that makes Spring Boot so developer-friendly.

## What is Autoconfiguration?

Autoconfiguration is Spring Boot's way of automatically configuring Spring applications based on the JAR dependencies present on the classpath. It follows the convention over configuration principle, reducing the need for manual configuration.

## How It Works

The autoconfiguration process works through these key steps:

1. **Classpath Scanning**: Spring Boot scans the classpath for specific dependencies
2. **Conditional Configuration**: Based on found dependencies, it conditionally creates beans
3. **Property-Based Customization**: Allows fine-tuning through application properties

### Conditional Beans

Spring Boot uses various `@Conditional` annotations to determine when to apply configuration:

```java
@Configuration
@ConditionalOnClass(DataSource.class)
@ConditionalOnMissingBean(type = "javax.sql.DataSource")
@EnableConfigurationProperties(DataSourceProperties.class)
public class DataSourceAutoConfiguration {
    // Configuration code
}
```

## Creating Custom Autoconfiguration

You can create your own autoconfiguration modules:

1. Create a configuration class with `@Configuration`
2. Use conditional annotations appropriately
3. Register your autoconfiguration in `META-INF/spring.factories`

```java
@Configuration
@ConditionalOnClass(MyService.class)
@EnableConfigurationProperties(MyProperties.class)
public class MyAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean
    public MyService myService(MyProperties properties) {
        return new MyService(properties);
    }
}
```

## Best Practices

- **Be explicit**: Use proper conditional annotations
- **Provide sensible defaults**: But allow customization
- **Document thoroughly**: Explain what your autoconfiguration does
- **Test thoroughly**: Ensure it works in various scenarios

## Debugging Autoconfiguration

Spring Boot provides tools to debug autoconfiguration:

```properties
# Enable debug logging for autoconfiguration
debug=true

# Or use the specific report endpoint
logging.level.org.springframework.boot.autoconfigure=DEBUG
```

You can also use the Actuator endpoint `/actuator/conditions` to see what conditions were evaluated.

## Conclusion

Understanding autoconfiguration is key to mastering Spring Boot. It's a powerful mechanism that can significantly reduce boilerplate code while maintaining flexibility.

Have you encountered any interesting autoconfiguration scenarios? Share your experiences in the comments!
""");
        article1.setCreatedAt(LocalDateTime.now().minusDays(7));
        article1.setUpdatedAt(LocalDateTime.now().minusDays(7));
        
        Article article2 = new Article();
        article2.setTitle("Performance Engineering with eBPF");
        article2.setSlug("performance-engineering-with-ebpf");
        article2.setSummary("Exploring how eBPF can revolutionize system performance monitoring and debugging in modern applications.");
        article2.setContent("""
# Performance Engineering with eBPF

eBPF (extended Berkeley Packet Filter) is revolutionizing how we approach system performance monitoring and debugging. Let's explore this powerful technology and its applications in modern software engineering.

## What is eBPF?

eBPF is a revolutionary technology that allows safe, sandboxed programs to run in the Linux kernel without changing kernel source code or loading kernel modules. It originated from BPF filters but has evolved into a general-purpose execution environment.

## Why eBPF Matters for Performance Engineering

### Traditional Limitations

Traditional performance monitoring approaches have several limitations:
- High overhead from system calls
- Limited visibility into kernel space
- Difficulty correlating events across different layers

### eBPF Advantages

eBPF addresses these limitations by:
- **Low overhead**: Programs run in kernel space
- **Rich visibility**: Access to kernel and user-space data
- **Real-time insights**: Immediate feedback on system behavior
- **Safety**: Verifier ensures programs don't crash the kernel

## Practical Applications

### Network Performance Monitoring

```c
SEC("socket")
int socket_filter(struct __sk_buff *skb) {
    // Monitor network packets with minimal overhead
    u64 pid_tgid = bpf_get_current_pid_tgid();
    u32 pid = pid_tgid >> 32;
    
    // Collect metrics
    struct event_t event = {
        .pid = pid,
        .timestamp = bpf_ktime_get_ns(),
        .bytes = skb->len
    };
    
    bpf_perf_event_output(skb, &events, BPF_F_CURRENT_CPU, &event, sizeof(event));
    return 0;
}
```

### Application Tracing

eBPF can trace application behavior without code changes:

```c
SEC("uprobe")
int trace_function(struct pt_regs *ctx) {
    // Trace specific function calls
    u64 pid_tgid = bpf_get_current_pid_tgid();
    u32 pid = pid_tgid >> 32;
    
    // Filter by process if needed
    if (target_pid && pid != target_pid) {
        return 0;
    }
    
    // Collect timing information
    struct data_t data = {
        .pid = pid,
        .timestamp = bpf_ktime_get_ns()
    };
    
    bpf_perf_event_output(ctx, &events, BPF_F_CURRENT_CPU, &data, sizeof(data));
    return 0;
}
```

## Tools and Ecosystem

### Popular eBPF Tools

- **BCC**: BPF Compiler Collection for writing eBPF programs
- **BPFtrace**: High-level tracing language
- **Cilium**: Networking and security with eBPF
- **Pixie**: Kubernetes observability with eBPF

### Integration with Existing Systems

eBPF integrates well with existing observability stacks:
- Prometheus metrics export
- Grafana dashboards
- OpenTelemetry tracing

## Getting Started

### Prerequisites

- Linux kernel 4.4+ (recommended 5.8+ for CO-RE)
- LLVM/Clang for compiling eBPF programs
- BCC or libbpf for development

### Simple Example

```bash
# Install BCC tools
sudo apt-get install bpfcc-tools

# Trace syscalls
sudo tracepoint:syscalls:sys_enter_openat
```

## Best Practices

### Performance Considerations

- Keep programs simple and efficient
- Use maps wisely to avoid memory overhead
- Consider CO-RE (Compile Once Run Everywhere) for portability

### Security Guidelines

- Always validate input data
- Use bounded loops to avoid verifier rejection
- Follow least privilege principle

## Real-World Use Cases

### Netflix's eBPF Usage

Netflix uses eBPF for:
- Network performance monitoring
- Security policy enforcement
- Service mesh optimization

### Cloud Native Applications

eBPF is ideal for:
- Service mesh implementations
- Container networking
- Runtime security

## Future Directions

The eBPF ecosystem is rapidly evolving:
- More kernel hooks being added
- Better tooling and debugging support
- Increased adoption in cloud-native stack

## Conclusion

eBPF represents a paradigm shift in system performance engineering. Its ability to provide deep insights with minimal overhead makes it invaluable for modern applications.

As the technology matures, we can expect even more innovative use cases and tools. Now is the perfect time to start exploring eBPF for your performance engineering needs.

Have you used eBPF in your projects? Share your experiences and use cases!
""");
        article2.setCreatedAt(LocalDateTime.now().minusDays(14));
        article2.setUpdatedAt(LocalDateTime.now().minusDays(14));
        
        Article article3 = new Article();
        article3.setTitle("System Design: Building Scalable Microservices");
        article3.setSlug("system-design-scalable-microservices");
        article3.setSummary("Key principles and patterns for designing microservices that can scale effectively in production environments.");
        article3.setContent("""
# System Design: Building Scalable Microservices

Designing microservices that scale effectively requires careful consideration of architecture, data management, and operational concerns. Let's explore the key principles and patterns.

## Core Principles

### Single Responsibility

Each microservice should have a single, well-defined business capability:

```
UserService: User management and authentication
OrderService: Order processing and management
PaymentService: Payment processing and validation
```

### Loose Coupling

Services should communicate through well-defined APIs and avoid direct dependencies:

```java
// Good: Event-driven communication
@EventListener
public void handleOrderCreated(OrderCreatedEvent event) {
    // Process order asynchronously
}

// Avoid: Direct service calls
// userService.getUserDetails(order.getUserId());
```

### High Cohesion

Related functionality should be grouped within the same service.

## Communication Patterns

### Synchronous Communication

```java
@RestController
public class OrderController {
    
    @PostMapping("/orders")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest request) {
        // Direct HTTP call to inventory service
        InventoryResponse inventory = inventoryClient.checkAvailability(request.getItems());
        
        if (!inventory.isAvailable()) {
            return ResponseEntity.badRequest().build();
        }
        
        Order order = orderService.createOrder(request);
        return ResponseEntity.ok(OrderResponse.from(order));
    }
}
```

### Asynchronous Communication

```java
@Component
public class OrderEventHandler {
    
    @EventListener
    @Async
    public void handleOrderCreated(OrderCreatedEvent event) {
        // Process order asynchronously
        notificationService.sendOrderConfirmation(event.getOrder());
        analyticsService.trackOrderCreation(event.getOrder());
    }
}
```

## Data Management

### Database per Service

Each service should own its data:

```yaml
# User Service Database
users:
  - id: UUID
  - email: string
  - name: string
  - created_at: timestamp

# Order Service Database
orders:
  - id: UUID
  - user_id: UUID
  - items: array
  - status: string
  - created_at: timestamp
```

### Data Consistency Patterns

#### Saga Pattern

```java
@Service
public class OrderSaga {
    
    public void processOrder(Order order) {
        try {
            // Step 1: Reserve inventory
            inventoryService.reserveItems(order.getItems());
            
            // Step 2: Process payment
            paymentService.processPayment(order.getPaymentInfo());
            
            // Step 3: Confirm order
            orderService.confirmOrder(order.getId());
            
        } catch (Exception e) {
            // Compensating transactions
            inventoryService.releaseReservation(order.getItems());
            paymentService.refundPayment(order.getPaymentInfo());
            orderService.cancelOrder(order.getId());
        }
    }
}
```

## Scaling Strategies

### Horizontal Scaling

```yaml
# Kubernetes deployment example
apiVersion: apps/v1
kind: Deployment
metadata:
  name: order-service
spec:
  replicas: 3
  selector:
    matchLabels:
      app: order-service
  template:
    metadata:
      labels:
        app: order-service
    spec:
      containers:
      - name: order-service
        image: order-service:latest
        ports:
        - containerPort: 8080
        resources:
          requests:
            memory: "256Mi"
            cpu: "250m"
          limits:
            memory: "512Mi"
            cpu: "500m"
```

### Load Balancing

```java
@Configuration
public class LoadBalancerConfig {
    
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    @Bean
    public LoadBalancerClient loadBalancerClient() {
        return new RoundRobinLoadBalancer();
    }
}
```

## Resilience Patterns

### Circuit Breaker

```java
@Service
public class PaymentService {
    
    @CircuitBreaker(name = "paymentService", fallbackMethod = "fallbackProcessPayment")
    public PaymentResult processPayment(PaymentRequest request) {
        // External payment gateway call
        return paymentGateway.process(request);
    }
    
    public PaymentResult fallbackProcessPayment(PaymentRequest request, Exception e) {
        log.error("Payment service unavailable, using fallback", e);
        return PaymentResult.failed("Payment service temporarily unavailable");
    }
}
```

### Retry Pattern

```java
@Retryable(value = {SQLException.class}, maxAttempts = 3, backoff = @Backoff(delay = 1000))
public void updateInventory(InventoryUpdate update) {
    inventoryRepository.updateStock(update);
}
```

## Monitoring and Observability

### Distributed Tracing

```java
@RestController
public class OrderController {
    
    @NewSpan("create-order")
    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest request) {
        Span span = tracer.nextSpan().name("order-validation");
        try (Tracer.SpanInScope ws = tracer.withSpanInScope(span)) {
            validateOrder(request);
            Order order = orderService.createOrder(request);
            return ResponseEntity.ok(order);
        } finally {
            span.end();
        }
    }
}
```

### Metrics Collection

```java
@Component
public class OrderMetrics {
    
    private final Counter orderCounter;
    private final Timer orderProcessingTime;
    
    public OrderMetrics(MeterRegistry meterRegistry) {
        this.orderCounter = Counter.builder("orders.created")
            .description("Number of orders created")
            .register(meterRegistry);
        
        this.orderProcessingTime = Timer.builder("orders.processing.time")
            .description("Time taken to process orders")
            .register(meterRegistry);
    }
    
    public void recordOrderCreated() {
        orderCounter.increment();
    }
    
    public void recordProcessingTime(Duration duration) {
        orderProcessingTime.record(duration);
    }
}
```

## Security Considerations

### API Gateway

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/public/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2.jwt());
        
        return http.build();
    }
}
```

## Best Practices

### Service Discovery

```yaml
# Consul service registration
consul:
  host: localhost
  port: 8500
  discovery:
    service-name: order-service
    health-check-path: /actuator/health
    health-check-interval: 10s
```

### Configuration Management

```java
@RestController
@RefreshScope
public class OrderController {
    
    @Value("${order.max-items:100}")
    private int maxItems;
    
    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest request) {
        if (request.getItems().size() > maxItems) {
            throw new IllegalArgumentException("Too many items in order");
        }
        // Process order
    }
}
```

## Conclusion

Building scalable microservices requires careful attention to architecture patterns, data management, and operational concerns. By following these principles and patterns, you can create systems that scale effectively while maintaining maintainability and reliability.

The key is to start simple, iterate based on requirements, and continuously monitor and improve your architecture.

What patterns have you found most effective in your microservices architecture? Share your experiences!
""");
        article3.setCreatedAt(LocalDateTime.now().minusDays(21));
        article3.setUpdatedAt(LocalDateTime.now().minusDays(21));
        
        articleRepository.save(article1);
        articleRepository.save(article2);
        articleRepository.save(article3);
    }
}
