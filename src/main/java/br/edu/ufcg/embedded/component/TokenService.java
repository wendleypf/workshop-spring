package br.edu.ufcg.embedded.component;

import br.edu.ufcg.embedded.model.Coach;
import br.edu.ufcg.embedded.model.Student;
import br.edu.ufcg.embedded.model.User;
import br.edu.ufcg.embedded.service.CoachService;
import br.edu.ufcg.embedded.service.StudentService;
import br.edu.ufcg.embedded.util.AuthException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.Key;
import java.util.Date;
@Component
public class TokenService {
    private CoachService coachService;
    private StudentService studentService;

    @Autowired
    public TokenService(CoachService coachService, StudentService studentService) {
        this.coachService = coachService;
        this.studentService = studentService;
    }

    private static final String API_SECRET = "Ve7GLoDiKyX3f3XV";
    private static final Long EXPIRATION_SECONDS = 604800L;
    private static final Integer DIVIDER = 1000;

    private String encrypt(final String text) throws Exception {
        Key aesKey = new SecretKeySpec(API_SECRET.getBytes(Charset.forName("UTF-8")), "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, aesKey);

        byte[] encrypted = cipher.doFinal(text.getBytes(Charset.forName("UTF-8")));
        return Base64.encodeBase64String(encrypted);
    }

    private String decrypt(final String text) throws Exception {
        byte[] decodedText = Base64.decodeBase64(text);

        Key aesKey = new SecretKeySpec(API_SECRET.getBytes(Charset.forName("UTF-8")), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, aesKey);

        byte[] decrypted = cipher.doFinal(decodedText);

        return new String(decrypted, Charset.forName("UTF-8"));
    }

    public String generateToken(final User user) {
        long currentTime = new Date().getTime() / DIVIDER;

        JSONObject json = new JSONObject();
        json.put("email", user.getEmail());
        json.put("expires", currentTime + EXPIRATION_SECONDS);

        String token = null;

        try {
            token = encrypt(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return token;
    }

    public User validateToken(final String token) {
        String value = token.replace("Token ", "");

        JSONObject json;
        String email;
        long expires;

        try {
            String decrypted = decrypt(value);
            json = new JSONObject(decrypted);
            email = json.getString("email");
            expires = json.getLong("expires");
        } catch (Exception e) {
            throw new AuthException("Token malformed.");
        }

        long currentTime = new Date().getTime() / DIVIDER;

        if (currentTime > expires) {
            throw new AuthException("Token has expired.");
        }


        Coach coach = coachService.getByEmail(email);
        Student student = studentService.getByEmail(email);

        if (coach != null) {
            return coach;
        } else if (student != null) {
            return student;
        }

        throw new AuthException("User not found.");
    }

    public User getUser(final String token) {
        try {
            return validateToken(token);
        } catch (Exception e) {
            return null;
        }
    }
}
