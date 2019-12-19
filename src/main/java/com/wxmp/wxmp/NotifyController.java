package com.wxmp.wxmp;


import com.fulihui.weixinmp.web.notify.form.NotifyForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/**
 * @author wahaha
 */
@RestController
@Slf4j
public class NotifyController {


    @PostMapping("/notify")
    public String xxx(NotifyForm form,HttpServletRequest request) {
          log.info("form:{}",form);
         return Boolean.TRUE.toString();
    }
}
