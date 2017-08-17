// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CipherDES.java
package com.wdfall.security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

// Referenced classes of package com.yeonsoft.typhoon.util.nkia:
//            CipherWorld, CipherUtil

public class CipherDES
    implements CipherWorld
{

    public CipherDES(byte pass[])
    {
        key = null;
        key = pass;
    }

    public byte[] getEncryptedByte(String message)
        throws Exception
    {
        KeyGenerator kgen = KeyGenerator.getInstance("DES");
        kgen.init(56);
        SecretKeySpec skeySpec = new SecretKeySpec(key, "DES");
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(1, skeySpec);
        byte encrypted[] = cipher.doFinal(message.getBytes("euc-kr"));
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
        KeyGenerator kgen = KeyGenerator.getInstance("DES");
        kgen.init(56);
        SecretKeySpec skeySpec = new SecretKeySpec(key, "DES");
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(2, skeySpec);
        byte original[] = cipher.doFinal(message);
        return new String(original, "euc-kr");
    }

    public String getDecryptedString(String message)
        throws Exception
    {
        return getDecryptedString(message.getBytes());
    }

    private final String algorithm = "DES";
    private final String mod = "ECB";
    private final String padding = "PKCS5Padding";
    private byte key[];
}
