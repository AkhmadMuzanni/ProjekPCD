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
public class PieceWise {

    BufferedImage image;
    int width;
    int height;
    double C = 20.0;
    double r1 = 100;
    double s1 = 0;
    double r2 = 100;
    double s2 = 255;
//    double r1 = 25;
//    double s1 = 50;
//    double r2 = 150;
//    double s2 = 100;
    
//    public PieceWise(BufferedImage image){
//        this.image = image;
//        this();
//    }

    public PieceWise() {

        try {
//            File input = new File("src\\digital_image_processing.jpg");
            File input = new File("E:\\1.jpg");
            image = ImageIO.read(input);
            this.image = image;
            width = image.getWidth();
            height = image.getHeight();
            
//            for (int i = 0 ; i < r1 ; i++){
//                for (int j = 0 ; j < s1 ; j++){
//                    
//                }
//            }

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    Color c = new Color(image.getRGB(j, i));
                    
                    //int abu = (int) ((c.getRed() + c.getGreen() + c.getBlue()/3));
//                    int negatif = (int)(255 - (c.getRed()+c.getGreen()+c.getBlue())/3);
                    //int red = (int) (c.getRed() * 0.299);
                    //int green = (int) (c.getGreen() * 0.587);
                    //int blue = (int) (c.getBlue() * 0.114);
                    double red = (c.getRed() * 0.299);
                    double green = (c.getGreen() * 0.587);
                    double blue = (c.getBlue() * 0.114);
                    double gray = red + green + blue;
                    double hasil = 0;
                    if (gray < r1){
                        hasil = (gray / r1 )* s1;
//                        System.out.println(gray + " A " + hasil);
                    } else if(gray >= r1 && gray <= r2){
                        hasil = s1 + ((gray-r1) / (r2 - r1) * (s2 - s1));
//                        System.out.println(gray + " B " + hasil);
                    } else {
                        hasil = s2 + (gray - r2) / (255 - r2) * (255 - s2);
//                        System.out.println(gray + " C " + hasil);
                    }
//                    double logTransform = C * log(1.0 + gray);
                    Color newColor = new Color((int)hasil ,
                            (int)hasil, (int)hasil);
//                    Color newColor = new Color((int)logTransform ,
//                            (int)logTransform, (int)logTransform);
//                    Color newColor = new Color(negatif ,
//                            negatif, negatif);

                    image.setRGB(j, i, newColor.getRGB());
                }
            }

            File ouptut = new File("E:\\pieceWise4.jpg");
            ImageIO.write(image, "jpg", ouptut);

        } catch (IOException e) {
            System.out.println(" IO gagal");
        } catch (Exception e){
            System.out.println("proses gagal");
        }
    }
    
    public static BufferedImage PieceWise(BufferedImage image){
        double C = 20.0;
        double r1 = 100;
        double s1 = 0;
        double r2 = 100;
        double s2 = 255;
        
        try {
//            this.image = image;
//            width = image.getWidth();
//            height = image.getHeight();
            for (int i = 0; i < image.getHeight(); i++) {
                for (int j = 0; j < image.getWidth(); j++) {
                    Color c = new Color(image.getRGB(j, i));
                    double red = (c.getRed() * 0.299);
                    double green = (c.getGreen() * 0.587);
                    double blue = (c.getBlue() * 0.114);
                    double gray = red + green + blue;
                    double hasil = 0;
//                    if (gray < r1){
//                        hasil = (gray / r1 )* s1;
////                        System.out.println(gray + " A " + hasil);
//                    } else if(gray >= r1 && gray <= r2){
//                        hasil = s1 + ((gray-r1) / (r2 - r1) * (s2 - s1));
////                        System.out.println(gray + " B " + hasil);
//                    } else {
//                        hasil = s2 + (gray - r2) / (255 - r2) * (255 - s2);
////                        System.out.println(gray + " C " + hasil);
//                    }
                    if (gray < r1){
                        hasil = 0;                        
                    } else {
                        hasil = 255;
                    }
                    Color newColor = new Color((int)hasil ,
                            (int)hasil, (int)hasil);
//                    System.out.println(hasil);
                    image.setRGB(j, i, newColor.getRGB());
                }
            }

//            File ouptut = new File("E:\\pieceWise4.jpg");
//            ImageIO.write(image, "jpg", ouptut);

        } catch (Exception e){
            System.out.println("proses gagal");
        }
        return image;
    }

    static public void main(String args[]) throws Exception {
        PieceWise obj = new PieceWise();
        
    }

//    private PieceWise() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
}
