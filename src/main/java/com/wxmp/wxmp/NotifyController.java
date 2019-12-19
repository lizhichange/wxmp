package com.wxmp.wxmp;


import com.fulihui.weixinmp.web.notify.form.NotifyForm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * @author wahaha
 */
@Controller
@Slf4j
public class NotifyController {



    @RequestMapping(value = "/notify", method = RequestMethod.GET)
    @ResponseBody
    String doGet(NotifyForm form) {
        log.info("form:{}",form);
        return Boolean.TRUE.toString();
     }



    @RequestMapping(value = "/notify", method = RequestMethod.POST)
    @ResponseBody
    String doPost(NotifyForm form, HttpServletRequest request) throws IOException {
        String postData = IOUtils.toString(request.getInputStream(), "UTF-8");
        log.info("form:{}",form);
        log.info("postData:{}",postData);
        return Boolean.TRUE.toString();
    }
}
