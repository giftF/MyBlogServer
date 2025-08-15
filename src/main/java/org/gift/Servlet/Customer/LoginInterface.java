package org.gift.Servlet.Customer;

import eu.bitwalker.useragentutils.UserAgent;
import org.gift.PersistantObject.entity.Login;
import org.gift.PersistantObject.entity.LoginRecord;
import org.gift.PersistantObject.entity.Users;
import org.gift.PersistantObject.mapper.LoginMapper;
import org.gift.PersistantObject.mapper.LoginRecordMapper;
import org.gift.PersistantObject.mapper.UsersMapper;
import org.gift.Servlet.Dao.HTTPRequest;
import org.gift.Utiliy.JwtUtil;
import org.gift.Utiliy.LogUtil;
import org.gift.Utiliy.MD5;
import org.gift.Utiliy.RedisCacheService;
import org.gift.ValueObject.Common.ResSubmit;
import org.gift.ValueObject.Customer.ResLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
public class LoginInterface extends HTTPRequest {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private LoginRecordMapper loginRecordMapper;

    @Autowired
    private LoginMapper loginMapper;

    @Autowired
    private RedisCacheService redisCacheService;

    @RequestMapping(value = "/ttt")
    public String ttt(){
        ResSubmit res = new ResSubmit();
        res.setMsg("token缺失或过期，请重新登录");
        return response(res);
    }

    /*
    * HttpServletRequest  获取请求头
    * @RequestParam 获取data参数
    * @RequestBody  获取json参数
    * */
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String login(HttpServletRequest request, @RequestBody HashMap<String, Object> body) throws Exception {
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("user-agent"));
        String clientType = userAgent.getOperatingSystem().getDeviceType().toString();
        LogUtil.LOGGING.info("clientType = " + clientType);   //客户端类型  手机、电脑、平板
        String os = userAgent.getOperatingSystem().getName();
        LogUtil.LOGGING.info("os = " + os);    //操作系统类型
        String browser = userAgent.getBrowser().toString();
        LogUtil.LOGGING.info("browser = " + browser);    // 浏览器类型

        // 获取ip
        String ip = request.getRemoteAddr();
        LogUtil.LOGGING.info("{} 调用/customer/login接口，RequestBody:{}",ip,body);
        // 实例化data对象
        ResLogin res = new ResLogin();
        // 判断是否满足12小时内最近5次连续登录失败
        if(LoginRecord(ip)){
            LogUtil.LOGGING.info("该用户12小时内连续登录失败次数过多");
            res.setMsg("12小时内连续登录失败次数过多，请稍后再试！");
            res.setStatus(0);
            return response(res);
        }else{
            String username = body.get("username").toString();
            String password = MD5.md5(body.get("password").toString());
            LoginRecord loginRecord = new LoginRecord();
            loginRecord.setIp(ip);
            loginRecord.setValue(body.toString());
            Users user = usersMapper.selectUserforPassword(username, password);
            if(null != user){
                loginRecord.setStatus(1);
                Login login = new Login();
                login.setIp(ip);
                login.setUser(user);
                login.setEquipment(browser);
                loginMapper.save(String.valueOf(login.getUser().getId()), login.getIp(), login.getEquipment());
                Map<String, Object> info = new HashMap<>();
                info.put("username", username);
                info.put("password", password);
                String token = JwtUtil.sign(String.valueOf(user.getId()), info);
                redisCacheService.add(token, "1", 5 * 60, TimeUnit.SECONDS);
                LogUtil.LOGGING.info("登录成功了");
                res.setPhoto(user.getPhoto());
                res.setUsername(user.getUsername());
                res.setMsg("登录成功");
                res.setStatus(1);
                res.setToken(token);
            }else{
                loginRecord.setStatus(0);
                LogUtil.LOGGING.info("登录失败，将登录情况写入表中");
                res.setMsg("用户名或密码错误，请重新尝试");
                res.setStatus(0);
            }
            loginRecordMapper.save(loginRecord);
            return response(res);
        }
    }

    private void saveLoginInformation(){
        /*
        * 保存登录信息的方法
        * 1、记录LoginRecordMapper表
        * 2、如果登录成功则记录Login表
        * */
    }

    private Boolean size(String s, int low, int up){
        return low < s.length() && s.length() < up;
    }

    private Boolean LoginRecord(String ip){
        /*
        * 通过LoginRecord表查询该IP24小时内最新5次记录是否都是失败
        * 连续5次失败返回true 否则返回false
        * */
        Integer num = loginRecordMapper.SelectCount(ip);
        return num != null && num == 5;
    }
}
