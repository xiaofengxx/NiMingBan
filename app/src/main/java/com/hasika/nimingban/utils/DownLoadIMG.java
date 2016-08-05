package com.hasika.nimingban.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by HaSiKa on 2016/7/16.
 */
public class DownLoadIMG extends Thread{

    static final int SUCCES = 1;
    static final int FAILED = 2;
    static final int PROGRESS= 3;

    String path;
    String uri;
    boolean finish;
    Callback callback;
    public DownLoadIMG(String uri,String path){
        this(uri, path, new Callback() {
            @Override
            public void run(Object... objects) {

            }
        });
    }
    public DownLoadIMG(String uri,String path,Callback callback){
        this.uri = uri;
        this.callback = callback;
        this.path = path;
        finish = true;
    }
    public interface Callback{
        void run(Object... objects);
    }
    @Override
    public void run(){
        InputStream inputStream = null;
        HttpURLConnection conn = null;
        FileOutputStream fileOutputStream = null;
        try {
            URL url = new URL(uri);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.connect();
            String temp;
            temp = conn.getHeaderField("Content-Length");
            int contentlength = -1;
            if(temp != null)
                contentlength = Integer.valueOf(temp).intValue();
            int count = 0;
            inputStream = conn.getInputStream();
            File file = new File(path);
            if(!file.exists())
                file.createNewFile();
            fileOutputStream = new FileOutputStream(file);
            byte[] b = new byte[1024];
            int len;
            while((len = inputStream.read(b,0,b.length))!= -1) {
                fileOutputStream.write(b, 0, len);
                count += len;
                callback.run(PROGRESS,(count*1.0f)/contentlength);
            }
            callback.run(SUCCES,path);
        } catch (Exception e) {
            e.printStackTrace();
            finish = false;
        } finally {
            if(!finish)
                callback.run(FAILED);
            if(inputStream != null)
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if(fileOutputStream != null)
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

    }
}
