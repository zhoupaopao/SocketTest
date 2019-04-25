package com.example.sockettest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Lenovo on 2019/4/25.
 */

public class ChatActivity extends Activity implements Runnable{
    //定义相关变量,完成初始化
    private TextView txtshow;
    private EditText editsend;
    private Button btnsend;
    private static final String HOST = "192.168.3.196";
    private static final int PORT = 12345;
    private Socket socket = null;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private String content = "";
    private StringBuilder sb = null;
    private int num=0;

    //定义一个handler对象,用来刷新界面
    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                sb.append(content);
                txtshow.setText(sb.toString());
            }
        }

        ;
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        sb = new StringBuilder();
        txtshow = (TextView) findViewById(R.id.txtshow);
        editsend = (EditText) findViewById(R.id.editsend);
        btnsend = (Button) findViewById(R.id.btnsend);

        //当程序一开始运行的时候就实例化Socket对象,与服务端进行连接,获取输入输出流
        //因为4.0以后不能再主线程中进行网络操作,所以需要另外开辟一个线程
        new Thread() {

            public void run() {
                try {
                    socket = new Socket(HOST, PORT);
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                    out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                            socket.getOutputStream())), true);
                    Log.i("runnnnn: ", "111");
                    while (true) {
                        if (socket.isConnected()) {
                            if (!socket.isInputShutdown()) {
                                if ((content = in.readLine()) != null) {
                                    content += "\n";
                                    Log.i("runnnnn: ", content);
                                    handler.sendEmptyMessage(0x123);
                                    num++;
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        //为发送按钮设置点击事件
        btnsend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String msg = editsend.getText().toString();
                if (socket.isConnected()) {
                    if (!socket.isOutputShutdown()) {
                        new Thread() {
                            @Override
                            public void run() {
                                Log.i("run: ", msg);
                                out.println(msg);
                            }
                        }.start();

                    }
                }
            }
        });
        new Thread(ChatActivity.this).start();
    }
    //重写run方法,在该方法中输入流的读取
    @Override
    public void run() {
        try {
//            while (true) {
//                if (socket.isConnected()) {
//                    if (!socket.isInputShutdown()) {
//                        if ((content = in.readLine()) != null) {
//                            content += "\n";
//                            Log.i("run: ", content);
//                            handler.sendEmptyMessage(0x123);
//                            num++;
//                        }
//                    }
//                }
//            }
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }
}
