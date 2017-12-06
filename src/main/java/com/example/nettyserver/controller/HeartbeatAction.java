package com.example.nettyserver.controller;

import com.example.nettyserver.netty.BaseMsg;
import com.example.nettyserver.protobuf.Command;
import com.example.nettyserver.protobuf.Employee;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by Eric on 2017/12/6.
 */
@NettyController
public class HeartbeatAction {
    @ActionMap(key= Command.HeartBeatRea)
    public void heartRes(ChannelHandlerContext ct, BaseMsg message) throws Exception{
        Employee.HeartBeatReq req = Employee.HeartBeatReq.parseFrom(message.getBodyData());

        Employee.HeartBeatRes.Builder heartRes = Employee.HeartBeatRes.newBuilder();
        heartRes.setSID(req.getSID());
        heartRes.setLoginId(req.getLoginId());

        ct.channel().writeAndFlush(heartRes.build());
    }
}
