package com.wxmp.wxmp;

import com.wxmp.wxmp.dal.WeChatConfig;
import com.wxmp.wxmp.repository.WeChatConfigRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class WxmpApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	WeChatConfigRepository weChatConfigRepository;
	@Test
	void query() {
		List<WeChatConfig> all = weChatConfigRepository.findAll();
		for (WeChatConfig item : all) {
			System.out.println(item);
		}
	}



}
