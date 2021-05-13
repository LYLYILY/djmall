package com.dj.mall.cmpt.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.dj.mall.cmpt.api.EMailApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class EMailApiImpl implements EMailApi {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.properties.from}")
    private String from;

    /**
     * 发送普通邮件
     *
     * @param to       收件人
     * @param subject  主题
     * @param mailText 邮件内容
     */
    @Override
    public boolean sendMail(String to, String subject, String mailText) {
        SimpleMailMessage mail = new SimpleMailMessage();
        //to 收件人
        mail.setTo(to);
        //发件人
        mail.setFrom(from);
        //邮件名称
        mail.setSubject(subject);
        //邮件主题
        mail.setText(mailText);
        //发送邮件
        mailSender.send(mail);
        return true;
    }

    /**
     * 发送HTML邮件
     *
     * @param to       收件人
     * @param subject  主题
     * @param mailHTML 邮件内容(带HTML)
     */
    @Override
    public boolean sendMailHTML(String to, String subject, String mailHTML) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        //to 收件人
        helper.setTo(to);
        //发件人
        helper.setFrom(new InternetAddress(from, "", "UTF-8"));
        //邮件名称
        helper.setSubject(subject);
        //邮件主体内容
        helper.setText(mailHTML, true);
        //发送邮件
        mailSender.send(message);
        return true;
    }

    /**
     * 发送带文件的邮件
     *
     * @param to       收件人
     * @param subject  主题
     * @param mailText 邮件内容
     * @param file     附件(默认的Dubbo协议不支持传文件)
     * @throws Exception
     */
    @Override
    public boolean sentMailFile(String to, String subject, String mailText, byte[] file) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        //to 收件人
        helper.setTo(to);
        //发件人
        helper.setFrom(from);
        //邮件名称
        helper.setSubject(subject);
        //邮件主题
        helper.setText(mailText);
        InputStreamSource inputStream = new ByteArrayResource(file);
        //将附件添加至邮件主体
        helper.addAttachment("附件", inputStream);
        //发送邮件
        mailSender.send(message);
        return true;
    }

}
