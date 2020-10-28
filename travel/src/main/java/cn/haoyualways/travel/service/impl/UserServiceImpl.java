package cn.haoyualways.travel.service.impl;

import cn.haoyualways.travel.dao.UserDao;
import cn.haoyualways.travel.dao.impl.UserDaoImpl;
import cn.haoyualways.travel.domain.User;
import cn.haoyualways.travel.service.UserService;
import cn.haoyualways.travel.util.MailUtils;
import cn.haoyualways.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();
    /**
     * 用户注册,与邮箱的激活
     * @param user
     * @return
     */
    @Override
    public boolean regist(User user) {
        //根据用户名查询用户对象
        User user1 = userDao.findByUsername(user.getUsername());
        //判断user1是否为null
        if ( user1 != null ){
            //用户名存在
            return false;
        }
        //保存用户信息
            //设置激活码,唯一的字符串
        user.setCode(UuidUtil.getUuid());
        //设置激活状态
        user.setStatus("N");
        userDao.save(user);

        //激活邮件发送,邮件正文
        String content = "<a href='http://localhost/travel/user/active?code="+user.getCode()+"'>点击激活【haoyualways】</a>";
        MailUtils.sendMail(user.getEmail(),content,"激活邮件");
        return true;
    }

    /**
     * 邮箱激活
     * @param code
     * @return
     */
    @Override
    public boolean active(String code) {
        //根据激活码查询用户对象
        User user = userDao.findByCode(code);
        if (user!=null){
            //调用dao的修改激活状态的方法
            userDao.updateStatus(user);
            return true;
        }else {
            return false;
        }
    }

    /**
     * 根据用户名与密码查询
     * @return
     */
    @Override
    public User login(User user) {
        return userDao.findByUsernameAndPassword(user.getUsername(),user.getPassword());
    }
}
