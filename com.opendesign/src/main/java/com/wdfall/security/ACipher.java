// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ACipher.java

package com.wdfall.security;


// Referenced classes of package com.yeonsoft.typhoon.util.nkia:
//            CipherAES, CipherUtil, CipherWorld

public class ACipher
{

    public ACipher()
    {
    }

    public static String encrypt(String seed, String plain)
    {
        CipherWorld acw = null;
        byte abt[] = null;
        try
        {
            acw = new CipherAES(CipherUtil.generateAESKey(seed, "SHA-1"));
            abt = acw.getEncryptedByte(plain);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return CipherUtil.getBase64EncodeString(abt);
    }

    public static String decrypt(String seed, String msg)
    {
        CipherWorld acw = null;
        byte temp[] = null;
        String result = null;
        try
        {
            acw = new CipherAES(CipherUtil.generateAESKey(seed, "SHA-1"));
            temp = CipherUtil.getBase64DecodeBuffer(msg);
            result = acw.getDecryptedString(temp);
        }
        catch(Exception e)
        {
            result = msg;
        }
        return result;
    }
}
