package com.aorez.web;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {

    //根据资源路径进行方法分发
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求路径
        String uri = req.getRequestURI();

        //获取最后一段路径
        //找最后一个斜杠的位置
        int index = uri.lastIndexOf('/');
        //获取最后的子串
        String methodName = uri.substring(index + 1);

        //因为是在子类进行调用，所以this直接是BrandServlet或者UserServlet等等
        Class<? extends BaseServlet> cls = this.getClass();

        //获取方法并执行方法
        try {
            Method method = cls.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this, req, resp);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
