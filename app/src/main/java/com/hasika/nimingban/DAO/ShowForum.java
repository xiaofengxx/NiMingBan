package com.hasika.nimingban.DAO;


import com.hasika.nimingban.bean.Content;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HaSiKa on 2016/6/10.
 */
public class ShowForum extends Need_Deal{

    public String get_the_Get_Tag() {
        return "/Api/showf";
    }
    public void deal() {
        Content content = null;
        List<Content> contents = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(get_Json());
            for(int i = 0; i < jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                content = Content.getThread(jsonObject);
                contents.add(content);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            getCallBcak().run(contents);
        }
    }
}
