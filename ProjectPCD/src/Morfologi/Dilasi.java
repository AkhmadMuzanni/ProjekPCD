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
    int color;
    double[][] filter, filter2;    
    public Dilasi() throws IOException{
        File input = new File("E:\\2.jpg");
        image = ImageIO.read(input);
        filtered = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        filtered = GrayScale.luminosityGray(image);
        filtered = PieceWise.PieceWise(filtered);
        setStrElement(filtered);
        File output = new File("E:\\Erosi-"+input.getName());
        ImageIO.write(result, "jpg", output);
    }
    public void setStrElement(BufferedImage image){
        result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        double[][] strElement = {{1, 1, 1},{1, 1, 1},{1, 1, 1}};
        int x = strElement.length / 2;
        int y = strElement[0].length / 2;        
        for (int i = 0 ; i < image.getHeight() ; i++){
            for (int j = 0 ; j < image.getWidth() ; j++){
                String warnaAsli = "", warna = "";
                for (int k = 0 ; k < strElement.length ; k++){
                    for (int l = 0 ; l < strElement[0].length ; l++){
                        Color asli = new Color(image.getRGB(l, k));
                        if(getColor(j-(k-x),i-(l-y)) != ""){
                            warnaAsli += (int)strElement[l][k];
                            warna += getColor(j-(k-x),i-(l-y));
                        }
                    }
                }
                Color newWarna;
//                if(hit(warna, warnaAsli)){
                if(fit(warna, warnaAsli)){
                    newWarna = new Color(255, 255, 255);
                } else {
                    newWarna = new Color(0, 0, 0);
                }
                result.setRGB(j, i, newWarna.getRGB());
//                Color warnaBaru = new Color(result.getRGB(j, i));
//                System.out.println(warna + " - " + warnaAsli + " - " + hit(warna, warnaAsli) + " - " + fit(warna, warnaAsli) + " - " + newWarna);
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
