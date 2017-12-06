package com.example.nettyserver.dao;

import com.example.nettyserver.dao.base.GenericDaoImpl;
import com.example.nettyserver.dao.base.SqlParam;
import com.example.nettyserver.entity.User;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Repository
@Transactional("transactionManager")
public class UserDaoImpl extends GenericDaoImpl<User, Long> implements UserDao {
    @Resource(name = "sessionFactory")
    public void setServerSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public Boolean has(String username, String pwd){
        List<User>result = getUser(username,pwd);

        if (null != result && result.size() > 0) {
            return  true;
        }else {
            return false;
        }
    }

    public List<User> getUser(String username,String pwd){
        SqlParam name = new SqlParam("username", username);
        SqlParam password = new SqlParam("pwd", pwd);
        SqlParam[] params = new SqlParam[]{name,password};

        List<User> result = find("from User u where u.username=:username and u.loginpwd=:pwd",params);
        return result;
    }

}
