package com.example.sockettest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tencent.bugly.crashreport.CrashReport;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_accept = (Button) findViewById(R.id.btn_accept);
        Button btn_accept1 = (Button) findViewById(R.id.btn_accept1);
        Button useudp = (Button) findViewById(R.id.useudp);
        Button chat=findViewById(R.id.chat);
        btn_accept.setOnClickListener(this);
        btn_accept1.setOnClickListener(this);
        useudp.setOnClickListener(this);
        chat.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_accept:
//                CrashReport.testJavaCrash();
                //让程序报错
                ArrayList<String>list=new ArrayList<>();
                list.get(2);
                break;
            case R.id.btn_accept1:
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            acceptServer();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
            case R.id.chat:
                Intent intent=new Intent(this,ChatActivity.class);
                startActivity(intent);
                break;
            case R.id.useudp:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                useudp();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();

                break;
        }
    }

    private void useudp() throws IOException {
        /*
         * 向服务器端发送数据
         */
        // 1.定义服务器的地址、端口号、数据
        InetAddress address = InetAddress.getByName("192.168.3.196");
        int port = 12346;
        byte[] data = "用户名：admin;密码：123".getBytes();
        // 2.创建数据报，包含发送的数据信息
        DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
        // 3.创建DatagramSocket对象
        DatagramSocket socket = new DatagramSocket();
        // 4.向服务器端发送数据报
        socket.send(packet);

        /*
         * 接收服务器端响应的数据
         */
        // 1.创建数据报，用于接收服务器端响应的数据
        byte[] data2 = new byte[1024];
        DatagramPacket packet2 = new DatagramPacket(data2, data2.length);
        // 2.接收服务器响应的数据
        socket.receive(packet2);
        // 3.读取数据
        String reply = new String(data2, 0, packet2.getLength());
        System.out.println("我是客户端，服务器说：" + reply);
        // 4.关闭资源
        socket.close();
    }

    private void acceptServer() throws IOException {
        //1.创建客户端Socket，指定服务器地址和端口
        Socket socket = new Socket("192.168.3.196", 12345);
        //2.获取输出流，向服务器端发送信息
        OutputStream os = socket.getOutputStream();//字节输出流
        PrintWriter pw = new PrintWriter(os);//将输出流包装为打印流
        //获取客户端的IP地址
        InetAddress address = InetAddress.getLocalHost();
        String ip = address.getHostAddress();
        pw.write("客户端：~" + ip + "~ 接入服务器！！");
        pw.flush();
        socket.shutdownOutput();//关闭输出流
        socket.close();
    }
}
