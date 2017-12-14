/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MomenInvarian;

import Morfologi.*;
import Histogram.*;
import ImageEnhancement.GrayScale;
import ImageEnhancement.PieceWise;
import java.awt.Color;
import java.awt.List;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
//import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 * @author USER
 */
class Node{
    private double warna;
    private int x;
    private int y;
    public Node (double warna, int x, int y){
        this.warna = warna;
        this.x = x;
        this.y = y;
    }

    public double getWarna() {
        return warna;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
}
public class MomenInvarian {
    BufferedImage image, filtered, result;
    BufferedImage image2, filtered2, result2, hasilSubstraksi;
    int color;
    double[][] filter, filter2;    
    double[][] strElement = {{1, 1, 1},{1, 1, 1},{1, 1, 1}};
    ArrayList<Node> node = new ArrayList<Node>();
    public MomenInvarian() throws IOException{
        File input = new File("E:\\kotak.bmp");
        image = ImageIO.read(input);
        double[] ciriKotak = {getCiri1(), getCiri2(), getCiri3(), getCiri4(), getCiri5(), getCiri6(), getCiri7()};
        input = new File("E:\\segitiga.bmp");
        image = ImageIO.read(input);
        double[] ciriSegitiga = {getCiri1(), getCiri2(), getCiri3(), getCiri4(), getCiri5(), getCiri6(), getCiri7()};
        input = new File("E:\\lingkaran.bmp");
        image = ImageIO.read(input);
        double[] ciriLingkaran = {getCiri1(), getCiri2(), getCiri3(), getCiri4(), getCiri5(), getCiri6(), getCiri7()};
        input = new File("E:\\kotak5.bmp");
        image = ImageIO.read(input);
        double[] ciriUji = {getCiri1(), getCiri2(), getCiri3(), getCiri4(), getCiri5(), getCiri6(), getCiri7()};
        double jarakKotak = hitungJarak(ciriKotak, ciriUji);
        double jarakSegitiga = hitungJarak(ciriSegitiga, ciriUji);
        double jarakLingkaran = hitungJarak(ciriLingkaran, ciriUji);
        System.out.println("Jarak ke Kotak " + jarakKotak);
        System.out.println("Jarak ke Segitiga " + jarakSegitiga);
        System.out.println("Jarak ke Lingkaran " + jarakLingkaran);        
//        if ()
    }
    public int getMomenCitra(int p, int q){
        int result = 0;
        for (int i = 0 ; i < image.getHeight() ; i++){
            for (int j = 0 ; j < image.getWidth() ; j++){
                if (getColor(j,i) == 1){
                    result += Math.pow(j, p) * Math.pow(i, q) * getColor(j,i);                    
                }
            }
        }
        return result;
    }
    public double getMomenRataRata(int i){
        if (i == 0){
            return (double)getMomenCitra(1,0) / (double)getMomenCitra(0,0);
        } else {
            return (double)getMomenCitra(0,1) / (double)getMomenCitra(0,0);
        }
    }
    public double getMomenPusat(int p, int q){
        double xBar = getMomenRataRata(0);
        double yBar = getMomenRataRata(1);
        double result = 0;
        for (int i = 0 ; i < image.getHeight() ; i++){
            for (int j = 0 ; j < image.getWidth() ; j++){
//                System.out.println(getColor(j, i));
                if (getColor(j,i) == 1){
                    result += Math.pow(j - xBar, p) * Math.pow(i - yBar, q) * getColor(j,i);
//                    System.out.println(Math.pow(j - xBar, p)+ " "+ Math.pow(i - yBar, q) + " "+getColor(j,i));
                }
            }
        }
        return result;
    }
    public double getMN(int p, int q){
        return getMomenPusat(p, q) / Math.pow(getMomenPusat(0, 0), ((double)(p+q)/2.0)+1);
    }
    public double getCiri1(){
        return getMN(2, 0) + getMN(0,2);
    }
    public double getCiri2(){
        return Math.pow(getCiri1(), 2) + (4 * Math.pow(getMN(1, 1),2));
    }
    public double getCiri3(){
        return Math.pow(getMN(3, 0) - 3 * getMN(1, 2),2) +
                Math.pow(3 * getMN(2, 1) - getMN(0, 3),2);                
    }
    public double getCiri4(){
        return Math.pow(getMN(3, 0) + getMN(1, 2),2) +
                Math.pow(getMN(2, 1) + getMN(0, 3),2);                
    }
    public double getCiri5(){
        return ((getMN(3, 0) - 3 * getMN(1, 2)) * 
                ((getMN(3, 0) + getMN(1, 2)) * 
                (Math.pow(getMN(3, 0) + getMN(1, 2),2) - 3 * Math.pow(getMN(2, 1) + getMN(0, 3),2)))) +
                ((3 * getMN(2, 1) - getMN(0, 3)) * 
                ((getMN(2, 1) + getMN(0, 3)) * 
                (3 * Math.pow(getMN(3, 0) + getMN(1, 2),2) - Math.pow(getMN(2, 1) + getMN(0, 3),2))));
    }
    public double getCiri6(){
        return ((getMN(2,0) - getMN(0,2)) *
                (Math.pow(getMN(3, 0) + getMN(1, 2),2) - 3 * Math.pow(getMN(2, 1) + getMN(0, 3),2))) + 
                4 * getMN(1,1) * (Math.pow(getMN(3, 0) + getMN(1, 2),2) * Math.pow(getMN(2, 1) + getMN(0, 3),2))
                ;
    }
    public double getCiri7(){
        return ((3 * getMN(2, 1) - getMN(0, 3)) * 
                ((getMN(3, 0) + getMN(1, 2)) * 
                (Math.pow(getMN(3, 0) + getMN(1, 2),2) - 3 * Math.pow(getMN(2, 1) + getMN(0, 3),2)))) +
                ((3 * getMN(1, 2) - getMN(3, 0)) * 
                ((getMN(2, 1) + getMN(0, 3)) * 
                (3 * Math.pow(getMN(3, 0) + getMN(1, 2),2) - Math.pow(getMN(2, 1) + getMN(0, 3),2))));
    }
    public double hitungJarak(double[] array1, double[] array2){
        double result = 0;
        if (array1.length == array2.length){
            for (int i = 0 ; i < array1.length ; i++){
                result += Math.pow(array1[i] - array2[i], 2);
            }
        }
        return result;
    }
    public int getColor(int x,int y){
        try{
            Color asli = new Color(image.getRGB(x, y));
            if(asli.getRed() == 255){
                return 1;
            } else {
                return 0;
            }
//            return Integer.toString(asli.getRed());
        } catch(ArrayIndexOutOfBoundsException ex){
            return 0;
        }
    }    
    public static void main(String[] args) throws IOException {
        MomenInvarian momenInvarian = new MomenInvarian();
    }
}
