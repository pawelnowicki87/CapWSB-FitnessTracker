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
     * Sends an email using the information provided in the given {@link EmailDto}.
     * If the sending process fails, logs the error.
     *
     * @param emailDto Data transfer object containing the recipient's address, subject, and content of the email.
     */
    @Override
    public void send(EmailDto emailDto) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailDto.toAddress());
            message.setSubject(emailDto.subject());
            message.setText(emailDto.content());

            mailSender.send(message);
            log.info("Email sent successfully to {}", emailDto.toAddress());
        } catch (Exception e) {
            log.error("Failed to send email to {}", emailDto.toAddress(), e);
        }
    }
}
