package org.gift.Servlet.Photo;

import org.gift.PersistantObject.entity.Photo;
import org.gift.PersistantObject.entity.PhotoAlbum;
import org.gift.PersistantObject.mapper.PhotoAlbumMapper;
import org.gift.PersistantObject.mapper.PhotoMapper;
import org.gift.Servlet.Dao.HTTPRequest;
import org.gift.Utiliy.LogUtil;
import org.gift.Utiliy.aspect.UploadFileServer;
import org.gift.ValueObject.Common.ResSubmit;
import org.gift.ValueObject.Response.ResList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(value="/photo")
public class PhotoInterface extends HTTPRequest {

    @Autowired
    private PhotoMapper photoMapper;

    @Autowired
    private PhotoAlbumMapper photoAlbumMapper;

    @Autowired
    private UploadFileServer uploadFileServer;

    private void sort(Integer photoalbum_id, Integer index, Integer tp){
        // 照片排序，新增照片时index之后的依次加1，删除照片时index之后的依次减1
        photoMapper.sort(photoalbum_id, index, tp);
    }

    @RequestMapping(value="/submit", method= RequestMethod.POST)
    public String submit(HttpServletRequest request, @RequestParam Map<String, Object> body, @RequestParam("file") MultipartFile zipFile) throws Exception {
        String ip = request.getRemoteAddr();
        LogUtil.LOGGING.info("{} 调用/photo/submit接口，RequestBody:{}",ip,body);

        ResSubmit req = new ResSubmit();
        Photo photo = new Photo();

        try {
            if(body.get("id") != null){
                photo.setId(Integer.valueOf((String) body.get("id")));
            }
            photo.setPhotoAlbum_id(Integer.valueOf((String) body.get("photoalbum_id")));
            photo.setTitle((String) body.get("title"));
            photo.setCommit((String) body.get("commit"));
            photo.setIndex(Integer.valueOf((String) body.get("index")));
            if(photo.getId() == null){
                sort(photo.getPhotoAlbum_id(), photo.getIndex(), 1);
                try {
                    photo.setUrl(uploadFileServer.uploadeFile(zipFile));
                    photoMapper.add(photo);
                    req.setMsg("新增照片成功");
                }catch (Exception e){
                    e.printStackTrace();
                    sort(photo.getPhotoAlbum_id(), photo.getIndex(), -1);
                    LogUtil.LOGGING.error(e.toString());
                    req.setMsg(e.toString());
                    req.setStatus(0);
                }
            }else {
                photoMapper.update(photo);
                req.setMsg("编辑照片成功");
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
        LogUtil.LOGGING.info("{} 调用/photo/delete接口，RequestBody:{}",ip,body);

        ResSubmit req = new ResSubmit();
        try {
            Integer id = (Integer) body.get("id");
            Photo photo = photoMapper.selectByID(id);
            photoMapper.delete(id);
            photoAlbumMapper.setfrontcover(id);
            photoMapper.sort(photo.getPhotoAlbum_id(), photo.getIndex(), -1);
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


    @RequestMapping(value="/list", method=RequestMethod.POST)
    public String getlist(HttpServletRequest request, @RequestBody Map<String, Object> body) throws Exception {
        String ip = request.getRemoteAddr();
        LogUtil.LOGGING.info("{} 调用/photo/list接口，RequestBody:{}",ip,body);
        ResSubmit req;
        // 获取查询条件
        try {
            Integer id = (Integer) body.get("id");
            Integer rowcount = (Integer) body.get("rowcount");
            Integer page = (Integer) body.get("page");

            if(rowcount == null){
                rowcount = 10;
            }
            if(page == null){
                page = 1;
            }
            ResList resList = new ResList();
            resList.setList(photoMapper.getList(id, rowcount, (page-1)*rowcount));
            if(resList.getList().size() == 0){
                resList.setMsg("很抱歉，没有查询到任何照片");
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
