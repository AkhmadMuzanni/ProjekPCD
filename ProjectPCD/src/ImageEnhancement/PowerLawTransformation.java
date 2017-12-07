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
public class PowerLawTransformation {
    BufferedImage image;
    int width;
    int height;
    double C = 1.0;
    double Gamma = 1.0;
    public PowerLawTransformation() {
        try {
            File input = new File("D:\\1.jpg");
            image = ImageIO.read(input);
            image = GrayScale.luminosityGray(image);
            for (int i = 0; i < image.getHeight(); i++) {
                for (int j = 0; j < image.getWidth(); j++) {
                    Color c = new Color(image.getRGB(j, i));                    
                    double powerLaw = C * Math.pow(c.getRed(), Gamma);
//                    if (powerLaw > 255){
//                        powerLaw = 255;
//                    }
                    int intS = (int) Math.round(powerLaw / 255);
//                    System.out.println(powerLaw);
                    System.out.println(powerLaw);
                    Color newColor = new Color(intS, intS, intS);
                    image.setRGB(j, i, newColor.getRGB());
                }
            }
            File ouptut = new File("E:\\powerLaw-"+input.getName());
            ImageIO.write(image, "jpg", ouptut);            
        } catch (IOException e) {
            System.out.println("gagal");
        } 
    }
    static public void main(String args[]) throws Exception {
        PowerLawTransformation obj = new PowerLawTransformation();
        
    }
}
