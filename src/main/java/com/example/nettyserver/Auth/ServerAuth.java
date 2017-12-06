package com.example.nettyserver.Auth;

/**
 * Created by Eric on 2017/9/12.
 */

import com.example.nettyserver.config.Configuration;

import javax.net.ssl.*;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.Properties;


public class ServerAuth {
    private static SSLContext sslContext;

    public static SSLContext getSSLContext() throws Exception{
        Properties p = com.example.nettyserver.config.Configuration.getConfig();

        String protocol = p.getProperty("protocol");
        String serverCer = p.getProperty("serverCer");
        String serverCerPwd = p.getProperty("serverCerPwd");
        String serverKeyPwd = p.getProperty("serverKeyPwd");


        //Key Stroe
        KeyStore keyStore = KeyStore.getInstance("JKS");

        //String path = URLDecoder.decode(Configuration.class.getClassLoader().getResource(serverCer).getPath(), "utf-8");//不适用于jar发布
        //String path = System.getProperty("user.dir") + "/resources/" + serverCer;

        //keyStore.load(new FileInputStream(path),
        //        serverCerPwd.toCharArray());

        InputStream in = Configuration.class.getResourceAsStream("/" + serverCer);
        keyStore.load(in, serverCerPwd.toCharArray());

        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
        keyManagerFactory.init(keyStore, serverKeyPwd.toCharArray());
        KeyManager[] kms = keyManagerFactory.getKeyManagers();

        TrustManager[] tms = null;
        if(com.example.nettyserver.config.Configuration.getConfig().getProperty("authority").equals("2")){

            /*此处不适用
            String serverTrustCer = p.getProperty("serverTrustCer");
            String serverTrustCerPwd = p.getProperty("serverTrustCerPwd");

            //Trust Key Store
            keyStore = KeyStore.getInstance("JKS");
            keyStore.load(new FileInputStream(serverTrustCer),
                    serverTrustCerPwd.toCharArray());
                    */

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
            trustManagerFactory.init(keyStore);
            tms = trustManagerFactory.getTrustManagers();
        }
        sslContext = SSLContext.getInstance(protocol);
        sslContext.init(kms, tms, null);

        return sslContext;
    }
}