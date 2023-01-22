package ru.itis.tdportal.paymentservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;
import ru.itis.tdportal.paymentservice.models.exceptions.ExternalException;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

@Component
@RequiredArgsConstructor
// TODO: сменить пакет
public class HttpClient {

    private final ObjectMapper objectMapper;

    public Object sendRequest(Class<?> responseType, HttpRequestBase request) {
        Object response;
        try (CloseableHttpClient client = HttpClients.createDefault()) {

            CloseableHttpResponse httpResponse = client.execute(request);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode >= 400) {
                throw new ExternalException(String.format("Server (%s) replied with status code = %s", request.getURI(), statusCode));
            }
            response = parseResponseContent(
                    httpResponse,
                    responseType
            );
        } catch (IOException e) {
            throw new ExternalException(e);
        }
        return response;
    }

    private Object parseResponseContent(CloseableHttpResponse httpResponse, Class<?> responseType) throws IOException {
        InputStream responseStream = httpResponse.getEntity().getContent();
        JsonNode jsonNode = objectMapper.readTree(responseStream);

        return objectMapper.treeToValue(jsonNode, responseType);
    }

    public StringEntity parseRequestContent(Object dto) {
        StringEntity entity;
        try {
            String json = objectMapper.writeValueAsString(dto);
            entity = new StringEntity(json);

        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
        return entity;
    }
}