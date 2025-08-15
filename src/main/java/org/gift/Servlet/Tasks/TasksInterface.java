package org.gift.Servlet.Tasks;

import org.gift.PersistantObject.entity.Card;
import org.gift.PersistantObject.entity.Tasks;
import org.gift.PersistantObject.mapper.TasksMapper;
import org.gift.Utiliy.LogUtil;
import org.gift.ValueObject.Common.ResSubmit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.gift.Servlet.Dao.HTTPRequest;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/tasks")
public class TasksInterface extends HTTPRequest {

    @Autowired
    private TasksMapper tasksMapper;

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String addtask(HttpServletRequest request, @RequestBody Map<String, Object> body) throws Exception {
        String ip = request.getRemoteAddr();
        LogUtil.LOGGING.info("{} 调用/tasks/add，RequestBody:{}",ip,body);

        ResSubmit req;
        req = new ResSubmit();

        Tasks tasks = new Tasks();
        // 实例化data对象
        try{
            tasks.setUser_id(1);
            tasks.setTitle(body.get("title").toString());
            tasks.setDeadline((Date) body.get("deadline"));
            tasksMapper.save(tasks);
            req.setStatus(0);
            req.setMsg("保存成功");
        }
        catch (Exception e){
            e.printStackTrace();
            req.setStatus(1);
            req.setMsg(e.toString());
        }
        return response(req);
    }

    @RequestMapping(value="/select", method= RequestMethod.POST)
    public String selecttask(HttpServletRequest request, @RequestBody Map<String, Object> body) throws Exception {
        String ip = request.getRemoteAddr();
        LogUtil.LOGGING.info("{} 调用/tasks/select",ip);

        List<Tasks> cardList = tasksMapper.getList();

        return response(cardList);
    }

    @RequestMapping(value="/update", method= RequestMethod.POST)
    public String updatetask(HttpServletRequest request, @RequestBody Map<String, Object> body) throws Exception {
        String ip = request.getRemoteAddr();
        LogUtil.LOGGING.info("{} 调用/task/update，RequestBody:{}",ip,body);

        ResSubmit req;
        req = new ResSubmit();

        try{
            Integer id = Integer.valueOf(body.get("id").toString());
            String status = body.get("status").toString();
            Integer progress = Integer.valueOf(body.get("progress").toString());
            tasksMapper.update(id, status, progress);
            req.setStatus(0);
            req.setMsg("保存成功");
        }catch (Exception e){
            e.printStackTrace();
            req.setStatus(1);
            req.setMsg(e.toString());
        }

        return response(req);
    }
}
