package com.hasika.nimingban.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.hasika.nimingban.DAO.GetForumList;
import com.hasika.nimingban.DAO.NMBCallBcak;
import com.hasika.nimingban.DAO.To_Deal;
import com.hasika.nimingban.MainActivity;
import com.hasika.nimingban.R;
import com.hasika.nimingban.bean.Fgroup;
import com.hasika.nimingban.bean.Forum;
import com.hasika.nimingban.bean.The_Type;
import java.util.List;

/**
 * Created by HaSiKa on 2016/7/17.
 */
public class FgroupListAdapter extends BaseAdapter implements AdapterView.OnItemClickListener{
    private View preview;
    private int preposition  = - 1;
    List<Fgroup> fgroups;
    Context context;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            notifyDataSetChanged();
            super.handleMessage(msg);
        }
    };
    public FgroupListAdapter(@NonNull Context context){
        preposition = 1;
        MainActivity.NEWContent(4);
        this.context = context;
        new To_Deal(new GetForumList(), new NMBCallBcak() {
            @Override
            public void run(Object... objects) {
                fgroups = (List<Fgroup>) objects[0];
                handler.sendEmptyMessage(1);
            }
        }).start();
    }
    @Override
    public int getCount() {
        if(fgroups == null)
            return 0;
        int count = 0;
        for(Fgroup f : fgroups){
            count++;
            count += f.getForums_list().size();
        }
        return count;
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
        if( convertView == preview )
            preview = null;
        The_Type the_type = getFromposition(position);
        if(convertView == null)
            convertView = new ForumView_Holder(context,R.layout.fgroup_list_item).set_type(the_type.get_type()).getmRootview();
        else
            ((ForumView_Holder) convertView.getTag()).H2O2(context);
        if(((ForumView_Holder)convertView.getTag()).get_type() == The_Type._Fgroup){
            convertView = new ForumView_Holder(context,R.layout.fgroup_list_item).set_type(the_type.get_type()).getmRootview();
        }
        if(the_type.get_type() == The_Type._Fgroup){
            ForumView_Holder forumView_holder = (ForumView_Holder) convertView.getTag();
            TextView textView = forumView_holder.getView(R.id.fgourps_list_showname);
            textView.setTextColor(context.getResources().getColor(R.color.colorAccent));
        }
        ForumView_Holder forumView_holder = (ForumView_Holder) convertView.getTag();
        forumView_holder.setText(R.id.fgourps_list_showname,the_type.getName());
        if(preposition == position) {
            forumView_holder.changed(context);
            preview = forumView_holder.getmRootview();
        }
        return convertView;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(!clickable(position))
            return;
        if(preview != null) {
            ForumView_Holder forumView_holder = (ForumView_Holder) preview.getTag();
            forumView_holder.H2O2(context);
        }
        ForumView_Holder forumView_holder = (ForumView_Holder) view.getTag();
        forumView_holder.changed(context);
        preview = view;
        preposition = position;
        MainActivity.TcloseDrawer();
        MainActivity.setToolbarTiltle(getFromposition(position).getName());
        String iii = ((Forum)getFromposition(position)).getId();
        MainActivity.NEWContent(Integer.valueOf(iii).intValue());
    }
    public boolean clickable(int position){
        The_Type the_type = getFromposition(position);
        return the_type.get_type() == The_Type._Forum;
    }
    private The_Type getFromposition(int position){
        The_Type the_type = null;
        for(int i = 0; position >= 0 ; i++){
            Fgroup fgroup = fgroups.get(i);
            if(position == 0) {
                the_type = fgroup;
                break;
            }
            position -- ;
            if(position < fgroup.getForums_list().size())
                the_type = fgroup.getForums_list().get(position);
            position -= fgroup.getForums_list().size();
        }
        return the_type;
    }
}
