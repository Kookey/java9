package com.lemo.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

@WebServlet("/post/upload")
@MultipartConfig(maxFileSize = 100000, maxRequestSize = 6000)
public class FileUploadServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Collection<Part> parts = req.getParts();
        for (Part part : parts){
            String name = part.getName();
            long size = part.getSize();
            String fileName = part.getSubmittedFileName();
            //将文件写入到磁盘
            part.write("/Users/lemo-wu/Documents/" + fileName);
            System.out.printf("name = %s, filename=%s, size = %d", name,fileName, size);
            
        }

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/plain;charset=utf-8");
        try(PrintWriter writer = resp.getWriter()){
            writer.write("我是post文件上传");
            writer.flush();
            writer.close();
        }
    }
}
