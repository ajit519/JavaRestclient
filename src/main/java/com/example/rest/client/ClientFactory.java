package com.example.rest.client;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public enum ClientFactory {

    INSTANCE;

    ClientFactory() {
        client = ClientBuilder.newClient();
        if(httpsEnabled){
            httpsClientBuild(client);
        }
        client.register(JacksonJsonProvider.class);
    }

    private Client client;
    private boolean httpsEnabled = false;

    public Client getClient() {
        return client;
    }

    private void httpsClientBuild(Client client) {
        TrustManager[] certs = new TrustManager[] { getTrustManager() };
        SSLContext ctx = null;
        try {
            ctx = SSLContext.getInstance("TLSv1");
            ctx.init(null, certs, new SecureRandom());
        } catch (java.security.GeneralSecurityException e) {
            e.printStackTrace();
        }

        HttpsURLConnection.setDefaultSSLSocketFactory(ctx.getSocketFactory());

        ClientBuilder clientBuilder = ClientBuilder.newBuilder();
        try {
            clientBuilder.sslContext(ctx);
            clientBuilder.hostnameVerifier((hostname, session) -> true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.client = clientBuilder.build();
    }

    private X509TrustManager getTrustManager() {
        return new X509TrustManager() {
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }
        };
    }
}
