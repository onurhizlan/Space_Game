
package UzayOyunu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

class Ates{
        private int x;
        private int y;
    
    public Ates(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

   
}

public class Oyun extends JPanel implements KeyListener, ActionListener{
//klavyeden işlemlerimiz anlaması için keylisteneri implements etmemiz gerekiyor
//oyunumuza klavye haraketleri eklemek için asagıdaki 4 methodu da ekliyoruz
    
    Timer timer = new Timer(5, this);
    
    private int gecen_sure = 0;
    private int harcanan_ates = 0;
    private BufferedImage image;
    
    private ArrayList<Ates> atesler = new ArrayList<Ates>();
    
    private int atesdirY = 1;
    private int topX = 0;
    private int topdirX = 2;
    private int uzayGemisiX = 0;
    private int diruzayX = 20;
    
    public boolean kontrolEt(){
        for (Ates ates: atesler) {
            if (new Rectangle(ates.getX(), ates.getY(), 10,20).intersects(new Rectangle(topX,0,20,20))) { //iki rectangle(dikdortgen) da carpısırsa if in içerisine giricez
                return true; 
            }
        }
        return false;
    }
    

    //constructor
    public Oyun() {
        try {
            image = ImageIO.read(new FileImageInputStream(new File("resim.png")));
        } catch (IOException ex) {
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        }
        setBackground(Color.BLACK);
        
        timer.start();
    }

   
    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs); //To change body of generated methods, choose Tools | Templates.
        gecen_sure += 5; //oyun sonunda gecen sureyi bulmak için
        grphcs.setColor(Color.red);
        grphcs.fillOval(topX, 0, 20, 20); //topu ciziyoruz
        grphcs.drawImage(image, uzayGemisiX, 490, image.getWidth() / 10, image.getHeight() / 10, this); // uzay gemisini çizmek
         
        //atesleri ciziyouz
        for (Ates ates: atesler) {
            if (ates.getY() < 0) { // ates JFrameden cıkınca silsin, (oyunu yavalatmasın yer kaplayarak )
                atesler.remove(ates);
            }
        }
        grphcs.setColor(Color.blue);
            for (Ates ates: atesler) {
                grphcs.fillRect(ates.getX(), ates.getY(), 10, 20); //kare ates olusturuyoruz
            }
            
            if (kontrolEt()) { //carpısma olursa atesle top arasında kontrolet metdohudu true ya giriyor ve sonuc gerceklesiyor
             timer.stop();
             String message = "Kazandınız\n" +
                     "Harcanan Ates : " + harcanan_ates +
                     "\nGecen Sure : " + gecen_sure / 1000.0 + " saniye";
                JOptionPane.showMessageDialog(this, message);
                System.exit(0); 
        }
    }
    
    
 //oyunlarda repainti yazmamız gerekiyor. oyunun en sonunda repainti cagırıcaz
    @Override
    public void repaint() {
        super.repaint(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        
    }

    @Override
    public void keyPressed(KeyEvent ke) {
       //uzay gemisini hareket ettirmek - sola bastıkmı sola saga basktıkmı saga gitmesini saglamak
        int c = ke.getKeyCode(); 
        
        if (c == KeyEvent.VK_LEFT) {
            if (uzayGemisiX <= 0) { //uzay gemisi en soldaysa yani 0 dan daha fazla gidemez 0 a esitledik degilse sola dogru azalttık
                uzayGemisiX = 0;
            }else{
                uzayGemisiX -= diruzayX;
            }
        }else if(c == KeyEvent.VK_RIGHT){
            if (uzayGemisiX >= 750) { //uzay gemisi en sagdaysa yani 720 den daha fazla gidemez 720 a esitledik degilse saga dogru arttırdık
                uzayGemisiX = 750;
            }else{
                uzayGemisiX += diruzayX;
            }
        }else if(c == KeyEvent.VK_CONTROL){ //ctrl tusuna basıldıysa 
            atesler.add(new Ates(uzayGemisiX+15, 485));
            harcanan_ates++;
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
       
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        //toplu ileri geri hareket ettirme
        //750.ci koordinata geldimi geri doncek
        //(timer ile toplu hareket ettirdik)
        
        for(Ates ates: atesler){ //ateslerin Y koordinatlarını degistirmek için foreach yazıyoruz
            ates.setY(ates.getY() - atesdirY);
        }
        
        topX += topdirX;
        if (topX >= 780) {
            topdirX = -topdirX;  
        }
        if (topX <= 0) {
             topdirX = -topdirX; 
        }
        repaint(); //repainti cagırıp oda painti calıstırıo
    }
    
}
