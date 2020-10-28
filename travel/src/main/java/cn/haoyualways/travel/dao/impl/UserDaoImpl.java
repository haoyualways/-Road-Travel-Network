package cn.haoyualways.travel.dao.impl;

import cn.haoyualways.travel.dao.UserDao;
import cn.haoyualways.travel.domain.User;
import cn.haoyualways.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    @Override
    public User findByUsername(String username) {
        User user = null;
        try {
            //定义SQL
            String sql = "select * from tab_user where username = ? ";
            //执行sql
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        }catch (Exception e){
            e.printStackTrace();
        }

        return user;
    }

    /**
     * 用户保存
     * @param user
     */
    @Override
    public void save(User user) {
        //定义SQL
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code)" + "values(?,?,?,?,?,?,?,?,?)";
        //执行SQL
        template.update(sql,user.getUsername(),user.getPassword(),user.getName(),user.getBirthday(),user.getSex(),user.getTelephone(),user.getEmail(),user.getStatus(),user.getCode());
    }

    /**
     * 根据激活码查询用户对象
     * @param code
     * @return
     */
    @Override
    public User findByCode(String code) {
        User user = null;
        try {
            String sql = "select * from tab_user where code = ? ";
            user = template.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),code);
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 修改指定用户激活状态
     * @param user
     */
    @Override
    public void updateStatus(User user) {
        String sql = "update tab_user set status = 'Y' where uid = ? ";
        template.update(sql,user.getUid());
    }

    /**
     * 根据用户名与密码查询
     * @param username
     * @param password
     * @return
     */
    @Override
    public User findByUsernameAndPassword(String username, String password) {
        User user = null;
        try {
            //定义SQL
            String sql = "select * from tab_user where username = ? and password = ? ";
            //执行sql
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username,password);
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }
}
