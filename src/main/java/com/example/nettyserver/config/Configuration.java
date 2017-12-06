package com.example.nettyserver.config;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by Eric on 2017/9/12.
 */
public class Configuration {
    private static Properties config;

    static Logger logger = Logger.getLogger(Configuration.class);

    public static Properties getConfig(){
        try{
            if(null == config){
                /*String path = URLDecoder.decode(Configuration.class.getClassLoader().getResource("./conf/conf.properties").getPath(), "utf-8");
                //String path = System.getProperty("user.dir") + "/resources/conf/conf.properties";
                File configFile = new File(path);

                if(configFile.exists() && configFile.isFile() && configFile.canRead()){
                    InputStream input = new FileInputStream(configFile);
                    config = new Properties();
                    config.load(input);
                }
                else{
                    System.out.println("The config file does not exist");
                }
*/

                config = new Properties();
                InputStream in = Configuration.class.getResourceAsStream("/conf/conf.properties");
                BufferedReader ipss = new BufferedReader(new InputStreamReader(in));

                config.load(ipss);
                logger.info("config file load success");
            }
        }catch(Exception e){
            logger.error(e.getMessage());
            System.out.println("Config file read error");
            //default set
            config = new Properties();
            config.setProperty("protocol", "SSLV3");
            config.setProperty("serverCer", "./certificate/server.keystore");
            config.setProperty("serverCerPwd", "serverks");
            config.setProperty("serverKeyPwd", "serverkey");
            config.setProperty("serverTrustCer", "./certificate/serverTrust.jks");
            config.setProperty("serverTrustCerPwd", "1234sp");
            config.setProperty("clientCer", "./certificate/client.keystore");
            config.setProperty("clientCerPwd", "clientks");
            config.setProperty("clientKeyPwd", "clientkey");
            config.setProperty("clientTrustCer", "./certificate/client.keystore");
            config.setProperty("clientTrustCerPwd", "1234sp");
            config.setProperty("serverListenPort", "7070");
            config.setProperty("serverThreadPoolSize", "5");
            config.setProperty("serverRequestQueueSize", "10");
            config.setProperty("socketStreamEncoding", "UTF-8");
        }

        return config;
    }
}
