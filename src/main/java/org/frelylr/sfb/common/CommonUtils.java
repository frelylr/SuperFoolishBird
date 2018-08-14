package org.frelylr.sfb.common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;
import org.frelylr.sfb.session.UserPermission;

public class CommonUtils {

    /**
     * password encryption
     */
    public static String encrypt(String str) {

        try {
            return new String(Hex.encodeHex(MessageDigest.getInstance("SHA-512").digest(str.getBytes())));
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * convert bytes into string
     */
    public static String byteToString(byte[] bytes) throws UnsupportedEncodingException {

        return new String(bytes, Constants.UNICODE_UTF_8);
    }

    /**
     * parsing user permissions
     */
    public static UserPermission calculatePermission(Integer n) {

        UserPermission userPermission = new UserPermission();

        if (n == null) {
            return userPermission;
        }

        String binaryString = String.format("%04d", Integer.parseInt(Integer.toBinaryString(n)));
        String[] s = StringUtils.reverse(binaryString).split(StringUtils.EMPTY);
        userPermission.setSelect(Constants.BINARY_VALUE_1.equals(s[Constants.INDEX_0]));
        userPermission.setInsert(Constants.BINARY_VALUE_1.equals(s[Constants.INDEX_1]));
        userPermission.setUpdate(Constants.BINARY_VALUE_1.equals(s[Constants.INDEX_2]));
        userPermission.setDelete(Constants.BINARY_VALUE_1.equals(s[Constants.INDEX_3]));

        return userPermission;
    }
}
