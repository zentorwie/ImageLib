package org.dyzeng.imagelib;

import org.dyzeng.imagelib.config.AppConfig;
import org.dyzeng.imagelib.config.RepositoryConfig;
import org.dyzeng.imagelib.service.ImageUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

import static org.dyzeng.imagelib.Application.ROOT;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {AppConfig.class, RepositoryConfig.class})
public class ImageUtilsTest {

    @Autowired
    private ImageUtils imageUtils;

    @Test
    public void testConvertRgb() {
        int in1 = 0xFFFFFF;
        int out1 = imageUtils.convertRgb(in1);

        Assert.isTrue(out1 == ((3<<4)|(3<<2)|3));

        int in2 = 0;
        int out2 = imageUtils.convertRgb(in2);

        Assert.isTrue(out2 == 0);

        int in3 = 0x3090C0;
        int out3 = imageUtils.convertRgb(in3);
        Assert.isTrue(out3 == 0b001011);

    }

    @Test
    public void testGetFeatures() throws Exception {
        BufferedImage image = ImageIO.read(new File(ROOT + "/1.jpg"));
        long[] vec = imageUtils.getFeatures(image);
        for (long x : vec) {
            System.out.printf(" %d", x);
        }
        System.out.println();
    }

    @Test
    public void testCosTheta() throws Exception {
        BufferedImage image = ImageIO.read(new File(ROOT + "/1.jpg"));
        long[] vec = imageUtils.getFeatures(image);

        double cos = imageUtils.getCosTheta(vec, vec);
        System.out.println(cos);

        BufferedImage image1 = ImageIO.read(new File(ROOT + "/5.jpg"));
        long[] vec1 = imageUtils.getFeatures(image1);
        double cos1 = imageUtils.getCosTheta(vec, vec1);
        System.out.println(cos1);
    }

//    @Test
    public void testUpdate() throws Exception {
        imageUtils.updateLibrary();
    }
}
