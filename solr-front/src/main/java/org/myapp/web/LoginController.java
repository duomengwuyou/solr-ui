package org.myapp.web;

import javax.servlet.http.HttpServletRequest;
import org.myapp.common.constant.ConstantVariable;
import org.myapp.common.enumclass.ControllerStatusEnum;
import org.myapp.common.redis.RedisService;
import org.myapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

/**
 * 登录注册相关
 * 
 * @author xinglong
 *
 */
@Controller
public class LoginController {
    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;
    
    @Autowired
    private RedisService redisService;

    /**
     * 用户登录
     * 
     * @param userEmail
     * @param userPass
     * @return
     */
    @ResponseBody
    @RequestMapping("/login")
    public JSONObject login(HttpServletRequest request, String userName, String userPass) {
        logger.info("调用服务login");
        JSONObject jsonObj = new JSONObject();
       
        jsonObj.put(ConstantVariable.STATUS, ControllerStatusEnum.OK.getStatus());
        jsonObj.put(ConstantVariable.MESSAGE, ControllerStatusEnum.OK.getMessage());
        return jsonObj;
    }

}
