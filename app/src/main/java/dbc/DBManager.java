package dbc;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static android.content.ContentValues.TAG;

/**
 * Created by admin on 2017/10/25.
 */

public class DBManager {
    // 数据库连接常量
    public static final String DRIVER = "com.mysql.jdbc.Driver";
    public static final String USER = "ailab";
    public static final String PASS = "ailab";
    public static final String URL = "jdbc:mysql://123.207.148.32:3306/AILab";
    // 静态成员，支持单态模式
    private static DBManager per = null;
    private ResultSet rs = null;
    private Connection conn = null;
    private Statement stmt = null;
    // 单态模式-懒汉模式
    private DBManager() {
    }
    public static DBManager createInstance() {
        if (per == null) {
            per = new DBManager();
            per.initDB();
        }
        return per;
    }
    public void initDB() {
        try {
            Class.forName(DRIVER);
            Log.d("连接数据库","加载驱动成功");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("连接数据库","加载驱动异常");
        }
    }
    // 连接数据库，获取句柄+对象
    public void connectDB() {
        Log.d("连接数据库","正在连接数据库.....若无异常，则成功连接");
        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
            stmt = conn.createStatement();
            if (!(conn == null) && !(stmt == null))
                Log.d("连接数据库","已经成功连接");
            else
                Log.d("连接数据库", "connectDB: 失败！");
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d("连接数据库","数据库连接异常");
        }
    }
    // 关闭数据库 关闭对象，释放句柄
    public void closeDB() {
        try {
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.d("连接数据库","成功关闭数据库连接");
    }
    // 查询
    public ResultSet queryBySql(String sql) {
        ResultSet rs = null;
        Log.d(TAG, "queryBySql: ");
        try {
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            Log.d("连接数据库","执行查询操作异常");
            e.printStackTrace();
        }
        Log.d("连接数据库","成功执行查询操作");
        return rs;
    }
}
