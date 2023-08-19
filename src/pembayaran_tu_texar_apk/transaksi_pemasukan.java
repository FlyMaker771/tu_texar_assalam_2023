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
public class transaksi_pemasukan {
    private PreparedStatement stat;
    private ResultSet rs;
    String no_trans, kode_pembayaran, tanggal, nis, nama_siswa;
    String nama_pemasukan, id_petugas, nama_petugas;
    int jml_pemasukan;

    public transaksi_pemasukan() {
    }
    
    public void hapusPemasukan(String no_pemasukan){
        koneksi k = new koneksi();
        k.connect2();
        try {
            stat = k.getCon().prepareStatement("delete from transaksi where no_transaksi="+no_pemasukan+"");
            stat.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR hapus pemasukan "+e.getMessage());
        }
    }
}
