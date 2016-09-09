package com.ygccw.wechat.imagecode;

import core.framework.util.JSONBinder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author soldier
 */
public class ImageCodeDemo {
    public static void main(String[] args) {
        try {
//            List<BufferedImage> bufferedImageList = splitImage(removeBackground("E:\\验证码\\wordnew\\DrawImage.png"));
//            for (BufferedImage bufferedImage : bufferedImageList) {
//                ImageIO.write(bufferedImage, "png", new File("E:\\验证码\\wordnew\\" + System.currentTimeMillis() + ".png"));
//            }
            ImageIO.write(Rotate(drawString("那"), 385), "png", new File("E:\\验证码\\wordnew\\" + System.currentTimeMillis() + ".png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage removeBackground(String picFile) throws Exception {
        BufferedImage img = ImageIO.read(new File(picFile));
        int width = img.getWidth();
        int height = img.getHeight();
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                if (isBlack(img.getRGB(x, y)) == 1 || isGreen(img.getRGB(x, y)) == 1) {
                    img.setRGB(x, y, Color.white.getRGB());
                } else {
                    if (isWhite(img.getRGB(x, y)) == 1) {
                        img.setRGB(x, y, img.getRGB(x, y));
                    } else {
                        img.setRGB(x, y, Color.black.getRGB());
                    }

                }
            }
        }
        return img;
    }

    public static int isBlack(int colorInt) {
        Color color = new Color(colorInt);
        if (color.getRGB() == Color.black.getRGB()) {
            return 1;
        }
        return 0;
    }

    public static int isGreen(int colorInt) {
        Color color = new Color(colorInt);
        if (color.getRGB() == -16711936) {
            return 1;
        }
        return 0;
    }

    public static int isWhite(int colorInt) {
        Color color = new Color(colorInt);
        if (color.getRGB() == Color.white.getRGB()) {
            return 1;
        }
        return 0;
    }

    public static List<BufferedImage> splitImage(BufferedImage img) throws Exception {
        List<BufferedImage> subImageList = new ArrayList<BufferedImage>();
        int width = img.getWidth();
        int height = img.getHeight();
        Map<Integer, Integer> heightMap = new LinkedHashMap<>();
        for (int x = 0; x < width; x++) {
            int count = 0;
            for (int y = 0; y < height; y++) {
                if (isWhite(img.getRGB(x, y)) == 1) {
                    count++;
                }
            }
            heightMap.put(x, count);
        }
        int spitWidthBegin = 0;
        int spitWidthEnd = 0;
        List<Integer> markWidthList = new ArrayList<>();
        for (Map.Entry<Integer, Integer> mapEntry : heightMap.entrySet()) {
            if (mapEntry.getKey() == 0 && mapEntry.getValue() != height) {
                spitWidthBegin = mapEntry.getKey();
                markWidthList.add(spitWidthBegin);
            } else if (mapEntry.getKey() != 0 && mapEntry.getValue() != height && heightMap.get(mapEntry.getKey() - 1) == height) {
                spitWidthBegin = mapEntry.getKey();
                markWidthList.add(spitWidthBegin);
            }
            if (mapEntry.getKey() == width && mapEntry.getValue() != height) {
                spitWidthEnd = mapEntry.getKey();
                markWidthList.add(spitWidthEnd);
            } else if (mapEntry.getKey() != 0 && mapEntry.getValue() != height && heightMap.get(mapEntry.getKey() + 1) == height) {
                spitWidthEnd = mapEntry.getKey() + 1;
                markWidthList.add(spitWidthEnd);
            }
        }
        System.out.println(JSONBinder.toJSON(markWidthList));
        subImageList.add(splitHeightImage(img.getSubimage(markWidthList.get(0), 0, markWidthList.get(1) - markWidthList.get(0), height)));
        subImageList.add(splitHeightImage(img.getSubimage(markWidthList.get(2), 0, markWidthList.get(3) - markWidthList.get(2), height)));
        subImageList.add(splitHeightImage(img.getSubimage(markWidthList.get(4), 0, markWidthList.get(5) - markWidthList.get(4), height)));
        subImageList.add(splitHeightImage(img.getSubimage(markWidthList.get(6), 0, markWidthList.get(7) - markWidthList.get(6), height)));
        return subImageList;


    }

    public static BufferedImage splitHeightImage(BufferedImage img) throws Exception {
        int width = img.getWidth();
        int height = img.getHeight();
        Map<Integer, Integer> widthMap = new LinkedHashMap<>();
        for (int y = 0; y < height; y++) {
            int count = 0;
            for (int x = 0; x < width; x++) {
                if (isWhite(img.getRGB(x, y)) == 1) {
                    count++;
                }
            }
            widthMap.put(y, count);
        }

        int spitHeightBegin = 0;
        int spitHeightEnd = 0;
        List<Integer> markHeightList = new ArrayList<>();
        for (Map.Entry<Integer, Integer> mapEntry : widthMap.entrySet()) {
            if (mapEntry.getKey() == 0 && mapEntry.getValue() != width) {
                spitHeightBegin = mapEntry.getKey();
                markHeightList.add(spitHeightBegin);
            } else if (mapEntry.getKey() != 0 && mapEntry.getValue() != width && widthMap.get(mapEntry.getKey() - 1) == width) {
                spitHeightBegin = mapEntry.getKey();
                markHeightList.add(spitHeightBegin);
            }
            if (mapEntry.getKey() == height && mapEntry.getValue() != width) {
                spitHeightEnd = mapEntry.getKey();
                markHeightList.add(spitHeightEnd);
            } else if (mapEntry.getKey() != 0 && mapEntry.getValue() != width && widthMap.get(mapEntry.getKey() + 1) == width) {
                spitHeightEnd = mapEntry.getKey() + 1;
                markHeightList.add(spitHeightEnd);
            }
        }
        System.out.println(JSONBinder.toJSON(markHeightList));
        return img.getSubimage(0, markHeightList.get(0), width, markHeightList.get(1) - markHeightList.get(0));

    }

    public static BufferedImage drawString(String message) {
        int width = 50;
        int height = 50;
        int fontSize = 20;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setColor(Color.black);
        g.setBackground(Color.white);
        Font f = new Font("宋体", Font.CENTER_BASELINE, fontSize);
        g.setFont(f);
        int len = message.length();
        g.drawString(message, (width - fontSize * len) / 2,
                (height + (int) (fontSize / 1.5)) / 2);
        g.dispose();
        return image;
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


}
