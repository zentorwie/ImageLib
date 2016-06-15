package org.dyzeng.imagelib.service;

import org.dyzeng.imagelib.data.ImageInfo;
import org.dyzeng.imagelib.data.ImageInfoRepository;
import org.dyzeng.imagelib.data.ImageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.dyzeng.imagelib.Application.ROOT;

@Component
public class ImageUtils {

    @Autowired
    private ImageInfoRepository imageInfoRepository;

    public long[] getFeatures(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        long[] vec = new long[64];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgb = image.getRGB(x, y);
                int cRgb = convertRgb(rgb);
                vec[cRgb]++;
            }
        }

        return vec;
    }

    public int convertRgb(int rgb) {
        int ans = 0;
        int r = (rgb >> 16) & 0xff;
        int g = (rgb >> 8) & 0xff;
        int b = rgb & 0xff;
        ans = ((r / 64) << 4) | ((g / 64) << 2) | (b / 64);
        return ans;
    }

    public double getCosTheta(long[] a, long[] b) throws Exception {
        if (a.length != b.length || a.length != 64) throw new Exception("Vector dimension error.");

        double dot = 0;

        for (int i = 0; i < a.length; i++) {
            dot += a[i] * b[i];
        }

        return dot / (getNorm(a) * getNorm(b));
    }


    public double getNorm(long[] a) {
        double sum = 0;

        for (long x : a) {
            sum += x * x;
        }

        return Math.sqrt(sum);
    }

    public void updateLibrary() throws Exception {
        File dbDir = new File(ROOT);

        File[] files = dbDir.listFiles();

        imageInfoRepository.deleteAll();

        for (File file : files) {
            if (file.isDirectory()) continue;
            if (!file.getName().endsWith(".jpg")) continue;
            String name = file.getName();
            System.out.println("Processing " + name);
            ImageInfo image = new ImageInfo();
            long[] features = getFeatures(ImageIO.read(file));
            image.setFileName(name);
            List<Long> f = new ArrayList<>();
            for (long x: features) f.add(x);

            image.setFeatures(f);
            imageInfoRepository.save(image);
            System.out.println(name + " saved");
        }
    }

    public List<ImageResult> searchSimilar(BufferedImage image) throws Exception {
        long[] features = getFeatures(image);
        List<ImageInfo> all = imageInfoRepository.findAll();
        List<ImageResult> ans = new ArrayList<>();

        for (ImageInfo img2 : all) {
            long[] f2 = new long[64];
            List<Long> list = img2.getFeatures();
            for (int i = 0; i < 64; i++) {
                f2[i] = list.get(i);
            }

            double simi = getCosTheta(features, f2);
            if (simi > 0.80) ans.add(new ImageResult("img/" + img2.getFileName(), simi));
        }

        Collections.sort(ans);

        return ans;
    }
}
