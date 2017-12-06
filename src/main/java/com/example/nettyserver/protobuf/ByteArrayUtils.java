package com.example.nettyserver.protobuf;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by Eric on 2017/11/13.
 */
public class ByteArrayUtils {
    public static byte[] intToByteArray(int i) throws Exception {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(buf);
        out.writeInt(i);
        byte[] b = buf.toByteArray();
        out.close();
        buf.close();
        return b;
    }

    /** * 借助字节输出流ByteArrayOutputStream来实现字节数组的合并 * */
    public static byte[] streamCopy(List<byte[]> srcArrays) {
        byte[] destAray = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            for (byte[] srcArray : srcArrays) {
                bos.write(srcArray);
            }
            bos.flush();
            destAray = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
            }
        }
        return destAray;
    }
}
