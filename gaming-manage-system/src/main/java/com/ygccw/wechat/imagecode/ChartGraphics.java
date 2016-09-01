package com.ygccw.wechat.imagecode;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author soldier
 */
public class ChartGraphics {
    private BufferedImage image;
    private int width; // 图片宽度
    private int height; // 图片高度

    public ChartGraphics(int width, int height) {

        this.width = width;
        this.height = height;
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    public static void main(String[] args) {
        String outPath = "E:\\验证码\\result61.png";
        ChartGraphics chartGraphics = new ChartGraphics(25, 18);

        chartGraphics.drawString("难", 18, Color.black);
//        chartGraphics.scale(1, 1);
        chartGraphics.save(outPath);
    }

    /**
     * 创建一个含有指定颜色字符串的图片
     *
     * @param message  字符串
     * @param fontSize 字体大小
     * @param color    字体颜色
     * @return 图片
     */
    public BufferedImage drawString(String message, int fontSize, Color color) {
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setColor(color);
        g.setBackground(Color.white);
        Font f = new Font("宋体", Font.ITALIC, fontSize);
        g.setFont(f);
        int len = message.length();
        g.drawString(message, (width - fontSize * len) / 2,
                (height + (int) (fontSize / 1.5)) / 2);
        g.dispose();
        return image;
    }

    /**
     * 缩放图片
     *
     * @param scaleW 水平缩放比例
     * @param scaleH 垂直缩放比例
     * @return
     */
    public BufferedImage scale(double scaleW, double scaleH) {
        width = (int) (width * scaleW);
        height = (int) (height * scaleH);

        BufferedImage newImage = new BufferedImage(width, height,
                image.getType());
        Graphics g = newImage.getGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();
        image = newImage;
        return image;
    }

    /**
     * 旋转90度旋转
     *
     * @return 对应图片
     */
    public BufferedImage rotate() {
        BufferedImage dest = new BufferedImage(height, width,
                BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++) {
                dest.setRGB(height - j - 1, i, image.getRGB(i, j));
            }
        image = dest;
        return image;
    }

    /**
     * 合并两个图像
     *
     * @param anotherImage 另一张图片
     * @return 合并后的图片，如果两张图片尺寸不一致，则返回null
     */
    public BufferedImage mergeImage(BufferedImage anotherImage) {

        int w = anotherImage.getWidth();
        int h = anotherImage.getHeight();
        if (w != width || h != height) {
            return null;
        }

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                int rgb1 = image.getRGB(i, j);
                int rgb2 = anotherImage.getRGB(i, j);

                Color color1 = new Color(rgb1);
                Color color2 = new Color(rgb2);

                // 如果该位置两张图片均没有字体经过，则跳过
                // 如果跳过，则最后将会是黑色背景
                if (color1.getRed() + color1.getGreen() + color1.getBlue()
                        + color2.getRed() + color2.getGreen()
                        + color2.getBlue() == 0) {
                    continue;
                }

                Color color = new Color(
                        (color1.getRed() + color2.getRed()) / 2,
                        (color1.getGreen() + color2.getGreen()) / 2,
                        (color1.getBlue() + color2.getBlue()) / 2);
                image.setRGB(i, j, color.getRGB());
            }
        }
        return image;
    }

    /**
     * 保存图片int rgb1 = image.getRGB(i, j); int rgb2 = anotherImage.getRGB(i, j);
     * rgb2 = rgb1 & rgb2; image.setRGB(height - i, j, rgb2);
     *
     * @param filePath 图片路径
     */
    public void save(String filePath) {
        try {
            ImageIO.write(image, "png", new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到对应的图片
     *
     * @return
     */
    public BufferedImage getImage() {
        return image;
    }

}
