package org.example.capstone3.Service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {

    // In-memory storage for OTPs. For a real application, consider a database or distributed cache.
    // Key: entityType_entityId_operation (e.g., "ADVERTISER_1_UPDATE")
    // Value: OTP string
    private final Map<String, String> otpStore = new ConcurrentHashMap<>();

    public String generateOtp() {
        Random random = new Random();
        int otp = 100_000 + random.nextInt(900_000);
        return String.valueOf(otp);
    }

    public void storeOtp(String key, String otp) {
        otpStore.put(key, otp);
    }

    public boolean verifyOtp(String key, String otp) {
        String storedOtp = otpStore.get(key);
        if (storedOtp != null && storedOtp.equals(otp)) {
            otpStore.remove(key); // OTP consumed after successful verification
            return true;
        }
        return false;
    }
}