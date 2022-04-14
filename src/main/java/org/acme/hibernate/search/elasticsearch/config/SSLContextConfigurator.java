package org.acme.hibernate.search.elasticsearch.config;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.client.RestClientBuilder;

import io.quarkus.elasticsearch.restclient.lowlevel.ElasticsearchClientConfig;

@ElasticsearchClientConfig
public class SSLContextConfigurator implements RestClientBuilder.HttpClientConfigCallback {
    @Override
    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
        try {
            SSLContextBuilder sslBuilder = SSLContexts.custom()
                    .loadTrustMaterial(null, (x509Certificates, s) -> true);
            final SSLContext sslContext = sslBuilder.build();
            httpClientBuilder.setSSLContext(sslContext).setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return httpClientBuilder;
    }
}
