package com.gemalto.gemaltoapi.data;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * Created by jacksondeng on 16/12/18.
 */


public class DataEncryption {
    private Cipher cipher;
    private static SecretKeySpec secretKeySpec;
    private static IvParameterSpec ivParameterSpec;
    private boolean do_encrypt = true;


    public DataEncryption() {
        String saltasString = "gemaltoassignment2018";
        String paddedskey = ("jacksondeng" + saltasString).substring(0,16);
        secretKeySpec = new SecretKeySpec(paddedskey.getBytes(),"AES/CBC/PKCS5Padding");
        ivParameterSpec = new IvParameterSpec((saltasString.substring(0,16)).getBytes());
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (Exception e){
            //e.printStackTrace();
        }
    }

    public void encryptUser(User user){
        Username username = new Username(user.getrealUsername());
        username.setTitle(encrypt(username.getTitle()));
        username.setFirstName(encrypt(username.getFirstName()));
        username.setLastName(encrypt(username.getLastName()));
        user.setUsername(username);
        user.setDob(new Dob((encrypt(user.getDobStr()))));
        user.setEmail(encrypt(user.getEmail()));
        user.setSeed(encrypt(user.getSeed()));
        user.setGender(encrypt(user.getGender()));
        user.setProfilePicUrl(encrypt(user.getProfilePicUrl()));
    }

    public void decryptUser(User user){
        Username username = new Username(user.getrealUsername());
        username.setTitle(decrypt(username.getTitle()));
        username.setFirstName(decrypt(username.getFirstName()));
        username.setLastName(decrypt(username.getLastName()));
        user.setUsername(username);
        user.setDob(new Dob((decrypt(user.getDobStr()))));
        user.setEmail(decrypt(user.getEmail()));
        user.setSeed(decrypt(user.getSeed()));
        user.setGender(decrypt(user.getGender()));
        user.setProfilePicUrl(decrypt(user.getProfilePicUrl()));
    }

    public String encrypt(String toEncrypt) {
        if (!do_encrypt) {
            return toEncrypt;
        }
        byte[] encrypted;
        try {
            cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec,ivParameterSpec);
            encrypted = cipher.doFinal(toEncrypt.getBytes());
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
        return Base64.encodeToString(encrypted,Base64.DEFAULT);
    }

    public String decrypt(String toDecrypt)  {
        if (!do_encrypt) {
            return toDecrypt;
        }
        byte[] decrypted;
        try {
            cipher.init(Cipher.DECRYPT_MODE,secretKeySpec,ivParameterSpec);
            decrypted = cipher.doFinal(Base64.decode(toDecrypt,Base64.DEFAULT));
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
        return new String(decrypted);
    }
}

