package cn.haoyualways.travel.web.servlet;

import cn.haoyualways.travel.domain.ResultInfo;
import cn.haoyualways.travel.domain.User;
import cn.haoyualways.travel.service.UserService;
import cn.haoyualways.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //效验验证码
        String check = request.getParameter("check");
        //创建Session对象
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER"); //验证码只能使用一次
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //调用Service
        UserService service = new UserServiceImpl();
        User u = service.login(user);
        ResultInfo info = new ResultInfo();
        request.getSession().setAttribute("user",u);
        //判断用户名是否为null
        if ( u == null ){
            //用户名错误
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误");
        }
        if ( u != null && !"Y".equals(u.getStatus())){
            info.setFlag(false);
            info.setErrorMsg("您尚未激活,请激活");
        }
        if (!checkcode_server.equalsIgnoreCase(check)){
            info.setFlag(false);
            info.setErrorMsg("验证码错误请重新输入！");
        }
        if ( u != null && "Y".equals(u.getStatus()) && check.equalsIgnoreCase(checkcode_server) ){
            //登录成功
            info.setFlag(true);
        }
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),info);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
