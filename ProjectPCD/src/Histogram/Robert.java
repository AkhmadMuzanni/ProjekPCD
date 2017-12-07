/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Histogram;

import ImageEnhancement.GrayScale;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author USER
 */
public class Robert {
    BufferedImage image;
    public Robert() throws IOException{
        File input = new File("E:\\gray-1.jpg");
        image = ImageIO.read(input);
        image = GrayScale.luminosityGray(image);        
        int[][] histo = new int[256][2];
        for (int i = 0 ; i < histo.length ; i++){
            histo[i][0] = i;
        }
//        for (int i = 0 ; i < image.getHeight() ; i++){
//            for (int j = 0 ; j < image.getWidth() ; j++){
//                Color warna = new Color(image.getRGB(j, i));
//                histo[warna.getRed()][1]++;
//            }
//        }
//        for (int i = 0 ; i < histo.length ; i++){
//            System.out.println(histo[i][0] + " => " + histo[i][1]);
//        }
    }
    public static void main(String[] args) throws IOException {
//        BufferedImage aimage = new BufferedImage();
        Robert histo = new Robert();
    }
}
