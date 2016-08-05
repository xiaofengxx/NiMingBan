package com.hasika.nimingban.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by HaSiKa on 2016/7/17.
 */
public class FgroupListView extends ListView{
    public FgroupListView(Context context) {
        super(context);
        init(context);
    }
    public FgroupListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public FgroupListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context){
        setAdapter(new FgroupListAdapter(context));
        setOnItemClickListener((OnItemClickListener) getAdapter());
    }
}
