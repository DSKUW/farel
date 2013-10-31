package pl.edu.uw.dsk.dev.farel.utils;

import java.net.URI;

import org.apache.http.client.HttpClient;
import org.apache.http.protocol.HttpContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

public class ContextAwareHttpComponentsClientHttpRequestFactory extends HttpComponentsClientHttpRequestFactory {
    private HttpContext httpContext;

    public ContextAwareHttpComponentsClientHttpRequestFactory(HttpClient httpClient) {
        super(httpClient);
    }

    protected HttpContext createHttpContext(HttpMethod httpMethod, URI uri) {
        return httpContext;
    }

    public void setHttpContext(HttpContext httpContext) {
        this.httpContext = httpContext;
    }
}