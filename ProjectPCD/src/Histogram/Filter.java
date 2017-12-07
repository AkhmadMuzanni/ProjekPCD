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
public class Filter {
    BufferedImage image, filtered;
    int color;
    double[][] filter, filter2;
    public Filter() throws IOException{
        File input = new File("E:\\1.jpg");
        image = ImageIO.read(input);
        image = GrayScale.luminosityGray(image);        
//        setHighPassFilter();
//        setRobertFilter();
//        setSobelFilter();
//        setMedianFilter();
        setPrewittFilter();
//        setLaplacianFilter();
        File output = new File("E:\\PrewittFilter-"+input.getName());
        ImageIO.write(filtered, "jpg", output);
    }
    public void setHighPassFilter(){
        filter = new double[3][3];
        for (int i = 0 ; i < filter.length ; i++){
            for (int j = 0 ; j < filter[i].length ; j++){
                filter[i][j] = -1;
            }
        }
        filter[1][1] = 9;
        konvolusi3x3();
    }
    public void setLowPassFilter(){
        filter = new double[3][3];
        for (int i = 0 ; i < filter.length ; i++){
            for (int j = 0 ; j < filter[i].length ; j++){
                filter[i][j] = 1.0/9.0;                
            }
        }
        konvolusi3x3();
    }
    public void setPrewittFilter(){
        filter = new double[3][3];
        filter2 = new double[3][3];
        for (int i = 0 ; i < 2 ; i++){
            filter[0][i] = -1;
            filter[2][i] = 1;
            filter2[i][0] = -1;
            filter2[i][2] = 1;
        }
        konvolusi233();
    }
    public void setRobertFilter(){
        filter = new double[2][2];        
        filter2 = new double[2][2];        
        filter[0][0] = -1;
        filter[1][1] = 1;
        filter2[1][0] = 1;
        filter2[0][1] = -1;
        konvolusi222();
    }
    public void setSobelFilter(){
        filter = new double[3][3];
        filter2 = new double[3][3];
        filter[0][0] = -1;
        filter[0][1] = -2;
        filter[0][2] = -1;        
        filter[2][0] = 1;
        filter[2][1] = 2;
        filter[2][2] = 1;
        filter2[0][0] = -1;
        filter2[1][0] = -2;
        filter2[2][0] = -1;
        filter2[0][2] = 1;
        filter2[1][2] = 2;
        filter2[2][2] = 1;
        konvolusi233();
    }
    public void setLaplacianFilter(){
        filter = new double[3][3];
        filter2 = new double[3][3];
        for (int i = 0 ; i < filter2.length ; i++){
            for (int j = 0 ; j < filter2[i].length ; j++){
                filter2[i][j] = 1;
            }
        }
        filter[1][0] = filter[0][1] = filter[1][2] = filter[2][1] = 1;
        filter[1][1] = -5;
        filter2[1][1] = -9;
        konvolusi233();
    }
    public void setMedianFilter(){
        filtered = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        for (int i = 0 ; i < image.getHeight() ; i++){
            for (int j = 0 ; j < image.getWidth() ; j++){
                Color asli = new Color(image.getRGB(j, i));
                color = asli.getRed();
                double warna = 0;
                double[] dataWarna = {getColor(j-1,i-1), getColor(j,i-1), getColor(j+1,i-1),
                getColor(j-1,i), getColor(j,i), getColor(j+1,i),
                getColor(j-1,i+1), getColor(j,i+1), getColor(j+1,i+1)};
                warna = hitungMedian(dataWarna);
                Color newWarna = new Color((int)warna, (int)warna, (int)warna);
                filtered.setRGB(j, i, newWarna.getRGB());
            }
        }
    }
    public double hitungMedian(double[] data){
        for (int i = 0; i < data.length - 1; i++) {
//            System.out.println("Langkah " + (i + 1) + ":");
            for (int j = data.length - 1; j > i; j--) {
                if (data[j - 1] < data[j]) { // Yang Diganti
                    double temp = data[j];
                    data[j] = data[j - 1];
                    data[j - 1] = temp;
                }
//                System.out.println(L[j] + " index =" + (j + 1));
            }
//            System.out.println(L[j] + " index =" + (j + 1));
        }
        if (data.length % 2 == 1){
            return data[(data.length/2) + 1];
        } else {
            return (data[data.length / 2] + data[data.length + 1])/2;
        }
        
    }
    public void konvolusi3x3(){
        filtered = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        for (int i = 0 ; i < image.getHeight() ; i++){
            for (int j = 0 ; j < image.getWidth() ; j++){
                Color asli = new Color(image.getRGB(j, i));
                color = asli.getRed();
                double warna = 0;
                warna += (int)(filter[0][0] * getColor(j-1,i-1));
                warna += (int)(filter[0][1] * getColor(j,i-1));
                warna += (int)(filter[0][2] * getColor(j+1,i-1));
                warna += (int)(filter[1][0] * getColor(j-1,i));
                warna += (int)(filter[1][1] * getColor(j,i));
                warna += (int)(filter[1][2] * getColor(j+1,i));
                warna += (int)(filter[2][0] * getColor(j-1,i+1));
                warna += (int)(filter[2][1] * getColor(j,i+1));
                warna += (int)(filter[2][2] * getColor(j+1,i+1));
                if (warna > 255){
                    warna = 255;                    
                } else if (warna < 0){
                    warna = 0;
                }
                Color newWarna = new Color((int)warna, (int)warna, (int)warna);
                filtered.setRGB(j, i, newWarna.getRGB());
            }
        }
    }
    public void konvolusi222(){
        filtered = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        for (int i = 0 ; i < image.getHeight() ; i++){
            for (int j = 0 ; j < image.getWidth() ; j++){
                Color asli = new Color(image.getRGB(j, i));
                color = asli.getRed();
                double warna = 0;
                warna += (int)(filter[0][0] * getColor(j,i));                
                warna += (int)(filter[1][1] * getColor(j+1,i+1));                
                warna += (int)(filter2[0][1] * getColor(j,i+1));
                warna += (int)(filter2[1][0] * getColor(j+1,i));
                if (warna > 255){
                    warna = 255;                    
                } else if (warna < 0){
                    warna = 0;
                }
                Color newWarna = new Color((int)warna, (int)warna, (int)warna);
                filtered.setRGB(j, i, newWarna.getRGB());
            }
        }
    }
    public void konvolusi233(){
        filtered = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        for (int i = 0 ; i < image.getHeight() ; i++){
            for (int j = 0 ; j < image.getWidth() ; j++){
                Color asli = new Color(image.getRGB(j, i));
                color = asli.getRed();
                double warna = 0;
                warna += (int)(filter[0][0] * getColor(j-1,i-1));
                warna += (int)(filter[0][1] * getColor(j-1,i));
                warna += (int)(filter[0][2] * getColor(j-1,i+1));
                warna += (int)(filter[1][0] * getColor(j,i-1));                
                warna += (int)(filter[1][1] * getColor(j,i));
                warna += (int)(filter[1][2] * getColor(j,i+1));
                warna += (int)(filter[2][0] * getColor(j+1,i-1));                
                warna += (int)(filter[2][1] * getColor(j+1,i));
                warna += (int)(filter[2][2] * getColor(j+1,i+1));
                warna += (int)(filter2[0][0] * getColor(j-1,i-1));
                warna += (int)(filter2[0][1] * getColor(j-1,i));
                warna += (int)(filter2[0][2] * getColor(j-1,i+1));
                warna += (int)(filter2[1][0] * getColor(j,i-1));                
                warna += (int)(filter2[1][1] * getColor(j,i));
                warna += (int)(filter2[1][2] * getColor(j,i+1));
                warna += (int)(filter2[2][0] * getColor(j+1,i-1));                
                warna += (int)(filter2[2][1] * getColor(j+1,i));
                warna += (int)(filter2[2][2] * getColor(j+1,i+1));
//                System.out.println(j + " " + i + " " + warna);
                if (warna > 255){
                    warna = 255;                    
                } else if (warna < 0){
                    warna = 0;
                }
                Color newWarna = new Color((int)warna, (int)warna, (int)warna);
                filtered.setRGB(j, i, newWarna.getRGB());
            }
        }
    }
    public double getColor(int x,int y){
        try{
            Color asli = new Color(image.getRGB(x, y));
            return asli.getRed();
        } catch(ArrayIndexOutOfBoundsException ex){
            return color;
        }
    }
    public static void main(String[] args) throws IOException {
        Filter histo = new Filter();
    }
}
