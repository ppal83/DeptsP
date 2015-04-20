package com.aimprosoft.connection;

import org.apache.commons.dbcp.BasicDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBCPDataSourceFactory {

    private static BasicDataSource ds = null;

    static {
        Properties props = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        try (InputStream in = classLoader.getResourceAsStream("db.properties") ) {
            props.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String drv = props.getProperty("jdbc.drivers");
        String url = props.getProperty("jdbc.url");
        String usr = props.getProperty("jdbc.username");
        String pwd = props.getProperty("jdbc.password");

        createDataSource(drv, url, usr, pwd);
    }

    public static void createDataSource(String drv, String url,
                                        String usr, String pwd) {
        ds = new BasicDataSource();
        ds.setDriverClassName(drv);
        ds.setUrl(url);
        ds.setUsername(usr);
        ds.setPassword(pwd);
    }

    public static BasicDataSource getDataSource(){
        return ds != null ? ds : null;
    }
}
