
package UzayOyunu;

import java.awt.HeadlessException;
import javax.swing.JFrame;


public class OyunEkrani extends JFrame{

    public OyunEkrani(String string) throws HeadlessException {
        super(string);
    }
    
    public static void main(String[] args) {
        OyunEkrani ekran = new OyunEkrani("Uzay Oyunu");
        
        ekran.setResizable(false);
        ekran.setFocusable(false); //odagımızı JFrameden cekme için yaptık
        ekran.setSize(800,600);
        ekran.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Oyun oyun = new Oyun();
        
        //klavye işlemlerimizi analamamız için asagıdakileri yazıyoruz
        oyun.requestFocus(); 
        oyun.addKeyListener(oyun); //klavyeden işlemleri anlamamıza yarıyor
        oyun.setFocusable(true); //odagımız JPanelde olması için true yaptık
        oyun.setFocusTraversalKeysEnabled(false); // klavye işlemleri gerceklestirmek için false dememiz gerekiyor
        
        ekran.add(oyun);
        ekran.setVisible(true);
    }
    
}
