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
 * @author KAHFI
 */
public class tagihan_siswa {
    private PreparedStatement stat;
    private ResultSet rs;
    String nis, nama, tingkat, kelas, thn_masuk, nama_jurusan;
        int tagihan_spp, tagihan_ppdb, tagihan_bangunan, daful_xxi, daful_xixii, daful_athn;
        int stat_ppd, stat_bangunan, stat_sppx, stat_sppxi, stat_sppxii, stat_sppxiii;
        int stat_dafulxxi, stat_dafulxixii, stat_athn;

        public tagihan_siswa(String nis, String nama, String tingkat, String kelas, String thn_masuk,int tagihan_spp, int tagihan_ppdb, int tagihan_bangunan, int daful_xxi, int daful_xixii, int daful_athn, int stat_ppd, int stat_bangunan, int stat_sppx, int stat_sppxi, int stat_sppxii, int stat_sppxiii, int stat_dafulxxi, int stat_dafulxixii, int stat_athn) {
            this.nis = nis;
            this.nama = nama;
            this.tingkat = tingkat;
            this.kelas = kelas;
            this.thn_masuk = thn_masuk;
            this.tagihan_spp = tagihan_spp;
            this.tagihan_ppdb = tagihan_ppdb;
            this.tagihan_bangunan = tagihan_bangunan;
            this.daful_xxi = daful_xxi;
            this.daful_xixii = daful_xixii;
            this.daful_athn = daful_athn;
            this.stat_ppd = stat_ppd;
            this.stat_bangunan = stat_bangunan;
            this.stat_sppx = stat_sppx;
            this.stat_sppxi = stat_sppxi;
            this.stat_sppxii = stat_sppxii;
            this.stat_sppxiii = stat_sppxiii;
            this.stat_dafulxxi = stat_dafulxxi;
            this.stat_dafulxixii = stat_dafulxixii;
            this.stat_athn = stat_athn;
        }

    public tagihan_siswa() {
    }
    
    public void updateSiswa(String nama_kolom, int nominal, String nis){
        koneksi k = new koneksi();
        k.connect2();
        try {
            stat = k.getCon().prepareStatement("UPDATE siswa SET "+nama_kolom+" = "+nominal+" WHERE nis = "+nis+"");
            stat.executeUpdate();
            System.out.println("Update data siswa Berhasil");
        } catch (Exception e) {
            System.out.println("Error update tagihan "+e.getMessage());
        }
    }
}
