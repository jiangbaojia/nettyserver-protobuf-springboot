package com.example.nettyserver.netty;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;

/**
 * Created by Eric on 2017/11/9.
 */
public class SslContextUtils {
    private static final String STOREPASS = "tutorial123";

    public static final SSLContext createAndInitSSLContext(final String jksFileName) throws Exception {
        final ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        final InputStream inputStream = classloader.getResourceAsStream(jksFileName);

        final KeyStore trustStore = KeyStore.getInstance("jks");
        trustStore.load(inputStream, STOREPASS.toCharArray());

        final KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(trustStore, STOREPASS.toCharArray());

        final TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(trustStore);

        final SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());

        return sslContext;
    }
}
