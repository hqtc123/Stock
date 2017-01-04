package net.hqdo.stock.crawler;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Qing
 * @since 2017/1/3.
 */
public class HttpTool {
    private static CloseableHttpClient httpClient;

    public static String doHttpGet(String url) throws URISyntaxException, IOException {
        URI uri = new URI(url);
        if (httpClient == null) {
            httpClient = HttpClients.createDefault();
        }

        HttpGet httpGet = new HttpGet(uri);
        ResponseHandler<String> handler = httpResponse -> {
            int status = httpResponse.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = httpResponse.getEntity();
                return EntityUtils.toString(entity);
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        };
        return httpClient.execute(httpGet, handler);
    }

    public static void closeHttpClient() throws IOException {
        if (httpClient != null) {
            httpClient.close();
        }
    }
}
