package app.handong.feed.util;


import app.handong.feed.exception.InvalidTokenException;
import app.handong.feed.exception.NoAuthenticatedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
public class TokenFactory {

    @Value("${TOKEN_KEY:${TOKEN_KEY_DEFAULT:#{null}}}")
    String tempKey;

    private static String staticTempKey;

    static int intervalRefreshToken = 86400 * 7; // 7 days
    int intervalAccessToken = 6000;

    @PostConstruct
    public void init() {
        staticTempKey = tempKey;
    }

    public static String issueRefreshToken(String tbuserId) {
        return generateToken(tbuserId, intervalRefreshToken);
    }

    public String issueAccessToken(String refreshToken) throws Exception {
        return generateToken(verifyToken(refreshToken), intervalAccessToken);
    }

    public static String generateToken(String tbuserId, int second) {
        String returnVal = "";
        // 저장해야 하는 정보
        // 유저 아이디값, 만료일.
        long period = 0;
        AES256Cipher aes = new AES256Cipher();
        try {
            NowDate now = new NowDate();
            String due = now.getDue(second);
            System.out.println("tbuserId : " + tbuserId);
            System.out.println("due : " + due);
            returnVal = aes.AES_Encode(staticTempKey, tbuserId + "_" + due);
        } catch (Exception e) {
            System.out.println("error....");
            e.printStackTrace();
        }
        return returnVal;
    }

    public String verifyToken(String token) throws Exception {
        String returnVal = "";
        AES256Cipher aes = new AES256Cipher();
        try {
            // id_만료일
            returnVal = aes.AES_Decode(staticTempKey, token);
        } catch (Exception e) {
            System.out.println("error....");
            e.printStackTrace();
        }
        if ("".equals(returnVal)) {
            throw new NoAuthenticatedException("Not Authenticated User");
        }

        String[] arrayVal = returnVal.split("_");
        String tbuserId = arrayVal[0];
        String due = arrayVal[1];
        NowDate nowDate = new NowDate();
        String now = nowDate.getNow();
//        System.out.println("due : " + due);
//        System.out.println("now : " + now);

        String[] arrayNow = {due, now};
        Arrays.sort(arrayNow);
        if (due.equals(arrayNow[1])) {
            //정상!
            return tbuserId;
        } else {
            //만료!
            throw new InvalidTokenException("Token Expired");
        }
    }
}