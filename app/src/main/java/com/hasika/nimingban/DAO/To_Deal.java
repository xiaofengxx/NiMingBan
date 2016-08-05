package com.hasika.nimingban.DAO;


import com.hasika.nimingban.utils.Netget;

/**
 * Created by HaSiKa on 2016/6/10.
 */
public class To_Deal extends Thread {

    private Need_Deal need_deal;
    private String the_result;
    private String params;

    public To_Deal(Need_Deal need_deal, String params, NMBCallBcak callBcak){
        this.need_deal = need_deal;
        need_deal.setCallbcak(callBcak);
        this.params = params;
    }
    public To_Deal(Need_Deal need_deal,NMBCallBcak callBcak){
        this(need_deal,"",callBcak);
    }

    private String getURL(){
        return need_deal.get_the_Get_Tag()+params;
    }

    @Override
    public void run() {
        String the_head,the_content;
        the_result = Netget.HTTPSGET(getURL());
        if(the_result == null)
            return;
        int temp = the_result.indexOf(Netget.split_by);
        the_head = the_result.substring(0,temp);
        the_content = the_result.substring(temp+8,the_result.length());
        need_deal.set_byJson(the_content);
        need_deal.set_HeaderField(the_head);
        need_deal.deal();
    }
}
