package com.epam.hotel.dao.util;

import org.apache.commons.codec.digest.DigestUtils;

public class EncryptMD5 {

    public static String encrypt(String string){

        return DigestUtils.md5Hex(string);
    }

}
