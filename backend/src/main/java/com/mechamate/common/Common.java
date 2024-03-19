//fathima
package com.mechamate.common;

import org.bson.types.ObjectId;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Common {

    public static String getSha256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }


    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }


    public static String toTitleCase(String string) {
        String ret = (string == null) ? "" : string.trim();
        try {
            ret = ret.substring(0, 1).toUpperCase() + ret.substring(1).toLowerCase();
        } catch (Exception ignore) {}
        return ret;
    }

    public static List<ObjectId> getPredictionModelObjectIds(List<String> predictionModels) {
        List<ObjectId> pModels = new ArrayList<>();
        for(String m: predictionModels) {
            try {
                ObjectId oid = new ObjectId(m);
                pModels.add(oid);
            } catch (Exception e) {}
        }
        return pModels;
    }

}
