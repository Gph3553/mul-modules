package com.dfjx.sendlog.sink;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @创建人 PanHu.Gao
 * @创建时间 2020/6/7
 * @描述
 */

public interface SinkLogToKafka {

    /**
     *
     * @param optTime       用户操作时间 包括登录时间
     * @param visitModule   访问模块    //example: 用户认证 、业务模块
     * @param function      点击模块下的功能  //登录成功 登录失败 注册 注销 生成令牌
     * @param optContent    具体操作内容  //例如查看了【我的申请】模块  周畅登录平台！
     * @param requestPra    请求参数
     * @param servicePath   服务路径
     */
    public void sendUserActionLog(String optTime,String visitModule,String function,String optContent, String requestPra,String servicePath);

}
