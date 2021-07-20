package com.bit.yourmine.config;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class MailHandler {
    private final JavaMailSender sender;
    private final MimeMessage mailMessage;
    private final MimeMessageHelper messageHelper;

    public MailHandler(JavaMailSender jsender) throws MessagingException {
        this.sender = jsender;
        mailMessage = jsender.createMimeMessage();
        messageHelper = new MimeMessageHelper(mailMessage, true, "UTF-8");
    }

    // 보내는 사람
    public void setFrom(String from) throws MessagingException {
        messageHelper.setFrom(from);
    }

    // 받는사람
    public void setToemail(String toEmail) throws MessagingException {
        messageHelper.setTo(toEmail);
    }

    // 제목
    public void setTitle(String title) throws MessagingException {
        messageHelper.setSubject(title);
    }

    // 내용
    public void setText(String text, boolean useHtml) throws MessagingException {
        messageHelper.setText(text, useHtml);
    }

    // 메일 전송
    public void send() {
        try {
            sender.send(mailMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
