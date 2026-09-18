package com.java.md_luxury_2.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class PhoneHashUtil {

    private static final byte[] HMAC_KEY = "khoa-bi-mat-rieng-cho-hmac-tim-kiem".getBytes();

    // Cùng một số điện thoại đã chuẩn hoá luôn sinh ra cùng một mã băm
    public static String hash(String normalizedPhone) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(HMAC_KEY, "HmacSHA256"));
            byte[] result = mac.doFinal(normalizedPhone.getBytes());
            return Base64.getEncoder().encodeToString(result);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi tạo mã băm số điện thoại", e);
        }
    }

    // Chuẩn hoá số điện thoại về định dạng chung (+84...)
    public static String normalize(String rawPhone) {
        if (rawPhone == null) return null;
        String digits = rawPhone.replaceAll("[^0-9]", "");
        if (digits.startsWith("0")) {
            return "+84" + digits.substring(1);
        }
        return digits.startsWith("84") ? "+" + digits : "+84" + digits;
    }
}