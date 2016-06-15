package org.dyzeng.imagelib.web;

import org.dyzeng.imagelib.Application;
import org.dyzeng.imagelib.data.ImageInfo;
import org.dyzeng.imagelib.data.ImageResult;
import org.dyzeng.imagelib.service.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class SearchController {

    @Autowired
    private ImageUtils imageUtils;

    @RequestMapping(method = RequestMethod.GET, value = "/search")
    public String provideUploadInfo(Model model) {
//
//        if (true) {
//            return "searchForm";
//        }
//        File rootFolder = new File(Application.ROOT);
//        List<String> fileNames = Arrays.stream(rootFolder.listFiles())
//                .map(f -> f.getName())
//                .collect(Collectors.toList());
//        model.addAttribute("files",
//                Arrays.stream(rootFolder.listFiles())
//                        .sorted(Comparator.comparingLong(f -> -1 * f.lastModified()))
//                        .map(f -> f.getName())
//                        .collect(Collectors.toList())
//        );

        return "searchForm";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/search")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        ImageInfo imageInfo = new ImageInfo();
        String name = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + UUID.randomUUID().toString();
        imageInfo.setFileName(name);

        if (!file.isEmpty()) {
            try {
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(new File(Application.TEMP_DIR + "/" + name)));
                FileCopyUtils.copy(file.getInputStream(), stream);
                stream.close();
                redirectAttributes.addFlashAttribute("sampleurl", "temp/" + name);

                BufferedImage img = ImageIO.read(new File(Application.TEMP_DIR + "/" + name));
                List<ImageResult> result = imageUtils.searchSimilar(img);

                redirectAttributes.addFlashAttribute("result", result);
                redirectAttributes.addFlashAttribute("message", "一共找到" + result.size() + "个结果");
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("message",
                        "遇到错误 " + ": " + e.getMessage());
            }

        } else {
            redirectAttributes.addFlashAttribute("message", "请上传要搜索的图片");
        }

        return "redirect:/search";
    }
}

