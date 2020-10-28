package cn.haoyualways.travel.service;

import cn.haoyualways.travel.domain.User;

public interface UserService {
    /**
     * 用户注册
     * @param user
     * @return
     */
    boolean regist(User user);

    /**
     * 邮箱激活
     * @param code
     * @return
     */
    boolean active(String code);

    /**
     * 根据用户名或密码查询
     * @param user
     * @return
     */
    User login(User user);
}
