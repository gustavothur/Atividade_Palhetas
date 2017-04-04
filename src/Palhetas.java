import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.lang.Math.pow;

/**
 * Created by gustavo.thur on 27/03/2017.
 */
public class Palhetas {

    public int palheta(int pos){
        int[] palheta64 = {
                0x000000, 0x00AA00, 0x0000AA, 0x00AAAA, 0xAA0000, 0xAA00AA, 0xAAAA00, 0xAAAAAA,
                0x000055, 0x0000FF, 0x00AA55, 0x00AAFF, 0xAA0055, 0xAA00FF, 0xAAAA55, 0xAAAAFF,
                0x005500, 0x0055AA, 0x00FF00, 0x00FFAA, 0xAA5500, 0xAA55AA, 0xAAFF00, 0xAAFFAA,
                0x005555, 0x0055FF, 0x00FF55, 0x00FFFF, 0xAA5555, 0xAA55FF, 0xAAFF55, 0xAAFFFF,
                0x550000, 0x5500AA, 0x55AA00, 0x55AAAA, 0xFF0000, 0xFF00AA, 0xFFAA00, 0xFFAAAA,
                0x550055, 0x5500FF, 0x55AA55, 0x55AAFF, 0xFF0055, 0xFF00FF, 0xFFAA55, 0xFFAAFF,
                0x555500, 0x5555AA, 0x55FF00, 0x55FFAA, 0xFF5500, 0xFF55AA, 0xFFFF00, 0xFFFFAA,
                0x555555, 0x5555FF, 0x55FF55, 0x55FFFF, 0xFF5555, 0xFF55FF, 0xFFFF55, 0xFFFFFF
        };
        return palheta64[pos];
    }

    public int saturate (int value)
    {
        if (value > 255)
        {
            return 255;
        }
        else if (value < 0)
        {
            return 0;
        }
        else
            return value;
    }

    public double distance (Color pixel0, Color pixel1){
        double dist = Math.sqrt(
                pow((pixel0.getRed()-pixel1.getRed()),2)+
                        pow((pixel0.getGreen()-pixel1.getGreen()),2)+
                        pow((pixel0.getBlue()-pixel1.getBlue()),2));
        return dist;
    }



    public BufferedImage compare (BufferedImage img){
        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < out.getHeight(); y++)
        {
            for (int x =0; x < out.getWidth(); x++)
            {
                Color pixel = new Color(img.getRGB(x,y));
                double dist = 255;
                int a = 0;
                for(int i = 0; i < 64; i++) {
                    Color palheta64 = new Color (palheta(i));
                    if (distance(pixel, palheta64) < dist )
                    {
                        a = i;
                        dist = distance(pixel, palheta64);
                    }
                }
                Color newPixel = new Color (palheta(a));
                out.setRGB(x,y, newPixel.getRGB());
            }
        }
        return out;
    }

    public void run () throws IOException {
        File PATH = new File ("C:\\Users\\gustavo.thur\\IdeaProjects\\Dunno\\src");
        BufferedImage arcoiris = ImageIO.read(new File(PATH, "arco_iris.jpg"));
        BufferedImage EGAArcoIris = compare(arcoiris);
        ImageIO.write(EGAArcoIris, "png", new File(PATH, "ArcoIris.png"));
    }

    public static void main (String[] args) throws IOException {
        Palhetas atividade = new Palhetas();
        atividade.run();
    }
}
