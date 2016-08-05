package com.hasika.nimingban.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.hasika.nimingban.R;
import com.hasika.nimingban.bean.Content;
import com.hasika.nimingban.utils.Netget;

import java.util.List;

/**
 * Created by HaSiKa on 2016/7/17.
 */
public class Content_CardView extends CardView{
    Content content;
    TextView userid;
    TextView id;
    TextView now;
    TextView replyCount;
    TextView the_content;
    TextSwitcher replys;
    NMBImageView img;
    long pretime,len;
    Context context;

    int index;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    showReplys();
                    userid.setText(content.getUserid());
                    id.setText(content.getId());
                    now.setText(content.getNow().substring(5));
                    replyCount.setText("回复:"+content.getReplyCount());
                    break;
                case 1:
                    if(System.currentTimeMillis() >= pretime +len)
                        showReplys();
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };
    public Content_CardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public Content_CardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    private void showReplys(){
        if(replys == null)
            return;
        if(content.getReplys() == null)
            return;
        List<Content> contents = content.getReplys();
        if(contents.size() == 0)
            return;
        index ++;
        index %= contents.size();
        replys.setText(contents.get(index).getContent());
        len = (long)(5000-Math.random()*2000);
        pretime = System.currentTimeMillis();
        handler.sendEmptyMessageDelayed(1,len);
    }


    public void init(){
        if(userid == null)
            userid = (TextView) findViewById(R.id.content_userid);
        if(id == null)
            id = (TextView) findViewById(R.id.content_id);
        if(now == null)
            now = (TextView) findViewById(R.id.content_now);
        if(replyCount == null)
            replyCount = (TextView) findViewById(R.id.content_replyCount);
        if(the_content == null)
            the_content = (TextView) findViewById(R.id.content_content);
        if(replys == null){
            replys = (TextSwitcher) findViewById(R.id.content_replys);
            replys.setFactory(new ViewSwitcher.ViewFactory() {
                @Override
                public View makeView() {
                    TextView tv = new TextView(context);
                    tv.setTextColor(context.getResources().getColor(R.color.gr));
                    tv.setMaxLines(1);
                    return tv;
                }
            });
        }
        if(img == null)
            img = (NMBImageView) findViewById(R.id.content_img_);
    }
    public Content_CardView setContent(Content content){
        init();
        this.content = content;
        index = 0;
        the_content.setText(content.getContent());
        if("".equals(content.getImg().trim()))
            img.setVisibility(GONE);
        else {
            img.setVisibility(VISIBLE);
            img.setUri(Netget.getImg_url_thumb(content.getImg(), content.getExt()));
        }
        handler.sendEmptyMessage(0);
        return this;
    }
}
