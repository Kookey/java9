package com.lemo.web;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

@WebServlet("/post/params")
public class PostServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Enumeration<String> names = req.getParameterNames();
        while (names.hasMoreElements()){
            String name = names.nextElement();
            String value = req.getParameter(name);
            System.out.println(name+ " = " + value);
        }

        String contentType = req.getContentType();
        System.out.println("content-type: " + contentType);
        StringBuffer buffer = new StringBuffer();
        try (ServletInputStream in = req.getInputStream()){
            byte[] bytes = new byte[1024];
            int length = 0;
            while ((length = in.read(bytes)) != -1){
                buffer.append(new String(bytes, 0, length));
            }
            System.out.println("body = {" + buffer.toString() + "}");
        }




        resp.setContentType("text/plain;charset=utf-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = resp.getWriter();
        writer.write("我是Post请求");
    }
}
