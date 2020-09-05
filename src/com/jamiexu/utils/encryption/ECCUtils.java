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
     * @return KeySpace
     */
    public static KeySpace genKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
            keyPairGenerator.initialize(571, new SecureRandom());
            KeyPair keyPair = keyPairGenerator.genKeyPair();
            ECPublicKey ecPublicKey = (ECPublicKey) keyPair.getPublic();
            ECPrivateKey ecPrivateKey = (ECPrivateKey) keyPair.getPrivate();
            return new KeySpace(ConvertUtils.bytesToHex(ecPublicKey.getEncoded()),
                    ConvertUtils.bytesToHex(ecPrivateKey.getEncoded()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 公钥加密
     *
     * @param data      待加密数据
     * @param publicKey 公钥
     * @return byte[]
     */
    public static byte[] encrypt(byte[] data, String publicKey) {
        try {
            byte[] bytes = ConvertUtils.hexToBytes(publicKey);
            ECPublicKey ecPublicKey = (ECPublicKey) KeyFactory.getInstance("EC").generatePublic(new X509EncodedKeySpec(bytes));
            Cipher cipher = new NullCipher();
            cipher.init(Cipher.ENCRYPT_MODE, ecPublicKey);
            return cipher.doFinal(data);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 私钥解密
     *
     * @param data       待解密数据
     * @param privateKey 私钥
     * @return byte[]
     */
    public static byte[] decrypt(byte[] data, String privateKey) {
        try {
            byte[] bytes = ConvertUtils.hexToBytes(privateKey);
            ECPrivateKey ecPrivateKey = (ECPrivateKey) KeyFactory.getInstance("EC").generatePrivate(new PKCS8EncodedKeySpec(bytes));
            Cipher cipher = new NullCipher();
            cipher.init(Cipher.ENCRYPT_MODE, ecPrivateKey);
            return cipher.doFinal(data);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
