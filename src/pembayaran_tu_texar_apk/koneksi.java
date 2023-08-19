package pembayaran_tu_texar_apk;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class koneksi {
    private String ip_address = "";
    private String db_name = "";
    private String url="";
    private String username_xampp="";
    private String password_xampp="";
    private Connection con;
    ArrayList<String> listA = new ArrayList<String>();

    public koneksi() {
        try {
            File myObj = new File("C:/Program Files/Aplikasi Keuangan TU Texar/koneksi.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
                listA.add(data);
            }
                myReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
        }
        this.ip_address = listA.get(0);
        this.db_name = listA.get(1);
        url="jdbc:mysql://"+ip_address+"/"+db_name;
        this.username_xampp = listA.get(2);
        this.password_xampp = listA.get(3);
    }
    
    public String connect(){
        String msg = "";
        try {
            con = DriverManager.getConnection(url, username_xampp, password_xampp);
            msg = "<html><head><style>div{text-align: center;font-family:arial;color:red;}</style></head><body><div>KONEKSI BERHASIL</div></body></html>";
        } catch (Exception e) {
            msg = "ERROR KONEKSI KE DATABASE:\n" + e;
        }
        return msg;
    }
    
    public void connect2(){
        try {
            con = DriverManager.getConnection(url, username_xampp, password_xampp);
        } catch (Exception e) {
            System.out.println("Error Koneksi "+e.getMessage());
        }
    }

    public Connection getCon() {
        return con;
    }  
}
