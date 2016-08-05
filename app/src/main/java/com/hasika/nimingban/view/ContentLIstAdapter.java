package com.hasika.nimingban.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.hasika.nimingban.DAO.NMBCallBcak;
import com.hasika.nimingban.DAO.ParamsCreat;
import com.hasika.nimingban.DAO.ShowForum;
import com.hasika.nimingban.DAO.To_Deal;
import com.hasika.nimingban.R;
import com.hasika.nimingban.bean.Content;

import java.util.List;

/**
 * Created by HaSiKa on 2016/7/17.
 */
public class ContentLIstAdapter extends BaseAdapter implements AdapterView.OnItemClickListener{
    private int the_id;
    private int the_page;
    private List<Content> contents;
    private Context context;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            notifyDataSetChanged();
            super.handleMessage(msg);
        }
    };


    public ContentLIstAdapter(@NonNull Context context){
        this.context = context;
    }
    public ContentLIstAdapter NEWContent(int id){
        the_id = id;
        the_page = 1;
        new To_Deal(new ShowForum(), ParamsCreat.getShowf(the_id + "", the_page), new NMBCallBcak() {
            @Override
            public void run(Object... objects) {
                if(objects[0] == null) {
                    Toast.makeText(context, "网络出错了,没有获取到信息", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(contents != null)
                    contents.clear();
                contents = (List<Content>) objects[0];
                handler.sendEmptyMessage(0);

            }

        }).start();
        return this;

    }

    private ContentLIstAdapter LoadmoreContent(){
        the_page ++;
        new To_Deal(new ShowForum(), ParamsCreat.getShowf(the_id + "", the_page), new NMBCallBcak() {
            @Override
            public void run(Object... objects) {
                if(objects[0] == null){
                    Toast.makeText(context, "网络出错了,没有获取到信息", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Content> contentList = (List<Content>) objects[0];
                for (Content content : contentList)
                    contents.add(content);
                handler.sendEmptyMessage(0);
            }
        }).start();
        return this;
    }
    @Override
    public int getCount() {
        if(contents != null)
            return contents.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
            convertView = View.inflate(context, R.layout.content_list_item,null);
        ((Content_CardView)convertView).setContent(contents.get(position));
        if(position == contents.size()-1)
            LoadmoreContent();
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

    }
}
