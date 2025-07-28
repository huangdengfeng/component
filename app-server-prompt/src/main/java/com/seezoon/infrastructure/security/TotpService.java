package com.seezoon.infrastructure.security;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class TotpService {

    private static final int CODE_LENGTH = 6;
    /**
     * 允许前一分钟和后一分钟，相当于是2分钟有效
     */
    private static final int TIME_STEP = 60; // 1分钟有效期
    private static final String CRYPTO_ALGORITHM = "HmacSHA256";
    private static final int KEY_LENGTH = 32; // 32字节密钥

    // 使用静态 final 实例确保线程安全
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    /**
     * 生成随机密钥
     */
    public byte[] generateSecretKey() {
        byte[] bytes = new byte[KEY_LENGTH];
        SECURE_RANDOM.nextBytes(bytes);
        return bytes;
    }

    /**
     * 生成验证码,为了安全使用20字节以上密钥
     */
    public String generateCode(@NotEmpty @Size(min = 20) byte[] secretKey) {
        long timeStep = System.currentTimeMillis() / 1000L / TIME_STEP;
        return generateCode(secretKey, timeStep);
    }

    /**
     * 验证验证码
     */
    public boolean validateCode(@NotEmpty byte[] secretKey, @NotEmpty String code) {
        long timeStep = System.currentTimeMillis() / 1000L / TIME_STEP;
        // 验证当前时间步长的验证码
        if (code.equals(generateCode(secretKey, timeStep))) {
            return true;
        }
        // 验证前一个时间步长的验证码（允许2分钟的误差）
        if (code.equals(generateCode(secretKey, timeStep - 1))) {
            return true;
        }
        return false;
    }

    private String generateCode(byte[] secretKey, long timeStep) {
        try {
            byte[] timeBytes = longToBytes(timeStep);

            Mac mac = Mac.getInstance(CRYPTO_ALGORITHM);
            SecretKeySpec keySpec = new SecretKeySpec(secretKey, CRYPTO_ALGORITHM);
            mac.init(keySpec);
            byte[] hash = mac.doFinal(timeBytes);

            int offset = hash[hash.length - 1] & 0xf;
            int binary = ((hash[offset] & 0x7f) << 24) |
                    ((hash[offset + 1] & 0xff) << 16) |
                    ((hash[offset + 2] & 0xff) << 8) |
                    (hash[offset + 3] & 0xff);

            int otp = binary % (int) Math.pow(10, CODE_LENGTH);
            return String.format("%0" + CODE_LENGTH + "d", otp);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to generate TOTP code", e);
        }
    }

    private byte[] longToBytes(long l) {
        byte[] result = new byte[8];
        for (int i = 7; i >= 0; i--) {
            result[i] = (byte) (l & 0xFF);
            l >>= 8;
        }
        return result;
    }
} 