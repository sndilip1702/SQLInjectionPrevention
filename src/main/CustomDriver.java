package main;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by sndil on 4/25/2016.
 */
public class CustomDriver implements Driver {

    public static final String WRAPPED_DRIVER = "com.mysql.jdbc.Driver";
    private Driver customDriver;

    public static final String WRAPPED_DRIVER_SCHEME = "jdbc:mysql:";
    public static final String THIS_DRIVER_SCHEME = "custom:";

    static {
        try {
            DriverManager.registerDriver(new CustomDriver());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CustomDriver() throws SQLException{
        try {
            customDriver = (Driver) Class.forName(WRAPPED_DRIVER).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String fixupUrl(String url) {
        if (url.startsWith(THIS_DRIVER_SCHEME)) {
            url = WRAPPED_DRIVER_SCHEME + url.substring(THIS_DRIVER_SCHEME.length());
        }

        return url;
    }

    @Override
    public Connection connect(String url, Properties info) throws SQLException {
        url = fixupUrl(url);
        Connection conn = customDriver.connect(url,info);
        return new CustomConnection(conn);
    }

    @Override
    public boolean acceptsURL(String url) throws SQLException {
        String fixedUrl = fixupUrl(url);
        if (fixedUrl.equals(url)) {
            return false;
        }
        return customDriver.acceptsURL(url);
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        return customDriver.getPropertyInfo(url, info);
    }

    @Override
    public int getMajorVersion() {
        return customDriver.getMajorVersion();
    }

    @Override
    public int getMinorVersion() {
        return customDriver.getMinorVersion();
    }

    @Override
    public boolean jdbcCompliant() {
        return customDriver.jdbcCompliant();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
