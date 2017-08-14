package com.example.rest.client;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HttpUtil {

    private WebTarget target;
    private Builder builder;
    private String url;
    private Map<String, String> queryParams;
    private Map<String, Object> headers;

    // private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);
    private HttpUtil(HttpUtilBuilder builder) {
        this.url = builder.url;
        this.queryParams = builder.queryParams;
        this.headers = builder.headers;
    }

    public Response get() {
        initialize();
        //   LogRegistry.registerInfo(LOGGER, "Fetching url :: {} with query parameters :: {} ", url,
        //           queryParams);
        return builder.accept(MediaType.APPLICATION_JSON_TYPE).get();
    }

    public <T> Response postJson(T body) {
        initialize();
        return builder.post(Entity.json(body));
    }

    public Response postFormData(Form body) {
        initialize();
        return builder.post(Entity.form(body));
    }

    private void initialize() {
        target = ClientFactory.INSTANCE.getClient().target(url);
        target = addQueryParams(target, queryParams);
        builder = addHeaders(target, headers);
    }

    private WebTarget addQueryParams(WebTarget target, Map<String, String> queryParams) {
        if (queryParams == null) {
            return target;
        }
        for (Entry<String, String> entry : queryParams.entrySet()) {
            target = target.queryParam(entry.getKey(), entry.getValue());
        }
        return target;
    }

    private Builder addHeaders(WebTarget target, Map<String, Object> headers) {
        if (headers == null)
            return target.request();

        MultivaluedMap<String, Object> head = new MultivaluedHashMap<>();
        for (Entry<String, Object> entry : headers.entrySet()) {
            head.putSingle(entry.getKey(), entry.getValue());
        }
        return target.request().headers(head);
    }

    public static class HttpUtilBuilder {
        private String url;
        private Map<String, String> queryParams;
        private Map<String, Object> headers;

        public HttpUtilBuilder(String url) {
            this.url = url;
            this.queryParams = new HashMap<>();
            this.headers = new HashMap<>();
        }

        public HttpUtilBuilder addQueryParam(String name, String value) {
            this.queryParams.put(name, value);
            return this;
        }

        public HttpUtilBuilder addQueryParams(Map<String, String> params) {
            this.queryParams.putAll(params);
            return this;
        }

        public HttpUtilBuilder addHeader(String name, String values) {
            this.headers.put(name, values);
            return this;
        }

        public HttpUtilBuilder addHeader(String name, List<String> values) {
            this.headers.put(name, values);
            return this;
        }

        public HttpUtilBuilder addHeaders(Map<String, List<String>> headers) {
            this.headers.putAll(headers);
            return this;
        }

        public HttpUtil build() {
            return new HttpUtil(this);
        }
    }
}
