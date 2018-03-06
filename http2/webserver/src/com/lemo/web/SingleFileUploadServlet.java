package com.lemo.web;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet("/post/single/upload")
public class SingleFileUploadServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(ServletInputStream is = req.getInputStream(); ByteArrayOutputStream stream = new ByteArrayOutputStream()){
            Path path = Paths.get("/Users/lemo-wu/Documents", "abc.png");
            byte[] bytes = new byte[1024];
            int read = 0;
            while ((read = is.read(bytes)) != -1){
                stream.write(bytes,0,read);
            }
            Files.write(path, stream.toByteArray());
        }
    }
}
