package com.wxmp.wxmp.repository;

import com.wxmp.wxmp.dal.WeChatConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wahaha
 */
@Repository
public interface WeChatConfigRepository extends JpaRepository<WeChatConfig, Long> {

    WeChatConfig envType(String envType);

}
