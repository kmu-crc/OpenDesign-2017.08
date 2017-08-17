// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CipherUtil.java
package com.wdfall.security;

import java.security.MessageDigest;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class CipherUtil
{

    public CipherUtil()
    {
    }

    public static String asHex(byte buf[])
    {
        StringBuffer strbuf = new StringBuffer(buf.length * 2);
        for(int i = 0; i < buf.length; i++)
        {
            if((buf[i] & 0xff) < 16)
                strbuf.append("0");
            strbuf.append(Long.toString(buf[i] & 0xff, 16));
        }

        return strbuf.toString();
    }

    public static byte[] generateAESKey(String seed)
        throws Exception
    {
        MessageDigest md5 = null;
        byte digest[] = null;
        md5 = MessageDigest.getInstance("MD5");
        md5.update(seed.getBytes());
        return md5.digest();
    }

    public static byte[] generateAESKey(String seed, String hashtype)
        throws Exception
    {
        MessageDigest md5 = null;
        byte digest[] = null;
        md5 = MessageDigest.getInstance(hashtype);
        md5.update(seed.getBytes());
        byte ret[] = new byte[16];
        System.arraycopy(md5.digest(), 0, ret, 0, 16);
        return ret;
    }

    public static byte[] generateDESKey(String seed)
        throws Exception
    {
        MessageDigest md5 = null;
        byte digest[] = null;
        md5 = MessageDigest.getInstance("MD5");
        md5.update(seed.getBytes());
        byte ret[] = new byte[8];
        System.arraycopy(md5.digest(), 0, ret, 0, 8);
        return ret;
    }

    public static byte[] generateDESKey(String seed, String hashtype)
        throws Exception
    {
        MessageDigest md5 = null;
        byte digest[] = null;
        md5 = MessageDigest.getInstance(hashtype);
        md5.update(seed.getBytes());
        byte ret[] = new byte[8];
        System.arraycopy(md5.digest(), 0, ret, 0, 8);
        return ret;
    }

    public static String getBase64EncodeString(byte bytemsg[])
    {
        return (new BASE64Encoder()).encode(bytemsg);
    }

    public static byte[] getBase64DecodeBuffer(String msg)
        throws Exception
    {
        return (new BASE64Decoder()).decodeBuffer(msg);
    }
}
