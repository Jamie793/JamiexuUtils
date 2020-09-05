package com.jamiexu.utils.encryption;

import com.jamiexu.utils.convert.ConvertUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NullCipher;
import java.security.*;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author Jamiexu/Jamie793
 * @version 1.0
 * @date 2020/9/5
 * @time 20:00
 * @blog https://blog.jamiexu.cn
 **/
public class ECCUtils {


    /**
     * 生成公钥和私钥
     *
     * @return String[]
     */
    public static String[] genKeyPair() {
        String[] strings = new String[2];
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
            keyPairGenerator.initialize(571,new SecureRandom());
            KeyPair keyPair = keyPairGenerator.genKeyPair();
            ECPublicKey ecPublicKey = (ECPublicKey) keyPair.getPublic();
            ECPrivateKey ecPrivateKey = (ECPrivateKey) keyPair.getPrivate();
            strings[0] = ConvertUtils.bytesToHex(ecPublicKey.getEncoded());
            strings[1] = ConvertUtils.bytesToHex(ecPrivateKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return strings;
    }


    /**
     * 公钥加密
     *
     * @param data      待加密数据
     * @param publicKey 公钥
     * @return byte[]
     */
    public static byte[] encrypt(byte[] data, String publicKey) {
        return doFinal(data, publicKey, Cipher.ENCRYPT_MODE);
    }


    /**
     * 私钥解密
     *
     * @param data       待解密数据
     * @param privateKey 私钥
     * @return byte[]
     */
    public static byte[] decrypt(byte[] data, String privateKey) {
        return doFinal(data, privateKey, Cipher.DECRYPT_MODE);
    }


    private static byte[] doFinal(byte[] data, String key, int mode) {
        try {
            byte[] bytes = ConvertUtils.hexToBytes(key);
            ECPublicKey ecPublicKey = null;
            if (mode == Cipher.ENCRYPT_MODE)
                ecPublicKey = (ECPublicKey) KeyFactory.getInstance("EC").generatePublic(new X509EncodedKeySpec(bytes));
            else
                ecPublicKey = (ECPublicKey) KeyFactory.getInstance("EC").generatePublic(new PKCS8EncodedKeySpec(bytes));
            Cipher cipher = new NullCipher();
            cipher.init(mode, ecPublicKey);
            return cipher.doFinal(data);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
