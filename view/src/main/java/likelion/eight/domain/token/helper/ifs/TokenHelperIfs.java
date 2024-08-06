package likelion.eight.domain.token.helper.ifs;

import likelion.eight.domain.token.model.Token;

import java.util.Map;

public interface TokenHelperIfs {

    Token issueAccessToken(Map<String, Object> data);

    Token issueRefreshToken(Map<String, Object> data);

    Map<String, Object> validationTokenWithThrow(String token);

}
