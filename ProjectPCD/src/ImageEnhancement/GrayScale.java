/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImageEnhancement;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
/**
 *
 * @author Widodo, Muzanni
 */
public class GrayScale {
    BufferedImage image;
    int width;
    int height;
    double C = 20.0;
    public GrayScale() {
        try {
            File input = new File("D:\\1.jpg");
            image = ImageIO.read(input);            
            image = GrayScale.luminosityGray(image);
            File output = new File("E:\\gray2-"+input.getName());
            ImageIO.write(image, "jpg", output);
        } catch (Exception e) {
            System.out.println("Gagal");
        }
    }
    public static BufferedImage lightnessGray(BufferedImage image){
        for (int i = 0; i < image.getHeight() ; i++){
            for (int j = 0 ; j < image.getWidth() ; j++){
                Color c = new Color(image.getRGB(j, i));
                int gray = (Math.max(Math.max(c.getRed(), c.getGreen()), c.getBlue()) 
                        + Math.min(Math.min(c.getRed(), c.getGreen()), c.getBlue())) / 2;
                Color newColor = new Color(gray , gray, gray);
                image.setRGB(j, i, newColor.getRGB());
            }
        }
        return image;
    }
    public static BufferedImage luminosityGray(BufferedImage image){
        for (int i = 0; i < image.getHeight() ; i++){
            for (int j = 0 ; j < image.getWidth() ; j++){
                Color c = new Color(image.getRGB(j, i));
                double red = (c.getRed() * 0.299);
                double green = (c.getGreen() * 0.587);
                double blue = (c.getBlue() * 0.114);
                double gray = red + green + blue;
                Color newColor = new Color((int)gray , (int)gray, (int)gray);
                image.setRGB(j, i, newColor.getRGB());
            }
        }
        return image;
    }
    public static BufferedImage averageGray(BufferedImage image){
        for (int i = 0; i < image.getHeight() ; i++){
            for (int j = 0 ; j < image.getWidth() ; j++){
                Color c = new Color(image.getRGB(j, i));
                int gray = (c.getRed() + c.getGreen() + c.getBlue()) / 3;
                Color newColor = new Color((int)gray , (int)gray, (int)gray);
                image.setRGB(j, i, newColor.getRGB());
            }
        }
        return image;
    }

    static public void main(String args[]) throws Exception {
        GrayScale obj = new GrayScale();
        
    }
}
