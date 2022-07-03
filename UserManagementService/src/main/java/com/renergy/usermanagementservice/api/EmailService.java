package com.renergy.usermanagementservice.api;

import com.renergy.usermanagementservice.request.EmailMapper;
import com.renergy.usermanagementservice.request.HelpEmailMapper;
import com.renergy.usermanagementservice.responses.DefaultResponse;
import com.renergy.usermanagementservice.subscribers.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.List;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private UserManagementService userManagementService;

    @Value("${renergy.email.from}")
    private String renergy_email_from;


    public DefaultResponse sendReview(String customerMessage) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(renergy_email_from);
        message.setSubject("Customer Review");
        message.setText(customerMessage);
        try{
            emailSender.send(message);
        } catch (Exception e) {
            return new DefaultResponse("500", e.getMessage());
        }
        return new DefaultResponse("200", "Response sent successfully");
    }

    public DefaultResponse sendMessageToSubscribers(
            EmailMapper emailMapper,
            List<MultipartFile> files) throws MessagingException, IOException {
        // ...
        if (emailMapper != null) {
            List<Subscriber> subscribers = userManagementService.getAllSubscribers();

            for (int index = 0; index < subscribers.size(); index++) {
                final String sendTo = subscribers.get(index).getEmail();
                emailSender.send(new MimeMessagePreparator() {
                    public void prepare(MimeMessage mimeMessage) throws MessagingException {
                        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                        message.setFrom(renergy_email_from);
                        message.setTo(sendTo);
                        message.setSubject(emailMapper.getSubject());
                        message.setText(emailMapper.getDescription());
                        if (files != null) {
                            for (int indexOfFiles = 0; indexOfFiles < files.size(); indexOfFiles++) {
                                message.addAttachment(files.get(indexOfFiles).getOriginalFilename(), files.get(indexOfFiles));
                            }
                        }

                    }
                });
            }

            return new DefaultResponse("200", "Email sent successfully");
        }
        return new DefaultResponse("500", "Empty message error");
    }

    public DefaultResponse sendHelpEmail(HelpEmailMapper helpEmailMapper) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(renergy_email_from);
        message.setSubject("Customer Help");
        message.setText(helpEmailMapper.getMessage() + "\n\n" + "Customer Name : " + helpEmailMapper.getName() + "\n\n" + "Customer Email : " + helpEmailMapper.getEmail() + "\n\n" + "Customer Phone : " + helpEmailMapper.getPhoneNumber() + "\n\n" + "Customer City : " + helpEmailMapper.getCity());
        try{
            emailSender.send(message);
        } catch (Exception e) {
            return new DefaultResponse("500", e.getMessage());
        }
        return new DefaultResponse("200", "Email sent successfully");
    }


}
