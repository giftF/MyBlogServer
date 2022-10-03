package org.gift.Servlet.Dispose;

import org.gift.PersistantObject.entity.Deploy;
import org.gift.PersistantObject.mapper.DeployMapper;
import org.gift.Servlet.Dao.HTTPRequest;
import org.gift.Utiliy.LogUtil;
import org.gift.ValueObject.Common.ResSubmit;
import org.gift.ValueObject.Common.Dispose.ResQuery;
import org.gift.ValueObject.Common.Dispose.ResQuerylist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(value="/dispose")
public class DisposeInterface extends HTTPRequest {
    @Autowired
    private DeployMapper deployMapper;

    @RequestMapping(value="/querylist", method= RequestMethod.POST)
    public String querylist(HttpServletRequest request, @RequestBody Map<String, Object> body) throws Exception {
        String ip = request.getRemoteAddr();
        LogUtil.LOGGING.info("{} 调用/dispose/querylist接口，RequestBody:{}",ip,body);

        ResSubmit req;
        // 实例化data对象
        try{
            String classify = (String) body.get("classify");
            if(classify == null){
                req = new ResSubmit();
                req.setStatus(0);
                req.setMsg("classify不能为空");
                return response(req);
            }
            ResQuerylist resQuerylist = new ResQuerylist();
            resQuerylist.setClassifylist(deployMapper.selectByClassify(classify));
            if(resQuerylist.getClassifylist().size() == 0){
                req = new ResSubmit();
                req.setStatus(0);
                req.setMsg("没有查询到classify信息");
                return response(req);
            }
            resQuerylist.setMsg("查询成功");
            resQuerylist.setStatus(1);
            return response(resQuerylist);
        }catch (Exception e){
            req = new ResSubmit();
            req.setStatus(0);
            req.setMsg(e.toString());
            return response(req);
        }
    }


    @RequestMapping(value="/query", method= RequestMethod.POST)
    public String query(HttpServletRequest request, @RequestBody Map<String, Object> body) throws Exception {
        String ip = request.getRemoteAddr();
        LogUtil.LOGGING.info("{} 调用/dispose/query接口，RequestBody:{}",ip,body);

        ResSubmit req;
        ResQuery resQuery = new ResQuery();
        Deploy deploy = new Deploy();
        // 实例化data对象
        try{
            deploy.setId((Integer) body.get("id"));
            // 判断id是否为null
            if(deploy.getId() == null){
                // 走通过classify和key组合查询
                deploy.setClassify((String) body.get("classify"));
                deploy.setKey((String) body.get("key"));
                if(deploy.getClassify() == null || deploy.getKey() == null){
                    req = new ResSubmit();
                    req.setStatus(0);
                    req.setMsg("id和（classify或key）不能同时为空");
                    return response(req);
                }
            }
            resQuery.setClassify(deployMapper.selectByIDorKey(deploy));
            if(resQuery.getClassify() == null){
                req = new ResSubmit();
                req.setStatus(0);
                req.setMsg("错误的ID，没有查到匹配的信息");
                return response(req);
            }else{
                resQuery.setMsg("查询成功");
                resQuery.setStatus(1);
                return response(resQuery);
            }
        }catch (Exception e){
            e.printStackTrace();
            req = new ResSubmit();
            req.setStatus(0);
            req.setMsg(e.toString());
            return response(req);
        }
    }
}
