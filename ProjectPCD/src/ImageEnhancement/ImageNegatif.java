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
public class ImageNegatif {
    BufferedImage image;
    int width;
    int height;
    double C = 20.0;
    public ImageNegatif() {
        try {
            File input = new File("D:\\1.jpg");
            image = ImageIO.read(input);
            width = image.getWidth();
            height = image.getHeight();
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    Color c = new Color(image.getRGB(j, i));
                    int negatif = (int)(255 - (c.getRed()+c.getGreen()+c.getBlue())/3);
                    Color newColor = new Color(negatif, negatif, negatif);
                    image.setRGB(j, i, newColor.getRGB());
                }
            }

            File ouptut = new File("E:\\negatif-"+input.getName());
            ImageIO.write(image, "jpg", ouptut);
            System.out.println(input.getName());
        } catch (Exception e) {
            System.out.println("gagal");
        }
    }
    public static BufferedImage ImageNegatif(BufferedImage image){        
        int width = image.getWidth();
        int height = image.getHeight();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color c = new Color(image.getRGB(j, i));
                int negatif = (int)(255 - (c.getRed()+c.getGreen()+c.getBlue())/3);
                Color newColor = new Color(negatif, negatif, negatif);
                image.setRGB(j, i, newColor.getRGB());
            }
        }
        return image;
    }
    static public void main(String args[]) throws Exception {
        ImageNegatif obj = new ImageNegatif();        
    }
}
