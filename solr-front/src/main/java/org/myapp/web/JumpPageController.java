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
 * 跳转页面
 * 
 * @author xinglong
 *
 */
@Controller
public class JumpPageController {
    private static Logger logger = LoggerFactory.getLogger(JumpPageController.class);

    /**
     * 跳转界面
     * @param request
     * @return
     */
    @RequestMapping("/search")
    public String search(HttpServletRequest request) {
        logger.info("调用服务search");
        return "app/search";
    }

}
