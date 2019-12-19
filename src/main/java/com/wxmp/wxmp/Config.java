package com.wxmp.wxmp;


import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static me.chanjar.weixin.common.api.WxConsts.EventType.SUBSCRIBE;
import static me.chanjar.weixin.common.api.WxConsts.EventType.UNSUBSCRIBE;
import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType.EVENT;
import static me.chanjar.weixin.mp.constant.WxMpEventConstants.CustomerService.*;
import static me.chanjar.weixin.mp.constant.WxMpEventConstants.POI_CHECK_NOTIFY;

/**
 * @author wahaha
 */
@Configuration
@AllArgsConstructor
public class Config {


    @Bean
    public WxMpService wxMpService() {
        // 代码里 getConfigs()处报错的同学，请注意仔细阅读项目说明，你的IDE需要引入lombok插件！！！！
        WxMpService service = new WxMpServiceImpl();
        Map<String, WxMpConfigStorage> configStorageMap= Maps.newHashMap();
        WxMpDefaultConfigImpl configStorage = new WxMpDefaultConfigImpl();
        configStorage.setToken("EvIYhBs2ZuM0EvjScv2J9Ad2dbIlaZSU");
        configStorage.setAesKey("lxOjHNlrewtzPEHCBh7boSmq9jlAe7JF5CINVf2a8IZ");
        configStorageMap.put("xxx",configStorage);
        service.setMultiConfigStorages(configStorageMap);
        return service;
    }
}
