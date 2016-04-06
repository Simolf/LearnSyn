/*
AsyncTask 异步操作类,轻量级
doInBackgoround不能进行UI操作或者与UI交互
对UI的操作都在接口函数中进行
 */
package com.example.cxx.learnsyn;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text= (TextView) findViewById(R.id.textView);
        findViewById(R.id.read).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReadURL("http://www.baidu.com");
            }
        });
    }
    public void ReadURL(String url){
        new AsyncTask<String, Float, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    URL url = new URL(params[0]);
                    try {
                        URLConnection connection = url.openConnection();
                        //网页内容的全部长度，用于onProgressUpdate接口函数
                        //long total = connection.getContentLength();
                        InputStream in = connection.getInputStream();
                        InputStreamReader reader = new InputStreamReader(in);
                        BufferedReader br = new BufferedReader(reader);
                        String line;
                        StringBuilder builder = new StringBuilder();
                        while((line=br.readLine())!=null){
                            builder.append(line);
                            //获取当前进度，传递给onProgressUpdate（）函数
                         //  publishProgress((float)builder.toString().length()/total);
                        }
                        //字符读取完毕后将流逐个关闭，后序
                        br.close();
                        in.close();
                        return builder.toString();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
            }

            @Override
            protected void onCancelled(String s) {
                super.onCancelled(s);
            }

            @Override
            //参数s为doInBackground（）的返回值，result
            protected void onPostExecute(String s) {
                text.setText(s);
                super.onPostExecute(s);
            }

            @Override

            //开始读取网页内容之前调用
            //使用Toast提示消息
            protected void onPreExecute() {
                Toast.makeText(MainActivity.this,"开始读取",Toast.LENGTH_SHORT).show();
                super.onPreExecute();
            }

            @Override
            //执行任务的过程中，对外发布执行任务的进度，读取进度
            //参数与定义AsyncTask时第二个参数一致
            protected void onProgressUpdate(Float... values) {
                //System.out.println(values[0]);
                super.onProgressUpdate(values);
            }
        }.execute(url);
    }
}
