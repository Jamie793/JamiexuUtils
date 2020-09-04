package com.jamiexu.utils.encryption;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author Jamiexu/Jamie793
 * @version 1.0
 * @date 2020/9/4
 * @time 20:36
 * @blog https://blog.jamiexu.cn
 **/
public class RsaUtils {


    public static String[] genKeyPair() {
        String[] strings = new String[2];
        try {
            // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(2048, new SecureRandom());
            // 生成一个密钥对，保存在keyPair中
            KeyPair keyPair = keyPairGen.generateKeyPair();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
            strings[0] = new String(Base64.getEncoder().encodeToString(publicKey.getEncoded()));
            // 得到私钥字符串
            strings[1] = new String(Base64.getEncoder().encodeToString((privateKey.getEncoded())));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        return strings;
    }


    /**
     * RSA公钥加密
     *
     * @param str       密字符串
     * @param publicKey 公钥
     * @return String 密文
     */
    public static String encrypt(String str, String publicKey) {
        String result = null;
        try {
            //base64编码的公钥
            byte[] decoded = Base64.getDecoder().decode(publicKey);
            RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
            //RSA加密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            result = Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)));
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * RSA私钥解密
     *
     * @param str        加密字符串
     * @param privateKey 私钥
     * @return String 密文
     */
    public static String decrypt(String str, String privateKey) {
        String reslut = null;
        try {
            //64位解码加密后的字符串
            byte[] inputByte = Base64.getDecoder().decode(str.getBytes(StandardCharsets.UTF_8));
            //base64编码的私钥
            byte[] decoded = Base64.getDecoder().decode(privateKey);
            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
            //RSA解密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            reslut = new String(cipher.doFinal(inputByte));
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return reslut;
    }


}
