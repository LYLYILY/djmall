package com.dj.mall.cmpt.api;

/**
 * 邮件服务
 */
public interface EMailApi {

    /**
     * 发送普通邮件
     *
     * @param to       收件人
     * @param subject  主题
     * @param mailText 邮件内容
     * @return
     * @throws Exception
     */
    boolean sendMail(String to, String subject, String mailText) throws Exception;

    /**
     * 发送HTML邮件
     *
     * @param to       收件人
     * @param subject  主题
     * @param mailHTML 邮件内容(带HTML)
     * @return
     * @throws Exception
     */
    boolean sendMailHTML(String to, String subject, String mailHTML) throws Exception;

    /**
     * 发送带附件的邮件
     *
     * @param to       收件人
     * @param subject  主题
     * @param mailText 邮件内容
     * @param file     附件
     * @return
     * @throws Exception
     */
    boolean sentMailFile(String to, String subject, String mailText, byte[] file) throws Exception;
}
