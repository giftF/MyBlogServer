package org.gift.Servlet.Message;


import org.gift.PersistantObject.entity.Blogs;
import org.gift.PersistantObject.entity.Leave;
import org.gift.PersistantObject.mapper.LeaveMapper;
import org.gift.Servlet.Dao.HTTPRequest;
import org.gift.Utiliy.LogUtil;
import org.gift.Utiliy.ManyUtil;
import org.gift.ValueObject.Common.ResSubmit;
import org.gift.ValueObject.Message.ResList;
import org.gift.ValueObject.Message.ResStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(value="/message")
public class MessageInterface extends HTTPRequest {
    @Autowired
    private LeaveMapper leaveMapper;

    @RequestMapping(value="/submit", method= RequestMethod.POST)
    public String submit(HttpServletRequest request, @RequestBody Map<String, Object> body) throws Exception {
        String ip = request.getRemoteAddr();
        LogUtil.LOGGING.info("{} 调用/message/submit接口，RequestBody:{}",ip,body);

        ResSubmit req = new ResSubmit();

        Leave leave = new Leave();
        leave.setIp(ip);
        leave.setMsg((String) body.get("value"));
        if(leave.getMsg() == null){
            LogUtil.LOGGING.info("留言内容为空，留言失败");
            req.setStatus(0);
            req.setMsg("留言内容不能为空");
            return response(req);
        }
        leaveMapper.add(leave);
        req.setMsg("提交成功");
        req.setStatus(1);
        return response(req);
    }


    @RequestMapping(value="/statistics", method= RequestMethod.POST)
    public String statistics(HttpServletRequest request, @RequestBody Map<String, Object> body) throws Exception {
        String ip = request.getRemoteAddr();
        LogUtil.LOGGING.info("{} 调用/message/statistics接口，RequestBody:{}",ip,body);

        ResStatistics resStatistics = leaveMapper.statistics();
        resStatistics.setMsg("查询成功");
        resStatistics.setStatus(1);
        return response(resStatistics);
    }


    @RequestMapping(value="/read", method= RequestMethod.POST)
    public String read(HttpServletRequest request, @RequestBody Map<String, Object> body) throws Exception {
        String ip = request.getRemoteAddr();
        LogUtil.LOGGING.info("{} 调用/message/read接口，RequestBody:{}",ip,body);

        Integer id = (Integer) body.get("id");
        leaveMapper.read(id);
        ResSubmit req = new ResSubmit();
        req.setStatus(1);
        req.setMsg("修改成功");
        return response(req);
    }


    @RequestMapping(value="/list", method= RequestMethod.POST)
    public String getlist(HttpServletRequest request, @RequestBody Map<String, Object> body) throws Exception {
        String ip = request.getRemoteAddr();
        LogUtil.LOGGING.info("{} 调用/message/list接口，RequestBody:{}",ip,body);

        try {
            Integer rownum = (Integer) body.get("rownum");
            Integer page = (Integer) body.get("page");
            if(rownum == null){
                rownum = 10;
            }
            if(page == null){
                page = 1;
            }
            ResList req = new ResList();
            req.setMessagelist(leaveMapper.getList(rownum, (page-1)*rownum));
            req.setMsg("查询成功");
            req.setStatus(1);
            return response(req);
        }catch (Exception e){
            ResSubmit reqerror = new ResSubmit();
            reqerror.setMsg(e.toString());
            reqerror.setStatus(0);
            return response(reqerror);
        }
    }
}
