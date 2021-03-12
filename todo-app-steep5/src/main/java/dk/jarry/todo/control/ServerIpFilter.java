package dk.jarry.todo.control;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;

import io.vertx.core.http.HttpServerRequest;

@Provider
public class ServerIpFilter implements ContainerResponseFilter {

	private static final Logger LOG = Logger.getLogger(ServerIpFilter.class);

	@Context
	HttpServerRequest request;

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {

		String name = "X-server-ip";
		String value = request.localAddress().host();
		responseContext.getHeaders().add(name, value);

		LOG.info("X-server-ip: " + value);

	}

}
