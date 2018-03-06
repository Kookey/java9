package com.lemo.http2;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

public class Main {

    private static Logger LOG = Logger.getLogger(Main.class.getSimpleName());

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Hello World!");

        //get请求
       // downloadImage();

        //post 请求
//        doPost();
        //文件上传
        singleUploadPost();
    }


    /**
     * get请求
     * @throws IOException
     * @throws InterruptedException
     */
    public static void doGet() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("https://www.jianshu.com/p/62e8185a857c?utm_campaign=maleskine&utm_content=note&utm_medium=pc_all_hots&utm_source=recommendation")).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandler.asString());
        LOG.info(response.body());
    }

    /**
     * 下载到临时文件
     * @throws IOException
     * @throws InterruptedException
     */
    public static void doGetToFile() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("https://www.jianshu.com/p/62e8185a857c?utm_campaign=maleskine&utm_content=note&utm_medium=pc_all_hots&utm_source=recommendation")).build();
        Path tempFile = Files.createTempFile("consol-labs-home", ".html");
        HttpResponse<Path> response = client.send(request, HttpResponse.BodyHandler.asFile(tempFile));
        if (response.statusCode()==200){
            Path body = response.body();
            List<String> strings = Files.readAllLines(body);
            System.out.println(strings.size());
        }
    }

    /**
     * 文件下载
     * @throws IOException
     * @throws InterruptedException
     */
    public static void downloadImage() throws IOException, InterruptedException {
        String url = "https://cdn2.jianshu.io/assets/web/web-note-ad-1-10f08e404d3887d2d45a4bc8f1831403.png";
        Path path = Paths.get("/Users/lemo-wu/Documents", "10f08e404d3887d2d45a4bc8f1831403.png");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
        HttpResponse<Path> response = client.send(request, HttpResponse.BodyHandler.asFile(Files.createFile(path)));
        if (response.statusCode()==200){
            System.out.println("下载成功");
        }
    }

    /**
     * 如果是通过post方式提交参数,则必须指定其content-type为application/x-www-form-urlencoded
     * 否则服务端就没有办法获取参数结果,而只是获取body里面的内容
     * @throws IOException
     * @throws InterruptedException
     */
    public static void doPost() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/post/params");
        HttpRequest request = HttpRequest.newBuilder(uri).POST(HttpRequest.BodyProcessor.fromString("name=wangxl&age=40")).header("Content-Type","application/x-www-form-urlencoded").build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandler.asString());
        System.out.println(response.body());
    }

    /**
     * 文件上传
     * @throws IOException
     * @throws InterruptedException
     */
    public static void singleUploadPost() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/post/single/upload");
        HttpRequest request = HttpRequest.newBuilder(uri).POST(HttpRequest.BodyProcessor.fromFile(Paths.get("/Users/lemo-wu/Downloads/16.png")))
                //.header("Content-Type","multipart/form-data")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandler.asString());
        System.out.println(response.statusCode());
        System.out.println(response.body());
    }
}
