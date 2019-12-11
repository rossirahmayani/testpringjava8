package com.rossi.testspringjava8.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author rossirahmayani
 */

@Component
@Slf4j
public class EncryptUtils {

    private static final String UTF_8 = "UTF-8";
    private static final String SECRET_KEY_1 = "#f!*8#tKmBKazxzz";
    private static final String SECRET_KEY_2 = "MS&+427VhZA=#J87";

    private IvParameterSpec ivParameterSpec;
    private SecretKeySpec secretKeySpec;
    private Cipher cipher;


    public EncryptUtils() {
        try {
            ivParameterSpec = new IvParameterSpec(SECRET_KEY_1.getBytes("UTF-8"));
            secretKeySpec = new SecretKeySpec(SECRET_KEY_2.getBytes("UTF-8"), "AES");
            cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException ex) {
            log.error("Failed to create DefaultEncryption instance!", ex);
        }
    }


    public String encrypt(String toBeEncrypt) {
        if (toBeEncrypt == null) {
            return null;
        }
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] encrypted = cipher.doFinal(toBeEncrypt.getBytes());
            return org.apache.tomcat.util.codec.binary.Base64.encodeBase64String(encrypted);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException ex) {
            log.error("Failed to encrypt: \"" + toBeEncrypt + "\"", ex);
        }
        return null;
    }


    public String decrypt(String encrypted) {
        if (encrypted == null) {
            return null;
        }
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] decryptedBytes = cipher.doFinal(Base64.decodeBase64(encrypted));
            return new String(decryptedBytes);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException ex) {
            log.error("Failed to decrypt: \"" + encrypted + "\"", ex);
        }
        return null;
    }

    public static String hex(byte[] input) {
        char[] HEX_TABLE = new char[]{
                '0', '1', '2', '3', '4', '5', '6', '7',
                '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        // create a StringBuffer 2x the size of the hash array
        StringBuilder sb = new StringBuilder(input.length * 2);
        // retrieve the byte array data, convert it to hex
        // and add it to the StringBuffer
        for (int i = 0; i < input.length; i++) {
            sb.append(HEX_TABLE[(input[i] >> 4) & 0xf]);
            sb.append(HEX_TABLE[input[i] & 0xf]);
        }
        return sb.toString();
    }


    public String getSingleErrorMessage(List<FieldError> errorFields, List<ObjectError> getGlobalErrors) {
        if (errorFields != null && !errorFields.isEmpty()) {
            for (FieldError fieldError : errorFields) {
                log.error("error "+fieldError.getDefaultMessage());
                return fieldError.getDefaultMessage();
            }
        }
        if (getGlobalErrors != null && getGlobalErrors.size() > 0) {
            List<String> messages = new ArrayList<>();
            for (ObjectError objError : getGlobalErrors) {
                log.error("Object name: {}, message: {}", objError.getObjectName(), objError.getDefaultMessage());
                messages.add(objError.getDefaultMessage());
                return objError.getDefaultMessage();
            }
        }
        return null;
    }


    public String getSingleErrorMessage(List<FieldError> errorFields) {
        if (errorFields != null && !errorFields.isEmpty()) {
            for (FieldError fieldError : errorFields) {
                return fieldError.getDefaultMessage();
            }
        }
        return null;
    }

    public String getUniqueID() {
        UUID uniqueKey = UUID.randomUUID();
        return uniqueKey.toString();
    }

    public String getElementWords(String ...elements){
        StringBuilder elementWords = new StringBuilder();
        for(String element: elements){
            elementWords.append(element);
        }
        return elementWords.toString();
    }

    public String generateWordHMACSHA256(String text, String secretKey){
        String result = null;
        try {
            byte[] b = secretKey.getBytes(Charset.forName("UTF-8"));//new BigInteger(secureSecret, 16).toByteArray();
            SecretKey key = new SecretKeySpec(b, "HmacSHA256");
            Mac m = Mac.getInstance("HmacSHA256");
            m.init(key);
            m.update(text.getBytes("ISO-8859-1"));
            byte[] mac = m.doFinal();
            result = hex(mac);
        } catch (Exception e) {
            log.error("Hash HMAC SHA256 : {}", e);
        }
        return result;
    }


    public String generateWordSHA1(String text) {
        try {
            java.security.MessageDigest md;
            md = java.security.MessageDigest.getInstance("SHA-1");
            md.update(text.getBytes("iso-8859-1"), 0, text.length());
            byte[] sha1hash = md.digest();
            return toHex(sha1hash);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            log.error("Hash SHA1 : {}", ex);
        }
        return "";
    }


    private String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            String paddingFormat = "%0" + paddingLength + "d";
            return String.format(paddingFormat, 0) + hex;
        } else {
            return hex;
        }
    }

    @SuppressWarnings("unchecked")
    public String mapToQueryParamsUri(Map<String, Object> map) {
        StringBuilder sb = new StringBuilder();
        Iterator<?> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> pairs = (Map.Entry<String, Object>) it.next();
            try {
                sb.append(URLEncoder.encode((String) pairs.getKey(), UTF_8)).append("=");
                sb.append(URLEncoder.encode(pairs.getValue() != null ? String.valueOf(pairs.getValue()) : "", UTF_8));

            } catch (UnsupportedEncodingException ex) {
                log.error("Exception Error ", ex);
            }
            if (it.hasNext()) {
                sb.append("&");
            }
        }
        return sb.toString();
    }

}
