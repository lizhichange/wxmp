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


/**
 * @author wahaha
 */
@Controller
@Slf4j
@AllArgsConstructor
public class NotifyController {


    @Autowired
    WxMpService wxMpService;

private final     WxMpMessageRouter messageRouter;

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


//    @RequestMapping(value = "/notify", method = RequestMethod.POST)
//    @ResponseBody
//    String doPost(NotifyForm form, HttpServletRequest request) throws IOException {
//        String postData = IOUtils.toString(request.getInputStream(), "UTF-8");
//        log.info("form:{}", form);
//        log.info("postData:{}", postData);
//        return Boolean.TRUE.toString();
//    }


    @RequestMapping(value = "/notify",produces = "application/xml; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public String post(
                       @RequestBody String requestBody,
                       @RequestParam("signature") String signature,
                       @RequestParam("timestamp") String timestamp,
                       @RequestParam("nonce") String nonce,
                       @RequestParam("openid") String openid,
                       @RequestParam(name = "encrypt_type", required = false) String encType,
                       @RequestParam(name = "msg_signature", required = false) String msgSignature) {
        log.info("\n接收微信请求：[openid=[{}], [signature=[{}], encType=[{}], msgSignature=[{}],"
                        + " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
                openid, signature, encType, msgSignature, timestamp, nonce, requestBody);



        if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
        }

        String out = null;
        if (encType == null) {
            // 明文传输的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(requestBody);
            WxMpXmlOutMessage outMessage = this.route(inMessage);
            if (outMessage == null) {
                return "";
            }

            out = outMessage.toXml();
        } else if ("aes".equalsIgnoreCase(encType)) {
            // aes加密的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(requestBody, wxMpService.getWxMpConfigStorage(),
                    timestamp, nonce, msgSignature);
            log.debug("\n消息解密后内容为：\n{} ", inMessage.toString());
            WxMpXmlOutMessage outMessage = this.route(inMessage);
            if (outMessage == null) {
                return "";
            }

            out = outMessage.toEncryptedXml(wxMpService.getWxMpConfigStorage());
        }

        log.debug("\n组装回复信息：{}", out);
        return out;
    }

    private WxMpXmlOutMessage route(WxMpXmlMessage message) {
        try {
            return this.messageRouter.route(message);
        } catch (Exception e) {
            log.error("路由消息时出现异常！", e);
        }

        return null;
    }

}
