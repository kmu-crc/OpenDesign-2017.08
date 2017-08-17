// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CipherWorld.java
package com.wdfall.security;


public interface CipherWorld
{

    public abstract byte[] getEncryptedByte(String s)
        throws Exception;

    public abstract String getEncryptedHexString(String s)
        throws Exception;

    public abstract String getDecryptedString(byte abyte0[])
        throws Exception;

    public abstract String getDecryptedString(String s)
        throws Exception;
}
