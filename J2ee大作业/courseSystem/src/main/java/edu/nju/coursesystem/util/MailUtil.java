package edu.nju.coursesystem.util;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class MailUtil {
    private static String username = "xzt_jjc@163.com";
    private static String password = "Xzt970828";

    private static String myEmailSMTPHost = "smtp.163.com";

    public static void sendMail(String mailReceiver,String mailContent)throws Exception {
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", myEmailSMTPHost);
        props.setProperty("mail.smtp.auth", "true");
        Session session = Session.getInstance(props);
        session.setDebug(false);//用于查看log
        MimeMessage message = createMail(session, username, mailReceiver,mailContent);
        Transport transport = session.getTransport();
        transport.connect(username, password);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();

    }

    private static MimeMessage createMail(Session session, String username, String mailReceiver, String mailContent) throws Exception{
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username, "CourseSystem", "UTF-8"));
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(mailReceiver, "XX用户", "UTF-8"));
        message.setSubject("Mail Confirm", "UTF-8");
        message.setContent(mailContent, "text/html;charset=UTF-8");
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }




}
