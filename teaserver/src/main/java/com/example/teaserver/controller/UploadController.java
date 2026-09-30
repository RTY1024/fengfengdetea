package com.example.teaserver.controller;

import com.example.teaserver.common.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/upload")
public class UploadController {

    @PostMapping("/image")
    public Result uploadImage(@RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) return Result.error("文件为空");

        try {
            // 保存目录：项目根目录/uploads/
            String dir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator;
            File d = new File(dir);
            if (!d.exists()) d.mkdirs();

            // 原文件名取扩展名
            String original = file.getOriginalFilename();
            String ext = ".png";
            if (original != null && original.contains(".")) {
                ext = original.substring(original.lastIndexOf("."));
            }

            // 生成新文件名
            String name = "img_" + System.currentTimeMillis() + ext;
            File target = new File(dir + name);
            file.transferTo(target);

            Map<String, Object> data = new HashMap<>();
            data.put("name", name);                    // 存数据库用
            data.put("url", "/uploads/" + name);       // 浏览器访问用
            return Result.ok(data);

        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("上传失败：" + e.getMessage());
        }
    }
}