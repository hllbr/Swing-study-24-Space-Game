
import java.awt.HeadlessException;
import javax.swing.JFrame;


public class MainOyunEkran extends JFrame{//super class
    //bu alan bir adet JFrameden extends olacak
        public MainOyunEkran(String title) throws HeadlessException {
        super(title);
        //bu yapı benim constructor içerisine yapmış olduğum tanımlamayı sistemin anlaması için 
    }
    public static void main(String[] args) {
        //bu alanda bir adet JFRAME oluşturuyorum
        MainOyunEkran ekran = new MainOyunEkran("UZAY OYUNU PRJOESİ");//bu yapıyı bu şekilde tanımlamam için bir constructur yazmam gerekiyor
        
        //bu ekranın resiable olmasını istemiyorum  bunun için 
        ekran.setResizable(false);//yeniden boyutlandırma anlamına geliyor.
        ekran.setFocusable(false);//odaklanabilir anlamına geliyor bunuda onaylamadım.
        //bütün işlemlerimin panel üzerinde olmasını istiyorum jframe üzerinde değil .özet oalrak yapıya jframe odaklanma dedim.
        ekran.setSize(800, 600);//ekran boyutumu belirledim
        ekran.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// X işaretine basıldığında gerçekleştirilmesini istediğim operasyon tipini belirledim
        
        //JPanel'den bir obje üreterek işlemlerime devam ediyorum.
        
        Oyun oyun = new Oyun();
        //jpanelin yapmak istediğim klavye işlemlerimi anlaması için tanımlamalar ve görev atamaları yapmam gerekiyor
        oyun.requestFocus();//klavyeden işlemleri anlamsı için direkt olarak bana odağı ver şeklinde bir ifade söylemiş olduk
        //daha sonra jpanel üzerinde keyslistener şeklinde bir interface implemente ederek işlemlerime devam ediyorum
        //bu interface benim klavye işlemlerimi anlamayı sağlıcak
        oyun.addKeyListener(oyun);//bu yapı listener cinsinden bir referans bekler 
        //bu yapı sayesinde oyun sadece jpanel üzerinde çalışacak
        oyun.setFocusable(true);////burada odağımı jpanele vermiş oldum
        oyun.setFocusTraversalKeysEnabled(false);
        //klavye işlemlerini jpanelin anlaması için gerkeli bir işlem burada false diyerek klavye işlemlerimin direkt olarak gerçekleşmesini sağlamış oluyorum
        //şimdi jpaneli jframe'e eklemem gerekiyor artık
        ekran.add(oyun);
        ekran.setVisible(true);//jframe'e jpanel ekledniğinde oluşsun şeklinde bir tanımlama yaptım.
        //burada gerçekleştirdiğim fonksiyonların sıraları çok önemli ...Yer dfeğişiklikleri yaparsak yani algoritma üzerinde değişiklik yaparsam klavye üzerinde input değerleri alamayabilirim
        
        
    }
}
