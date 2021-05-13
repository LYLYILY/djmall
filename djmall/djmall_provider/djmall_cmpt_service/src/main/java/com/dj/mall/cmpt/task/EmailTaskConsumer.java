package com.dj.mall.cmpt.task;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dj.mall.cmpt.api.EMailApi;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EmailTaskConsumer {

    @Value("${user.active}")
    private String activeUrl;

    @Reference
    private EMailApi eMailApi;

    @RabbitHandler
    @RabbitListener(queues = "directQueue1")
    public void sendRegistryEmail(Message message) {
            try {
                String str = new String(message.getBody(), "UTF-8");
                JSONObject data = JSON.parseObject(str);
                String content = String.format("恭喜%s注册成功，请去激活，<hr><a href='%s'>去激活</a>",data.getString("userName"), activeUrl, data.getInteger("userId"));
                eMailApi.sendMailHTML(data.getString("email"), "激活邮件", content);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    @RabbitHandler
    @RabbitListener(queues = "directQueue2")
    public void sendRestPwdEmail(Message message) {
        try {
            String str = new String(message.getBody(), "UTF-8");
            JSONObject data = JSON.parseObject(str);
            String reset_content = "尊敬的用户，您好！<br>&nbsp;&nbsp;&nbsp;&nbsp;您在<span style='color:blue'>dj商城</span>的账号于今日%s修改为&nbsp;<span style='color:red'>%s</span>&nbsp;。";
            eMailApi.sendMailHTML(data.getString("email"), "重置密码", String.format(reset_content, LocalDateTime.now().toString(), data.getString("userPwd")));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
