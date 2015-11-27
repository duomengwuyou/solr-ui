package org.myapp.common.mail;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.SendFailedException;
import javax.mail.Authenticator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailUtil {

    private static final String DEFAULT_CHARSET = "UTF-8";

    private static Logger logger = LoggerFactory.getLogger(EmailUtil.class);

    public static enum EncryptionTypes {
        Default, TLS, SSL
    }

    private String mainHost = "proxy-in.baidu.com";
    private int mailPort = 25;
    private int encryptionType = EncryptionTypes.Default.ordinal();
    private boolean auth = false;
    private String mainHostAccount = "duapps-gpanalyse@baidu.com";
    private String mainHostPassword = "";
    private boolean isHtml = false;

    public EmailUtil(String mainHost) {
        this.mainHost = mainHost;
    }

    public EmailUtil() {
    }

    public EmailUtil(String mainHost, boolean auth, String account, String password) {
        this(mainHost, 25, EncryptionTypes.Default.ordinal(), auth, account, password);
    }

    public EmailUtil(String mainHost, int mailPort, int encryptionType, boolean auth, String account, String password) {
        this.mainHost = mainHost;
        this.mailPort = mailPort;
        this.encryptionType = encryptionType;
        this.auth = auth;
        this.mainHostAccount = account;
        this.mainHostPassword = password;
    }

    public EmailUtil(String mainHost, boolean auth, String account, String password, boolean isHtml) {
        this(mainHost, 25, EncryptionTypes.Default.ordinal(), auth, account, password, isHtml);
    }

    public EmailUtil(String mainHost, int mailPort, int encryptionType, boolean auth, String account, String password,
            boolean isHtml) {
        this.mainHost = mainHost;
        this.mailPort = mailPort;
        this.encryptionType = encryptionType;
        this.auth = auth;
        this.mainHostAccount = account;
        this.mainHostPassword = password;
        this.isHtml = isHtml;
    }

    /**
     * Send email to a single recipient or recipient string.
     * 
     * @param senderAddress the sender email address
     * @param senderName the sender name
     * @param receiverAddress the recipient email address
     * @param sub the subject of the email
     * @param msg the message content of the email
     */
    public void sendEmail(String senderAddress, String senderName, String receiverAddress, String sub, String msg)
            throws Exception {
        String[] address = receiverAddress.split(";");
        List recipients = new ArrayList();
        for (int i = 0; i < address.length; i++) {
            if (address[i].trim().length() > 0) {
                recipients.add(address[i]);
            }
        }

        this.sendEmail(senderAddress, senderName, recipients, sub, msg);
    }

    public void sendEmail(String senderAddress, String senderName, List recipients, String sub, String msg)
            throws SendFailedException {
        this.sendEmail(senderAddress, senderName, recipients, sub, msg, null);
    }

    public void sendEmail(String senderAddress, String senderName, String receiverAddress, String sub, String msg,
            Collection attachments) throws Exception {
        String[] address = receiverAddress.split(";");
        List recipients = new ArrayList();
        for (int i = 0; i < address.length; i++) {
            if (address[i].trim().length() > 0) {
                recipients.add(address[i]);
            }
        }

        this.sendEmail(senderAddress, senderName, recipients, sub, msg, attachments);
    }

    /**
     * 发送邮件
     * 
     * @param receiverEmail 接受者email
     * @param emailContent email内容
     * @param attachmentPaths 附件地址
     * @param title 邮件简要
     */
    public void sendEmail(List<String> receiverEmail, String emailContent, List<String> attachmentPaths, String title) {
        EmailUtil email = new EmailUtil("proxy-in.baidu.com", 25, 2, true, "duapps-gpanalyse@baidu.com", "", true);
        Collection col = new ArrayList();
        for (String attachmentPath : attachmentPaths) {
            col.add(attachmentPath);
        }

        try {
            String content = "<html><body><h3>" + title + "</h3><p>" + emailContent + "</p></body></html>";
            email.sendEmail("duapps-gpanalyse@baidu.com", "GPReviewAnalyser", receiverEmail, "Google Play评论管理平台",
                    content, col);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("发送邮件失败：" + ex.getMessage());
        }
    }

    /**
     * Send email to a list of recipients.
     * 
     * @param senderAddress the sender email address
     * @param senderName the sender name
     * @param recipients a list of receipients email addresses
     * @param sub the subject of the email
     * @param msg the message content of the email
     * @param attachments attachments list of the email
     */
    public void sendEmail(String senderAddress, String senderName, List recipients, String sub, String msg,
            Collection attachments) throws SendFailedException {

        Transport transport = null;

        try {

            Properties props = this.getProperties();

            Session session = this.getSession(props);

            MimeMessage message = new MimeMessage(session);

            if (this.getDefaultIsHtml()) {
                message.addHeader("Content-type", "text/html");
            } else {
                message.addHeader("Content-type", "text/plain");
            }

            message.setSubject(sub, DEFAULT_CHARSET);
            message.setFrom(new InternetAddress(senderAddress, senderName));

            for (Iterator it = recipients.iterator(); it.hasNext();) {
                String email = (String) it.next();
                message.addRecipients(Message.RecipientType.TO, email);
            }

            Multipart mp = new MimeMultipart();

            // content
            MimeBodyPart contentPart = new MimeBodyPart();

            if (this.getDefaultIsHtml()) {
                contentPart.setContent("<meta http-equiv=Content-Type content=text/html; charset=" + DEFAULT_CHARSET
                        + ">" + msg, "text/html;charset=" + DEFAULT_CHARSET);
            } else {
                contentPart.setText(msg, DEFAULT_CHARSET);
            }

            mp.addBodyPart(contentPart);

            // attachment
            if (attachments != null) {
                MimeBodyPart attachPart;
                for (Iterator it = attachments.iterator(); it.hasNext();) {
                    attachPart = new MimeBodyPart();
                    FileDataSource fds = new FileDataSource(it.next().toString().trim());
                    attachPart.setDataHandler(new DataHandler(fds));
                    if (fds.getName().indexOf("$") != -1) {
                        attachPart.setFileName(fds.getName().substring(fds.getName().indexOf("$") + 1,
                                fds.getName().length()));
                    } else {
                        attachPart.setFileName(fds.getName());
                    }
                    mp.addBodyPart(attachPart);
                }

            }

            message.setContent(mp);

            message.setSentDate(new Date());

            if (this.getDefaultEncryptionType() == EncryptionTypes.SSL.ordinal()) {
                Transport.send(message);
            } else {
                transport = session.getTransport("smtp");

                transport.connect(this.mainHost, this.mailPort, this.mainHostAccount, this.mainHostPassword);

                transport.sendMessage(message, message.getAllRecipients());
            }

        } catch (Exception e) {
            System.out.println("send mail error" + e);
            throw new SendFailedException(e.toString());
        } finally {
            if (transport != null) {
                try {
                    transport.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private Properties getProperties() {

        Properties props = System.getProperties();

        int defaultEncryptionType = this.getDefaultEncryptionType();

        if (defaultEncryptionType == EncryptionTypes.TLS.ordinal()) {
            props.put("mail.smtp.auth", String.valueOf(this.auth));
            props.put("mail.smtp.starttls.enable", "true");
        } else if (defaultEncryptionType == EncryptionTypes.SSL.ordinal()) {
            props.put("mail.smtp.host", this.mainHost);
            props.put("mail.smtp.socketFactory.port", this.mailPort);
            // props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.debug", "true");
            props.put("mail.smtp.auth", String.valueOf(this.auth));
            props.put("mail.smtp.port", this.mailPort);
        } else {
            props.put("mail.smtp.host", this.mainHost);
            props.put("mail.smtp.auth", String.valueOf(this.auth));
        }

        return props;
    }

    private Session getSession(Properties props) {
        Session session = null;

        if (this.getDefaultEncryptionType() == EncryptionTypes.TLS.ordinal()) {
            session = Session.getInstance(props);
        } else if (this.getDefaultEncryptionType() == EncryptionTypes.SSL.ordinal()) {
            session = Session.getInstance(props, new MyAuthenticator(this.mainHostAccount, this.mainHostPassword));
        } else {
            session = Session.getDefaultInstance(props, null);
        }

        return session;
    }

    private boolean getDefaultIsHtml() {
        boolean rst = this.isHtml;
        return rst;
    }

    private class MyAuthenticator extends Authenticator {
        String user;
        String password;

        public MyAuthenticator() {

        }

        public MyAuthenticator(String user, String password) {
            this.user = user;
            this.password = password;
        }

        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(this.user, this.password);
        }
    }

    /**
     * get default encryption type, for 465, SSL for 587, TLS
     * 
     * @return
     */
    private int getDefaultEncryptionType() {
        int rst = this.encryptionType;
        if (this.encryptionType == EncryptionTypes.Default.ordinal()) {
            if (this.mailPort == 465) {
                rst = EncryptionTypes.SSL.ordinal();
            } else if (this.mailPort == 587) {
                rst = EncryptionTypes.TLS.ordinal();
            }
        }

        return rst;
    }

    /**
     * 单独给某用户发送无附件邮件
     * 
     * @param receiverEmail
     * @param emailContent
     * @param title
     */
    public void sendSingleEmail(String receiverEmail, String emailContent, String title) {
        EmailUtil email = new EmailUtil("proxy-in.baidu.com", 25, 2, true, "duapps-gpanalyse@baidu.com", "", true);
        Collection col = new ArrayList();

        try {
            String content = "<html><body><h3>" + title + ",</h3><p>" + emailContent + "</p></body></html>";
            email.sendEmail("duapps-gpanalyse@baidu.com", "GPReviewAnalyser", receiverEmail,
                    "Google Play Review Analyse Email", content, col);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("发送邮件失败：" + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        EmailUtil obj = new EmailUtil();

        String attachmentPath1 = "/Users/baidu/Documents/tempFile/log/log.log";
        String attachmentPath2 = "/Users/baidu/Documents/tempFile/log/error.log";
        List<String> attachmentPaths = new ArrayList<String>();
        attachmentPaths.add(attachmentPath1);
        attachmentPaths.add(attachmentPath2);

        String receiver1 = "zhangxinglong@baidu.com";
        String receiver2 = "zhangxinglong1990@163.com";
        List<String> receiverEmail = new ArrayList<String>();
        receiverEmail.add(receiver1);
        receiverEmail.add(receiver2);

        obj.sendEmail(receiverEmail, "just want to try email", attachmentPaths, "BAD NEWS");
    }

}
