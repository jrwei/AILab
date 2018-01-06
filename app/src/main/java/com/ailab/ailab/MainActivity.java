package com.ailab.ailab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import dbc.CallBackListener;
import dbc.DBThread;

public class MainActivity extends AppCompatActivity {
    private String sql_one = "SELECT COUNT(*) FROM Environment_Humi WHERE DATA > 50";
/*    private ResultSet rs = null;
    private DBManager sql;
    private String data;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1 = (Button) findViewById(R.id.button_db);
       /* button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                try{
                                    sql = DBManager.createInstance();
                                    sql.connectDB();
                                }catch(SQLException e){
                                    Log.d("MainActivity","连接数据库失败");
                                }

                                try{
                                    rs = sql.queryBySql(sql_one);
                                }catch (SQLException e){
                                    e.printStackTrace();
                                    Log.d("MainActivity","查询数据失败");
                                }
                                try{
                                    data = rs.getString(1);
                                }catch(Exception e){

                                }
                                Log.d("连接数据库并查询成功，值大于50有",data+"秒");
                                try{
                                    sql.closeDB();
                                }catch(SQLException e){
                                    Log.d("MainActivity","关闭流异常");
                                }
                            }
                        }
                ).start();
                Toast.makeText(MainActivity.this,"查询结果为："+data,Toast.LENGTH_SHORT).show();
            }
        });*/

       button1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               DBThread.SqlQuery(sql_one, new CallBackListener() {
                   @Override
                   public void onFinish(final String info) {
                       runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               Toast.makeText(MainActivity.this, "查询数值大于50的次数为："+ info, Toast.LENGTH_SHORT).show();
                           }
                       });

                   }

                   @Override
                   public void onError(Exception e) {
                       e.printStackTrace();
                   }
               });
           }
       });
    }
}
