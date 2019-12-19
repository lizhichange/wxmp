package com.wxmp.wxmp;


import com.fulihui.weixinmp.web.notify.form.NotifyForm;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author wahaha
 */
@Controller
@Slf4j
public class NotifyController {


    @Autowired
    WxMpService wxMpService;


    @Bean
    public WxMpService wxMpService() {
        // 代码里 getConfigs()处报错的同学，请注意仔细阅读项目说明，你的IDE需要引入lombok插件！！！！
        WxMpService service = new WxMpServiceImpl();
        Map<String, WxMpConfigStorage> configStorageMap= Maps.newHashMap();
        WxMpDefaultConfigImpl configStorage = new WxMpDefaultConfigImpl();
        configStorage.setToken("EvIYhBs2ZuM0EvjScv2J9Ad2dbIlaZSU");
        configStorageMap.put("xxx",configStorage);
        service.setMultiConfigStorages(configStorageMap);
        return service;
    }

    @RequestMapping(value = "/notify", method = RequestMethod.GET)
    @ResponseBody
    String doGet(NotifyForm form) {
        log.info("\n接收到来自微信服务器的认证消息：[{}, {}, {}, {}]", form.getSignature(),
                form.getTimestamp(), form.getNonce(), form.getEchostr());
        if (StringUtils.isAnyBlank(form.getSignature(), form.getTimestamp(), form.getNonce(), form.getEchostr())) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }
        String timestamp = form.getTimestamp();
        String nonce = form.getNonce();
        String signature = form.getSignature();
        if (wxMpService.checkSignature(timestamp, nonce, signature)) {
            return form.getEchostr();
        }

        return "非法请求";
    }


    @RequestMapping(value = "/notify", method = RequestMethod.POST)
    @ResponseBody
    String doPost(NotifyForm form, HttpServletRequest request) throws IOException {
        String postData = IOUtils.toString(request.getInputStream(), "UTF-8");
        log.info("form:{}", form);
        log.info("postData:{}", postData);
        return Boolean.TRUE.toString();
    }
}
