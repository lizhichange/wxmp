package com.wxmp.wxmp;


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


    @GetMapping("/notify")
    @PostMapping("/notify")
    public String xxx(HttpServletRequest request) {
          log.info("notify:{}",request);
         return "SUCCESS";
    }
}
