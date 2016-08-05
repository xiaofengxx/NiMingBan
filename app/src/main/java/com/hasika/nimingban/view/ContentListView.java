package com.hasika.nimingban.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by HaSiKa on 2016/7/17.
 */
public class ContentListView extends ListView{
    public ContentListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ContentListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        setAdapter(new ContentLIstAdapter(context));
        setOnItemClickListener((OnItemClickListener) getAdapter());
    }
    public ContentListView NEWContent(int id){
        ContentLIstAdapter contentLIstAdapter = (ContentLIstAdapter) getAdapter();
        if(contentLIstAdapter != null)
            contentLIstAdapter.NEWContent(id);
        return this;
    }
}
