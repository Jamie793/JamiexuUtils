package com.jamiexu.utils.encryption;

import com.jamiexu.utils.convert.ConvertUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

/**
 * @author Jamiexu/Jamie793
 * @version 1.0
 * @date 2020/9/4
 * @time 20:36
 * @blog https://blog.jamiexu.cn
 **/
public class RSAUtils {


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
            strings[0] = ConvertUtils.bytesToHex(publicKey.getEncoded());
            // 得到私钥字符串
            strings[1] = ConvertUtils.bytesToHex(privateKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        return strings;
    }


    /**
     * RSA公钥加密
     *
     * @param bytes     待加密数据
     * @param publicKey 公钥
     * @return byte[] 加密后数据
     */
    public static byte[] encrypt(byte[] bytes, String publicKey) {
        byte[] result = null;
        try {
            byte[] encode = ConvertUtils.hexToBytes(publicKey);
            RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(encode));
            //RSA加密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            result = cipher.doFinal(bytes);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * RSA私钥解密
     *
     * @param bytes      待解密数组
     * @param privateKey 私钥
     * @return byte[] 解密后数据
     */
    public static byte[] decrypt(byte[] bytes, String privateKey) {
        byte[] bytes1 = null;
        try {
            byte[] decoded = ConvertUtils.hexToBytes(privateKey);
            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
            //RSA解密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            bytes1 = cipher.doFinal(bytes);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return bytes1;
    }


    /**
     * RSA公钥加密文件
     *
     * @param path 待加密文件路径
     * @param path2 加密后输出路径
     * @param publicKey 公钥
     * @return boolean 状态
     */
    public static boolean encryptFile(String path, String path2, String publicKey) {
        boolean status = false;
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(path);
            fileOutputStream = new FileOutputStream(path2);
            byte[] encode = ConvertUtils.hexToBytes(publicKey);
            RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(encode));
            //RSA加密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            byte[] bytes = new byte[1024*8];
            int len;
            while((len = fileInputStream.read(bytes))!=-1){
                fileOutputStream.write(cipher.doFinal(bytes,0,len));
            }
            status = new File(path2).exists();
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                IOException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        } finally {
            if(fileInputStream != null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(fileOutputStream != null){
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return status;
    }


    /**
     * RSA私钥解密文件
     *
     * @param path 待解密文件路径
     * @param path2 解密后输出路径
     * @param privateKey 私钥
     * @return boolean 状态
     */
    public static boolean decryptFile(String path, String path2, String privateKey) {
        boolean status = false;
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(path);
            fileOutputStream = new FileOutputStream(path2);
            byte[] decoded = ConvertUtils.hexToBytes(privateKey);
            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
            //RSA解密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            byte[] bytes = new byte[1024*8];
            int len;
            while((len = fileInputStream.read(bytes))!=-1){
                fileOutputStream.write(cipher.doFinal(bytes,0,len));
            }
            status = new File(path2).exists();
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
                | IOException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        } finally {
            if(fileInputStream != null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(fileOutputStream != null){
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return status;
    }


}
