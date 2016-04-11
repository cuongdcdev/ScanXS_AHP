package com.gk.db;

import com.gk.app.MyConfig;
import com.gk.utils.Tool;
import java.sql.*;
import org.apache.log4j.Logger;
import snaq.db.ConnectionPool;

public class XSDBPool {

    static Logger logger = Logger.getLogger(XSDBPool.class);
    static ConnectionPool pool;
    //--
    public static int MAX_CONNECTIONS;
    public static int INI_CONNECTIONS;
    public static int TIME_OUT;
    private static String driverString = "com.mysql.jdbc.Driver";
    private static String user = "xs";
    private static String pass = "xs";
    private static String url_db = "jdbc:mysql://localhost:3306/advertise_link_vn?useUnicode=true&characterEncoding=UTF-8";

    static {
        try {
            driverString = MyConfig.getString("driver", "com.mysql.jdbc.Driver", "XSDBPool");
            url_db = MyConfig.getString("url", "jdbc:mysql://localhost:3306/advertise_link_vn?useUnicode=true&characterEncoding=UTF-8", "XSDBPool");
            user = MyConfig.getString("user", "xs", "XSDBPool");
            pass = MyConfig.getString("pass", "xs", "XSDBPool");
            //--
            INI_CONNECTIONS = MyConfig.getInt("init_connection", 10, "XSDBPool");
            MAX_CONNECTIONS = MyConfig.getInt("max_connection", 50, "XSDBPool");
            TIME_OUT = MyConfig.getInt("time_out", 10, "XSDBPool");
            //******
            Class c = Class.forName(driverString);
            Driver driver = (Driver) c.newInstance();
            DriverManager.registerDriver(driver);
            
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            e.printStackTrace();
            logger.error(Tool.getLogMessage(e));
        }

    }

    public static void CreatePool() {
        pool = new ConnectionPool("local",
                INI_CONNECTIONS, /*min pool*/
                MAX_CONNECTIONS, /*Max Pool*/
                MAX_CONNECTIONS, /*Max size*/
                TIME_OUT, /*Second*/
                url_db,
                user,
                pass);
        pool.setCaching(false, true, true);
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = pool.getConnection();
//            System.out.println("Connection Mode:"+conn.getAutoCommit());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void release() {
        pool.release();
    }

    public static void freeConn(ResultSet rs, PreparedStatement pstm, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pstm != null) {
                pstm.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(Tool.getLogMessage(e));
        }
    }

    public static void releadRsPstm(ResultSet rs, PreparedStatement pstm) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pstm != null) {
                pstm.close();
            }
        } catch (Exception e) {
        }
    }

    public static int size() {
        return pool.getSize();
    }
}
