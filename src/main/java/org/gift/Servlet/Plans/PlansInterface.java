package org.gift.Servlet.Plans;

import org.gift.PersistantObject.entity.Leave;
import org.gift.PersistantObject.mapper.PlansMapper;
import org.gift.Servlet.Dao.HTTPRequest;
import org.gift.Utiliy.LogUtil;
import org.gift.ValueObject.Common.ResSubmit;
import org.gift.ValueObject.Response.ResList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(value="/Plans")
public class PlansInterface extends HTTPRequest {
    @Autowired
    private PlansMapper plansMapper;

    @RequestMapping(value="/save", method= RequestMethod.POST)
    public String save(HttpServletRequest request, @RequestBody Map<String, Object> body) throws Exception {
        String ip = request.getRemoteAddr();
        LogUtil.LOGGING.info("{} 调用/Plans/save接口，RequestBody:{}",ip,body);
        ResSubmit req = new ResSubmit();
        String message = body.get("message").toString();

        try{
            plansMapper.save(message);
            req.setMsg("新增计划成功");
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.LOGGING.error(e.toString());
            req.setMsg(e.toString());
            req.setStatus(0);
        }

        return response(req);
    }

    @RequestMapping(value="/selectAllPlans", method= RequestMethod.POST)
    public String selectAll(HttpServletRequest request, @RequestBody Map<String, Object> body) throws Exception {
        String ip = request.getRemoteAddr();
        LogUtil.LOGGING.info("{} 调用/Plans/selectAllPlans接口，RequestBody:{}",ip,body);
        ResSubmit req = new ResSubmit();
        ResList resList = new ResList();

        try{
            resList.setList(plansMapper.selectAllPlans());
            resList.setStatus(1);
            resList.setMsg("查询成功");
            return response(resList);
        }catch (Exception e){
            e.printStackTrace();
            req  = new ResSubmit();
            LogUtil.LOGGING.error(e.toString());
            req.setMsg(e.toString());
            req.setStatus(0);
            return response(req);
        }
    }

    @RequestMapping(value="/update", method= RequestMethod.POST)
    public String update(HttpServletRequest request, @RequestBody Map<String, Object> body) throws Exception {
        String ip = request.getRemoteAddr();
        LogUtil.LOGGING.info("{} 调用/Plans/update接口，RequestBody:{}",ip,body);
        ResSubmit req = new ResSubmit();
        int id = Integer.parseInt(body.get("id").toString());
        int tp = Integer.parseInt(body.get("tp").toString());

        try{
            plansMapper.updatePlan(id, tp);
            req.setMsg("计划状态变更成功");
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.LOGGING.error(e.toString());
            req.setMsg(e.toString());
            req.setStatus(0);
        }

        return response(req);
    }
}
