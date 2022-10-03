package org.gift.Servlet.PhotoAlbum;


import org.gift.PersistantObject.entity.Photo;
import org.gift.PersistantObject.entity.PhotoAlbum;
import org.gift.PersistantObject.mapper.PhotoAlbumMapper;
import org.gift.PersistantObject.mapper.PhotoMapper;
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
@RequestMapping(value="/photoalbum")
public class PhotoAlbumInterface extends HTTPRequest {

    @Autowired
    private PhotoAlbumMapper photoAlbumMapper;

    @Autowired
    private PhotoMapper photoMapper;

    @RequestMapping(value="/submit", method= RequestMethod.POST)
    public String submit(HttpServletRequest request, @RequestBody Map<String, Object> body) throws Exception {
        String ip = request.getRemoteAddr();
        LogUtil.LOGGING.info("{} 调用/photoalbum/submit接口，RequestBody:{}",ip,body);

        ResSubmit req = new ResSubmit();

        PhotoAlbum photoAlbum = new PhotoAlbum();
        try {
            photoAlbum.setId((Integer) body.get("id"));
            photoAlbum.setTitle((String) body.get("title"));
            photoAlbum.setCommit((String) body.get("commit"));
            photoAlbum.setTop((Integer) body.get("top"));
            if(photoAlbum.getId() == null){
                photoAlbumMapper.add(photoAlbum);
                LogUtil.LOGGING.info("新增相册成功");
                req.setMsg("新增相册成功");
            }else{
                photoAlbumMapper.update(photoAlbum);
                LogUtil.LOGGING.info("修改相册成功");
                req.setMsg("修改相册成功");
            }
            req.setStatus(1);
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.LOGGING.error(e.toString());
            req.setMsg(e.toString());
            req.setStatus(0);
        }
        return response(req);
    }


    @RequestMapping(value="/delete", method= RequestMethod.POST)
    public String delete(HttpServletRequest request, @RequestBody Map<String, Object> body) throws Exception {
        String ip = request.getRemoteAddr();
        LogUtil.LOGGING.info("{} 调用/photoalbum/delete接口，RequestBody:{}",ip,body);

        ResSubmit req = new ResSubmit();
        try {
            Integer id = (Integer) body.get("id");
            photoAlbumMapper.delete(id);
            LogUtil.LOGGING.info("删除成功");
            req.setMsg("删除成功");
            req.setStatus(1);
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.LOGGING.error(e.toString());
            req.setMsg(e.toString());
            req.setStatus(0);
        }
        return response(req);
    }


    @RequestMapping(value="/frontcover", method= RequestMethod.POST)
    public String frontcover(HttpServletRequest request, @RequestBody Map<String, Object> body) throws Exception {
        String ip = request.getRemoteAddr();
        LogUtil.LOGGING.info("{} 调用/photoalbum/frontcover接口，RequestBody:{}",ip,body);

        ResSubmit req = new ResSubmit();
        try {
            Integer id = (Integer) body.get("id");
            Integer photoid = (Integer) body.get("photoid");
            Photo photo = photoMapper.selectByID(photoid);
            if(photo.getPhotoAlbum_id() == id){
                photoAlbumMapper.frontcover(id, photoid);
                LogUtil.LOGGING.info("设置成功");
                req.setMsg("设置成功");
                req.setStatus(1);
            }else {
                LogUtil.LOGGING.info("要设置的照片不属于该相册");
                req.setMsg("要设置的照片不属于该相册");
                req.setStatus(0);
            }
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.LOGGING.error(e.toString());
            req.setMsg(e.toString());
            req.setStatus(0);
        }
        return response(req);
    }


    @RequestMapping(value="/list", method=RequestMethod.POST)
    public String getlist(HttpServletRequest request, @RequestBody Map<String, Object> body) throws Exception {
        String ip = request.getRemoteAddr();
        LogUtil.LOGGING.info("{} 调用/photoalbum/list接口，RequestBody:{}",ip,body);
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
            resList.setList(photoAlbumMapper.getList(top, sort, rowcount, (page-1)*rowcount));
            if(resList.getList().size() == 0){
                resList.setMsg("很抱歉，没有查询到任何相册");
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
