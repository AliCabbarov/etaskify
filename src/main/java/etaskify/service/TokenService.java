package etaskify.service;


import etaskify.model.entity.User;

public interface TokenService {
    String generateAndSaveRefreshToken(User user);
    String generateAndSaveConfirmationToken(User user);

}
