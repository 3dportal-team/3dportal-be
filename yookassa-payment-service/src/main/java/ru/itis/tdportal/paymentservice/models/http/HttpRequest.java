package ru.itis.tdportal.paymentservice.models.http;

import lombok.NoArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.springframework.http.HttpMethod;

import java.net.URI;
import java.nio.charset.StandardCharsets;

// TODO: постараться переписать на аннотации
public class HttpRequest {

    public static HttpRequestBuilder builder() {
        return new HttpRequestBuilder();
    }

    @NoArgsConstructor
    public static class HttpRequestBuilder {

        private HttpRequestBase requestBase;

        public HttpRequestBase build() {
            return requestBase;
        }

        public HttpRequestBuilder method(HttpMethod method) {
            if (HttpMethod.GET.equals(method)) {
                requestBase = new HttpGet();

            } else if (HttpMethod.POST.equals(method)) {
                requestBase = new HttpPost();
            }

            return this;
        }

        public HttpRequestBuilder json(StringEntity entity) {
            if (requestBase instanceof HttpPost) {
                ((HttpPost) requestBase).setEntity(entity);
            }
            String jsonMimeType = ContentType.APPLICATION_JSON.getMimeType();
            requestBase.setHeader(HttpHeaders.ACCEPT, jsonMimeType);
            requestBase.setHeader(HttpHeaders.CONTENT_TYPE, jsonMimeType);
            return this;
        }

        public HttpRequestBuilder url(String url) {
            requestBase.setURI(URI.create(url));
            return this;
        }

        public HttpRequestBuilder setHeader(String header, String value) {
            requestBase.setHeader(header, value);
            return this;
        }

        // TODO: переписать на упрощенную версию
        public HttpRequestBuilder baseAuth(String userName, String password) {
            StringBuilder auth = new StringBuilder();
            auth.append(userName).append(":").append(password);
            byte[] encodedAuth = Base64.encodeBase64(auth.toString().getBytes(StandardCharsets.UTF_8));
            setHeader(HttpHeaders.AUTHORIZATION, "Basic " + new String(encodedAuth));
            return this;
        }
    }
}
