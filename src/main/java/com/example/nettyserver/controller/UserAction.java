package com.example.nettyserver.controller;

import com.example.nettyserver.entity.User;
import com.example.nettyserver.netty.BaseMsg;
import com.example.nettyserver.netty.ChannelMapObject;
import com.example.nettyserver.netty.NettyChannelMap;
import com.example.nettyserver.protobuf.Command;
import com.example.nettyserver.protobuf.Employee;
import com.example.nettyserver.service.EmployeeService;
import com.example.nettyserver.service.UserService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Eric on 2017/12/6.
 */
@NettyController()
public class UserAction {

    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeService employeeService;

    //登录请求
    @ActionMap(key= Command.LoginReq)
    public String login(ChannelHandlerContext ct, BaseMsg message) throws Exception{
        Employee.LoginReq req = Employee.LoginReq.parseFrom(message.getBodyData());
        Employee.LoginRes.Builder res = Employee.LoginRes.newBuilder();

        List<User> resData = userService.getUser(req.getLoginAccount(), req.getPwd());

        if (null != resData && resData.size() == 1){
            //新增到信道映射表
            ChannelMapObject channelMapObject = new ChannelMapObject((SocketChannel) ct.channel());
            NettyChannelMap.add(req.getLoginAccount(), channelMapObject);

            res.setReturnCode(99999);
            User user = resData.get(0);
            res.setLoginId(user.getId().intValue());
            res.setSID(req.getSID());
            System.out.println("Client " + req.getLoginAccount() + " login success");
        }else{
            res.setReturnCode(1002);
        }

        ct.channel().writeAndFlush(res.build());
        return "";
    }

    //获取所有员工资料
    @ActionMap(key = Command.EmployeeReq)
    public void getEmployees(ChannelHandlerContext ct, BaseMsg msg) throws  Exception{
        Employee.EmployeeReq req = Employee.EmployeeReq.parseFrom(msg.getBodyData());
        Employee.EmployeeRes.Builder res = Employee.EmployeeRes.newBuilder();

        List<com.example.nettyserver.entity.Employee> result = employeeService.findAllEmployees();
        if (null != result && result.size()> 0){
            for(int i = 0; i<result.size(); i++) {
                com.example.nettyserver.entity.Employee employee = result.get(i);
                Employee.EmployeeInfo.Builder info = Employee.EmployeeInfo.newBuilder();
                info.setEmployeeId(employee.getId());
                info.setEmployeeName(employee.getName());
                res.setEmployeelist(info);
            }
        }
    }

}
