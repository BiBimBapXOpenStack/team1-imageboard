package bibimbap.openstack.imageboard.util.file.auth;

import lombok.Data;
import lombok.Getter;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Component
@Getter
public class AuthManager {

    private Token token;

    @Data
    public class TokenRequest {
        private Auth auth = new Auth();

        @Data
        public class Auth {
            private String tenantId;
            private PasswordCredentials passwordCredentials = new PasswordCredentials();
        }

        @Data
        public class PasswordCredentials {
            private String username;
            private String password;
        }
    }

    public boolean isTokenValidate() {
        return token!=null && token.getExpires().after(new Date());
    }

    public void generateToken() throws JSONException, ParseException {

        if (isTokenValidate()) return;

        RestTemplate restTemplate = new RestTemplate();
        String URL = "https://api-identity.infrastructure.cloud.toast.com/v2.0/tokens";
        String tenantId = "35682dae0076479ab712dbb328468535";
        String username = "anjm1020@gmail.com";
        String password = "dkswoals2010";

        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.getAuth().setTenantId(tenantId);
        tokenRequest.getAuth().getPasswordCredentials().setUsername(username);
        tokenRequest.getAuth().getPasswordCredentials().setPassword(password);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        HttpEntity<TokenRequest> httpEntity = new HttpEntity<>(tokenRequest, headers);

        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.POST, httpEntity, String.class);

        String res = response.getBody();
        JSONObject obj = new JSONObject(res);
        JSONObject resToken = obj.getJSONObject("access").getJSONObject("token");

        String dateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        token = Token.builder()
                .id(resToken.getString("id"))
                .expires(new SimpleDateFormat(dateFormat).parse(resToken.getString("expires")))
                .tenantId(resToken.getJSONObject("tenant").getString("id"))
                .build();

    }

}
