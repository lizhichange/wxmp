package com.wxmp.wxmp;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


/**
 * @author wahaha
 */
@Controller
@Slf4j
public class NotifyController {


    @Autowired
    WxMpService wxMpService;



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
        String requestBody = IOUtils.toString(request.getInputStream(), "UTF-8");
        log.info("form:{}", form);
        log.info("postData:{}", requestBody);
        if (!wxMpService.checkSignature(form.getTimestamp(), form.getNonce(), form.getSignature())) {
            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
        }

        String encType = form.getEncrypt_type();
        String out = null;
        if (encType == null) {
            // 明文传输的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(requestBody);
            log.debug("\ninMessage：{}", inMessage);
        }  else if ("aes".equalsIgnoreCase(encType)) {
            // aes加密的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(requestBody, wxMpService.getWxMpConfigStorage(),
                    form.getTimestamp(), form.getNonce(), form.getMsg_signature());
            log.debug("\n消息解密后内容为：\n{} ", inMessage.toString());
            WxMpXmlOutMessage outMessage = null;
            if (outMessage == null) {
                return "";
            }
            out = outMessage.toEncryptedXml(wxMpService.getWxMpConfigStorage());
        }

        log.debug("\n组装回复信息：{}", out);
        return out;
    }



   // @RequestMapping(value = "/notify", method = RequestMethod.POST)
   // @ResponseBody
//    public String post(NotifyForm form, HttpServletRequest request) throws IOException {
//
//        String requestBody = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
//        log.info("form:{}", form);
//
//        log.info("\n接收微信请求：requestBody=[\n{}\n] ", requestBody);
//
//        if (!wxMpService.checkSignature(form.getTimestamp(), form.getNonce(), form.getSignature())) {
//            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
//        }
//
//        String encType = form.getEncrypt_type();
//        String out = null;
//        if (encType == null) {
//            // 明文传输的消息
//            WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(requestBody);
//            WxMpXmlOutMessage outMessage = this.route(inMessage);
//            if (outMessage == null) {
//                return "";
//            }
//            out = outMessage.toXml();
//        } else if ("aes".equalsIgnoreCase(encType)) {
//            // aes加密的消息
//            WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(requestBody, wxMpService.getWxMpConfigStorage(),
//                    form.getTimestamp(), form.getNonce(), form.getMsg_signature());
//            log.debug("\n消息解密后内容为：\n{} ", inMessage.toString());
//            WxMpXmlOutMessage outMessage = this.route(inMessage);
//            if (outMessage == null) {
//                return "";
//            }
//
//            out = outMessage.toEncryptedXml(wxMpService.getWxMpConfigStorage());
//        }
//
//        log.debug("\n组装回复信息：{}", out);
//        return out;
//    }


}
