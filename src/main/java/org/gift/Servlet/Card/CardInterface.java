package org.gift.Servlet.Card;


import org.gift.PersistantObject.entity.Card;
import org.gift.PersistantObject.entity.Deploy;
import org.gift.PersistantObject.mapper.CardMapper;
import org.gift.Servlet.Dao.HTTPRequest;
import org.gift.Utiliy.LogUtil;
import org.gift.ValueObject.Common.Dispose.ResQuery;
import org.gift.ValueObject.Common.ResSubmit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/cards")
public class CardInterface extends HTTPRequest {

    @Autowired
    private CardMapper cardMapper;

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String addcard(HttpServletRequest request, @RequestBody Map<String, Object> body) throws Exception {
        String ip = request.getRemoteAddr();
        LogUtil.LOGGING.info("{} 调用/cards/add，RequestBody:{}",ip,body);

        ResSubmit req;
        req = new ResSubmit();

        Card card = new Card();
        // 实例化data对象
        try{
            card.setDc(body.get("dc").toString());
            card.setZy(body.get("zy").toString());
            card.setFy(body.get("fy").toString());
            cardMapper.save(card);
            req.setStatus(0);
            req.setMsg("保存成功");
        }catch(DuplicateKeyException e){
            e.printStackTrace();
            req.setStatus(1);
            req.setMsg("这个单词已经保存过了！");
        }
        catch (Exception e){
            e.printStackTrace();
            req.setStatus(1);
            req.setMsg(e.toString());
        }
        return response(req);
    }

    @RequestMapping(value="/select", method= RequestMethod.POST)
    public String selectcard(HttpServletRequest request, @RequestBody Map<String, Object> body) throws Exception {
        String ip = request.getRemoteAddr();
        LogUtil.LOGGING.info("{} 调用/cards/select",ip);

        List<Card> cardList = cardMapper.getList();

        List<Card> resdata = this.random(cardList, 10);

        return response(resdata);
    }

    @RequestMapping(value="/update", method= RequestMethod.POST)
    public String updatecard(HttpServletRequest request, @RequestBody Map<String, Object> body) throws Exception {
        String ip = request.getRemoteAddr();
        LogUtil.LOGGING.info("{} 调用/cards/update，RequestBody:{}",ip,body);

        ResSubmit req;
        req = new ResSubmit();

        try{
            String id = body.get("id").toString();
            Integer correct = (Integer) body.get("correct");
            Integer error = (Integer) body.get("error");
            cardMapper.update(id, correct, error);
            req.setStatus(0);
            req.setMsg("保存成功");
        }catch (Exception e){
            e.printStackTrace();
            req.setStatus(1);
            req.setMsg(e.toString());
        }

        return response(req);
    }

    private List<Card> random(List<Card> list, int number){
        List<Card> thiscardlist = new ArrayList<Card>();
        for(int i = 0; i < number; i++){
            thiscardlist.add(list.get((int)(Math.random() * list.size())));
        }
        return thiscardlist;
    }


}
