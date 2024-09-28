package etaskify.model.contant;

import org.springframework.beans.factory.annotation.Value;

public class TokenTime {
    @Value("${app.time.token.refresh}")
    public static long REFRESH_TOKEN_TIME;
    @Value("${app.time.token.confirmation}")
    public static long CONFIRMATION_TOKEN_TIME;
}
