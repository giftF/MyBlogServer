package org.gift.Servlet.Blog;

import org.gift.PersistantObject.entity.Blogs;
import org.gift.PersistantObject.mapper.BlogsMapper;
import org.gift.Servlet.Dao.HTTPRequest;
import org.gift.Utiliy.*;
import org.gift.ValueObject.Blog.ResContent;
import org.gift.ValueObject.Response.ResList;
import org.gift.ValueObject.Common.ResSubmit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(value="/blog")
public class BlogInterface extends HTTPRequest {

    @Autowired
    private BlogsMapper blogsMapper;

    @Autowired
    private RedisCacheService redisCacheService;

    /*
    * HttpServletRequest  获取请求头
    * @RequestParam 获取data参数
    * @RequestBody  获取json参数
    * */
    @RequestMapping(value="/submit", method=RequestMethod.POST)
    public String submit(HttpServletRequest request, @RequestBody Map<String, Object> body) throws Exception {
        String ip = request.getRemoteAddr();
        LogUtil.LOGGING.info("{} 调用/blog/submit接口，RequestBody:{}",ip,body);
        // 实例化data对象
        ResSubmit req = new ResSubmit();
        Blogs blogs = new Blogs();
        try {
            blogs.setId((Integer) body.get("id"));
            blogs.setTitle((String) body.get("title"));
            if (!ManyUtil.LengthComparison(blogs.getTitle(), 0, 50)) {
                LogUtil.LOGGING.info("title长度不能超过50个字符");
                req.setMsg("博客标题不能超过50个字符");
                req.setStatus(0);
                return response(req);
            }
            blogs.setTop((Integer) body.get("top"));
            blogs.setStatus(1);
            blogs.setClassify((Integer) body.get("classify"));
            blogs.setKeyword((String) body.get("keyword"));
            if (!ManyUtil.LengthComparison(blogs.getKeyword(), 0, 50)) {
                LogUtil.LOGGING.info("keyword长度不能超过50个字符");
                req.setMsg("关键词不能超过50个字符");
                req.setStatus(0);
                return response(req);
            }
            blogs.setValue((String) body.get("value"));
        }catch (Exception e){
            LogUtil.LOGGING.error(e.toString());
            req.setMsg(e.toString());
            req.setStatus(0);
            return response(req);
        }
        if(null == blogs.getId()){
            blogsMapper.add(blogs);
            LogUtil.LOGGING.info("新增成功");
            req.setMsg("新增成功");
        }else {
            blogsMapper.update(blogs);
            LogUtil.LOGGING.info("修改成功");
            req.setMsg("修改成功");
        }
        req.setStatus(1);
        return response(req);
    }


    @RequestMapping(value="/delete", method=RequestMethod.POST)
    public String delete(HttpServletRequest request, @RequestBody Map<String, Object> body) throws Exception {
        String ip = request.getRemoteAddr();
        LogUtil.LOGGING.info("{} 调用/blog/delete接口，RequestBody:{}",ip,body);
        // 实例化data对象
        ResSubmit req = new ResSubmit();
        Blogs blogs = new Blogs();
        try {
            blogs.setId((Integer) body.get("id"));
            blogs.setStatus(0);
        }catch (Exception e){
            LogUtil.LOGGING.error(e.toString());
            req.setMsg(e.toString());
            req.setStatus(0);
            return response(req);
        }
        if(null == blogs.getId()){
            LogUtil.LOGGING.info("删除接口博客ID不能为空");
            req.setMsg("删除接口博客ID不能为空");
            req.setStatus(0);
            return response(req);
        }else {
            blogsMapper.update(blogs);
            LogUtil.LOGGING.info("删除博客" + blogs.getId() + "成功");
            req.setMsg("删除博客" + blogs.getId() + "成功");
            req.setStatus(1);
            return response(req);
        }
    }


    @RequestMapping(value="/content", method=RequestMethod.POST)
    public String content(HttpServletRequest request, @RequestBody Map<String, Object> body) throws Exception {
        String ip = request.getRemoteAddr();
        LogUtil.LOGGING.info("{} 调用/blog/content接口，RequestBody:{}",ip,body);
        // 实例化data对象
        ResSubmit req;
        Blogs blogs = new Blogs();
        try {
            blogs.setId((Integer) body.get("id"));
            blogs.setStatus(0);
        }catch (Exception e){
            req  = new ResSubmit();
            LogUtil.LOGGING.error(e.toString());
            req.setMsg(e.toString());
            req.setStatus(0);
            return response(req);
        }
        if(null == blogs.getId()){
            req  = new ResSubmit();
            LogUtil.LOGGING.info("查询接口博客ID不能为空");
            req.setMsg("查询接口博客ID不能为空");
            req.setStatus(0);
            return response(req);
        }else {
            ResContent reqblog = blogsMapper.content(blogs);
            if(reqblog == null){
                req  = new ResSubmit();
                LogUtil.LOGGING.info("没有查询到对应的博客信息");
                req.setMsg("没有查询到对应的博客信息");
                req.setStatus(0);
                return response(req);
            }
            LogUtil.LOGGING.info("查询成功：" + reqblog.toString());
            reqblog.setMsg("查询成功");
            reqblog.setStatus(1);
            return response(reqblog);
        }
    }


    @RequestMapping(value="/list", method=RequestMethod.POST)
    public String getlist(HttpServletRequest request, @RequestBody Map<String, Object> body) throws Exception {
        String ip = request.getRemoteAddr();
        LogUtil.LOGGING.info("{} 调用/blog/list接口，RequestBody:{}",ip,body);
        ResSubmit req;
        // 获取查询条件
        try {
            Integer top = (Integer) body.get("top");
            Integer sort = (Integer) body.get("sort");
            Integer rowcount = (Integer) body.get("rowcount");
            Integer page = (Integer) body.get("page");
            if(top == null){
                top = 1;
            }
            if(sort == null){
                sort = 0;
            }
            if(rowcount == null){
                rowcount = 10;
            }
            if(page == null){
                page = 1;
            }
            ResList resList = new ResList();
            resList.setList(blogsMapper.getList(top, sort, rowcount, (page-1)*rowcount));
            if(resList.getList().size() == 0){
                resList.setMsg("很抱歉，站长还未发表任何博客，去催催他吧");
                resList.setStatus(1);
                return response(resList);
            }else{
                resList.setStatus(1);
                resList.setMsg("查询成功");
                return response(resList);
            }
        }catch (Exception e){
            e.printStackTrace();
            req  = new ResSubmit();
            LogUtil.LOGGING.error(e.toString());
            req.setMsg(e.toString());
            req.setStatus(0);
            return response(req);
        }
    }
}
