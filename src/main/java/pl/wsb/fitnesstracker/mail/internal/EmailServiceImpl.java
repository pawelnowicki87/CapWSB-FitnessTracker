package pl.wsb.fitnesstracker.mail.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.mail.api.EmailDto;
import pl.wsb.fitnesstracker.mail.api.EmailService;

/**
 * Implementation of the {@link EmailService} interface responsible for sending emails
 * using Spring's {@link JavaMailSender}.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    /**
     * Sends a simple email using the data provided in the {@link EmailDto}.
     * Logs an error message if the email could not be sent.
     *
     * @param emailDto DTO containing the recipient's address, subject, and message body.
     */
    @Override
    public void send(EmailDto emailDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailDto.toAddress());
        message.setSubject(emailDto.subject());
        message.setText(emailDto.content());

        try {
            mailSender.send(message);
            log.info("Email sent successfully to {}", emailDto.toAddress());
        } catch (Exception e) {
            log.error("Failed to send email to {}", emailDto.toAddress(), e);
        }
    }
}


