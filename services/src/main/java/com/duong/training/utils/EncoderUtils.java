/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.duong.training.utils;

import org.apache.commons.codec.binary.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;

/**
 * Created by DucTD on 17/4/2020
 */
public class EncoderUtils {

    private String PRIVATE_KEY = "";
    public static String createSHA512Hash(String s, String privateKey) throws Exception {

        String generatedHash = null;
        try {
            Signature sig = Signature.getInstance("SHA512withRSA");
//            sig.initSign(privateKey);

            sig.update(s.getBytes(StandardCharsets.UTF_8));
            byte[] signature = sig.sign();






            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] bytes = md.digest(s.getBytes(StandardCharsets.UTF_8));
            generatedHash = Hex.encodeHexString(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedHash;
    }

    public static String buildSignature(Integer id, String dateTime, String userName) {
        StringBuilder signature = new StringBuilder();
        signature.append(Parameters.ID + "=").append(id).append("|");
        signature.append(Parameters.TIME + "=").append(dateTime).append("|");
        signature.append(Parameters.USER_NAME + "=").append(userName);
        return signature.toString();
    }
    

}
