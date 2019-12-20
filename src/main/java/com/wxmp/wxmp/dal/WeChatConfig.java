package com.wxmp.wxmp.dal;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;


/**
 * @author wahaha
 */
@Data
@ToString
@Entity
@Table(name = "sys_wechat_config")
public class WeChatConfig {
    @GeneratedValue
    @Id
    private Long id;

    @Column(nullable = false, name = "config_code")
    private String configCode;


    @Column(nullable = false, name = "env_type")
    private String envType;

    @Column(nullable = false, name = "app_id")
    private String appId;

    @Column(nullable = false, name = "appsecret")
    private String appSecret;

    @Column(nullable = false, name = "mch_id")
    private String mchId;

    @Column(nullable = false, name = "sign_key")
    private String signKey;

    @Column(nullable = false, name = "cert_file")
    private String certFile;

    @Column(nullable = false, name = "token")
    private String token;


    @Column(nullable = false, name = "encoding_aes_key")
    private String encodingAesKey;

    @Column(nullable = false, name = "gmt_create")
    private Date gmtCreate;

    @Column(nullable = false, name = "gmt_modified")
    private Date gmtModified;

}
