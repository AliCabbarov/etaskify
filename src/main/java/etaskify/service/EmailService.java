package etaskify.service;

import org.springframework.scheduling.annotation.Async;

public interface EmailService {
    @Async
    void sendTo(String email, Object subject);
}
