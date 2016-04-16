package security

import java.security.MessageDigest

/**
 * Created by Farzin on 4/14/2016.
 */
class Encoding {

    public static String md5(String text) {

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(text.getBytes());

        byte[] byteData = md.digest();
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            String hex = Integer.toHexString(0xff & byteData[i]);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }

        hexString
    }
}
