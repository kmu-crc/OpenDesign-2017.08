// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CipherAES.java
package com.wdfall.security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

// Referenced classes of package com.yeonsoft.typhoon.util.nkia:
//            CipherWorld, CipherUtil

public class CipherAES
    implements CipherWorld
{

    public CipherAES(byte pass[])
    {
        key = null;
        key = pass;
    }

    public byte[] getEncryptedByte(String message)
        throws Exception
    {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(1, skeySpec);
        byte encrypted[] = cipher.doFinal(message.getBytes("utf-8"));
        return encrypted;
    }

    public String getEncryptedHexString(String message)
        throws Exception
    {
        return CipherUtil.asHex(getEncryptedByte(message));
    }

    public String getDecryptedString(byte message[])
        throws Exception
    {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(2, skeySpec);
        byte original[] = cipher.doFinal(message);
        return new String(original, "utf-8");
    }

    public String getDecryptedString(String message)
        throws Exception
    {
        return getDecryptedString(message.getBytes());
    }

    private final String algorithm = "AES";
    private final String mod = "ECB";
    private final String padding = "PKCS5Padding";
    private byte key[];
}
