package dk.jarry.todo;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Readiness;

import javax.enterprise.context.ApplicationScoped;

/**
 * https://quarkus.io/guides/microprofile-health
 */
@Readiness
@ApplicationScoped
public class DemoHealthCheck implements HealthCheck {
    
    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse.named("Demo health check");
        responseBuilder.withData("Foo", "Hello");
        responseBuilder.up();
        return responseBuilder.build();
    }

}