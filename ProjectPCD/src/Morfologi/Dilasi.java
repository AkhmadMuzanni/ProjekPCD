/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Morfologi;

import Histogram.*;
import ImageEnhancement.GrayScale;
import ImageEnhancement.PieceWise;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author USER
 */
public class Dilasi {
    BufferedImage image, filtered, result;
    BufferedImage image2, filtered2, result2, hasilSubstraksi;
    int color;
    double[][] filter, filter2;    
    double[][] strElement = {{1, 1, 1},{1, 1, 1},{1, 1, 1}};
    public Dilasi() throws IOException{
        File input = new File("E:\\2.jpg");
        File input2 = new File("E:\\2.jpg");
        image = ImageIO.read(input);
        image2 = ImageIO.read(input);
        filtered = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        hasilSubstraksi = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        filtered2 = new BufferedImage(image2.getWidth(), image2.getHeight(), image2.getType());
        filtered = GrayScale.luminosityGray(image);        
        filtered = PieceWise.PieceWise(filtered);
        filtered2 = PieceWise.PieceWise(filtered2);
        filtered = setStrElement(filtered, 0);
        filtered2 = setStrElement(filtered2, 1);
//        hasilSubstraksi = 
        substraksiImage(filtered, filtered2);
        File output = new File("E:\\Substraksi-"+input.getName());
        ImageIO.write(hasilSubstraksi, "jpg", output);
    }
    public BufferedImage setStrElement(BufferedImage image, int jenis){
        result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());        
        int x = strElement.length / 2;
        int y = strElement[0].length / 2;        
        for (int i = 0 ; i < image.getHeight() ; i++){
            for (int j = 0 ; j < image.getWidth() ; j++){
                String se = "", warna = "";
                for (int k = 0 ; k < strElement.length ; k++){
                    for (int l = 0 ; l < strElement[0].length ; l++){
                        Color asli = new Color(image.getRGB(l, k));
                        if(getColor(j-(k-x),i-(l-y)) != ""){
                            se += (int)strElement[l][k];
                            warna += getColor(j-(k-x),i-(l-y));
                        }
                    }
                }
                Color newWarna = new Color(0,0,0);
                if (jenis == 0){
                    newWarna = warnaHit(warna, se);
                } else {
                    newWarna = warnaFit(warna, se);
                }
                result.setRGB(j, i, newWarna.getRGB());
            }
        }
        return result;
    }    
    public Color warnaHit(String warna, String se){
        Color newWarna;
        if(hit(warna, se)){
            newWarna = new Color(255, 255, 255);
        } else {
            newWarna = new Color(0, 0, 0);
        }
//        result.setRGB(x, y, newWarna.getRGB());
        return newWarna;
    }
    public Color warnaFit(String warna, String se){
        Color newWarna;
        if(fit(warna, se)){
            newWarna = new Color(255, 255, 255);
        } else {
            newWarna = new Color(0, 0, 0);
        }
//        result.setRGB(x, y, newWarna.getRGB());
        return newWarna;
    }
    public void substraksiImage(BufferedImage image1, BufferedImage image2){
        hasilSubstraksi = new BufferedImage(image1.getWidth(), image1.getHeight(), image1.getType());        
//        int x = strElement.length / 2;
//        int y = strElement[0].length / 2;        
        for (int i = 0 ; i < image1.getHeight() ; i++){
            for (int j = 0 ; j < image1.getWidth() ; j++){
//                String se = "", warna = "";
//                for (int k = 0 ; k < strElement.length ; k++){
//                    for (int l = 0 ; l < strElement[0].length ; l++){
//                        Color asli = new Color(image.getRGB(l, k));
//                        if(getColor(j-(k-x),i-(l-y)) != ""){
//                            se += (int)strElement[l][k];
//                            warna += getColor(j-(k-x),i-(l-y));
//                        }
//                    }
//                }
                Color warnaImg1 = new Color(image1.getRGB(j, i));
                Color warnaImg2 = new Color(image2.getRGB(j, i));
                Color newWarna = new Color(0,0,0);
                if (warnaImg1.getRed() != warnaImg2.getRed()){
                    newWarna = new Color(255,255,255);
                }
//                Color warnaImg1 = new Color(0,0,0);
//                Color warnaImg2 = new Color(0,0,0);
//                newWarna = warnaHit(warna, se);
//                newWarna = warnaFit(warna, se);
                hasilSubstraksi.setRGB(j, i, newWarna.getRGB());
            }
        }
    }
    public boolean hit(String s1, String s2){
        boolean res = false;
        if (s1.length() == s2.length()){
            for (int i = 0 ; i < s1.length() ; i++){
                if (s1.charAt(i) == s2.charAt(i)){
                    res = true;
                    break;
                }
            }
        }
        return res;
    }
    public boolean fit(String s1, String s2){
        boolean res = true;
        if (s1.length() == s2.length()){
            for (int i = 0 ; i < s1.length() ; i++){
                if (s1.charAt(i) != s2.charAt(i)){
                    res = false;
                    break;
                }
            }
        } else {
            res = false;
        }
        return res;
    }
    public String getColor(int x,int y){
        try{
            Color asli = new Color(image.getRGB(x, y));
            if(asli.getRed() == 255){
                return "1";
            } else if (asli.getRed() == 0){
                return "0";
            } else {
                return "("+asli.getRed()+")";
            }
//            return Integer.toString(asli.getRed());
        } catch(ArrayIndexOutOfBoundsException ex){
            return "";
        }
    }
    public double getColorDouble(int x,int y){
        try{
            Color asli = new Color(image.getRGB(x, y));
            return asli.getRed();
        } catch(ArrayIndexOutOfBoundsException ex){
            return color;
        }
    }
    public static void main(String[] args) throws IOException {
        Dilasi dilasi = new Dilasi();
    }
}
