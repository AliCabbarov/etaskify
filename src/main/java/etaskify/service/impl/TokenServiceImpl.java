package etaskify.service.impl;

import etaskify.model.entity.Token;
import etaskify.model.entity.User;
import etaskify.model.enums.TokenType;
import etaskify.repository.TokenRepository;
import etaskify.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;
    @Value("${app.time.token.confirmation}")
    private long confirmationTime;
    @Value("${app.time.token.refresh}")
    private long refreshTime;


    @Override
    public String generateAndSaveRefreshToken(User user) {
        String refreshToken = getRandomUUID();
        tokenRepository.findByUserAndTokenType(user, TokenType.REFRESH).ifPresentOrElse(token -> {
            token.update(refreshToken,refreshTime);
            tokenRepository.save(token);
        },() -> {
            Token token = new Token(confirmationTime, refreshToken, TokenType.REFRESH, user);
            tokenRepository.save(token);
        });
        return refreshToken;
    }

    @Override
    public String generateAndSaveConfirmationToken(User user) {
        String confirmationToken = getRandomUUID();

        tokenRepository.findByUserAndTokenType(user, TokenType.CONFIRMATION).ifPresentOrElse(token -> {
            token.update(confirmationToken, confirmationTime);
            tokenRepository.save(token);
        }, () -> {
            Token token = new Token(confirmationTime, confirmationToken, TokenType.CONFIRMATION, user);
            tokenRepository.save(token);
        });

        return confirmationToken;
    }



    private String getRandomUUID() {
        return UUID.randomUUID().toString();
    }
}


