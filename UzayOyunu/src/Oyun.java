
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

class Ates{
    //ateşin x ve y koordinatları bulunacak
   // her actionmerformed çalıştığında bir ileri gitmeye çalışacak
    private int x ;
    private int y ;
    //bu yapıyı kullanmam için constructor ve getter ve setterlara ihtiyacım var

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
    //Ates classım ile işlemlerim bitti
}
public class Oyun extends JPanel implements KeyListener,ActionListener{//sub class
    Timer timer = new Timer(5, this);//birinci dedğer timerin kaç milisaniyede bir çalışıcağını ikinci değer ise timer içerisine bir actionlisteneri implemente eden bir bir objeyi göndermemiz gerekiyor.
    // bu yapıyı daha sonra jframe eklicem.
    //klavye işlemlerimi anlamam için keylistener şeklinde bir interface'i implemente etmem gerekiyor.
    //bu yapı biz belirli bir tuşa bastığımzda gerekli metodları kullanmamı sağlayan bir interface aynı zamanda topları ve uzay mekimizi hareket ettirmem için bir adet actionlistener şeklinde bir interface'i implemente etmeme gerekiyor.
    //ActionListener implemente etme sebebim bu interface içersinde actionperformed şeklinde bir metodu kullanarak ve timer ile her çalıştığında actionperformed hasrekete geçmiş olacak 
    //bu yapı sayesinde top hareket ettirmiş olucam 
    //*************************************************************************
    //oyunumda kullanıcağım özellikleri ve değişkenleri ve özellikleri bu alana tanımlıyorum
    //oyunda geçen süreyi anlamak için ...
    private int gecensure = 0;
    private int harcananates;
    private BufferedImage ımage;//bu image bir png dosyası
    //bu buffered yapısı sayesinde uzay gemisi resmimi jpanele eklemiş olacağım.
    //atılan ateşleri tutmak için bir arrayliste ihtiyacım var.Tek bir ateş olmasın birden fazla ateş olabilsin diye bu ateşleri bir arraylist içerisinde tutucam
    private ArrayList<Ates> atesler = new ArrayList<Ates>();
    //Ateşler yukarı doğru gitmeli sağa veya sola doğru gitmemeli.her timer çalıştığında bir ileri gitmesi gerekiyorki ateşler hareket edebilsin.
    //bunun için gerekli olan işlem ...
    private int atesdırY= 1;//ateşler oluşacak her actionperformed olduğunda ateşdıry ateşlerin y kordinatına eklenecek .Bu sayede  ateşler hareket etmiş olacak
    private int topX = 0;
    //bu yapımız ise sadece sağa veya sola gidecek .Topun ilk koordinatı ise 0 0 olacak 
    //ateşte yaptığım gibi bir arttırararak topu ilerletmiş olacağım.topdırX sayesinde olacak bu istediklerim
    
    private int topdırX = 2;
    private int uzayGemisix =0;//uzay gemisinin başlangıç noktasını x koordinatı açısından 0 olarak belirledim
    private int dırUzayX = 20;//20 birimlik ilerlemeler kaydeilmesini iztediğim için her basma işleminde yaptım bu tanımlamayı
    //bu özelliklere erişm için bir construvtura ihtiyacım var
    public boolean kontrolEt(){
        for(Ates ates :atesler){
            if(new Rectangle(ates.getX(),ates.getY(),10,20).intersects(new Rectangle(topX,0,20,20))){
                return true;
            }
        }
        return false;
    }
    public Oyun() { 
        //constructor
        //buradaki yapı çağrıldığında(başladığında) timeri direkt olarak başlatmak istiyorum.bunun için timer.start() metodundan yararlanıyorum.
        try {
            //oluşturmuş olduğum bufferedımage içerisini uzay gemisi png ile doldurmam gerekiyor.
            //temelde png dosyasını okumam ve ımgae içerisine atamam gerekiyor.
            ımage = ImageIO.read(new FileImageInputStream(new File("uzaygemisi.png")));//bu şekilde resmi okumuş oldum.Dosya okumaktan farkı yok.
        } catch (IOException ex) {
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        }
        //arkaplanı siyah renkte yapmak istiyorum.
        setBackground(Color.BLACK);
        //CONSTRUCTOR İŞLEMLERİM TAMAMLANMIŞ OLDU.
        timer.start();//burası çalıştığında actionperformed metodu kendiliğinden çalışacak.şimdi tüm yapmak istediğim işlemleri action performed içerisinde yapabilecek duruma getirdim yapımı.
        
        
    }
//ŞİMDİ ŞEKİLLERİ JPANEL ÜZERİNDE ÇİZMEM GEREKİYOR.
    @Override
    public void paint(Graphics g) {
        
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        gecensure += 5;
        g.setColor(Color.red);//benim topumu çizmem gerek rengini belirlemem gerek
        g.fillOval(topX, 0, 20, 20);
        
        g.drawImage(ımage, uzayGemisix,490, ımage.getWidth()/10, ımage.getHeight()/10,this);
        for(Ates ates : atesler){
            if(ates.getY() < 0){
                atesler.remove(ates);
            }
        }
        g.setColor(Color.BLUE);
        for(Ates ates : atesler){
            g.fillRect(ates.getX(), ates.getY(), 10, 20);
        }
        if(kontrolEt()){
            timer.stop();
            String message = "Kazandınız...\n"+"harcanan Ates : "+harcananates+"\nGeçen Süre : "+gecensure/1000.0+" saniye";
            JOptionPane.showMessageDialog(this, message);
            System.exit(0);
        }
    /*
        topun başlangıç noktası 0 0 olacak y ifademi 0 olarak atıyorum değiştirmeyeceğim için top her hareket ettiğinde topx güncellenmiş olacak 
        biz tekrar painti çağırdığımızda top hareket etmiş olacak 
        Topun Büyüklüğü 20 ye 20 olarak tanımlıyorum.Bu şekilde daire şeklinde bir top elde etmiş oldum.
        şimdi ise okumuş olduğum imagei graphics ile çizmek istiyorum.
        imagei verdim nerden başlıcağınıda ifade etmem gerekiyor.
        Y üzerinde değişiklik yapmak istemiyorum.bu yüzden sabit 490 ile zemine sabitledim gemiyi.
        burada sadece x koordinatı üzerinde değişiklikler yapıcam.
        Şimdi ise uzayGemisinin genişliğini ve boyunu vermem(belirtmem gerekiyor.)
        uzaygemisinin şuanki genişliğini alıcam ve bunu 10'a bölmem gerek istediğim boyutlara getirebilmek için.
        aynı durum boy içinde geçerli
        en son olarak ise bu yapının jpanel üzerinde gösterilmesini istediğimi this anahtar kelimesi ile ifade ediyorum.
        Tüm tasarımı yapmış olduk şimdi bizim hareketlilik ve tuş takımını tanımlamamız gerekiyor.
        
        */

    
    }

    @Override
    public void repaint() {//bu yapı herçağrıldığında paintide çağırıyor.bunu oyunlarda yazmak gerekiyor.bu yapı component içerisinde boş olarak bulunuyor.
        super.repaint(); //To change body of generated methods, choose Tools | Templates.
        //biz bu yapıyı actionperformed içerisinde en sonda çağırıcaz ki paint işlemlerinin yapılmasını isteyebilelim.
        //repaint çağıldığında paint tekrar şekillerimizi oluşturmuş oluyor.
        
    }
    
    
    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //uzay mekiniği hareket ettirmek için gerekli işlemlerin yapıldığı alanımız...
        int c = e.getKeyCode();//burası solo bastığımda sol değeri sağa bastığımda sağ değeri dönecek ifadem
        //bir kontrol ile yön atamsı işlemlerimi gerçekleştirmem lazım 
        if(c == KeyEvent.VK_LEFT){
            //sola tuşuna basılmışsa yapılacaklar
            //sola basıldığında 20 birim sola gitmek için bir yapı tanımlamıştım bu yapının adı dırUZAYX
            //sınırların dışına çıkmadığımızı kontrol etmemiz gerek bu durum için
            if(uzayGemisix <= 0){
                //frameden çıkılmadığını kontrol etmem gerek
                uzayGemisix = 0;
            }else{
                uzayGemisix -= dırUzayX;//sola gitmek için çıkartma işlemi gerektiriyorum
            }
            
        }else if(c == KeyEvent.VK_RIGHT){
            //sağa basıldığında yapılmasını istediğim işlemler
            if(uzayGemisix>=720){
                uzayGemisix =720;//buradan daha öteye gidilmesini engellemiş oldum.
            }else{
                uzayGemisix += dırUzayX;//sağ tarafa geçiş için topluyorum
            }
            
        }else if(c == KeyEvent.VK_CONTROL){
            atesler.add(new Ates(uzayGemisix+15, 470));
            harcananates++;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
       throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(Ates ates : atesler){
            ates.setY(ates.getY()-atesdırY);
            
        }
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
       //burada bir timer tanımlıyorum ve bu timer her çalıştığında repaint çağrılıcak vs...
       //bu noktada topumuz 0 0dan başladı.
       //Timer herçalıştığında topdırX i topx e eklemeye çalışıcam.Bunun için ilk olarak her yapım için gerçerli olan noktada bir timer objesi oluşturmam gerekiyor.
       //heractionperformed çalıştığında topmu hareket ettirmem için ...
       topX +=topdırX;//mevcut pozisyonu bu yapı ile güncellemiş oluyorum
       //eğer topum sınırdan çıkmışsa bir işlemle bunu engellemem gerekiyor.sınıra topum dayandığında geriye gelmesi gerekiyor.
       if(topX >=750){
           //750. koordinata geldiği zaman topdirx'İ - ile çarpmam gerekiyor.
           topdırX = -topdırX;//şimdi topum gerekli koordinata vardığında geriye doğru gitmiş olacak
       }
       if(topX <=0){//geriye gittiğindede 0 dışına çıkmaması gerekiyor bununda kontrol edilmesi gerek
           //BURADA İSE - OLAN DEĞERİMİ + YA ÇEVİRMEM GEREKİYOR
           topdırX = -topdırX;
       }
       //son olarak repainti çağırarak topu güncel pozisyonda tutmam gerekiyor.
       repaint();
       
    
    
    
    
    }

    
    
    
}
