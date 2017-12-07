/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImageEnhancement;

/**
 *
 * @author USER
 */
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
public class LoadImage {
    public static void main(String[] args) throws IOException {
        BufferedImage img = null;
        img = ImageIO.read(new File("img\\1.jpg"));
        System.out.println(img.getHeight());
        System.out.println(img.getWidth());
    }
}
