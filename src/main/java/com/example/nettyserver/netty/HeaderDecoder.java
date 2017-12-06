package com.example.nettyserver.netty;

import com.example.nettyserver.NettyserverApplication;
import com.google.protobuf.MessageLite;
import com.example.nettyserver.protobuf.Command;
import com.example.nettyserver.protobuf.Head;
import com.example.nettyserver.protobuf.Employee;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by Eric on 2017/11/15.
 */
public class HeaderDecoder extends ByteToMessageDecoder{

    protected void decode1(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        while (in.readableBytes() > Head.Header_Length) { // 如果可读长度小于包头长度，退出。
            in.markReaderIndex();

            in.readInt();
            int bodyLength = in.readInt() - Head.Header_Length;
            int command = in.readInt();

            NettyserverApplication.logger.info("Received Request command:" + command);
            // 如果可读长度小于body长度，恢复读指针，退出。
            if (in.readableBytes() < bodyLength) {
                in.resetReaderIndex();
                return;
            }

            ByteBuf frame = ctx.alloc().buffer(bodyLength);//这里需要加一位才能读取完整的包体字节数，为毛？
            // 读取body
            ByteBuf bodyByteBuf = in.readBytes(frame);

            byte[] array;

            int readableLen= bodyByteBuf.readableBytes();
            if (bodyByteBuf.hasArray()) {
                array = bodyByteBuf.array();
            } else {
                array = new byte[readableLen];
                bodyByteBuf.getBytes(bodyByteBuf.readerIndex(), array, 0, readableLen);
            }

            //反序列化
            //MessageLite result = decodeBody(command, array);

            BaseMsg result = new BaseMsg(command,array);
            out.add(result);
        }
    }

    private MessageLite decodeBody(int command, byte[] bodyBytes) throws Exception {
        if (command == Command.EmployeeReq) {
            return Employee.EmployeeReq.parseFrom(bodyBytes);
        }
        else if (command == Command.EmployeeRes){//测试netty 客户端用
            return Employee.EmployeeRes.parseFrom(bodyBytes);
        }


        return null; // or throw exception
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
        // 可读长度必须大于基本长度
        if (buffer.readableBytes() >= Head.Header_Length) {
            // 标记包头开始的index
            buffer.markReaderIndex();

            ByteBuf headBytes = ctx.alloc().buffer(Head.Header_Length);
            ByteBuf headBuf = buffer.readBytes(headBytes);
            headBytes.readInt();
            int bodyLength = headBytes.readInt() - Head.Header_Length;
            int command = headBytes.readInt();

            //buffer.readInt();
            //int bodyLength = buffer.readInt() - Head.Header_Length;
            //int command = buffer.readInt();

            // 判断请求数据包数据是否到齐
            if (buffer.readableBytes() < bodyLength) {
                // 还原读指针
                buffer.resetReaderIndex();
                return;
            }

            ByteBuf bodyByteBuf = buffer.readBytes(bodyLength);
            // 读取body
            byte[] array;
            int readableLen= bodyByteBuf.readableBytes();
            if (bodyByteBuf.hasArray()) {
                array = bodyByteBuf.array();
            } else {
                array = new byte[readableLen];
                bodyByteBuf.getBytes(bodyByteBuf.readerIndex(), array, 0, readableLen);
            }

            BaseMsg result = new BaseMsg(command, array);
            out.add(result);
        }
        else{
            System.out.println("Remote host cut down");
        }
    }

}
