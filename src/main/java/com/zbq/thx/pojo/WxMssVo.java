package com.zbq.thx.pojo;

import lombok.Data;

import java.util.Map;


/**
 * @author 郑百钦
 */
@Data
public class WxMssVo {
    //用户openid
    private String touser;
    //模版id
    private String templateId;
    //默认跳到小程序首页
    private String page = "index";
    //收集到的用户formid
    private String formId;
    //放大那个推送字段
    private String emphasisKeyword = "keyword1.DATA";
    //推送文字
    private Map<String, TemplateData> data;
}