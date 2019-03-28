package com.xifdf.controller;

import com.xifdf.service.UserService;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.commons.fileupload.FileItem;


import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.List;

@Controller
@RequestMapping("/debug")
public class DebugController {
    @Resource
    private UserService userService;

    @RequestMapping("/test")
    public void test(HttpServletResponse response) throws Exception {
        System.out.println("debug");
        response.getWriter().write("ok");
    }


    @RequestMapping("/upload")
    public void upload(HttpServletRequest request,HttpServletResponse response) throws Exception{
        //首先判断一下 上传的数据是表单数据还是一个带文件的数据
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        System.out.println(ServletFileUpload.isMultipartContent(request));
        if (isMultipart) {   //如果为true 说明是一个带有文件的数据
            //拿到servlet的真实路径
            String realpath = request.getSession().getServletContext().getRealPath("/files");
            //打印一下路径
            System.out.println("realpath-"+realpath);
            File dir = new File(realpath);
            if (!dir.exists())
                dir.mkdirs(); //如果目录不存在 把这个目录给创建出来
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory); //获取到上传文件的对象upload
            upload.setHeaderEncoding("UTF-8");
            response.getWriter().write("success");
            try {
                //判断一下上传的数据类型
                List<FileItem> items = upload.parseRequest(request);
                for (FileItem item : items) {
                    if (item.isFormField()) { //上传的数据类型 是一个表单类型
                        String name = item.getFieldName();// 得到请求参数的名称
                        String value = item.getString("UTF-8");// 得到参数值
                        System.out.println(name + "=" + value);
                    } else {
                        //说明是一个文件类型   进行上传
                        item.write(new File(dir, item.getName()));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally{

            }
        }else {
            response.getWriter().write("error");
        }
    }

    @RequestMapping("/download")
    public void download(HttpServletRequest request,HttpServletResponse response) throws Exception {
        String realPath = request.getSession().getServletContext().getRealPath("files/Activity.txt");
        File f = new File(realPath);
        if (f.exists()) {
            FileInputStream fis = new FileInputStream(f);
            String filename = URLEncoder.encode(f.getName(), "utf-8"); //解决中文文件名下载后乱码的问题
            byte[] b = new byte[fis.available()];
            fis.read(b);
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + filename + "");
            //获取响应报文输出流对象
            ServletOutputStream out = response.getOutputStream();
            //输出
            out.write(b);
            out.flush();
            out.close();
        }
    }


}
