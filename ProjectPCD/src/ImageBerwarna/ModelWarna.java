/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImageBerwarna;

//import Histogram.*;
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
public class ModelWarna {
    BufferedImage image;
    public ModelWarna() throws IOException{
        File input = new File("E:\\image2.jpg");
        image = ImageIO.read(input);
        RGBtoCMY();
//        File output = new File("E:\\CMY-"+input.getName());
//        ImageIO.write(image, "jpg", output);
//        int[][] a = {{1,3,5},{2,4,6}};
//        int[][] b = {{1,2},{4,5},{7,8}};
//        kaliMatrik(a,b);
        RGBtoYUV();
    }
    public double[][] kaliMatrik(double[][] a, double[][] b){
        double[][] result = new double[a.length][b[0].length];
        if (a[0].length == b.length){
            for (int i = 0 ; i < result.length ; i++){
                for(int j = 0 ; j < result[i].length ; j++){
                    for (int k = 0 ; k < a[0].length ; k++){
                        result[i][j] += a[i][k] * b[k][j];
                    }
                }
            }
        }
        return result;
    }
    public void RGBtoYUV(){
        for (int i = 0 ; i < image.getHeight() ; i++){
            for (int j = 0 ; j < image.getWidth() ; j++){
                Color warna = new Color(image.getRGB(j, i));
                double[][] gonzales = {{0.299, 0.587, 0.114},{-0.169,-0.331,0.5},{0.5,-0.419,-0.081}};
                double[][] rgb = {{warna.getRed()},{warna.getGreen()},{warna.getBlue()}};
                double[][] yuv = kaliMatrik(gonzales,rgb);
                System.out.println(j + " " + i + " => " + yuv[0][0] + " " + yuv[1][0] + " " + yuv[2][0]);
//                Color newWarna = new Color((int)yuv[0][0], (int)yuv[1][0], (int)yuv[2][0]);
//                image.setRGB(j, i, newWarna.getRGB());
            }
        }
    }
    public void RGBtoHSI(){
        for (int i = 0 ; i < image.getHeight() ; i++){
            for (int j = 0 ; j < image.getWidth() ; j++){
                Color warna = new Color(image.getRGB(j, i));                
                double saturation = 1 - (3 / (warna.getRed() + warna.getGreen() + warna.getBlue())) * 
                        Math.min(warna.getRed(), Math.min(warna.getGreen(), warna.getBlue()));
                double intensity = (warna.getRed() + warna.getGreen() + warna.getBlue())/3;
                Color newWarna = new Color(255 - warna.getRed(), 255 - warna.getGreen(), 255 - warna.getBlue());
                image.setRGB(j, i, newWarna.getRGB());
            }
        }
    }
    public void RGBtoCMY(){
        for (int i = 0 ; i < image.getHeight() ; i++){
            for (int j = 0 ; j < image.getWidth() ; j++){
                Color warna = new Color(image.getRGB(j, i));
                Color newWarna = new Color(255 - warna.getRed(), 255 - warna.getGreen(), 255 - warna.getBlue());
                image.setRGB(j, i, newWarna.getRGB());
            }
        }
    }
    public static void main(String[] args) throws IOException {
//        BufferedImage aimage = new BufferedImage();
        ModelWarna histo = new ModelWarna();
    }
}
