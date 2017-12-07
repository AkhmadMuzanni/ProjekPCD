/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImageEnhancement;
import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.*;
import static java.lang.Math.log;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 *
 * @author Widodo, Muzanni
 */
public class LogTransformation {
    BufferedImage image;
    int width;
    int height;
    double C = 10.0;
    public LogTransformation() {
        try {
            File input = new File("D:\\1.jpg");
            image = ImageIO.read(input);
            image = GrayScale.luminosityGray(image);
            for (int i = 0; i < image.getHeight(); i++) {
                for (int j = 0; j < image.getWidth(); j++) {
                    Color c = new Color(image.getRGB(j, i));
                    double logTransform = C * log(1.0 + c.getRed());
                    Color newColor = new Color((int)logTransform, (int)logTransform, (int)logTransform);
                    image.setRGB(j, i, newColor.getRGB());
                }
            }
            File ouptut = new File("E:\\logTrans-"+input.getName());
            ImageIO.write(image, "jpg", ouptut);            
        } catch (Exception e) {
            System.out.println("gagal");
        }
    }
    static public void main(String args[]) throws Exception {
        LogTransformation obj = new LogTransformation();
        
    }
}
