/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pembayaran_tu_texar_apk;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author KAHFI
 */
public class menu_utama extends javax.swing.JFrame {
    public String id_user="TU000";
    private DefaultTableModel model=null;
    private DefaultTableModel modelCariSiswa=null;
    private DefaultTableModel modelUser=null;
    private DefaultTableModel modelSiswa=null;
    private DefaultTableModel modelJurusan=null;
    private DefaultTableModel modelthnmasuk=null;
    private DefaultTableModel modelSblmNaik=null;
    private DefaultTableModel modelStlhNaik=null;
    private DefaultTableModel modelTagihan=null;
    private DefaultTableModel modelPengeluaran=null;
    private DefaultTableModel modelPemasukan=null;
    private DefaultTableCellRenderer cellRen=null;
    private String temp = "";
    private String path = "";
    private int no_trans = 0;
    private int pilihan_menu=0;
    private PreparedStatement stat;
    private ResultSet rs;
    koneksi k = new koneksi();
    tagihan_siswa tag_siswa = new tagihan_siswa();
    transaksi_pemasukan trans_masuk = new transaksi_pemasukan();
    
    public menu_utama() {
        initComponents();
        k.connect();
        refreshTable();
        refreshComboJurusan();
        refreshComboThnMasuk();
        refreshTableUser();
        refreshTableJurusan();
        refreshTableThnmasuk();
        refreshTableTagihan();
        refreshTabelPengeluaran();
    }
    
    public void setId_user(String id_user) {
        this.id_user = id_user;
    }
    
    public void refreshTable(){
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
        Label_waktulogin.setText(dateFormat.format(date));
        model=new DefaultTableModel();
        cellRen=new DefaultTableCellRenderer();
        cellRen.setHorizontalAlignment(jLabel1.CENTER);
        model.addColumn("No");
        model.addColumn("NAMA TAGIHAN");
        model.addColumn("STATUS PEMBAYARAN");
        model.addColumn("PEMBAYARAN MASUK");
        tbl_daftar_tagihan.setModel(model);
        tbl_daftar_tagihan.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
        tbl_daftar_tagihan.getColumnModel().getColumn(0).setCellRenderer(cellRen);
        tbl_daftar_tagihan.getColumnModel().getColumn(1).setCellRenderer(cellRen);
        tbl_daftar_tagihan.getColumnModel().getColumn(2).setCellRenderer(cellRen);
        tbl_daftar_tagihan.getColumnModel().getColumn(3).setCellRenderer(cellRen);
        tbl_daftar_tagihan.getColumnModel().getColumn(0).setPreferredWidth(1);
        tbl_daftar_tagihan.getColumnModel().getColumn(1).setPreferredWidth(300);
        tbl_daftar_tagihan.getColumnModel().getColumn(2).setPreferredWidth(100);
        tbl_daftar_tagihan.getColumnModel().getColumn(3).setPreferredWidth(200);
        tbl_cari_siswa.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
        modelCariSiswa = new DefaultTableModel();
        modelCariSiswa.addColumn("NIS");
        modelCariSiswa.addColumn("NAMA");
        modelCariSiswa.addColumn("TINGKAT");
        modelCariSiswa.addColumn("KELAS");
        modelCariSiswa.addColumn("TAHUN MASUK");
        tbl_cari_siswa.setModel(modelCariSiswa);
        
      
    }
    
    public void refreshTableUser(){
        modelUser=new DefaultTableModel();
        modelUser.addColumn("ID");
        modelUser.addColumn("USERNAME");
        modelUser.addColumn("PASSWORD");
        modelUser.addColumn("NAMA PENGGUNA");
        tabel_user.setModel(modelUser);
        try {
            this.stat = k.getCon().prepareStatement("SELECT * FROM petugas");
            this.rs = this.stat.executeQuery();
            while (rs.next()) {                
                Object[] data = {
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4)
                };
                modelUser.addRow(data);
            }
            idpengguna();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error "+e);
        }
        jTextField17.setText("");
        jTextField21.setText("");
        jTextField22.setText("");
    }
    
    private void idpengguna(){
        try {
            String sql="select * from petugas order by id_petugas desc";
            this.stat = k.getCon().prepareStatement(sql);
            this.rs = this.stat.executeQuery();
            if (rs.next()) {
                String nofak = rs.getString("id_petugas").substring(2);
                String AN = "" + (Integer.parseInt(nofak) + 1);
                String Nol = "";
                if(AN.length()==1)
                {Nol = "00";}
                else if(AN.length()==2)
                {Nol = "0";}
                else if(AN.length()==3)
                {Nol = "";}
               jTextField25.setText("TU" + Nol + AN);
            } else {
               jTextField25.setText("TU000");
            }
        }catch(Exception e){
           JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void refreshComboJurusan(){
        jComboBox6.removeAllItems();
        jComboBox12.removeAllItems();
        try {
            this.stat = k.getCon().prepareStatement("select * from jurusan");
            this.rs = this.stat.executeQuery();
            while (rs.next()) {
                jComboBox6.addItem(rs.getString("nama_jurusan"));
                jComboBox12.addItem(rs.getString("nama_jurusan"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void refreshComboThnMasuk(){
        jComboBox7.removeAllItems();
        jComboBox10.removeAllItems();
        jComboBox14.removeAllItems();
        try {
            this.stat = k.getCon().prepareStatement("select * from tagihan");
            this.rs = this.stat.executeQuery();
            while (rs.next()) {
                jComboBox7.addItem(rs.getString("tahun_masuk"));
                jComboBox10.addItem(rs.getString("tahun_masuk"));
                jComboBox14.addItem(rs.getString("tahun_masuk"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void refreshTableJurusan(){
        modelJurusan=new DefaultTableModel();
        modelJurusan.addColumn("NAMA JURUSAN");
        tabel_jurusan.setModel(modelJurusan);
        try {
            this.stat = k.getCon().prepareStatement("SELECT * FROM jurusan");
            this.rs = this.stat.executeQuery();
            while (rs.next()) {                
                Object[] data = {
                    rs.getString(1)
                };
                modelJurusan.addRow(data);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error "+e);
        }
        jTextField9.setText("");
        jTextField11.setText("");
    }
    
    public void refreshTableThnmasuk(){
        modelthnmasuk=new DefaultTableModel();
        modelthnmasuk.addColumn("TAHUN MASUK");
        tabel_thnmasuk.setModel(modelthnmasuk);
        try {
            this.stat = k.getCon().prepareStatement("SELECT * FROM tagihan");
            this.rs = this.stat.executeQuery();
            while (rs.next()) {                
                Object[] data = {
                    rs.getString(1)
                };
                modelthnmasuk.addRow(data);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error "+e);
        }
    }
    
    public void refreshTableTagihan(){
        modelTagihan=new DefaultTableModel();
        modelTagihan.addColumn("TAHUN MASUK");
        modelTagihan.addColumn("SPP");
        modelTagihan.addColumn("PPDB");
        modelTagihan.addColumn("BANGUNAN");
        modelTagihan.addColumn("DAFTAR ULANG X XI");
        modelTagihan.addColumn("DAFTAR ULANG XI XII");
        modelTagihan.addColumn("AKHIR TAHUN");
        tabel_tagihan.setModel(modelTagihan);
        try {
            this.stat = k.getCon().prepareStatement("SELECT * FROM tagihan");
            this.rs = this.stat.executeQuery();
            while (rs.next()) {                
                Object[] data = {
                    rs.getString(1),
                    RupiahFormat(rs.getString(2)),
                    RupiahFormat(rs.getString(3)),
                    RupiahFormat(rs.getString(4)),
                    RupiahFormat(rs.getString(5)),
                    RupiahFormat(rs.getString(6)),
                    RupiahFormat(rs.getString(7))
                };
                modelTagihan.addRow(data);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error "+e);
        }
        jTextField6.setText("");
        jTextField27.setText("");
        jTextField28.setText("");
        jTextField29.setText("");
        jTextField30.setText("");
        jTextField31.setText("");
        jTextField32.setText("");
    }
    
    public void generateTagihan(String cari2){
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
        Label_waktulogin.setText(dateFormat.format(date));
        model=new DefaultTableModel();
        cellRen=new DefaultTableCellRenderer();
        cellRen.setHorizontalAlignment(jLabel1.CENTER);
        model.addColumn("No");
        model.addColumn("NAMA TAGIHAN");
        model.addColumn("STATUS PEMBAYARAN");
        model.addColumn("PEMBAYARAN MASUK");
        tbl_daftar_tagihan.setModel(model);
        tbl_daftar_tagihan.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
        tbl_daftar_tagihan.getColumnModel().getColumn(0).setCellRenderer(cellRen);
        tbl_daftar_tagihan.getColumnModel().getColumn(1).setCellRenderer(cellRen);
        tbl_daftar_tagihan.getColumnModel().getColumn(2).setCellRenderer(cellRen);
        tbl_daftar_tagihan.getColumnModel().getColumn(3).setCellRenderer(cellRen);
        tbl_daftar_tagihan.getColumnModel().getColumn(0).setPreferredWidth(1);
        tbl_daftar_tagihan.getColumnModel().getColumn(1).setPreferredWidth(300);
        tbl_daftar_tagihan.getColumnModel().getColumn(2).setPreferredWidth(100);
        tbl_daftar_tagihan.getColumnModel().getColumn(3).setPreferredWidth(200);
        tbl_cari_siswa.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
        modelCariSiswa = new DefaultTableModel();
        modelCariSiswa.addColumn("NIS");
        modelCariSiswa.addColumn("NAMA");
        modelCariSiswa.addColumn("TINGKAT");
        modelCariSiswa.addColumn("KELAS");
        modelCariSiswa.addColumn("TAHUN MASUK");
        tbl_cari_siswa.setModel(modelCariSiswa);
        
        // search function
        try {
            this.stat = k.getCon().prepareStatement("select * from siswa inner join tagihan on siswa.tahun_masuk=tagihan.tahun_masuk where nis like '%"+ cari2 +"%'");
            this.rs = stat.executeQuery();
//            rs.next();
//            Object[][] data= { {
//              "1",
//              "PPDB",
//              "Status",
//              rs.getInt("siswa.stat_ppdb")
//            },{"2",
//              "Bangunan",
//              "Status",
//              rs.getInt("siswa.stat_bangunan")} };
//            model.addRow(data);
            rs.next();
            if (rs.getInt("siswa.stat_ppdb") == rs.getInt("tagihan.ppdb")) {
                Object[] data = {
                  "1",
                  "PPDB",
                  "Lunas",
                  rs.getInt("siswa.stat_ppdb")
                };
                model.addRow(data);
            } else {
                Object[] data = {
                  "1",
                  "PPDB",
                  "Belum Lunas",
                  rs.getInt("siswa.stat_ppdb")
                };
                model.addRow(data);
            }
            if (rs.getInt("siswa.stat_bangunan") == rs.getInt("tagihan.bangunan")) {
                Object[] data2 = {
              "2",
              "Bangunan",
              "Lunas",
              rs.getInt("siswa.stat_bangunan")
            };
            model.addRow(data2);
            } else {
                Object[] data2 = {
              "2",
              "Bangunan",
              "Belum Lunas",
              rs.getInt("siswa.stat_bangunan")
            };
            model.addRow(data2);
            }
            if (rs.getInt("siswa.stat_kenaikanxxi") == rs.getInt("tagihan.daftarulangx_xi")) {
                Object[] data3 = {
              "3",
              "Kenaikan X > XI",
              "Lunas",
              rs.getInt("siswa.stat_kenaikanxxi")
            };
            model.addRow(data3);
            } else {
                Object[] data3 = {
              "3",
              "Kenaikan X > XI",
              "Belum Lunas",
              rs.getInt("siswa.stat_kenaikanxxi")
            };
            model.addRow(data3);
            }
            if (rs.getInt("siswa.stat_kenaikanxixii") == rs.getInt("tagihan.daftarulangxi_xii")) {
                Object[] data4 = {
              "4",
              "Kenaikan XI > XII",
              "Lunas",
              rs.getInt("siswa.stat_kenaikanxixii")
            };
            model.addRow(data4);
            } else {
                Object[] data4 = {
              "4",
              "Kenaikan XI > XII",
              "Belum Lunas",
              rs.getInt("siswa.stat_kenaikanxixii")
            };
            model.addRow(data4);
            }
            if (rs.getInt("stat_akhirtahun") == rs.getInt("akhir_tahun")) {
                Object[] data5 = {
              "5",
              "Akhir Tahun",
              "Lunas",
              rs.getInt("siswa.stat_akhirtahun")
            };
            model.addRow(data5);
            } else {
                Object[] data5 = {
              "5",
              "Akhir Tahun",
              "Belum Lunas",
              rs.getInt("siswa.stat_akhirtahun")
            };
            model.addRow(data5);
            }
//              int no = 0;
//              while (rs.next()) {                
//                no++;
//                Object[] data = {
//                  no,
//                 rs.getString("nama_tagihan"),
//                 "status",
//                 rs.getString("siswa.stat_ppdb")
//                };
//            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
    }
    
    public void search(String cari){
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
        Label_waktulogin.setText(dateFormat.format(date));
        model=new DefaultTableModel();
        cellRen=new DefaultTableCellRenderer();
        cellRen.setHorizontalAlignment(jLabel1.CENTER);
        model.addColumn("No");
        model.addColumn("NAMA TAGIHAN");
        model.addColumn("STATUS PEMBAYARAN");
        model.addColumn("PEMBAYARAN MASUK");
        tbl_daftar_tagihan.setModel(model);
        tbl_daftar_tagihan.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
        tbl_daftar_tagihan.getColumnModel().getColumn(0).setCellRenderer(cellRen);
        tbl_daftar_tagihan.getColumnModel().getColumn(1).setCellRenderer(cellRen);
        tbl_daftar_tagihan.getColumnModel().getColumn(2).setCellRenderer(cellRen);
        tbl_daftar_tagihan.getColumnModel().getColumn(3).setCellRenderer(cellRen);
        tbl_daftar_tagihan.getColumnModel().getColumn(0).setPreferredWidth(1);
        tbl_daftar_tagihan.getColumnModel().getColumn(1).setPreferredWidth(300);
        tbl_daftar_tagihan.getColumnModel().getColumn(2).setPreferredWidth(100);
        tbl_daftar_tagihan.getColumnModel().getColumn(3).setPreferredWidth(200);
        tbl_cari_siswa.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
        modelCariSiswa = new DefaultTableModel();
        modelCariSiswa.addColumn("NIS");
        modelCariSiswa.addColumn("NAMA");
        modelCariSiswa.addColumn("TINGKAT");
        modelCariSiswa.addColumn("KELAS");
        modelCariSiswa.addColumn("TAHUN MASUK");
        tbl_cari_siswa.setModel(modelCariSiswa);
        
        // search function
        if (jComboBox1.getSelectedItem() == "nis") {
            try {
            this.stat = k.getCon().prepareStatement("select * from siswa where nis like '%"+ cari +"%'");
            this.rs = stat.executeQuery();
            while (rs.next()) {                
                Object[] data = {
                    rs.getString("nis"),
                    rs.getString("nama_siswa"),
                    rs.getString("tingkat"),
                    rs.getString("kelas"),
                    rs.getInt("tahun_masuk"),
                };
                modelCariSiswa.addRow(data);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        } else if(jComboBox1.getSelectedItem() == "nama") {
            try {
            this.stat = k.getCon().prepareStatement("select * from siswa where nama_siswa like '%"+ cari +"%'");
            this.rs = stat.executeQuery();
            while (rs.next()) {                
                Object[] data = {
                    rs.getString("nis"),
                    rs.getString("nama_siswa"),
                    rs.getString("tingkat"),
                    rs.getString("kelas"),
                    rs.getInt("tahun_masuk"),
                };
                modelCariSiswa.addRow(data);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } 
        }else if (jComboBox1.getSelectedItem() == "kelas"){
                try {
            this.stat = k.getCon().prepareStatement("select * from siswa where kelas like '%"+ cari +"%'");
            this.rs = stat.executeQuery();
            while (rs.next()) {                
                Object[] data = {
                    rs.getString("nis"),
                    rs.getString("nama_siswa"),
                    rs.getString("tingkat"),
                    rs.getString("kelas"),
                    rs.getInt("tahun_masuk"),
                };
                modelCariSiswa.addRow(data);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
                
        }
        
        
        
        
    }
    
    public String RupiahFormat(String nom){
        String format = "";
        int harga = Integer.parseInt(nom);
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        format = kursIndonesia.format(harga);
        return format;
    }
    
    public void refreshTabelPengeluaran(){
//      TODO
        text_id_pengeluaran.setText(Label_petugas.getText()+"-"+getnoTrans());
        text_penanggung_jawab.setText(Label_petugas.getText());
        modelPengeluaran = new DefaultTableModel();
        modelPengeluaran.addColumn("NO");
        modelPengeluaran.addColumn("ID TRANSAKSI");
        modelPengeluaran.addColumn("TANGGAL");
        modelPengeluaran.addColumn("NAMA PENGELUARAN");
        modelPengeluaran.addColumn("NOMINAL");
        modelPengeluaran.addColumn("PENANGGUNG JAWAB");
        tabel_pengeluaran.setModel(modelPengeluaran);
        tabel_pengeluaran.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tabel_pengeluaran.getColumnModel().getColumn(0).setCellRenderer(cellRen);
        tabel_pengeluaran.getColumnModel().getColumn(1).setCellRenderer(cellRen);
        tabel_pengeluaran.getColumnModel().getColumn(2).setCellRenderer(cellRen);
        tabel_pengeluaran.getColumnModel().getColumn(3).setCellRenderer(cellRen);
        tabel_pengeluaran.getColumnModel().getColumn(4).setCellRenderer(cellRen);
        tabel_pengeluaran.getColumnModel().getColumn(5).setCellRenderer(cellRen);
        tabel_pengeluaran.getColumnModel().getColumn(0).setPreferredWidth(1);
        tabel_pengeluaran.getColumnModel().getColumn(1).setPreferredWidth(200);
        tabel_pengeluaran.getColumnModel().getColumn(2).setPreferredWidth(50);
        tabel_pengeluaran.getColumnModel().getColumn(3).setPreferredWidth(300);
        tabel_pengeluaran.getColumnModel().getColumn(4).setPreferredWidth(100);
        tabel_pengeluaran.getColumnModel().getColumn(5).setPreferredWidth(100);
        try {
            this.stat = k.getCon().prepareStatement("SELECT * FROM transaksi WHERE kode_pembayaran = 'PENGELUARAN'");
            this.rs = this.stat.executeQuery();
            int no = 1;
            while (rs.next()) {                
                Object[] data = {
                    no,
                    rs.getString("id_transaksi"),
                    rs.getString("tanggal"),
                    rs.getString("nama_pembayaran"),
                    rs.getString("jml_pengeluaran"),
                    rs.getString("nama_petugas")
                };
                modelPengeluaran.addRow(data);
                no ++;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error "+e);
        }
        nominal_pengeluaran.setText("");
        nama_pengeluaran.setText("");
        btn_hpus_pengeluaran.setEnabled(false);
    }
    
    public int getnoTrans(){
        int notrans = 0; 
        try {
            String sql="select * from transaksi order by no_transaksi desc";
            this.stat = k.getCon().prepareStatement(sql);
            this.rs = this.stat.executeQuery();
            if (rs.next()) {
                notrans = rs.getInt("no_transaksi")+1;  
            } 
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR "+e.getMessage());
        }
        return notrans;
    }
    
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    
    public String RptoString(String Rp){
        String str1 = "", str2="";
        str1 = Rp.replaceAll("Rp. ", "");
        str2 = str1.replaceAll("\\,00", "");
        str1 = str2.replaceAll("\\.", "");
        int nol = Integer.parseInt(str1);
        return ""+nol;
    }
    
    class pengeluaran extends menu_utama{
        String id_out, penanggung_jawab, tanggal, nominal="", keperluan="", kode_pembayaran = "PENGELUARAN";

        public pengeluaran() {
            id_out = text_id_pengeluaran.getText();
            penanggung_jawab = text_penanggung_jawab.getText();
            try {
                Date date = Date_pengeluaran.getDate();
                DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");  
                tanggal = dateFormat.format(date);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Tanggal Belum diisi");
            }
            nominal = nominal_pengeluaran.getText().replaceAll("\\,", "");
            keperluan = nama_pengeluaran.getText();
        }
    }
    
    public static String right(String value, int length) {
        // To get right characters from a string, change the begin index.
        return value.substring(value.length() - length);
    }
    
    public boolean cekFormatExcel(String[] temp){
        boolean cek = false;
        if (temp.length == 11) {
            if (temp[0].equals("nis")&&
                    temp[1].equals("nama")&&
                    temp[2].equals("ppdb")&&
                    temp[3].equals("bangunan")&&
                    temp[4].equals("SPPkelasX")&&
                    temp[5].equals("SPPkelasXI")&&
                    temp[6].equals("SPPkelasXII")&&
                    temp[7].equals("SPPkelasXIII")&&
                    temp[8].equals("DUkelasXXI")&&
                    temp[9].equals("DUkelasXIXII")&&
                    temp[10].equals("akhirTahun")) {
                cek = true;
            }
        }
        return cek;
    }
    
    class siswa extends menu_utama{
        String nis, nama, num = "0",tingkat,kelas,tahun_masuk,nama_jurusan;

        public siswa() {
            tingkat = jComboBox5.getSelectedItem().toString();
            num = jTextField7.getText();
            nama_jurusan = jComboBox6.getSelectedItem().toString();
            kelas = nama_jurusan +" "+ num;
            tahun_masuk = jComboBox7.getSelectedItem().toString();
        }
    }
    
    public void refreshTableSiswa(String coloumn,String nilai){
        modelSiswa=new DefaultTableModel();
        modelSiswa.addColumn("NIS");
        modelSiswa.addColumn("NAMA");
        modelSiswa.addColumn("TINGKAT");
        modelSiswa.addColumn("KELAS");
        modelSiswa.addColumn("TAHUN MASUK");
        modelSiswa.addColumn("PPDB");
        modelSiswa.addColumn("BANGUNAN");
        modelSiswa.addColumn("SPP");
        table_siswa.setModel(modelSiswa);
        try {
            this.stat = k.getCon().prepareStatement("SELECT siswa.nis, siswa.nama_siswa,siswa.tingkat, siswa.kelas, tagihan.tahun_masuk, tagihan.ppdb, tagihan.bangunan, tagihan.spp " +
            "FROM siswa " +
            "INNER JOIN tagihan " +
            "ON siswa.tahun_masuk = tagihan.tahun_masuk " +
            "WHERE "+coloumn+" LIKE '"+nilai+"%'");
            this.rs = this.stat.executeQuery();
            while (rs.next()) {                
                Object[] data = {
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getString(8)
                };
                modelSiswa.addRow(data);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error "+e);
        }
    }
    
    public void refreshTableSiswa(String tahun_masuk,String tingkat,String kelas){
        modelSiswa=new DefaultTableModel();
        modelSiswa.addColumn("NIS");
        modelSiswa.addColumn("NAMA");
        modelSiswa.addColumn("TINGKAT");
        modelSiswa.addColumn("KELAS");
        modelSiswa.addColumn("TAHUN MASUK");
        modelSiswa.addColumn("PPDB");
        modelSiswa.addColumn("BANGUNAN");
        modelSiswa.addColumn("SPP");
        table_siswa.setModel(modelSiswa);
        try {
            this.stat = k.getCon().prepareStatement("SELECT siswa.nis, siswa.nama_siswa,siswa.tingkat, siswa.kelas, tagihan.tahun_masuk, tagihan.ppdb, tagihan.bangunan, tagihan.spp\n" +
            "FROM siswa\n" +
            "INNER JOIN tagihan\n" +
            "ON siswa.tahun_masuk = tagihan.tahun_masuk\n" +
            "WHERE siswa.tingkat = '"+tingkat+"' AND siswa.kelas = '"+kelas+"' AND tagihan.tahun_masuk = '"+tahun_masuk+"'\n" +
            "ORDER BY siswa.nis;");
            this.rs = this.stat.executeQuery();
            while (rs.next()) {                
                Object[] data = {
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getString(8)
                };
                modelSiswa.addRow(data);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error "+e);
        }
    }
     
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        tagihan_pembayaran = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_cari_siswa = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        btn_utama_reset = new javax.swing.JButton();
        jLabel58 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_daftar_tagihan = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        utama_nis = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        utama_nama = new javax.swing.JTextField();
        utama_tingkat = new javax.swing.JTextField();
        btn_utama_bayar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        utama_thnmasuk = new javax.swing.JTextField();
        utama_kelas = new javax.swing.JTextField();
        cetak_laporan = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        btn_cetakharian = new javax.swing.JButton();
        jLabel36 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        combo_kategoriharian = new javax.swing.JComboBox<>();
        jPanel15 = new javax.swing.JPanel();
        btn_cetakmingguan = new javax.swing.JButton();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        combo_kategorimingguan = new javax.swing.JComboBox<>();
        jPanel16 = new javax.swing.JPanel();
        btn_cetakbulanan = new javax.swing.JButton();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        combo_cetakbulanan = new javax.swing.JComboBox<>();
        jScrollPane12 = new javax.swing.JScrollPane();
        tabel_pemasukan = new javax.swing.JTable();
        btn_hapuspemasukan = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        pengeluaran = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        nominal_pengeluaran = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        nama_pengeluaran = new javax.swing.JTextPane();
        btn_pengeluaran = new javax.swing.JButton();
        text_penanggung_jawab = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        text_id_pengeluaran = new javax.swing.JTextField();
        jScrollPane8 = new javax.swing.JScrollPane();
        tabel_pengeluaran = new javax.swing.JTable();
        btn_hpus_pengeluaran = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        data_siswa = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jButton7 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jComboBox6 = new javax.swing.JComboBox<>();
        jTextField7 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jComboBox7 = new javax.swing.JComboBox<>();
        jLabel57 = new javax.swing.JLabel();
        combo_tokenizer = new javax.swing.JComboBox<>();
        jRadioButton2 = new javax.swing.JRadioButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        table_siswa = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jComboBox10 = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        jButton13 = new javax.swing.JButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jComboBox11 = new javax.swing.JComboBox<>();
        jComboBox12 = new javax.swing.JComboBox<>();
        jTextField23 = new javax.swing.JTextField();
        btn_ubahdatasiswa = new javax.swing.JButton();
        btn_hpusdatasiswa = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        combo_datasiswa = new javax.swing.JComboBox<>();
        text_datasiswa = new javax.swing.JTextField();
        btn_caridatasiswa = new javax.swing.JButton();
        btn_resetcarisiswa = new javax.swing.JButton();
        setelan_bayar = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jButton12 = new javax.swing.JButton();
        jTextField27 = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jTextField28 = new javax.swing.JTextField();
        jTextField29 = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jTextField30 = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        jTextField31 = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        jTextField32 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabel_tagihan = new javax.swing.JTable();
        thnmasuk_jurusan = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jButton15 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabel_thnmasuk = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jButton18 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        tabel_jurusan = new javax.swing.JTable();
        jLabel19 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jButton21 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        naik_tingkat = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jButton16 = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tabel_Sebelumnaik = new javax.swing.JTable();
        jComboBox14 = new javax.swing.JComboBox<>();
        jLabel32 = new javax.swing.JLabel();
        jComboBox8 = new javax.swing.JComboBox<>();
        jPanel11 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jTextField18 = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jTextField20 = new javax.swing.JTextField();
        jScrollPane9 = new javax.swing.JScrollPane();
        tabel_Setelahnaik = new javax.swing.JTable();
        jPanel12 = new javax.swing.JPanel();
        jComboBox9 = new javax.swing.JComboBox<>();
        jButton17 = new javax.swing.JButton();
        setelan_pengguna = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jButton29 = new javax.swing.JButton();
        jLabel43 = new javax.swing.JLabel();
        jTextField17 = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        jTextField21 = new javax.swing.JTextField();
        jTextField22 = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jTextField25 = new javax.swing.JTextField();
        jScrollPane11 = new javax.swing.JScrollPane();
        tabel_user = new javax.swing.JTable();
        jButton30 = new javax.swing.JButton();
        jButton32 = new javax.swing.JButton();
        jPanel18 = new javax.swing.JPanel();
        Label_petugas = new javax.swing.JLabel();
        Label_waktulogin = new javax.swing.JLabel();
        jButton31 = new javax.swing.JButton();
        jPanel19 = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("APLIKASI KEUANGAN & TATA USAHA (TU)");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SMK TEXAR KARAWANG");

        jTabbedPane1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTabbedPane1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cari data siswa berdasarkan: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 14))); // NOI18N
        jPanel2.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N

        jComboBox1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "nis", "nama_siswa", "kelas" }));

        jTextField1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        tbl_cari_siswa.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        tbl_cari_siswa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NIS", "NAMA", "TINGKAT", "KELAS", "TAHUN MASUK"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_cari_siswa.setRowHeight(25);
        tbl_cari_siswa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_cari_siswaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_cari_siswa);

        jButton2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jButton2.setText("LIHAT TAGIHAN");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButton4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton4.setText("CARI -->");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        btn_utama_reset.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btn_utama_reset.setText("RESET -->");
        btn_utama_reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_utama_resetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_utama_reset, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_utama_reset, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel58.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(255, 0, 0));
        jLabel58.setText("<html><head><style>div{word-wrap:break-word;}</style></head><body><div><b>1. Pencarian dengan NIS: </b> Masukan digit awal NIS<br><br><b>2. Pencarian dengan NAMA: </b>Masukan nama depan siswa<br><br><b>3. Pencarian dengan KELAS: </b>Masukan nama jurusan<br><br>kemudian tekan <b>Enter</b> atau <b>Klik Cari--></b></div></body></html>");
        jLabel58.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel58))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel58, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "DAFTAR TAGIHAN", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        tbl_daftar_tagihan.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        tbl_daftar_tagihan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "No", "NAMA TAGIHAN", "STATUS PEMBAYARAN", "PEMBAYARAN MASUK"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_daftar_tagihan.setRowHeight(40);
        tbl_daftar_tagihan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_daftar_tagihanMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_daftar_tagihan);

        jLabel3.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jLabel3.setText("NIS");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jLabel4.setText("NAMA");

        utama_nis.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        utama_nis.setForeground(new java.awt.Color(255, 0, 0));
        utama_nis.setEnabled(false);
        utama_nis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                utama_nisActionPerformed(evt);
            }
        });
        utama_nis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                utama_nisKeyReleased(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jLabel5.setText("KELAS");

        utama_nama.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        utama_nama.setEnabled(false);

        utama_tingkat.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        utama_tingkat.setEnabled(false);

        btn_utama_bayar.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn_utama_bayar.setText("BAYAR");
        btn_utama_bayar.setEnabled(false);
        btn_utama_bayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_utama_bayarActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jLabel6.setText("TAHUN MASUK");

        utama_thnmasuk.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        utama_thnmasuk.setEnabled(false);

        utama_kelas.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        utama_kelas.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(utama_nama, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(utama_nis, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(utama_thnmasuk, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(utama_tingkat, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(utama_kelas)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_utama_bayar, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(utama_nis, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(utama_tingkat, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(utama_kelas, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(utama_nama, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(utama_thnmasuk)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_utama_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout tagihan_pembayaranLayout = new javax.swing.GroupLayout(tagihan_pembayaran);
        tagihan_pembayaran.setLayout(tagihan_pembayaranLayout);
        tagihan_pembayaranLayout.setHorizontalGroup(
            tagihan_pembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tagihan_pembayaranLayout.createSequentialGroup()
                .addGroup(tagihan_pembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 21, Short.MAX_VALUE))
        );
        tagihan_pembayaranLayout.setVerticalGroup(
            tagihan_pembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tagihan_pembayaranLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("TAGIHAN PEMBAYARAN", tagihan_pembayaran);

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CETAK LAPORAN HARIAN", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Arial", 1, 18))); // NOI18N

        btn_cetakharian.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btn_cetakharian.setText("CETAK");
        btn_cetakharian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cetakharianActionPerformed(evt);
            }
        });

        jLabel36.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel36.setText("TANGGAL");

        jLabel59.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel59.setText("KATEGORI");

        combo_kategoriharian.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combo_kategoriharian.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SEMUA", "SPP-X", "SPP-XI", "SPP-XII", "SPP-XIII", "A-001", "A-002", "A-003", "A-004", "A-005" }));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_cetakharian, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(combo_kategoriharian, 0, 290, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(combo_kategoriharian, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(jLabel59, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_cetakharian, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CETAK LAPORAN MINGGUAN", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Arial", 1, 18))); // NOI18N

        btn_cetakmingguan.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btn_cetakmingguan.setText("CETAK");
        btn_cetakmingguan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cetakmingguanActionPerformed(evt);
            }
        });

        jLabel39.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel39.setText("TANGGAL AWAL");

        jLabel40.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel40.setText("TANGGAL AKHIR");

        jLabel60.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel60.setText("KATEGORI");

        combo_kategorimingguan.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combo_kategorimingguan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SEMUA", "SPP-X", "SPP-XI", "SPP-XII", "SPP-XIII", "A-001", "A-002", "A-003", "A-004", "A-005" }));

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_cetakmingguan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel40, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel39, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel60, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(combo_kategorimingguan, 0, 283, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel60, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(combo_kategorimingguan, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_cetakmingguan, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CETAK LAPORAN BULANAN", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Arial", 1, 18))); // NOI18N

        btn_cetakbulanan.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btn_cetakbulanan.setText("CETAK");
        btn_cetakbulanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cetakbulananActionPerformed(evt);
            }
        });

        jLabel41.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel41.setText("BULAN");

        jLabel42.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel42.setText("TAHUN");

        jLabel61.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel61.setText("KATEGORI");

        combo_cetakbulanan.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combo_cetakbulanan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SEMUA", "SPP-X", "SPP-XI", "SPP-XII", "SPP-XIII", "A-001", "A-002", "A-003", "A-004", "A-005" }));

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_cetakbulanan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel61, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addComponent(jLabel41, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel42, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(combo_cetakbulanan, 0, 296, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(combo_cetakbulanan, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(jLabel61, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_cetakbulanan, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabel_pemasukan.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        tabel_pemasukan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "NOMOR TRANSAKSI", "KODE PEMBAYARAN", "TANGGAL", "NIS", "NAMA", "NAMA TRANSAKSI", "PEMASUKAN", "NAMA PETUGAS"
            }
        ));
        tabel_pemasukan.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tabel_pemasukan.setRowHeight(25);
        tabel_pemasukan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_pemasukanMouseClicked(evt);
            }
        });
        jScrollPane12.setViewportView(tabel_pemasukan);

        btn_hapuspemasukan.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btn_hapuspemasukan.setText("<html>\n<head>\n<style>\n h3{text-align: center;}\n</style>\n</head>\n<body>\n<h3>HAPUS<br>PEMASUKAN</h3>\n</body>\n</html>");
        btn_hapuspemasukan.setActionCommand("<html>\n<head>\n<style>\n h2{text-align: center;}\n</style>\n</head>\n<body>\n<h3>HAPUS<br>PEMASUKAN</h3>\n</body>\n</html>");
        btn_hapuspemasukan.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_hapuspemasukan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapuspemasukanActionPerformed(evt);
            }
        });

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout cetak_laporanLayout = new javax.swing.GroupLayout(cetak_laporan);
        cetak_laporan.setLayout(cetak_laporanLayout);
        cetak_laporanLayout.setHorizontalGroup(
            cetak_laporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cetak_laporanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(cetak_laporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(cetak_laporanLayout.createSequentialGroup()
                        .addComponent(jScrollPane12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(cetak_laporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel38, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_hapuspemasukan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(cetak_laporanLayout.createSequentialGroup()
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        cetak_laporanLayout.setVerticalGroup(
            cetak_laporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cetak_laporanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(cetak_laporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(cetak_laporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(cetak_laporanLayout.createSequentialGroup()
                        .addComponent(btn_hapuspemasukan, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 83, Short.MAX_VALUE))
                    .addGroup(cetak_laporanLayout.createSequentialGroup()
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        jTabbedPane1.addTab("LAPORAN & PEMASUKAN", cetak_laporan);

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "INPUT PENGELUARAN", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Arial", 1, 18))); // NOI18N

        jLabel31.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel31.setText("PENANGGUNG JAWAB");

        jLabel34.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel34.setText("TANGGAL");

        jLabel35.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel35.setText("NOMINAL");

        nominal_pengeluaran.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        nominal_pengeluaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nominal_pengeluaranKeyReleased(evt);
            }
        });

        jLabel37.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel37.setText("UNTUK KEPERLUAN");

        nama_pengeluaran.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jScrollPane10.setViewportView(nama_pengeluaran);

        btn_pengeluaran.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btn_pengeluaran.setText("INPUT");
        btn_pengeluaran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pengeluaranActionPerformed(evt);
            }
        });

        text_penanggung_jawab.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        text_penanggung_jawab.setEnabled(false);

        jLabel56.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel56.setText("ID TRANSAKSI");

        text_id_pengeluaran.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        text_id_pengeluaran.setEnabled(false);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap(240, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel56, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(text_id_pengeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel31))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(nominal_pengeluaran, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(text_penanggung_jawab, javax.swing.GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_pengeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(284, 284, 284))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_pengeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(text_id_pengeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(text_penanggung_jawab, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nominal_pengeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(56, 56, 56))
        );

        tabel_pengeluaran.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        tabel_pengeluaran.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "NO", "ID", "TANGGAL", "NAMA PENGELUARAN", "NOMINAL", "PENANGGUNG JAWAB"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabel_pengeluaran.setRowHeight(40);
        tabel_pengeluaran.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_pengeluaranMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tabel_pengeluaran);

        btn_hpus_pengeluaran.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btn_hpus_pengeluaran.setText("HAPUS");
        btn_hpus_pengeluaran.setEnabled(false);
        btn_hpus_pengeluaran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hpus_pengeluaranActionPerformed(evt);
            }
        });

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout pengeluaranLayout = new javax.swing.GroupLayout(pengeluaran);
        pengeluaran.setLayout(pengeluaranLayout);
        pengeluaranLayout.setHorizontalGroup(
            pengeluaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pengeluaranLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pengeluaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pengeluaranLayout.createSequentialGroup()
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 1155, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pengeluaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_hpus_pengeluaran, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pengeluaranLayout.setVerticalGroup(
            pengeluaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pengeluaranLayout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 289, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pengeluaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pengeluaranLayout.createSequentialGroup()
                        .addComponent(btn_hpus_pengeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(61, 61, 61))
        );

        jTabbedPane1.addTab("PENGELUARAN", pengeluaran);

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "UPLOAD DATA SISWA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 18))); // NOI18N

        jButton7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton7.setText("BROWSE");
        jButton7.setEnabled(false);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButton8.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jButton8.setText("UPLOAD");
        jButton8.setEnabled(false);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel14.setText("FILE : ");

        jLabel15.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel15.setText("KELAS : ");

        jComboBox5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "X", "XI", "XII", "XIII" }));
        jComboBox5.setEnabled(false);

        jComboBox6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "JURUSAN" }));
        jComboBox6.setEnabled(false);
        jComboBox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox6ActionPerformed(evt);
            }
        });

        jTextField7.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jTextField7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField7.setEnabled(false);
        jTextField7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField7KeyReleased(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel16.setText("TAHUN MASUK");

        jComboBox7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2018", "2019", "2020", "2021" }));
        jComboBox7.setEnabled(false);
        jComboBox7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox7ActionPerformed(evt);
            }
        });

        jLabel57.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel57.setText("PEMISAH DATA");

        combo_tokenizer.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        combo_tokenizer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { ",", ";" }));
        combo_tokenizer.setEnabled(false);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel57, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox7, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(jComboBox5, 0, 80, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField7)
                                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)))
                            .addComponent(combo_tokenizer, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel57, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                    .addComponent(combo_tokenizer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButton2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton2)
                .addGap(0, 7, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioButton2)
                .addContainerGap(250, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 271, Short.MAX_VALUE)
                .addContainerGap())
        );

        table_siswa.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        table_siswa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "NIS", "NAMA", "TINGKAT", "KELAS", "TAHUN MASUK", "PPDB", "BANGUNAN", "SPP"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_siswa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_siswaMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(table_siswa);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "LIHAT DATA SISWA", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Arial", 1, 18))); // NOI18N

        jLabel20.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel20.setText("TAHUN MASUK");

        jComboBox10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jComboBox10.setEnabled(false);
        jComboBox10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox10MouseClicked(evt);
            }
        });
        jComboBox10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox10ActionPerformed(evt);
            }
        });
        jComboBox10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboBox10KeyReleased(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel21.setText("KELAS");

        jButton13.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jButton13.setText("TAMPILKAN");
        jButton13.setEnabled(false);
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButton1MouseClicked(evt);
            }
        });

        jComboBox11.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jComboBox11.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "X", "XI", "XII", "XIII" }));
        jComboBox11.setEnabled(false);

        jComboBox12.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jComboBox12.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "JURUSAN" }));
        jComboBox12.setEnabled(false);
        jComboBox12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox12ActionPerformed(evt);
            }
        });

        jTextField23.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jTextField23.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField23.setEnabled(false);
        jTextField23.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField23KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jComboBox11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBox12, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField23, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jComboBox10, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton1)
                .addGap(41, 41, 41))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox10, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jRadioButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox11, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox12, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField23, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        btn_ubahdatasiswa.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btn_ubahdatasiswa.setText("UBAH DATA SISWA");
        btn_ubahdatasiswa.setEnabled(false);
        btn_ubahdatasiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ubahdatasiswaActionPerformed(evt);
            }
        });

        btn_hpusdatasiswa.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btn_hpusdatasiswa.setText("HAPUS SISWA");
        btn_hpusdatasiswa.setEnabled(false);
        btn_hpusdatasiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hpusdatasiswaActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 0, 0));
        jLabel10.setText("<html> <head> <style> div { word-wrap: break-word;} </style> </head> <body> <div>Untuk melihat Detail Tagihan atau menghapus Siswa, silahkan dipilih dulu baris di tabel kemudian klik tombol LIHAT TAGIHAN atau HAPUS SISWA</div> </body> </html>");

        jPanel22.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CARI SISWA", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Arial", 1, 18))); // NOI18N

        combo_datasiswa.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combo_datasiswa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "nis", "nama_siswa" }));

        text_datasiswa.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        text_datasiswa.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        text_datasiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_datasiswaActionPerformed(evt);
            }
        });

        btn_caridatasiswa.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btn_caridatasiswa.setText("CARI");
        btn_caridatasiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_caridatasiswaActionPerformed(evt);
            }
        });

        btn_resetcarisiswa.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btn_resetcarisiswa.setText("RESET");
        btn_resetcarisiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_resetcarisiswaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addComponent(combo_datasiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(text_datasiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 578, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_caridatasiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_resetcarisiswa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(combo_datasiswa)
                    .addComponent(text_datasiswa, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_caridatasiswa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(btn_resetcarisiswa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout data_siswaLayout = new javax.swing.GroupLayout(data_siswa);
        data_siswa.setLayout(data_siswaLayout);
        data_siswaLayout.setHorizontalGroup(
            data_siswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(data_siswaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(data_siswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(data_siswaLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(data_siswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_hpusdatasiswa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_ubahdatasiswa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(data_siswaLayout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(data_siswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 630, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        data_siswaLayout.setVerticalGroup(
            data_siswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(data_siswaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(data_siswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(data_siswaLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(data_siswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(data_siswaLayout.createSequentialGroup()
                        .addComponent(btn_ubahdatasiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_hpusdatasiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("DATA SISWA", data_siswa);

        jPanel20.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "SETTING NOMINAL TAGIHAN", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Arial", 1, 18))); // NOI18N

        jButton12.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jButton12.setText("UBAH");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jTextField27.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jTextField27.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField27KeyReleased(evt);
            }
        });

        jLabel49.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel49.setText("TAHUN MASUK");

        jLabel50.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel50.setText("SPP");

        jLabel51.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel51.setText("PPDB");

        jTextField28.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jTextField28.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField28KeyReleased(evt);
            }
        });

        jTextField29.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jTextField29.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField29KeyReleased(evt);
            }
        });

        jLabel52.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel52.setText("BANGUNAN");

        jLabel53.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel53.setText("DAFTAR ULANG X XI");

        jTextField30.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jTextField30.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField30KeyReleased(evt);
            }
        });

        jLabel54.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel54.setText("DAFTAR ULANG XI XII");

        jTextField31.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jTextField31.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField31KeyReleased(evt);
            }
        });

        jLabel55.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel55.setText("AKHIR TAHUN");

        jTextField32.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jTextField32.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField32KeyReleased(evt);
            }
        });

        jTextField6.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jTextField6.setEnabled(false);

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel52, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel51, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel50, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel20Layout.createSequentialGroup()
                                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTextField28, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField29)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel20Layout.createSequentialGroup()
                                        .addComponent(jTextField27, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, Short.MAX_VALUE)
                                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jTextField30, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                                    .addComponent(jTextField31)
                                    .addComponent(jTextField32)))
                            .addComponent(jTextField6))))
                .addContainerGap())
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                    .addComponent(jTextField6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField27, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField30, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField28, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField31, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField29, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField32, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabel_tagihan.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        tabel_tagihan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "TAHUN MASUK", "SPP", "PPDB", "BANGUNAN", "DAFTAR ULANG X XI", "DAFTAR ULANG XI XII", "AKHIR TAHUN"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabel_tagihan.setRowHeight(40);
        tabel_tagihan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_tagihanMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tabel_tagihan);

        javax.swing.GroupLayout setelan_bayarLayout = new javax.swing.GroupLayout(setelan_bayar);
        setelan_bayar.setLayout(setelan_bayarLayout);
        setelan_bayarLayout.setHorizontalGroup(
            setelan_bayarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(setelan_bayarLayout.createSequentialGroup()
                .addGap(342, 342, 342)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(349, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, setelan_bayarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4)
                .addContainerGap())
        );
        setelan_bayarLayout.setVerticalGroup(
            setelan_bayarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(setelan_bayarLayout.createSequentialGroup()
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("SETELAN PEMBAYARAN", setelan_bayar);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "SETELAN TAHUN MASUK", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Arial", 1, 18))); // NOI18N

        jButton15.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jButton15.setText("TAMBAH");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel17.setText("TAHUN MASUK");

        tabel_thnmasuk.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        tabel_thnmasuk.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "TAHUN MASUK"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabel_thnmasuk.setRowHeight(40);
        tabel_thnmasuk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_thnmasukMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tabel_thnmasuk);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(172, 172, 172)
                        .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(120, 120, 120))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "SETELAN JURUSAN", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Arial", 1, 18))); // NOI18N

        jButton18.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jButton18.setText("TAMBAH");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel18.setText("NAMA JURUSAN");

        jTextField9.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        tabel_jurusan.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        tabel_jurusan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "NAMA JURUSAN"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabel_jurusan.setRowHeight(40);
        tabel_jurusan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_jurusanMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tabel_jurusan);

        jLabel19.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel19.setText("NAMA JURUSAN");

        jTextField11.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        jButton21.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jButton21.setText("EDIT");
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 0, 0));
        jLabel13.setText("<html> <head> <style> div { word-wrap: break-word;} </style> </head> <body> <div>Untuk meng EDIT Jurusan, silahkan dipilih dulu baris di tabel Jurusan nya baru kemudian bisa di klik EDIT</div> </body> </html>");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane6)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout thnmasuk_jurusanLayout = new javax.swing.GroupLayout(thnmasuk_jurusan);
        thnmasuk_jurusan.setLayout(thnmasuk_jurusanLayout);
        thnmasuk_jurusanLayout.setHorizontalGroup(
            thnmasuk_jurusanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thnmasuk_jurusanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(221, Short.MAX_VALUE))
        );
        thnmasuk_jurusanLayout.setVerticalGroup(
            thnmasuk_jurusanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thnmasuk_jurusanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(thnmasuk_jurusanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("TAHUN MASUK & JURUSAN", thnmasuk_jurusan);

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "TINGKAT SEBELUM", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Arial", 1, 18))); // NOI18N

        jButton16.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jButton16.setText("TAMPILKAN");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel22.setText("TAHUN MASUK");

        tabel_Sebelumnaik.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "NIS", "NAMA", "KELAS", "TAHUN MASUK"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabel_Sebelumnaik.setRowHeight(40);
        jScrollPane7.setViewportView(tabel_Sebelumnaik);

        jComboBox14.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jComboBox14.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TAHUN MASUK" }));
        jComboBox14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox14ActionPerformed(evt);
            }
        });

        jLabel32.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel32.setText("TINGKAT");

        jComboBox8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "X", "XI", "XII", "XIII" }));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 537, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox14, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox8, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox14, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "TINGKAT SESUDAH", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Arial", 1, 18))); // NOI18N

        jLabel30.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel30.setText("TAHUN MASUK");

        jTextField18.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jTextField18.setEnabled(false);

        jLabel33.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel33.setText("TINGKAT");

        jTextField20.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jTextField20.setEnabled(false);

        tabel_Setelahnaik.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "NIS", "NAMA", "KELAS", "TAHUN MASUK"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabel_Setelahnaik.setRowHeight(40);
        jScrollPane9.setViewportView(tabel_Setelahnaik);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane9)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField20))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField18)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "ATUR TINGKAT KE", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Arial", 1, 18))); // NOI18N

        jComboBox9.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jComboBox9.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "X", "XI", "XII", "XIII" }));
        jComboBox9.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jButton17.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jButton17.setText("ATUR");
        jButton17.setEnabled(false);
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox9, 0, 204, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jButton17, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox9, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(67, Short.MAX_VALUE))
            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel12Layout.createSequentialGroup()
                    .addGap(65, 65, 65)
                    .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout naik_tingkatLayout = new javax.swing.GroupLayout(naik_tingkat);
        naik_tingkat.setLayout(naik_tingkatLayout);
        naik_tingkatLayout.setHorizontalGroup(
            naik_tingkatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(naik_tingkatLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        naik_tingkatLayout.setVerticalGroup(
            naik_tingkatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(naik_tingkatLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(naik_tingkatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(naik_tingkatLayout.createSequentialGroup()
                .addGap(158, 158, 158)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(227, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("SETELAN NAIK TINGKAT", naik_tingkat);

        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "INPUT DATA PENGGUNA APLIKASI", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 18))); // NOI18N

        jButton29.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jButton29.setText("INPUT");
        jButton29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton29ActionPerformed(evt);
            }
        });

        jLabel43.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel43.setText("USERNAME");

        jTextField17.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N

        jLabel44.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel44.setText("PASSWORD");

        jTextField21.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N

        jTextField22.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N

        jLabel45.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel45.setText("NAMA PENGGUNA");

        jLabel47.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel47.setText("ID PENGGUNA");

        jTextField25.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jTextField25.setEnabled(false);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel17Layout.createSequentialGroup()
                                    .addComponent(jLabel45)
                                    .addGap(21, 21, 21))
                                .addGroup(jPanel17Layout.createSequentialGroup()
                                    .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGap(4, 4, 4)))
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addComponent(jLabel47, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField25, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
                            .addComponent(jTextField22)
                            .addComponent(jTextField21)
                            .addComponent(jTextField17)))))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField25, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton29, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tabel_user.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        tabel_user.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "USERNAME", "PASSWORD", "NAMA PENGGUNA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabel_user.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tabel_user.setRowHeight(40);
        tabel_user.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_userMouseClicked(evt);
            }
        });
        jScrollPane11.setViewportView(tabel_user);

        jButton30.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jButton30.setText("HAPUS");
        jButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton30ActionPerformed(evt);
            }
        });

        jButton32.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jButton32.setText("REFRESH");
        jButton32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton32ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout setelan_penggunaLayout = new javax.swing.GroupLayout(setelan_pengguna);
        setelan_pengguna.setLayout(setelan_penggunaLayout);
        setelan_penggunaLayout.setHorizontalGroup(
            setelan_penggunaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(setelan_penggunaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(setelan_penggunaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(setelan_penggunaLayout.createSequentialGroup()
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 1159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton30, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE))
                    .addGroup(setelan_penggunaLayout.createSequentialGroup()
                        .addGap(1169, 1169, 1169)
                        .addComponent(jButton32, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)))
                .addContainerGap())
        );
        setelan_penggunaLayout.setVerticalGroup(
            setelan_penggunaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(setelan_penggunaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(setelan_penggunaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(setelan_penggunaLayout.createSequentialGroup()
                        .addComponent(jButton30, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton32, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("SETELAN PENGGUNA", setelan_pengguna);

        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "USER LOGIN", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Arial", 1, 14))); // NOI18N

        Label_petugas.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        Label_petugas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Label_petugas.setText("ADMIN");

        Label_waktulogin.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        Label_waktulogin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jButton31.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jButton31.setText("LOGOUT");
        jButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton31ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Label_petugas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Label_waktulogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton31, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(Label_petugas, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Label_waktulogin, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pembayaran_tu_texar_apk/about.png"))); // NOI18N

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 26, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 872, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 872, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1363, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1))
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here
//        utama_nis.setText(modelCariSiswa.getValueAt(tbl_cari_siswa.getSelectedRow(), 0).toString());
//        utama_nama.setText(modelCariSiswa.getValueAt(tbl_cari_siswa.getSelectedRow(), 1).toString());
//        utama_tingkat.setText(modelCariSiswa.getValueAt(tbl_cari_siswa.getSelectedRow(), 2).toString());
//        utama_kelas.setText(modelCariSiswa.getValueAt(tbl_cari_siswa.getSelectedRow(), 3).toString());
//        utama_thnmasuk.setText(modelCariSiswa.getValueAt(tbl_cari_siswa.getSelectedRow(), 4).toString());
        
        //tbl_daftar_tagihan.setAutoResizeMode(tbl_daftar_tagihan.AUTO_RESIZE_OFF);
        String cari2 = utama_nis.getText();
        generateTagihan(cari2);
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btn_utama_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_utama_resetActionPerformed
        // TODO add your handling code here:
        refreshTable();
       for (int i = tbl_daftar_tagihan.getRowCount() - 1; i >= 0; i--) {
            model.removeRow(i);
       }
       jTextField1.setText("");
       
       for (int i = tbl_cari_siswa.getRowCount() - 1; i >= 0; i--) {
            modelCariSiswa.removeRow(i);
       }
       utama_kelas.setText("");
       utama_nis.setText("");
       utama_nama.setText("");
       utama_thnmasuk.setText("");
       utama_tingkat.setText("");
       btn_utama_bayar.setEnabled(false);
    }//GEN-LAST:event_btn_utama_resetActionPerformed

    private void btn_utama_bayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_utama_bayarActionPerformed
        // TODO add your handling code here:
        menu_pembayaran_gabungan mp = new menu_pembayaran_gabungan();
        mp.setVisible(true);
        
    }//GEN-LAST:event_btn_utama_bayarActionPerformed

    private void jComboBox7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox7ActionPerformed

    private void jComboBox6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox6ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        String token = combo_tokenizer.getSelectedItem().toString();
        String kelas = jTextField7.getText();
        if (isNumeric(kelas) && !kelas.equals("")) {
            File file = new File(this.path);
            try {
                String[] temp;
                int indexTemp = 0;
                ArrayList<String []> AlTemp = new ArrayList<>();
                BufferedReader br = new BufferedReader(new FileReader(file));  
                String line = "";  
                StringTokenizer st = null; 
                while ((line = br.readLine()) != null) {
                    temp = new String[11];
                    indexTemp = 0;
                   //use comma as token separator  
                    st = new StringTokenizer(line, token);  
                    while (st.hasMoreTokens()) {   
                        String sd=st.nextToken();
                        temp[indexTemp] = sd;
                        indexTemp++;
                    }
                    AlTemp.add(temp);
                }
                br.close();
                temp = new String[11];
                temp = AlTemp.get(0);
                boolean cek2 = cekFormatExcel(temp);
                if (cek2) {
                    siswa s = new siswa();
                    boolean cekDataSiswa = false;
                    int i = 1;
                    System.out.println(AlTemp.size());
                    while (i < AlTemp.size() && cekDataSiswa == false) {
                        temp = new String[11];
                        temp = AlTemp.get(i);
                        this.stat = k.getCon().prepareStatement("insert into siswa values(?,?,?,?,?,?,?,?,?,?,?)");
                        stat.setString(1, temp[0]);//nis
                        stat.setString(2, temp[1]);//nama
                        stat.setString(3, s.tingkat);//tingkat
                        stat.setString(4, s.kelas);//kelas
                        stat.setString(5, s.tahun_masuk);//tahun masuk
                        stat.setString(6, s.nama_jurusan);//nama jurusan
                        stat.setString(7, temp[2]);//ppdb
                        stat.setString(8, temp[3]);//bangunan
                        stat.setString(9, temp[8]);//kenaikanxxi
                        stat.setString(10, temp[9]);//kenaikanxixii
                        stat.setString(11, temp[10]);//akhirtahun
                        stat.executeUpdate();
                        System.out.println(i+" INPUT SUKSES");
                        i++;
                    }
                    refreshTableSiswa(s.tahun_masuk,s.tingkat,s.kelas);
                    jButton7.setEnabled(false);
                }else{
                    JOptionPane.showMessageDialog(null, "<html><head><style>h2{text-align: center;}</style></head><body><h2>FILE TIDAK MENDUKUNG<br>Format file .csv tidak sesuai</h2></body></html>");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "erorr"+e.getMessage());
            }
        }else{
            JOptionPane.showMessageDialog(null, "<html><head><style>h2{text-align: center;}</style></head><body><h2>ISIAN BELUM LENGKAP<br>isi terlebih dahulu kolom kelas dengan benar</h2></body></html>");
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        JFileChooser choose = new JFileChooser();
        int respon = choose.showOpenDialog(null);
        if (respon == JFileChooser.APPROVE_OPTION) {
            File file = new File(choose.getSelectedFile().getAbsolutePath());
            String cek = right(file.toString(), 3);
            if (!cek.equals("csv")) {
                JOptionPane.showMessageDialog(null, "<html><head><style>h2{text-align: center;}</style></head><body><h2>FILE TIDAK MENDUKUNG<br>File yang di Upload harus format .csv</h2></body></html>");
                System.out.println(cek);
            }else{
                jButton8.setEnabled(true);
                jLabel11.setText(file.getName());
                this.path = choose.getSelectedFile().getAbsolutePath();
                jComboBox5.setEnabled(true);
                jComboBox6.setEnabled(true);
                jTextField7.setEnabled(true);
                jComboBox7.setEnabled(true);
            }
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jComboBox10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox10ActionPerformed
        // TODO add your handling code here:
                
    }//GEN-LAST:event_jComboBox10ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        String tahun_masuk = jComboBox10.getSelectedItem().toString(),
        tingkat = jComboBox11.getSelectedItem().toString(),
        kelas = jComboBox12.getSelectedItem().toString()+" "+jTextField23.getText();
        refreshTableSiswa(tahun_masuk, tingkat, kelas);
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
        try {
            this.stat = k.getCon().prepareStatement("insert into tagihan values(?,?,?,?,?,?,?)");
            stat.setInt(1, cal_tahun.getYear());
            stat.setInt(2, 0);
            stat.setInt(3, 0);
            stat.setInt(4, 0);
            stat.setInt(5, 0);
            stat.setInt(6, 0);
            stat.setInt(7, 0);
            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "Tahun masuk "+cal_tahun.getYear()+" Berhasil ditambahkan");
            refreshTableThnmasuk();
            refreshComboThnMasuk();
            refreshTableTagihan();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR"+e.getMessage());
        }
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        // TODO add your handling code here:
        try {
            this.stat = k.getCon().prepareStatement("insert into jurusan values(?)");
            stat.setString(1, jTextField9.getText());
            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "Jurusan "+jTextField9.getText()+" Berhasil ditambahkan");
            refreshTableJurusan();
            refreshComboJurusan();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR"+e.getMessage());
        }
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        // TODO add your handling code here:
        String edit_jurusan = jTextField11.getText();
        try {
            stat = k.getCon().prepareStatement("UPDATE `jurusan` SET `nama_jurusan`='"+edit_jurusan+"' WHERE nama_jurusan='"+temp+"'");
            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "Jurusan Berhasil dirubah");
            refreshTableJurusan();
            refreshComboJurusan();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR"+e.getMessage());
        }
    }//GEN-LAST:event_jButton21ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jComboBox14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox14ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton17ActionPerformed

    private void btn_pengeluaranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pengeluaranActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_pengeluaranActionPerformed

    private void btn_cetakharianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cetakharianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_cetakharianActionPerformed

    private void btn_cetakmingguanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cetakmingguanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_cetakmingguanActionPerformed

    private void btn_cetakbulananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cetakbulananActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_cetakbulananActionPerformed

    private void jButton29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton29ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton29ActionPerformed

    private void tbl_daftar_tagihanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_daftar_tagihanMouseClicked
        // TODO add your handling code here:
        btn_utama_bayar.setEnabled(true);
    }//GEN-LAST:event_tbl_daftar_tagihanMouseClicked

    private void table_siswaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_siswaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_table_siswaMouseClicked

    private void btn_hpusdatasiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hpusdatasiswaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_hpusdatasiswaActionPerformed

    private void nominal_pengeluaranKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nominal_pengeluaranKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_nominal_pengeluaranKeyReleased

    private void jTextField7KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField7KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7KeyReleased

    private void jRadioButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton1MouseClicked
        // TODO add your handling code here:
        if (jRadioButton1.isSelected()) {
            jComboBox10.setEnabled(true);
            jComboBox11.setEnabled(true);
            jComboBox12.setEnabled(true);
            jTextField23.setEnabled(true);
            jButton13.setEnabled(true);
            jButton7.setEnabled(false);
            jComboBox5.setEnabled(false);
            jComboBox6.setEnabled(false);
            jComboBox7.setEnabled(false);
            jButton8.setEnabled(false);
            jTextField7.setEnabled(false);
            combo_tokenizer.setEnabled(false);
        }
    }//GEN-LAST:event_jRadioButton1MouseClicked

    private void jRadioButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton2MouseClicked
        // TODO add your handling code here:
        if (jRadioButton2.isSelected()) {
            jButton7.setEnabled(true);
            jComboBox10.setEnabled(false);
            jComboBox11.setEnabled(false);
            jButton13.setEnabled(false);
            jComboBox12.setEnabled(false);
            jTextField23.setEnabled(false);
            combo_tokenizer.setEnabled(true);
        }
    }//GEN-LAST:event_jRadioButton2MouseClicked

    private void jComboBox10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox10MouseClicked
        // TODO add your handling code here:
//        System.out.println(jComboBox10.getSelectedItem().toString());
    }//GEN-LAST:event_jComboBox10MouseClicked

    private void jComboBox10KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox10KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox10KeyReleased

    private void jComboBox12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox12ActionPerformed

    private void jTextField23KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField23KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField23KeyReleased

    private void jButton31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton31ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton31ActionPerformed

    private void tabel_userMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_userMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tabel_userMouseClicked

    private void jButton32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton32ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton32ActionPerformed

    private void jButton30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton30ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton30ActionPerformed

    private void tabel_jurusanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_jurusanMouseClicked
        // TODO add your handling code here:
        this.temp = modelJurusan.getValueAt(tabel_jurusan.getSelectedRow(), 0).toString();
        jTextField11.setText(modelJurusan.getValueAt(tabel_jurusan.getSelectedRow(), 0).toString());
    }//GEN-LAST:event_tabel_jurusanMouseClicked

    private void tabel_thnmasukMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_thnmasukMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_tabel_thnmasukMouseClicked

    private void btn_ubahdatasiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ubahdatasiswaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_ubahdatasiswaActionPerformed

    private void tabel_pengeluaranMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_pengeluaranMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tabel_pengeluaranMouseClicked

    private void btn_hpus_pengeluaranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hpus_pengeluaranActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_hpus_pengeluaranActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        String cari = jTextField1.getText();
        search(cari);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void tbl_cari_siswaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_cari_siswaMouseClicked
        // TODO add your handling code here:
        utama_nis.setText(modelCariSiswa.getValueAt(tbl_cari_siswa.getSelectedRow(), 0).toString());
        utama_nama.setText(modelCariSiswa.getValueAt(tbl_cari_siswa.getSelectedRow(), 1).toString());
        utama_tingkat.setText(modelCariSiswa.getValueAt(tbl_cari_siswa.getSelectedRow(), 2).toString());
        utama_kelas.setText(modelCariSiswa.getValueAt(tbl_cari_siswa.getSelectedRow(), 3).toString());
        utama_thnmasuk.setText(modelCariSiswa.getValueAt(tbl_cari_siswa.getSelectedRow(), 4).toString());
    }//GEN-LAST:event_tbl_cari_siswaMouseClicked

    private void btn_caridatasiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_caridatasiswaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_caridatasiswaActionPerformed

    private void btn_hapuspemasukanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapuspemasukanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_hapuspemasukanActionPerformed

    private void btn_resetcarisiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_resetcarisiswaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_resetcarisiswaActionPerformed

    private void text_datasiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_datasiswaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_datasiswaActionPerformed

    private void tabel_pemasukanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_pemasukanMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tabel_pemasukanMouseClicked

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        try {
            stat = k.getCon().prepareStatement("UPDATE `tagihan` SET `spp`='"+jTextField27.getText()+"',"
                + "`ppdb`='"+jTextField28.getText()+"',`bangunan`='"+jTextField29.getText()+"',"
                + "`daftarulangx_xi`='"+jTextField30.getText()+"',"
                + "`daftarulangxi_xii`='"+jTextField31.getText()+"',"
                + "`akhir_tahun`='"+jTextField32.getText()+"' "
                + "WHERE `tahun_masuk`='"+jTextField6.getText()+"'");
            stat.executeUpdate();
            refreshTableTagihan();
            JOptionPane.showMessageDialog(null, "Tagihan sudah berhasil diubah");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR"+e.getMessage());
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jTextField27KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField27KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField27KeyReleased

    private void jTextField28KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField28KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField28KeyReleased

    private void jTextField29KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField29KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField29KeyReleased

    private void jTextField30KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField30KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField30KeyReleased

    private void jTextField31KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField31KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField31KeyReleased

    private void jTextField32KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField32KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField32KeyReleased

    private void tabel_tagihanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_tagihanMouseClicked
        // TODO add your handling code here:
        jTextField6.setText(modelTagihan.getValueAt(tabel_tagihan.getSelectedRow(), 0).toString());
        jTextField27.setText(RptoString(modelTagihan.getValueAt(tabel_tagihan.getSelectedRow(), 1).toString()));
        jTextField28.setText(RptoString(modelTagihan.getValueAt(tabel_tagihan.getSelectedRow(), 2).toString()));
        jTextField29.setText(RptoString(modelTagihan.getValueAt(tabel_tagihan.getSelectedRow(), 3).toString()));
        jTextField30.setText(RptoString(modelTagihan.getValueAt(tabel_tagihan.getSelectedRow(), 4).toString()));
        jTextField31.setText(RptoString(modelTagihan.getValueAt(tabel_tagihan.getSelectedRow(), 5).toString()));
        jTextField32.setText(RptoString(modelTagihan.getValueAt(tabel_tagihan.getSelectedRow(), 6).toString()));
    }//GEN-LAST:event_tabel_tagihanMouseClicked

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        // TODO add your handling code here:
//        String cari = jTextField1.getText();
//        search(cari);
    }//GEN-LAST:event_jTextField1KeyReleased

    private void utama_nisKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_utama_nisKeyReleased
        // TODO add your handling code here:
        
    }//GEN-LAST:event_utama_nisKeyReleased

    private void utama_nisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_utama_nisActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_utama_nisActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(menu_utama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(menu_utama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(menu_utama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(menu_utama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new menu_utama().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel Label_petugas;
    public javax.swing.JLabel Label_waktulogin;
    private javax.swing.JButton btn_caridatasiswa;
    private javax.swing.JButton btn_cetakbulanan;
    private javax.swing.JButton btn_cetakharian;
    private javax.swing.JButton btn_cetakmingguan;
    private javax.swing.JButton btn_hapuspemasukan;
    private javax.swing.JButton btn_hpus_pengeluaran;
    private javax.swing.JButton btn_hpusdatasiswa;
    private javax.swing.JButton btn_pengeluaran;
    private javax.swing.JButton btn_resetcarisiswa;
    private javax.swing.JButton btn_ubahdatasiswa;
    private javax.swing.JButton btn_utama_bayar;
    private javax.swing.JButton btn_utama_reset;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JPanel cetak_laporan;
    private javax.swing.JComboBox<String> combo_cetakbulanan;
    private javax.swing.JComboBox<String> combo_datasiswa;
    private javax.swing.JComboBox<String> combo_kategoriharian;
    private javax.swing.JComboBox<String> combo_kategorimingguan;
    private javax.swing.JComboBox<String> combo_tokenizer;
    private javax.swing.JPanel data_siswa;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox10;
    private javax.swing.JComboBox<String> jComboBox11;
    private javax.swing.JComboBox<String> jComboBox12;
    private javax.swing.JComboBox<String> jComboBox14;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JComboBox<String> jComboBox8;
    private javax.swing.JComboBox<String> jComboBox9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField23;
    private javax.swing.JTextField jTextField25;
    private javax.swing.JTextField jTextField27;
    private javax.swing.JTextField jTextField28;
    private javax.swing.JTextField jTextField29;
    private javax.swing.JTextField jTextField30;
    private javax.swing.JTextField jTextField31;
    private javax.swing.JTextField jTextField32;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JPanel naik_tingkat;
    private javax.swing.JTextPane nama_pengeluaran;
    private javax.swing.JTextField nominal_pengeluaran;
    private javax.swing.JPanel pengeluaran;
    private javax.swing.JPanel setelan_bayar;
    private javax.swing.JPanel setelan_pengguna;
    private javax.swing.JTable tabel_Sebelumnaik;
    private javax.swing.JTable tabel_Setelahnaik;
    private javax.swing.JTable tabel_jurusan;
    private javax.swing.JTable tabel_pemasukan;
    private javax.swing.JTable tabel_pengeluaran;
    private javax.swing.JTable tabel_tagihan;
    private javax.swing.JTable tabel_thnmasuk;
    private javax.swing.JTable tabel_user;
    private javax.swing.JTable table_siswa;
    private javax.swing.JPanel tagihan_pembayaran;
    private javax.swing.JTable tbl_cari_siswa;
    private javax.swing.JTable tbl_daftar_tagihan;
    private javax.swing.JTextField text_datasiswa;
    private javax.swing.JTextField text_id_pengeluaran;
    public javax.swing.JTextField text_penanggung_jawab;
    private javax.swing.JPanel thnmasuk_jurusan;
    private javax.swing.JTextField utama_kelas;
    private javax.swing.JTextField utama_nama;
    private javax.swing.JTextField utama_nis;
    private javax.swing.JTextField utama_thnmasuk;
    private javax.swing.JTextField utama_tingkat;
    // End of variables declaration//GEN-END:variables
}
