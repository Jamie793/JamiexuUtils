package com.jamiexu.utils.encryption;

import com.jamiexu.utils.convert.ConvertUtils;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.zip.Adler32;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

/**
 * @author Jamiexu or Jamie793
 * @version 1.0
 * 博客 blog.jamiexu.cn
 */

public class EncryptionUtils {


    public static long getAlder32(byte[] bytes){
        Checksum checksumEngine = new Adler32();
        checksumEngine.update(bytes,0,bytes.length);
        return checksumEngine.getValue();
    }





}
