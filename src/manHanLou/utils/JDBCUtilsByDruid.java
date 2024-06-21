package manHanLou.utils;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author 黄钰琪
 * @version 1.0
 * @time 2024/6/17 16:24
 * 基于德鲁伊连接池的工具类
 */
public class JDBCUtilsByDruid {
    private static  DataSource ds;
    //在静态代码块完成ds的初始化
    static {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("src/manHanLou/druid.properties"));
            ds = DruidDataSourceFactory.createDataSource(properties);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection(){
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //这里的关闭连接不是真正的关闭，而是把连接对象放回数据库连接池
    public static void close(ResultSet resultSet, Statement statement, Connection connection){
        try {
            if(resultSet != null){
                resultSet.close();
            }
            if(statement != null){
                statement.close();
            }
            if(connection != null){
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
