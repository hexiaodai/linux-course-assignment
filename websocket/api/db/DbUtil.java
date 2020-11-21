package api.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DbUtil {
    static  DataSource ds;
    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://106.15.9.247:3306/websocket?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=false");
        config.setUsername("root");
        config.setPassword("0819");
        config.addDataSourceProperty("connectionTimeout", "1000"); // 连接超时：1秒
        config.addDataSourceProperty("idleTimeout", "600000"); // 空闲超时：60秒
        config.addDataSourceProperty("maximumPoolSize", "30"); // 最大连接数：10
        ds = new HikariDataSource(config);
    }
    public static Connection getConn() {
        try{
            return ds.getConnection();
        }catch (SQLException e){
            return null;
        }
    }

    public static void main(String[] args){
        System.out.println(getConn());
    }

}
