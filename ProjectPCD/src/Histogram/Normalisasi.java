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
public class Normalisasi {
    BufferedImage image;
    public Normalisasi() throws IOException{
        File input = new File("E:\\logTransfrom.jpg");
        image = ImageIO.read(input);
        image = GrayScale.luminosityGray(image);
        int[][] histo = new int[256][2];
        int[][] normalisasi = new int[256][2];
        for (int i = 0 ; i < histo.length ; i++){
            histo[i][0] = i;
            normalisasi[i][0] = i;
        }
        for (int i = 0 ; i < image.getHeight() ; i++){
            for (int j = 0 ; j < image.getWidth() ; j++){
                Color warna = new Color(image.getRGB(j, i));
                histo[warna.getRed()][1]++;
            }
        }
        double dimensi = image.getHeight() * image.getWidth();
        normalisasi[0][1] = histo[0][1];
        histo[0][1] = (int)((normalisasi[0][1] / dimensi) * 255);
        for (int i = 0 ; i < histo.length ; i++){            
            if(i != 0){
                normalisasi[i][1] = normalisasi[i - 1][1] + histo[i][1];
                histo[i][1] = (int)((normalisasi[i][1] / dimensi) * 255);
            }
        }
        for (int i = 0 ; i < histo.length ; i++){
            System.out.println(histo[i][0] + " => " + histo[i][1] + " / " + normalisasi[i][1]);
        }
        for (int i = 0 ; i < image.getHeight() ; i++){
            for (int j = 0 ; j < image.getWidth() ; j++){
                Color warna = new Color(image.getRGB(j, i));
                Color newWarna = new Color(histo[warna.getRed()][1], histo[warna.getRed()][1], histo[warna.getRed()][1]);
                image.setRGB(j, i, newWarna.getRGB());
            }
        }
        File output = new File("E:\\normalisasi-"+input.getName());
        ImageIO.write(image, "jpg", output);
    }
    public static void main(String[] args) throws IOException {
//        BufferedImage aimage = new BufferedImage();
        Normalisasi histo = new Normalisasi();
    }
}
