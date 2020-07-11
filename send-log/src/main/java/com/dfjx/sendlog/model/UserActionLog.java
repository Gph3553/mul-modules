package com.dfjx.sendlog.model;/**
 * <h3>kafkaUtils</h3>
 * <p>用户操作对象</p>
 *
 * @author : PanhuGao
 * @date : 2020-06-07 17:10
 **/

import lombok.Data;

/**
 * @创建人 PanHu.Gao
 * @创建时间 2020/6/7
 * @描述
 */
@Data
public class UserActionLog  {

    /**
     * 用户操作时间 包括登录时间
     */
    private String optTime;
    /**
     * 访问模块
     */
    private String visitModule;//example: 用户认证 、业务模块

    /**
     * 点击模块下的功能
     */
    private String function;  //登录成功 登录失败 注册 注销 生成令牌

    /**
     * 具体操作内容
     */
    private String optContent;//例如查看了【我的申请】模块  周畅登录平台！

    /**
     * 请求参数
     */
    private String requestPra;
    /**
     * 服务路径
     */
    private String servicePath;

}
