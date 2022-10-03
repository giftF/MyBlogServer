package org.gift.Servlet.Dao;

import org.gift.ValueObject.Response.Response;
import org.json.JSONObject;

public class HTTPRequest implements HTTPRequestInterface{
    public String response(Object data){
        Response res = new Response();
        res.setRecode("00000");
        res.setRemsg("请求成功");
        res.setData(data);
        JSONObject object = new JSONObject(res);
        String json = object.toString();
        return json;
    }
}
