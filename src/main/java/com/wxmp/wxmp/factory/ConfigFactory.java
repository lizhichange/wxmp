package com.wxmp.wxmp.factory;

import com.wxmp.wxmp.dal.WeChatConfig;
import com.wxmp.wxmp.repository.WeChatConfigRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author wahaha
 */
@Component
@Slf4j
public class ConfigFactory {


    @Autowired
    private ConfigurableEnvironment env;


   private   WeChatConfig weChatConfig;
    @Autowired
    WeChatConfigRepository weChatConfigRepository;


    @PostConstruct
    void init(){
        String profile = env.getActiveProfiles()[0];
        weChatConfig = weChatConfigRepository.envType(profile);
        log.info("weChatConfig:{}",  weChatConfig);

    }


    public WeChatConfig getWeChatConfig() {
        return weChatConfig;
    }

}
