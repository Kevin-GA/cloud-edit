package com.newtranx.cloud.edit.util;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Random;

/**
 * 验证码生成器
 *
 * @author
 */
public class VerificationCodeImgUtil {
    // 图片的宽度。
    private int width = 160;
    // 图片的高度。
    private int height = 40;
    // 验证码字符个数
    private int codeCount = 5;
    // 验证码干扰线数
    private int lineCount = 50;
    // 验证码
    private String code = null;
    // 验证码图片Buffer
    private BufferedImage buffImg = null;
 
    /**
     * 默认构造函数,设置默认参数
     */
    public VerificationCodeImgUtil(String code) {
        this.code = code;
        this.codeCount = code.length();
        this.createCode();
    }
 
 
    public void createCode() {
        int x , fontHeight, codeY;
 
        x = width / (codeCount + 2);// 每个字符的宽度(左右各空出一个字符)
        fontHeight = height - 2;// 字体的高度
        codeY = height - 4;
 
        // 图像buffer
        buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();
        // 生成随机数
        Random random = new Random();
        // 将图像填充为白色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        // 创建字体,可以修改为其它的
        Font font = new Font("Fixedsys", Font.PLAIN, fontHeight);
        // Font font = new Font("Times New Roman", Font.ROMAN_BASELINE, fontHeight);
        g.setFont(font);
 
        for (int i = 0; i < lineCount; i++) {
            // 设置随机开始和结束坐标
            int xs = random.nextInt(width);// x坐标开始
            int ys = random.nextInt(height);// y坐标开始
            int xe = xs + random.nextInt(width / 8);// x坐标结束
            int ye = ys + random.nextInt(height / 8);// y坐标结束
 
            // 产生随机的颜色值，让输出的每个干扰线的颜色值都将不同。
            g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            g.drawLine(xs, ys, xe, ye);
        }
 
        // randomCode记录随机产生的验证码
        StringBuffer randomCode = new StringBuffer();
        // 随机产生codeCount个字符的验证码。
        for (int i = 0; i < codeCount; i++) {
            String strRand = code.substring(i,i+1);
            // 产生随机的颜色值，让输出的每个字符的颜色值都将不同。
            g.setColor(new Color(random.nextInt(150), random.nextInt(150), random.nextInt(150)));
            g.drawString(strRand, (i + 1) * x, codeY);
            // 将产生的四个随机数组合在一起。
            randomCode.append(strRand);
        }
    }
 
    public void write(String path) throws IOException {
        OutputStream sos = new FileOutputStream(path);
        this.write(sos);
    }
 
    public void write(OutputStream sos) throws IOException {
        ImageIO.write(buffImg, "png", sos);
        sos.close();
    }
 
    public BufferedImage getBuffImg() {
        return buffImg;
    }
 
    public String getCode() {
        return code;
    }
 
    /**
     * 测试函数,默认生成到d盘
     *
     * @param args
     */
    public static void main(String[] args) {
        VerificationCodeImgUtil vCode = new VerificationCodeImgUtil(RandomGenUtil.codeGen(5));
        try {
            String path = "D:/" + new Date().getTime() + ".png";
            System.out.println(vCode.getCode() + " >" + path);
            vCode.write(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}