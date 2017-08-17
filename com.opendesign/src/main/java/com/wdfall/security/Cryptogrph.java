// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Cryptogrph.java
package com.wdfall.security;


public class Cryptogrph
{

    public Cryptogrph()
    {
    }

    public String decodeKey(String meKey)
        throws Exception
    {
        String decode_str = null;
        if(meKey == null || meKey.equals(""))
            return "";
        meKey = meKey.trim();
        byte inbuf[];
        int num_padings;
        int size;
        inbuf = meKey.getBytes();
        num_padings = 0;
        size = inbuf.length;
        if(size == 0) {
            throw new Exception("invalid ecoded string!");
        }
        
        if(inbuf[inbuf.length - 1] == 61)
        {
            size--;
            num_padings++;
            if(inbuf[inbuf.length - 2] == 61)
            {
                size--;
                num_padings++;
            }
        }
        byte outbuf[] = new byte[(inbuf.length / 4) * 3 - num_padings];
        int inpos = 0;
        int outpos = 0;
        do
        {
            if(size <= 0)
                break;
            byte a = pem_convert_array[inbuf[inpos++] & 0xff];
            byte b = pem_convert_array[inbuf[inpos++] & 0xff];
            outbuf[outpos++] = (byte)(a << 2 & 0xfc | b >>> 4 & 3);
            if(inbuf[inpos] == 61)
                break;
            a = b;
            b = pem_convert_array[inbuf[inpos++] & 0xff];
            outbuf[outpos++] = (byte)(a << 4 & 0xf0 | b >>> 2 & 0xf);
            if(inbuf[inpos] == 61)
                break;
            a = b;
            b = pem_convert_array[inbuf[inpos++] & 0xff];
            outbuf[outpos++] = (byte)(a << 6 & 0xc0 | b & 0x3f);
            size -= 4;
        } while(true);
        decode_str = new String(outbuf);
        return decode_str.trim();
    }

    public byte[] encode8to6(byte inbuf[])
    {
        if(inbuf.length == 0)
            return inbuf;
        byte outbuf[] = new byte[((inbuf.length + 2) / 3) * 4];
        int inpos = 0;
        int outpos = 0;
        for(int size = inbuf.length; size > 0; size -= 3)
            if(size == 1)
            {
                byte a = inbuf[inpos++];
                byte b = 0;
                byte c = 0;
                outbuf[outpos++] = (byte)pem_array[a >>> 2 & 0x3f];
                outbuf[outpos++] = (byte)pem_array[(a << 4 & 0x30) + (b >>> 4 & 0xf)];
                outbuf[outpos++] = 61;
                outbuf[outpos++] = 61;
            } else
            if(size == 2)
            {
                byte a = inbuf[inpos++];
                byte b = inbuf[inpos++];
                byte c = 0;
                outbuf[outpos++] = (byte)pem_array[a >>> 2 & 0x3f];
                outbuf[outpos++] = (byte)pem_array[(a << 4 & 0x30) + (b >>> 4 & 0xf)];
                outbuf[outpos++] = (byte)pem_array[(b << 2 & 0x3c) + (c >>> 6 & 3)];
                outbuf[outpos++] = 61;
            } else
            {
                byte a = inbuf[inpos++];
                byte b = inbuf[inpos++];
                byte c = inbuf[inpos++];
                outbuf[outpos++] = (byte)pem_array[a >>> 2 & 0x3f];
                outbuf[outpos++] = (byte)pem_array[(a << 4 & 0x30) + (b >>> 4 & 0xf)];
                outbuf[outpos++] = (byte)pem_array[(b << 2 & 0x3c) + (c >>> 6 & 3)];
                outbuf[outpos++] = (byte)pem_array[c & 0x3f];
            }

        return outbuf;
    }

    private static final char pem_array[] = {
        'B', 'A', 'C', 'I', 'E', 'K', 'F', 'G', 'D', 'H', 
        'J', 'L', 'n', 'm', 'o', 'u', 'q', 'w', 'r', 's', 
        'p', 't', 'v', 'x', 'y', 'z', '2', '1', '3', '9', 
        '5', '+', '6', '7', '4', '8', '0', '/', 'N', 'M', 
        'O', 'U', 'Q', 'W', 'R', 'S', 'P', 'T', 'V', 'X', 
        'Y', 'Z', 'b', 'a', 'c', 'i', 'e', 'k', 'f', 'g', 
        'd', 'h', 'j', 'l'
    };
    private static final byte pem_convert_array[];

    static 
    {
        pem_convert_array = new byte[256];
        for(int i = 0; i < 255; i++)
            pem_convert_array[i] = -1;

        for(int i = 0; i < pem_array.length; i++)
            pem_convert_array[pem_array[i]] = (byte)i;

    }
}
