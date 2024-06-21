package manHanLou.dao;

import manHanLou.utils.JDBCUtilsByDruid;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 黄钰琪
 * @version 1.0
 * @time 2024/6/18 0:04
 */
public class BasicDao<T> {
    private QueryRunner qr = new QueryRunner();

    //针对任意表开发通用dml方法，返回受影响的行数
    public int update(String sql, Object... parameters) {
        Connection connection = null;

        try {
            connection = JDBCUtilsByDruid.getConnection();
            int rows = qr.update(connection, sql, parameters);
            return rows;
        } catch (SQLException e) { //编译异常转为运行异常的好处在于，该方法的调用者不需要写捕获异常的代码
            //如果只是抛出到方法签名，则该方法的调用者就要捕获这个异常了
            throw new RuntimeException(e);
        } finally {
            JDBCUtilsByDruid.close(null, null, connection);
        }

    }

    public List<T> QueryMany(String sql, Class<T> clazz, Object... parameters) {

        Connection connection = null;
        try {
            connection = JDBCUtilsByDruid.getConnection();
            return qr.query(connection, sql, new BeanListHandler<T>(clazz), parameters);

        } catch (SQLException e) {
            throw  new RuntimeException(e); //将编译异常->运行异常 ,抛出
        } finally {
            JDBCUtilsByDruid.close(null, null, connection);
        }

    }


    public T QuerySingle(String sql, Class<T> clazz, Object... parameters) {
        Connection connection = null;

        try {
            connection = JDBCUtilsByDruid.getConnection();
            T t = qr.query(connection, sql, new BeanHandler<>(clazz), parameters);
            return t;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtilsByDruid.close(null, null, connection);
        }
    }

    public Object QueryScalar(String sql, Object... parameters) {
        Connection connection = null;
        try {
            connection = JDBCUtilsByDruid.getConnection();
            Object object = qr.query(connection, sql, new ScalarHandler(), parameters);
            return object;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtilsByDruid.close(null, null, connection);

        }
    }


}
