package etaskify.repository;

import etaskify.model.entity.Token;
import etaskify.model.entity.User;
import etaskify.model.enums.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByTokenTypeAndNameAndAvailable(TokenType tokenType, String name, boolean available);

    Optional<Token> findByUserAndTokenType(User user, TokenType tokenType);
}