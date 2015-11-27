package org.myapp.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.myapp.common.constant.ConstantVariable;
import org.myapp.common.enumclass.ControllerStatusEnum;
import org.myapp.common.model.DisplayItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 搜索内容
 * 
 * @author xinglong
 *
 */
@Controller
public class SearchController {
    private static Logger logger = LoggerFactory.getLogger(SearchController.class);

    /**
     * 用户登录
     * 
     * @param userEmail
     * @param userPass
     * @return
     */
    @ResponseBody
    @RequestMapping("/dosearch")
    public JSONObject dosearch(HttpServletRequest request, String queryContent) {
        logger.info("调用服务dosearch");
        JSONObject jsonObj = new JSONObject();
        
        List<DisplayItem> items = new ArrayList<DisplayItem>();
        
        for(int i = 0; i < 4; i++) {
            DisplayItem itemOne = new DisplayItem();
            itemOne.setTitle("标题" + i);
            itemOne.setContent("标题"+i+"的相关内容介绍");
            itemOne.setContentDate("2015-10-1"+i);
            itemOne.setSourceLink("http://www.baidu.com");
            itemOne.setIdentifyStr("123443"+i);
            items.add(itemOne);
        }

        jsonObj.put(ConstantVariable.DATA, JSONObject.toJSON(items));
        jsonObj.put(ConstantVariable.STATUS, ControllerStatusEnum.OK.getStatus());
        jsonObj.put(ConstantVariable.MESSAGE, ControllerStatusEnum.OK.getMessage());
        return jsonObj;
    }

}
