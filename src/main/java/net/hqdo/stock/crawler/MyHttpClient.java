package net.hqdo.stock.crawler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Qing
 * @since 2017/1/3.
 */
public class MyHttpClient {
    public static void main(String[] args) throws URISyntaxException, IOException {
        HttpClient httpClient = HttpClients.createDefault();
        URI uri = new URI("http://hq.sinajs.cn/list=sh601006");
        HttpGet gettor = new HttpGet(uri);

        ResponseHandler<String> handler = httpResponse -> {
            int status = httpResponse.getStatusLine().getStatusCode();
            if(status ==200){
                HttpEntity entity = httpResponse.getEntity();
                return EntityUtils.toString(entity);
            }else{
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        };
        String resposeBody =  httpClient.execute(gettor,handler);
        int debugger = 0;
    }
}
