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
//import static MomenInvarian.Sql.con;
//import static MomenInvarian.Sql.stm;
import java.awt.Color;
import java.awt.List;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
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
class Gambar{
    private String idGambar;
    private double jarakGambar;
    public Gambar(String idGambar, double jarakGambar){
        this.idGambar = idGambar;
        this.jarakGambar = jarakGambar;
    }
    public double getJarakGambar(){
        return jarakGambar;
    }
    public String getIdGambar(){
        return idGambar;
    }
}
public class MomenInvarian {
    BufferedImage image, filtered, result;
    BufferedImage image2, filtered2, result2, hasilSubstraksi;
    int color;
    double[][] filter, filter2;    
    double[][] strElement = {{1, 1, 1},{1, 1, 1},{1, 1, 1}};
    public static Connection con;
    public static Statement stm;
    String url ="jdbc:mysql://localhost/deteksikendaraan";
    String user="root";
    String pass="";
    String driver = "com.mysql.jdbc.Driver";
    ResultSet rs;
    ArrayList<Node> node = new ArrayList<Node>();
    public MomenInvarian() throws IOException, SQLException{
        File input = new File("E:\\dataset/segitiga6.bmp");
        image = ImageIO.read(input);
        double[] ciriUji = {getCiri1(), getCiri2(), getCiri3(), getCiri4(), getCiri5(), getCiri6(), getCiri7()};
        double jarak10 = hitungJarak(selectCiri("G010"), ciriUji);
        double jarak11 = hitungJarak(selectCiri("G011"), ciriUji);
        double jarak12 = hitungJarak(selectCiri("G012"), ciriUji);
        double jarak13 = hitungJarak(selectCiri("G013"), ciriUji);
        double jarak14 = hitungJarak(selectCiri("G014"), ciriUji);
        double jarak15 = hitungJarak(selectCiri("G015"), ciriUji);
        double jarak16 = hitungJarak(selectCiri("G016"), ciriUji);
        double jarak17 = hitungJarak(selectCiri("G017"), ciriUji);
        double jarak18 = hitungJarak(selectCiri("G018"), ciriUji);
        double jarak19 = hitungJarak(selectCiri("G019"), ciriUji);
        double jarak20 = hitungJarak(selectCiri("G020"), ciriUji);
        double jarak21 = hitungJarak(selectCiri("G021"), ciriUji);
        ArrayList<Gambar> listJarak = new ArrayList<Gambar>();
        listJarak.add(new Gambar("G010", jarak10));
        listJarak.add(new Gambar("G011", jarak11));
        listJarak.add(new Gambar("G012", jarak12));
        listJarak.add(new Gambar("G013", jarak13));
        listJarak.add(new Gambar("G014", jarak14));
        listJarak.add(new Gambar("G015", jarak15));
        listJarak.add(new Gambar("G016", jarak16));
        listJarak.add(new Gambar("G017", jarak17));
        listJarak.add(new Gambar("G018", jarak18));
        listJarak.add(new Gambar("G019", jarak19));
        listJarak.add(new Gambar("G020", jarak20));
        listJarak.add(new Gambar("G021", jarak21));        

        // Menentukan parameter perbandingan
        Comparator<Gambar> c = (o1, o2) -> {
            if (o1.getJarakGambar() < o2.getJarakGambar()){
                return -1;
            } else {
                return 1;
            }
        };
        // Sorting jarak terdekat
        listJarak.sort(c);
        int k = 3, jmlKotak = 0, jmlSegitiga = 0, jmlLingkaran = 0;
        for (int i = 0 ; i < listJarak.size() && i < k; i++){
            System.out.println(listJarak.get(i).getIdGambar() + " - " + selectKelas(listJarak.get(i).getIdGambar()));
            if (selectKelas(listJarak.get(i).getIdGambar()) == "Kotak"){
                jmlKotak++;
            } else if (selectKelas(listJarak.get(i).getIdGambar()) == "Segitiga"){
                jmlSegitiga++;
            } else if (selectKelas(listJarak.get(i).getIdGambar()) == "Lingkaran"){
                jmlLingkaran++;
            }
            System.out.println(listJarak.get(i).getJarakGambar());
        }
        int kelasTerpilih = Math.max(jmlKotak, Math.max(jmlSegitiga, jmlLingkaran));
        if (kelasTerpilih == jmlKotak){
            System.out.println("Objek Termasuk Kelas Kotak");
        } else if (kelasTerpilih == jmlSegitiga){
            System.out.println("Objek Termasuk Kelas Segitiga");
        } else {
            System.out.println("Objek Termasuk Kelas Lingkaran");
        }
        
        // Untuk Upload ke database

//        insertGambar("E:\\kotak4.bmp", 1);
//        insertGambar("E:\\segitiga4.bmp", 2);
    }
    public void insertGambar(String file, int kelas) throws IOException, SQLException{
//        File input = new File("E:\\kotak.bmp");
        File input = new File(file);
        image = ImageIO.read(input);
        double[] ciriKotak = {getCiri1(), getCiri2(), getCiri3(), getCiri4(), getCiri5(), getCiri6(), getCiri7()};
        con = DriverManager.getConnection(url, "root", "");
        String query = " insert into gambar (alamatGambar, kelas, ciriPertama, ciriKedua, ciriKetiga, ciriKeempat, ciriKelima, ciriKeenam, ciriKetujuh)"
          + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStmt = con.prepareStatement(query);
        preparedStmt.setString (1, input.getPath());
        preparedStmt.setInt (2, kelas);
        preparedStmt.setDouble (3, ciriKotak[0]);
        preparedStmt.setDouble (4, ciriKotak[1]);
        preparedStmt.setDouble (5, ciriKotak[2]);
        preparedStmt.setDouble (6, ciriKotak[3]);
        preparedStmt.setDouble (7, ciriKotak[4]);
        preparedStmt.setDouble (8, ciriKotak[5]);
        preparedStmt.setDouble (9, ciriKotak[6]);
        preparedStmt.execute();
        con.close();
    }
    public double[] selectCiri(String i) throws SQLException{
          con = DriverManager.getConnection(url, "root", "");
          String query = "SELECT * FROM gambar where idGambar = '" + i + "'";
          stm = con.createStatement();
          rs = stm.executeQuery(query);
          double ciriPertama = 0, ciriKedua = 0, ciriKetiga = 0, ciriKeempat = 0, ciriKelima = 0, ciriKeenam = 0, ciriKetujuh = 0;
          while (rs.next())
          {
            ciriPertama = rs.getDouble("ciriPertama");
            ciriKedua = rs.getDouble("ciriKedua");
            ciriKetiga = rs.getDouble("ciriKetiga");
            ciriKeempat = rs.getDouble("ciriKeempat");
            ciriKelima = rs.getDouble("ciriKelima");
            ciriKeenam = rs.getDouble("ciriKeenam");
            ciriKetujuh = rs.getDouble("ciriKetujuh");
          }
          stm.close();
          double[] arrayResult = {ciriPertama, ciriKedua, ciriKetiga, ciriKeempat, ciriKelima, ciriKeenam, ciriKetujuh};
          return arrayResult;
    }
    public String selectKelas(String idGambar) throws SQLException{
        con = DriverManager.getConnection(url, "root", "");
          String query = "SELECT kelas FROM gambar where idGambar = '" + idGambar + "'";
          stm = con.createStatement();
          rs = stm.executeQuery(query);
            int kelas = 0;
          while (rs.next())
          {
            kelas = rs.getInt("kelas");
          }
          stm.close();
          if (kelas == 1){
              return "Kotak";
          } else if (kelas == 2){
              return "Segitiga";
          } else if (kelas == 3){
              return "Lingkaran";
          } else {
              return "-";
          }
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
    public static void main(String[] args) throws IOException, SQLException {
        MomenInvarian momenInvarian = new MomenInvarian();
    }
}
