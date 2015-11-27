package org.myapp.common.mail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
public class MailService {

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Autowired
	private SimpleMailMessage mailMessage;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class);


	
	/**
	 * <br>
	 * 通过模板产生邮件正文</br>
	 * <p>
	 * by freemaker
	 * </p>
	 * 
	 * @param templateName
	 * @param map
	 * @return
	 */
	public String generateEmailContent(String templateName, Map map) {
		try {
			Configuration configuration = freeMarkerConfigurer
					.getConfiguration();
			Template t = configuration.getTemplate(templateName);
			return FreeMarkerTemplateUtils.processTemplateIntoString(t, map);
		} catch (TemplateException e) {
			LOGGER.error("Error while processing FreeMarker template ", e);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			LOGGER.error("Error while open template file ", e);
		} catch (IOException e) {
			LOGGER.error("Error while generate Email Content ", e);
		}
		return null;
	}
	
	/**
	 * 发送邮件
	 * @param emailAddresses 收件人地址的数组
	 * @param fromEmail	寄件人Email地址, null为默认寄件人@email
	 * @param bodyText	邮件正文
	 * @param subject	 邮件主题
	 * @param attachmentName  附件名
	 * @param resource	附件
	 * @throws javax.mail.MessagingException
	 */
	public void sendMessage(String[] emailAddresses, String fromEmail,
			String bodyText, String subject, String attachmentName,
			ClassPathResource resource) throws javax.mail.MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();

		// use the true flag to indicate you need a multipart message
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setTo(emailAddresses);
		if (fromEmail != null) {
			helper.setFrom(fromEmail);
		}
		helper.setText(bodyText, true);
		helper.setSubject(subject);

		if (attachmentName != null && resource != null)
			helper.addAttachment(attachmentName, resource);

		javaMailSender.send(message);
	}
	
	
	/**
	 * 通过模板来生成邮件正文
	 * @param msg
	 * @param templateName
	 * @param model
	 */
	public void send(String to, String subject, String templateName, Map model) {
		//生成html邮件内容
		String content = generateEmailContent(templateName, model);
		MimeMessage mimeMsg = null;
		try {
			mimeMsg = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMsg, true, "utf-8");
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setFrom(mailMessage.getFrom());
			helper.setText(content, true);
			javaMailSender.send(mimeMsg);
			
			System.out.println("发送成功");

		} catch (MessagingException ex) {
			LOGGER.error(ex.getMessage(), ex);
		}

	}

	
	/**
	 * 通过模板来生成邮件正文
	 * @param msg
	 * @param templateName
	 * @param model
	 */
	public void send(SimpleMailMessage msg, String templateName, Map model) {
        //生成html邮件内容
        String content = generateEmailContent(templateName, model);
        MimeMessage mimeMsg = null;
        try {
            mimeMsg = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMsg, true, "utf-8");
            helper.setTo(msg.getTo());
            
            if(msg.getSubject()!=null)
                helper.setSubject(msg.getSubject());
            
            if(msg.getFrom()!=null)
                helper.setFrom(msg.getFrom());
            
            helper.setText(content, true);
            
            javaMailSender.send(mimeMsg);
            
        } catch (MessagingException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }

    }


}
