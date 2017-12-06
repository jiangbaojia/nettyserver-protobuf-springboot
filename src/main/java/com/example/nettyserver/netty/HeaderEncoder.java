package com.example.nettyserver.netty;

import com.google.protobuf.MessageLite;
import com.example.nettyserver.protobuf.Command;
import com.example.nettyserver.protobuf.Head;
import com.example.nettyserver.protobuf.Employee;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by Eric on 2017/11/15.
 */
public class HeaderEncoder extends MessageToByteEncoder<MessageLite > {

    private static ByteBuf buf = Unpooled.buffer();
    @Override
    public void encode(ChannelHandlerContext ctx, MessageLite  msg, ByteBuf out) throws Exception{
        byte[] body = msg.toByteArray();
        byte[] header = encodeHeader(msg, body.length);

/*        List<byte[]> sd = new ArrayList<>();
        sd.add(header);
        sd.add(body);
        byte[] sendData = ByteArrayUtils.streamCopy(sd);
        buf.writeBytes(sendData);
        out.writeBytes(buf);

        ctx.writeAndFlush(Unpooled.copiedBuffer(sendData));*/

        out.writeBytes(header);
        out.writeBytes(body);
        ctx.flush();

        //ctx.channel().flush();

    }

    private byte[] encodeHeader(MessageLite msg, int bodyLength) throws Exception{

        int command = getCommand(msg);
        Head head = new Head(bodyLength, command, 10);
        byte[] headByte = head.getHeadByte();

        return headByte;
    }

    private int getCommand(MessageLite msg){
        if (msg instanceof Employee.EmployeeRes){
            return Command.EmployeeRes;
        }
        else if (msg instanceof Employee.EmployeeReq){//测试netty客户端用
            return Command.EmployeeReq;
        }
        else if (msg instanceof Employee.LoginReq){
            return Command.LoginReq;
        }
        else if (msg instanceof Employee.HeartBeatReq){
            return Command.HeartBeatRea;
        }
        else if (msg instanceof Employee.LoginRes){
            return Command.LoginRes;
        }
        return 0;
    }
}
