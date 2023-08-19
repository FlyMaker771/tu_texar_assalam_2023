/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pembayaran_tu_texar_apk;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
    
/**
 *
 * @author gunar
 */
public class siswa_spp {
    private PreparedStatement stat;
    private ResultSet rs;
    koneksi k = new koneksi();
    String nis;
    int agustus, september, oktober, november, desember, januari, februari, maret, april, mei, juni, juli;

    public siswa_spp() {
        k.connect();
    }
    
    public boolean insertSppX(String nis, int jml_bln) {
        //INSERT INTO `sppx`(`nis`, `agustus`, `september`, `oktober`, `november`, `desember`, `januari`, `februari`, `maret`, `april`, `mei`, `juni`) VALUES ('2005494',1,1,1,1,1,1,1,1,1,1,1)
        boolean cek = false;
        String values_bulan="";
        switch(jml_bln){
                case 0:
                    values_bulan = "('"+nis+"',0,0,0,0,0,0,0,0,0,0,0);";
                break;
                case 1:
                    values_bulan = "('"+nis+"',1,0,0,0,0,0,0,0,0,0,0);";
                break;
                case 2:
                    values_bulan = "('"+nis+"',1,1,0,0,0,0,0,0,0,0,0);";
                break;
                case 3:
                    values_bulan = "('"+nis+"',1,1,1,0,0,0,0,0,0,0,0);";
                break;
                case 4:
                    values_bulan = "('"+nis+"',1,1,1,1,0,0,0,0,0,0,0);";
                break;
                case 5:
                    values_bulan = "('"+nis+"',1,1,1,1,1,0,0,0,0,0,0);";
                break;
                case 6:
                    values_bulan = "('"+nis+"',1,1,1,1,1,1,0,0,0,0,0);";
                break;
                case 7:
                    values_bulan = "('"+nis+"',1,1,1,1,1,1,1,0,0,0,0);";
                break;
                case 8:
                    values_bulan = "('"+nis+"',1,1,1,1,1,1,1,1,0,0,0);";
                break;
                case 9:
                    values_bulan = "('"+nis+"',1,1,1,1,1,1,1,1,1,0,0);";
                break;
                case 10:
                    values_bulan = "('"+nis+"',1,1,1,1,1,1,1,1,1,1,0);";
                break;
                case 11:
                    values_bulan = "('"+nis+"',1,1,1,1,1,1,1,1,1,1,1);";
                break;
                default:
                    values_bulan = "('"+nis+"',0,0,0,0,0,0,0,0,0,0,0);";
            }
        try {
            this.stat = k.getCon().prepareStatement("INSERT INTO `sppx`(`nis`, `agustus`, `september`, `oktober`, `november`, `desember`, `januari`, `februari`, `maret`, `april`, `mei`, `juni`) VALUES "+values_bulan);
            stat.executeUpdate();
            cek = true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error "+e);
        }
        return cek;
    }
    
    public boolean insertSppXI(String nis, int jml_bln) {
        boolean cek = false;
        String values_bulan="";
        switch(jml_bln){
                case 0:
                    values_bulan = "('"+nis+"',0,0,0,0,0,0,0,0,0,0,0);";
                break;
                case 1:
                    values_bulan = "('"+nis+"',1,0,0,0,0,0,0,0,0,0,0);";
                break;
                case 2:
                    values_bulan = "('"+nis+"',1,1,0,0,0,0,0,0,0,0,0);";
                break;
                case 3:
                    values_bulan = "('"+nis+"',1,1,1,0,0,0,0,0,0,0,0);";
                break;
                case 4:
                    values_bulan = "('"+nis+"',1,1,1,1,0,0,0,0,0,0,0);";
                break;
                case 5:
                    values_bulan = "('"+nis+"',1,1,1,1,1,0,0,0,0,0,0);";
                break;
                case 6:
                    values_bulan = "('"+nis+"',1,1,1,1,1,1,0,0,0,0,0);";
                break;
                case 7:
                    values_bulan = "('"+nis+"',1,1,1,1,1,1,1,0,0,0,0);";
                break;
                case 8:
                    values_bulan = "('"+nis+"',1,1,1,1,1,1,1,1,0,0,0);";
                break;
                case 9:
                    values_bulan = "('"+nis+"',1,1,1,1,1,1,1,1,1,0,0);";
                break;
                case 10:
                    values_bulan = "('"+nis+"',1,1,1,1,1,1,1,1,1,1,0);";
                break;
                case 11:
                    values_bulan = "('"+nis+"',1,1,1,1,1,1,1,1,1,1,1);";
                break;
                default:
                    values_bulan = "('"+nis+"',0,0,0,0,0,0,0,0,0,0,0);";
            }
        try {
            this.stat = k.getCon().prepareStatement("INSERT INTO `sppxi`(`nis`, `agustus`, `september`, `oktober`, `november`, `desember`, `januari`, `februari`, `maret`, `april`, `mei`, `juni`) VALUES "+values_bulan);
            stat.executeUpdate();
            cek = true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error "+e);
        }
        return cek;
    }
    
    public boolean insertSppXII(String nis, int jml_bln) {
        boolean cek = false;
        String values_bulan="";
        switch(jml_bln){
                case 0:
                    values_bulan = "('"+nis+"',0,0,0,0,0,0,0,0,0,0,0);";
                break;
                case 1:
                    values_bulan = "('"+nis+"',1,0,0,0,0,0,0,0,0,0,0);";
                break;
                case 2:
                    values_bulan = "('"+nis+"',1,1,0,0,0,0,0,0,0,0,0);";
                break;
                case 3:
                    values_bulan = "('"+nis+"',1,1,1,0,0,0,0,0,0,0,0);";
                break;
                case 4:
                    values_bulan = "('"+nis+"',1,1,1,1,0,0,0,0,0,0,0);";
                break;
                case 5:
                    values_bulan = "('"+nis+"',1,1,1,1,1,0,0,0,0,0,0);";
                break;
                case 6:
                    values_bulan = "('"+nis+"',1,1,1,1,1,1,0,0,0,0,0);";
                break;
                case 7:
                    values_bulan = "('"+nis+"',1,1,1,1,1,1,1,0,0,0,0);";
                break;
                case 8:
                    values_bulan = "('"+nis+"',1,1,1,1,1,1,1,1,0,0,0);";
                break;
                case 9:
                    values_bulan = "('"+nis+"',1,1,1,1,1,1,1,1,1,0,0);";
                break;
                case 10:
                    values_bulan = "('"+nis+"',1,1,1,1,1,1,1,1,1,1,0);";
                break;
                case 11:
                    values_bulan = "('"+nis+"',1,1,1,1,1,1,1,1,1,1,1);";
                break;
                default:
                    values_bulan = "('"+nis+"',0,0,0,0,0,0,0,0,0,0,0);";
            }
        try {
            this.stat = k.getCon().prepareStatement("INSERT INTO `sppxii`(`nis`, `agustus`, `september`, `oktober`, `november`, `desember`, `januari`, `februari`, `maret`, `april`, `mei`, `juni`) VALUES "+values_bulan);
            stat.executeUpdate();
            cek = true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error "+e);
        }
        return cek;
    }
    
    public boolean insertSppXIII(String nis, int jml_bln) {
        boolean cek = false;
        String values_bulan="";
        switch(jml_bln){
                case 0:
                    values_bulan = "('"+nis+"',0,0,0,0,0,0,0,0,0,0,0,0);";
                break;
                case 1:
                    values_bulan = "('"+nis+"',1,0,0,0,0,0,0,0,0,0,0,0);";
                break;
                case 2:
                    values_bulan = "('"+nis+"',1,1,0,0,0,0,0,0,0,0,0,0);";
                break;
                case 3:
                    values_bulan = "('"+nis+"',1,1,1,0,0,0,0,0,0,0,0,0);";
                break;
                case 4:
                    values_bulan = "('"+nis+"',1,1,1,1,0,0,0,0,0,0,0,0);";
                break;
                case 5:
                    values_bulan = "('"+nis+"',1,1,1,1,1,0,0,0,0,0,0,0);";
                break;
                case 6:
                    values_bulan = "('"+nis+"',1,1,1,1,1,1,0,0,0,0,0,0);";
                break;
                case 7:
                    values_bulan = "('"+nis+"',1,1,1,1,1,1,1,0,0,0,0,0);";
                break;
                case 8:
                    values_bulan = "('"+nis+"',1,1,1,1,1,1,1,1,0,0,0,0);";
                break;
                case 9:
                    values_bulan = "('"+nis+"',1,1,1,1,1,1,1,1,1,0,0,0);";
                break;
                case 10:
                    values_bulan = "('"+nis+"',1,1,1,1,1,1,1,1,1,1,0,0);";
                break;
                case 11:
                    values_bulan = "('"+nis+"',1,1,1,1,1,1,1,1,1,1,1,0);";
                break;
                case 12:
                    values_bulan = "('"+nis+"',1,1,1,1,1,1,1,1,1,1,1,1);";
                break;
                default:
                    values_bulan = "('"+nis+"',0,0,0,0,0,0,0,0,0,0,0);";
            }
        try {
            this.stat = k.getCon().prepareStatement("INSERT INTO `sppxiii`(`nis`, `juli`, `agustus`, `september`, `oktober`, `november`, `desember`, `januari`, `februari`, `maret`, `april`, `mei`, `juni`) VALUES "+values_bulan);
            stat.executeUpdate();
            cek = true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error "+e);
        }
        return cek;
    }
    
    public int getJmlSppX(String nis){
        int jml = 0;
        try {
            this.stat = k.getCon().prepareStatement("SELECT `agustus`+ `september`+ `oktober`+ `november`+ `desember`+ `januari`+ `februari`+ `maret`+ `april`+ `mei`+ `juni` FROM `sppx` WHERE nis='"+nis+"';");
            this.rs = stat.executeQuery();
            while (rs.next()) {                
                jml = rs.getInt(1);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error "+e);
        }
        return jml;
    }
    
    public int getJmlSppXI(String nis){
        int jml = 0;
        try {
            this.stat = k.getCon().prepareStatement("SELECT `agustus`+ `september`+ `oktober`+ `november`+ `desember`+ `januari`+ `februari`+ `maret`+ `april`+ `mei`+ `juni` FROM `sppxi` WHERE nis='"+nis+"';");
            this.rs = stat.executeQuery();
            while (rs.next()) {                
                jml = rs.getInt(1);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error "+e);
        }
        return jml;
    }
    
    public int getJmlSppXII(String nis){
        int jml = 0;
        try {
            this.stat = k.getCon().prepareStatement("SELECT `agustus`+ `september`+ `oktober`+ `november`+ `desember`+ `januari`+ `februari`+ `maret`+ `april`+ `mei`+ `juni` FROM `sppxii` WHERE nis='"+nis+"';");
            this.rs = stat.executeQuery();
            while (rs.next()) {                
                jml = rs.getInt(1);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error "+e);
        }
        return jml;
    }
    
    public int getJmlSppXIII(String nis){
        int jml = 0;
        try {
            this.stat = k.getCon().prepareStatement("SELECT `juli`+ `agustus`+ `september`+ `oktober`+ `november`+ `desember`+ `januari`+ `februari`+ `maret`+ `april`+ `mei`+ `juni` FROM `sppxiii` WHERE nis='"+nis+"';");
            this.rs = stat.executeQuery();
            while (rs.next()) {                
                jml = rs.getInt(1);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error "+e);
        }
        return jml;
    }
    
    public boolean upSppX(String nis, String nama_bulan){
        boolean cek = false;
        try {
            this.stat = k.getCon().prepareStatement("UPDATE `sppx` SET `"+nama_bulan+"`=1 where `nis`='"+nis+"'");
            stat.executeUpdate();
            cek = true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error "+e);
        }
        return cek;
    } 
    
    public boolean upSppXI(String nis, String nama_bulan){
        boolean cek = false;
        try {
            this.stat = k.getCon().prepareStatement("UPDATE `sppxi` SET `"+nama_bulan+"`=1 where `nis`='"+nis+"'");
            stat.executeUpdate();
            cek = true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error "+e);
        }
        return cek;
    } 
    
    public boolean upSppXII(String nis, String nama_bulan){
        boolean cek = false;
        try {
            this.stat = k.getCon().prepareStatement("UPDATE `sppxii` SET `"+nama_bulan+"`=1 where `nis`='"+nis+"'");
            stat.executeUpdate();
            cek = true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error "+e);
        }
        return cek;
    } 
    
    public boolean upSppXIII(String nis, String nama_bulan){
        boolean cek = false;
        try {
            this.stat = k.getCon().prepareStatement("UPDATE `sppxiii` SET `"+nama_bulan+"`=1 where `nis`='"+nis+"'");
            stat.executeUpdate();
            cek = true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error "+e);
        }
        return cek;
    } 
    
    public boolean downSppX(String nis, String nama_bulan){
        boolean cek = false;
        try {
            this.stat = k.getCon().prepareStatement("UPDATE `sppx` SET `"+nama_bulan+"`=0 where `nis`='"+nis+"'");
            stat.executeUpdate();
            cek = true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error "+e);
        }
        return cek;
    } 
    
    public boolean downSppXI(String nis, String nama_bulan){
        boolean cek = false;
        try {
            this.stat = k.getCon().prepareStatement("UPDATE `sppxi` SET `"+nama_bulan+"`=0 where `nis`='"+nis+"'");
            stat.executeUpdate();
            cek = true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error "+e);
        }
        return cek;
    } 
    
    public boolean downSppXII(String nis, String nama_bulan){
        boolean cek = false;
        try {
            this.stat = k.getCon().prepareStatement("UPDATE `sppxii` SET `"+nama_bulan+"`=0 where `nis`='"+nis+"'");
            stat.executeUpdate();
            cek = true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error "+e);
        }
        return cek;
    } 
    
    public boolean downSppXIII(String nis, String nama_bulan){
        boolean cek = false;
        try {
            this.stat = k.getCon().prepareStatement("UPDATE `sppxiii` SET `"+nama_bulan+"`=0 where `nis`='"+nis+"'");
            stat.executeUpdate();
            cek = true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error "+e);
        }
        return cek;
    } 
}
