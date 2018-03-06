package com.lemo.web;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet(asyncSupported = true,urlPatterns = "/async/get")
public class AsynHttpServlet extends HttpServlet{

    @Override
    public void init() throws ServletException {
        super.init();

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        //设置contenttype和编码格式必须在resp.getWriter()之前,不然设置编码无效
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/plain");
        PrintWriter writer = resp.getWriter();
        LocalDateTime start = LocalDateTime.now();

        String startStr = start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        writer.println("请求开始-->" + startStr);

        if (req.isAsyncSupported()){
            AsyncContext context = req.startAsync();
            context.addListener(new AsyncWebListener());
            context.setTimeout(0);
            context.start(() -> {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ServletResponse response = context.getResponse();
                try {
                    PrintWriter w = response.getWriter();
                    w.println("异步请求处理结束");
                    context.complete();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });


        }
        LocalDateTime end = LocalDateTime.now();
        String endStr = end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        writer.println("请求结束-->" + endStr);
    }
}
