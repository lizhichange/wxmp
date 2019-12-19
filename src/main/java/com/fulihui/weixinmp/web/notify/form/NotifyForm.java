package com.fulihui.weixinmp.web.notify.form;


import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 通知的请求参数
 * @author Willard.Hu on 2016/11/1 0001.
 */
@Data
@ToString
public class NotifyForm implements Serializable {
    private static final long serialVersionUID = 2254924984090831831L;
    /** 签名字符串 */
    private String            signature;
    /** 时间戳 */
    private String            timestamp;
    /** 随机字符串 */
    private String            nonce;
    /** 成功时回馈的字符串 */
    private String            echostr;
    /** 消息密文 */
    private String            msg_signature;





 }
