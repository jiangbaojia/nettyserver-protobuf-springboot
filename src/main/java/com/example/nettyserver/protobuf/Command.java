package com.example.nettyserver.protobuf;

/**
 * Created by Eric on 2017/11/14.
 */
public class Command {
    public static final int EmployeeReq = 0x04070001;
    public static final int EmployeeRes = 0x04070002;

    public static final int LoginReq = 0x04070003;
    public static final int LoginRes = 0x04070004;

    public static final int HeartBeatRea = 0x04070005;
    public static final int HeartBeatRes = 0x04070006;
}
