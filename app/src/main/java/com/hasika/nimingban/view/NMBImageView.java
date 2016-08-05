package com.hasika.nimingban.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.hasika.nimingban.R;
import com.hasika.nimingban.utils.DownLoadIMG;
import com.hasika.nimingban.utils.MD5;

import java.io.File;

/**
 * Created by HaSiKa on 2016/7/16.
 */
public class NMBImageView extends ImageView{
    // what
    static final int SUCCES = 1;
    static final int FAILED = 2;
    static final int PROGRESS= 3;
    String uri;
    boolean CLICKABLE;
    boolean F5;
    String path;
    String cache_dir;
    boolean usecache;

    final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case PROGRESS:

                    break;
                case SUCCES:
                    if(msg.obj != null) {
                        Bitmap bitmap = (Bitmap) msg.obj;
                        setImageBitmap(bitmap);
                    }
                    break;
                case FAILED:
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };
    public NMBImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public NMBImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(@NonNull Context context, @NonNull AttributeSet attributeSet){
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.NMBImageView);
        uri = typedArray.getString(R.styleable.NMBImageView_url);
        usecache = typedArray.getBoolean(R.styleable.NMBImageView_usecache,true);
        F5 = typedArray.getBoolean(R.styleable.NMBImageView_F5,false);
        path = context.getCacheDir().getAbsolutePath();
        if(uri!= null && !"".equals(uri.trim()))
            NMBImageView_url();
        if(F5)
            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    NMBImageView_url();
                }
            });
    }
    public void setUri(String uri){
        this.uri = uri;
        new Thread(new Runnable() {
            @Override
            public void run() {
                NMBImageView_url();
            }
        }).start();

    }
    private void NMBImageView_url(){
        CLICKABLE = false;
        if("".equals(uri.trim()))
            return;
        try {
            String filename= MD5.GetMD5Code(uri);
            File file = new File(path + "/" + filename);
            cache_dir = file.getAbsolutePath();
            if(usecache&&file.exists()){
                Message message = Message.obtain();
                message.what = SUCCES;
                message.obj = decodeBitmap(file.getAbsolutePath());
                handler.sendMessage(message);
            }
            else {
                    new DownLoadIMG(uri, file.getAbsolutePath(), new DownLoadIMG.Callback() {
                    @Override
                    public void run(Object... objects) {
                        Message message = Message.obtain();
                        message.what = (int) objects[0];
                        switch (message.what) {
                            case PROGRESS:
                                message.obj = objects[1];
                                break;
                            case SUCCES:
                                System.out.println("加载完成");
                                if(cache_dir == (String) objects[1])
                                    message.obj = decodeBitmap((String) objects[1]);
                                else
                                    System.out.println("不匹配");
                                break;
                            case FAILED:
                                break;
                            default:
                                break;
                        }
                        handler.sendMessage(message);
                    }
                }).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Message message = Message.obtain();
            message.what = FAILED;
            handler.sendMessage(message);
        }finally {
            CLICKABLE = true;
        }
    }

    private Bitmap decodeBitmap(String path){
        BitmapFactory.Options op = new BitmapFactory.Options();
        op.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, op);
        int h = 50 , w = 50;
        if(getHeight() != 0 )
            h = getHeight();
        if(getWidth() != 0)
            w = getWidth();
        int wRatio = (int)Math.ceil(op.outWidth/h);
        int hRatio = (int)Math.ceil(op.outHeight/w);
        if(getVisibility() == GONE)
            return null;
        if(wRatio > 1 && hRatio > 1){
            if(wRatio > hRatio){
                op.inSampleSize = wRatio;
            }else{
                op.inSampleSize = hRatio;
            }
        }
        op.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, op);
    }

}
