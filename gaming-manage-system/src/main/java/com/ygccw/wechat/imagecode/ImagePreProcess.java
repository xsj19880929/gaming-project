package com.ygccw.wechat.imagecode;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author soldier
 */
public class ImagePreProcess {
    public static int isBlack(int colorInt) {
        Color color = new Color(colorInt);
        if (color.getRed() + color.getGreen() + color.getBlue() <= 100) {
            return 1;
        }
        return 0;
    }

    public static int isWhite(int colorInt) {
        Color color = new Color(colorInt);
        if (color.getRed() + color.getGreen() + color.getBlue() > 100) {
            return 1;
        }
        return 0;
    }

    public static int isDeep(int colorInt) {
        Color color = new Color(colorInt);
        if (color.getRed() + color.getGreen() + color.getBlue() > 400) {
            return 1;
        }
        return 0;
    }

    public static BufferedImage removeBackground(String picFile)
            throws Exception {
        BufferedImage img = ImageIO.read(new File(picFile));
        int width = img.getWidth();
        int height = img.getHeight();
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                if (isDeep(img.getRGB(x, y)) == 1 || y > 19 || y < 2) {
                    img.setRGB(x, y, Color.WHITE.getRGB());
                } else {
                    img.setRGB(x, y, Color.BLACK.getRGB());
                }
            }
        }
        return img;
    }

    public static BufferedImage removeBackgroundOne(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                if (isBlack(img.getRGB(x, y)) == 1 && isBlack(img.getRGB(x, y + 1)) == 0 && isBlack(img.getRGB(x, y - 1)) == 0 && isBlack(img.getRGB(x + 1, y)) == 0 && isBlack(img.getRGB(x - 1, y)) == 0) {
                    img.setRGB(x, y, Color.WHITE.getRGB());
                }
            }
        }
        return img;
    }

    public static List<BufferedImage> splitImageOne(BufferedImage img)
            throws Exception {
        List<BufferedImage> subImgs = new ArrayList<BufferedImage>();
        int width = img.getWidth();
        int height = img.getHeight();
        Map<Integer, Integer> xMap = new LinkedHashMap<>();
        for (int x = 0; x < width; x++) {
            int count = 0;
            for (int y = 0; y < height; y++) {
                if (isWhite(img.getRGB(x, y)) == 1) {
                    count++;
                }
            }
            xMap.put(x, count);
        }
        int spitY = 0;
        int spitCount = 0;
        for (Map.Entry<Integer, Integer> mapEntry : xMap.entrySet()) {
            if (mapEntry.getValue() < height - 1) {
                spitCount++;
            }
            if (spitCount == 3) {
                spitY = mapEntry.getKey() - 3;
            }
        }
        subImgs.add(img.getSubimage(spitY, 0, 14, height));
        subImgs.add(img.getSubimage(spitY + 14, 0, 14, height));
        subImgs.add(img.getSubimage(spitY + 28, 0, 14, height));
        subImgs.add(img.getSubimage(spitY + 42, 0, 14, height));
        return subImgs;
    }

    public static String getAllOcrOne(String file) throws Exception {
        BufferedImage img = ImageIO.read(new File(file));
        List<BufferedImage> listImg = splitImageOne(img);
//        Map<BufferedImage, String> map = loadTrainData();
        String result = "";
        int i = 0;
        for (BufferedImage bi : listImg) {
            i++;
            ImageIO.write(bi, "png", new File("E:\\验证码\\result" + i + ".png"));
        }

        return result;
    }

    public static BufferedImage Rotate(Image src, int angel) {
        int src_width = src.getWidth(null);
        int src_height = src.getHeight(null);
        // calculate the new image size
        Rectangle rect_des = CalcRotatedSize(new Rectangle(new Dimension(
                src_width, src_height)), angel);

        BufferedImage res = null;
        res = new BufferedImage(rect_des.width, rect_des.height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = res.createGraphics();
        // transform
        g2.setBackground(Color.WHITE);
        g2.clearRect(0, 0, rect_des.width, rect_des.height);
        g2.translate((rect_des.width - src_width) / 2,
                (rect_des.height - src_height) / 2);
        g2.rotate(Math.toRadians(angel), src_width / 2, src_height / 2);

        g2.drawImage(src, null, null);
        return res;
    }

    public static Rectangle CalcRotatedSize(Rectangle src, int angel) {
        // if angel is greater than 90 degree, we need to do some conversion
        if (angel >= 90) {
            if (angel / 90 % 2 == 1) {
                int temp = src.height;
                src.height = src.width;
                src.width = temp;
            }
            angel = angel % 90;
        }

        double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2;
        double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;
        double angel_alpha = (Math.PI - Math.toRadians(angel)) / 2;
        double angel_dalta_width = Math.atan((double) src.height / src.width);
        double angel_dalta_height = Math.atan((double) src.width / src.height);

        int len_dalta_width = (int) (len * Math.cos(Math.PI - angel_alpha
                - angel_dalta_width));
        int len_dalta_height = (int) (len * Math.cos(Math.PI - angel_alpha
                - angel_dalta_height));
        int des_width = src.width + len_dalta_width * 2;
        int des_height = src.height + len_dalta_height * 2;
        return new Rectangle(new Dimension(des_width, des_height));
    }

    public static BufferedImage splitImageWord(BufferedImage img)
            throws Exception {
        int width = img.getWidth();
        int height = img.getHeight();
        Map<Integer, Integer> xMap = new LinkedHashMap<>();
        for (int x = 0; x < width; x++) {
            int count = 0;
            for (int y = 0; y < height; y++) {
                if (isWhite(img.getRGB(x, y)) == 1) {
                    count++;
                }
            }
            xMap.put(x, count);
        }
        int spitYBegin = 0;
        int spitYEnd = 0;
        int spitYBeginCount = 0;
        int spitYEndCount = 0;
        for (Map.Entry<Integer, Integer> mapEntry : xMap.entrySet()) {
            if (mapEntry.getValue() < height - 1) {
                spitYBeginCount++;
            }
            if (spitYBeginCount == 3) {
                spitYBegin = mapEntry.getKey() - 2;
            }
            if (spitYBegin != 0) {
                if (mapEntry.getValue() == height) {
                    spitYEndCount++;
                }
                if (spitYEndCount == 3) {
                    spitYEnd = mapEntry.getKey() - 2;
                }
            }

        }

        Map<Integer, Integer> yMap = new LinkedHashMap<>();
        for (int x = 0; x < height; x++) {
            int count = 0;
            for (int y = 0; y < width; y++) {
                if (isWhite(img.getRGB(y, x)) == 1) {
                    count++;
                }
            }
            yMap.put(x, count);
        }
        int spitXBegin = 0;
        int spitXEnd = 0;
        int spitXBeginCount = 0;
        int spitXEndCount = 0;
        for (Map.Entry<Integer, Integer> mapEntry : yMap.entrySet()) {
            if (mapEntry.getValue() < width - 1) {
                spitXBeginCount++;
            }
            if (spitXBeginCount == 3) {
                spitXBegin = mapEntry.getKey() - 2;
            }
            if (spitXBegin != 0) {
                if (mapEntry.getValue() == width) {
                    spitXEndCount++;
                }
                if (spitXEndCount == 3) {
                    spitXEnd = mapEntry.getKey() - 2;
                }
            }

        }
        if (spitYEnd == 0) {
            spitYEnd = width;
        }
        if (spitXEnd == 0) {
            spitXEnd = height;
        }

        BufferedImage imgNew = img.getSubimage(spitYBegin, spitXBegin, spitYEnd - spitYBegin, spitXEnd - spitXBegin);
        ImageIO.write(imgNew, "png", new File("E:\\验证码\\" + System.currentTimeMillis() + ".png"));
        return imgNew;
    }

    public static String check(BufferedImage img) {
        String result = "";
        try {
            Map<BufferedImage, String> map = new HashMap<BufferedImage, String>();
            File dir = new File("E:\\验证码\\word");
            File[] files = dir.listFiles();
            for (File file : files) {
                map.put(ImageIO.read(file), file.getName().charAt(0) + "");
            }
            int width = img.getWidth();
            int height = img.getHeight();
            int temp = 0;
            for (BufferedImage bi : map.keySet()) {
                int count = 0;
                int biWidth = bi.getWidth();
                int biHeight = bi.getHeight();
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        if (y < biHeight && x < biWidth && isBlack(img.getRGB(x, y)) == isBlack(bi.getRGB(x, y))) {
                            count++;
                        }

                    }
                }
                System.out.println(map.get(bi) + "=" + count);
                if (temp < count) {
                    temp = count;
                    result = map.get(bi);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(result);
        return result;
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
//        // downloadImage();
//        for (int i = 0; i < 30; ++i) {
//            String text = getAllOcr("img2//" + i + ".jpg");
//            System.out.println(i + ".jpg = " + text);
//        }
//        removeBackgroudOne(removeBackgroud("E:\\验证码\\ValidateCode2.png"));
//        BufferedImage src = ImageIO.read(new File("E:\\验证码\\result61.png"));
//        BufferedImage des = Rotate(src, 345);
//        ImageIO.write(des, "png", new File("E:\\验证码\\result610.png"));
//        BufferedImage img = ImageIO.read(new File("E:\\验证码\\result\\ValidateCode6.png"));
//        getAllOcrOne("E:\\验证码\\result\\ValidateCode6.png");
//        BufferedImage img = ImageIO.read(new File("E:\\验证码\\result3.png"));
//        splitImageWord(img);
//        check(ImageIO.read(new File("E:\\验证码\\check\\2.png")));
        String[] words = new String[]{"创", "凭", "污", "亚"};
        outWord(words);
        work("E:\\验证码\\ValidateCode.png");


    }

    public static void work(String picFile) throws Exception {
        BufferedImage img = removeBackground(picFile);
        BufferedImage src = removeBackgroundOne(img);
        ImageIO.write(src, "png", new File("E:\\验证码\\" + System.currentTimeMillis() + ".png"));
        BufferedImage des = Rotate(src, 335);
        ImageIO.write(des, "png", new File("E:\\验证码\\" + System.currentTimeMillis() + ".png"));
        List<BufferedImage> bufferedImageList = splitImageOne(des);
        List<BufferedImage> bufferedImageListNew = new ArrayList<>();
        for (BufferedImage bufferedImage : bufferedImageList) {
            bufferedImageListNew.add(splitImageWord(bufferedImage));
        }
        for (BufferedImage bufferedImage : bufferedImageListNew) {
            check(bufferedImage);
        }
    }

    public static List<BufferedImage> outWord(String[] words) {
        List<BufferedImage> bufferedImageWordList = new ArrayList<>();
        for (String word : words) {
            String outPath = "E:\\验证码\\word\\" + word + ".png";
            ChartGraphics chartGraphics = new ChartGraphics(25, 18);
            chartGraphics.drawString(word, 18, Color.black);
            chartGraphics.save(outPath);
            bufferedImageWordList.add(chartGraphics.getImage());
        }
        return bufferedImageWordList;
    }
}
