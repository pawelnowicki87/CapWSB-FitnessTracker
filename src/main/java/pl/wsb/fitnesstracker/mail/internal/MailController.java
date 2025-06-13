package pl.wsb.fitnesstracker.mail.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.wsb.fitnesstracker.mail.api.EmailDto;
import pl.wsb.fitnesstracker.mail.api.EmailService;

/**
 * REST controller responsible for handling email-related actions.
 * Exposes endpoints for sending emails, including a simple test email.
 */
@RestController
@RequestMapping("/v1/mails")
@RequiredArgsConstructor
public class MailController {

    private final EmailService emailService;

    /**
     * Sends a test email to the specified recipient.
     * Intended to verify that the email sending functionality works as expected.
     *
     * @param to The recipient's email address.
     * @return ResponseEntity with a confirmation message.
     */
    @PostMapping("/send-test")
    public ResponseEntity<String> sendTestEmail(@RequestParam String to) {
        String subject = "Test Email from Fitness Tracker";
        String content = "This is a test email sent to verify the email sending functionality.";
        EmailDto emailDto = new EmailDto(to, subject, content);

        emailService.send(emailDto);

        return ResponseEntity.ok("Test email sent to " + to);
    }
}

