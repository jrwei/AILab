package dbc;

import android.database.SQLException;
import android.util.Log;

import java.sql.ResultSet;

import static android.content.ContentValues.TAG;

/**
 * Created by Tangqh on 2017/10/26.
 */

public class DBThread  extends Thread{

    public static void SqlQuery(final String sqlstr, final CallBackListener listner){
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        DBManager sql = null;
                        ResultSet rs = null;
                        try{
                            sql = DBManager.createInstance();
                            sql.connectDB();
                        }catch(SQLException e){
                            Log.d("MainActivity","连接数据库失败");
                        }

                        try{
                            if (sql == null){
                                Log.d(TAG, "run in thread: fail to connect to mysql.");
                                return;
                            }
                            rs = sql.queryBySql(sqlstr);
                        }catch (SQLException e){
                            e.printStackTrace();
                            Log.d("MainActivity","查询数据失败");
                        }

                        try{
                            if (!rs.wasNull()){
                                rs.first();
                                listner.onFinish(rs.getString(1));
                                Log.d("连接数据库并查询成功，值大于50有",rs.getString(1)+"秒");
                            }

                        }catch(Exception e){
                            listner.onError(e);
                        }

                        try{
                            sql.closeDB();
                        }catch(SQLException e){
                            Log.d("MainActivity","关闭流异常");
                        }
                    }
                }
        ).start();
    }
}
