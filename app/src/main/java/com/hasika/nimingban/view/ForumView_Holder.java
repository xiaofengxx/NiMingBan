package com.hasika.nimingban.view;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hasika.nimingban.MainActivity;
import com.hasika.nimingban.R;
import com.hasika.nimingban.bean.The_Type;


/**
 * Created by HaSiKa on 2016/6/16.
 */
public class ForumView_Holder implements The_Type {
    private int the_type;
    private SparseArray<View> sparseArray;
    private View mRootview;
    public ForumView_Holder(@NonNull Context context, @LayoutRes int layoutResid){
        this(context,layoutResid,null);
    }
    public ForumView_Holder(@NonNull Context context, @LayoutRes int layoutResid, ViewGroup rootview){
        mRootview = View.inflate(context,layoutResid,rootview);
        mRootview.setTag(this);
        sparseArray = new SparseArray<>();

    }
    public View getmRootview(){
        return mRootview;
    }
    public <V extends View> V getView(@IdRes int viewidRes){
        View view = sparseArray.get(viewidRes,null);
        if(view == null) {
            view = mRootview.findViewById(viewidRes);
            sparseArray.put(viewidRes,view);
        }
        return (V) view;
    }
    public ForumView_Holder changed(Context context){
        mRootview.setBackgroundColor(context.getResources().getColor(R.color.ggg));
        ((TextView)getView(R.id.fgourps_list_showname)).setTextColor(context.getResources().getColor(R.color.chooseed));
        return this;
    }

    public ForumView_Holder H2O2(Context context){
        mRootview.setBackgroundColor(context.getResources().getColor(R.color.cc));
        ((TextView)getView(R.id.fgourps_list_showname)).setTextColor((context.getResources().getColor(R.color.black)));
        return this;
    }
    public ForumView_Holder setBackgroundColor(@IdRes int viewResId, int color) {
        View view = getView(viewResId);
        view.setBackgroundColor(color);
        return this;
    }
    public ForumView_Holder setText(@IdRes int viewResId, CharSequence text) {
        TextView tv = getView(viewResId);
        tv.setText(text);
        return this;
    }

    public ForumView_Holder set_type(int type){
        this.the_type = type;
        return this;
    }
    @Override
    public int get_type() {
        return the_type;
    }

    @Override
    public String getName() {
        return null;
    }
}
