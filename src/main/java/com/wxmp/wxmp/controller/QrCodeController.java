package com.wxmp.wxmp.controller;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpQrcodeService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

/**
 * @author wahaha
 */
@RestController
@Slf4j
@AllArgsConstructor
public class QrCodeController {
    @Autowired
    WxMpQrcodeService wxMpQrcodeService;
    @GetMapping("/qrCode")
    File file() throws WxErrorException {
        WxMpQrCodeTicket wxMpQrCodeTicket = wxMpQrcodeService.qrCodeCreateLastTicket(100000);
        log.info("wx:{}", wxMpQrCodeTicket);
        File file = wxMpQrcodeService.qrCodePicture(wxMpQrCodeTicket);
        log.info("file:{}", file);
        return file;
    }

}
