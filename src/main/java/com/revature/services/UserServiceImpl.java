package com.revature.services;

import com.revature.models.User;
import com.revature.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private JavaMailSender mailSender;

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByCredentials(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
  
    @Override
    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void sendEmail(String email, int id){
        try {
            String subject = "Password Reset Request";
            String siteurl = "http://a28ea15be699e4cab91ffc61d183dfc6-120750143.us-east-1.elb.amazonaws.com/reset-password/" + id ;
            String senderName = "RevatureMerchTeam";
            String mailContent = "<p>Click the link below to change your password</p>"
                    + "<a href=\"http://a28ea15be699e4cab91ffc61d183dfc6-120750143.us-east-1.elb.amazonaws.com/reset-password/" + id +
                    "\"> Link to Reset Password</a>" + "<br> <p>Thank you for shopping with us</p>";
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(mailContent, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
