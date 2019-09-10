package com.zbq.thx.controller;

import com.alibaba.fastjson.JSON;
import com.zbq.thx.service.ThsStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateData;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;
import me.chanjar.weixin.common.error.WxErrorException;

/**
 * Created by qcl on 2019-05-20
 * 微信：2501902696
 * desc: 微信小程序模版推送实现
 */
@RestController
public class PushController {

    @Autowired
    private ThsStrategy thsStrategy;

    @GetMapping("/push")
    public String push(@RequestParam String openid, @RequestParam String formid) {
        //1,配置小程序信息
        WxMaInMemoryConfig wxConfig = new WxMaInMemoryConfig();
        wxConfig.setAppid("wx7222e9469d21bd5b");//小程序appid
        wxConfig.setSecret("539b132ee713e75cf126732b96acb256");//小程序AppSecret

        WxMaService wxMaService = new WxMaServiceImpl();
        wxMaService.setWxMaConfig(wxConfig);

        //2,设置模版信息（keyword1：类型，keyword2：内容）
        List<WxMaTemplateData> templateDataList = new ArrayList<>(2);
        Map strategy = thsStrategy.strategy();
        if (strategy.containsKey("data")){
            Map<String,Object> data = JSON.parseObject(strategy.get("data").toString(),Map.class);
            if (data.containsKey("list")){
                List<Map> list = JSON.parseArray(data.get("list").toString(),Map.class);
                WxMaTemplateData data1 = new WxMaTemplateData("keyword1", list.get(0).get("stockCode").toString());
                WxMaTemplateData data2 = new WxMaTemplateData("keyword2", list.get(0).get("stockName").toString());
                WxMaTemplateData data3 = new WxMaTemplateData("keyword3", list.get(0).get("lastPrice").toString());
                WxMaTemplateData data4 = new WxMaTemplateData("keyword4", list.get(0).get("businDate").toString());
                templateDataList.add(data1);
                templateDataList.add(data2);
                templateDataList.add(data3);
                templateDataList.add(data4);
            }
        }
        //3，设置推送消息
        WxMaTemplateMessage templateMessage = WxMaTemplateMessage.builder()
                .toUser(openid)//要推送的用户openid
                .formId(formid)//收集到的formid
                .templateId("4gtfR5oks4e99YbXXv_RyToE6Z3hTDydeE3Os2KY6ME")//推送的模版id（在小程序后台设置）
                .data(templateDataList)//模版信息
                .page("pages/index/index")//要跳转到小程序那个页面
                .build();
        //4，发起推送
        try {
            wxMaService.getMsgService().sendTemplateMsg(templateMessage);
        } catch (WxErrorException e) {
            System.out.println("推送失败：" + e.getMessage());
            return e.getMessage();
        }
        return "推送成功";
    }
}
