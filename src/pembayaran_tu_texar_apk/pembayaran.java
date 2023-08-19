/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pembayaran_tu_texar_apk;

import java.util.ArrayList;

/**
 *
 * @author KAHFI
 */
public class pembayaran {
    String nama, kode;
    int nominal, jml_blnBayar;
    ArrayList<String> bulan;
    String[] bulan_lengkap = {"AGUSTUS","SEPTEMBER","OKTOBER","NOVEMBER","DESEMBER","JANUARI","FEBRUARI","MARET","APRIL","MEI","JUNI"};
    public pembayaran() {
        bulan = new ArrayList<>();
    }
    
    public void tambahBulan(String nama_bulan){
        bulan.add(nama_bulan);
    }
    
    public String getNama(){
        String namaByr = "";
        if (this.kode.equals("SPP-X")) {
            if (bulan.size()>1) {
                namaByr = "SPP KELAS X BULAN " +bulan.get(0)+" - "+bulan.get(bulan.size()-1);
            }else{
                namaByr = "SPP KELAS X BULAN " +bulan.get(0);
            }
        }else if (this.kode.equals("SPP-XI")) {
            if (bulan.size()>1) {
                namaByr = "SPP KELAS XI BULAN " +bulan.get(0)+" - "+bulan.get(bulan.size()-1);
            }else{
                namaByr = "SPP KELAS XI BULAN " +bulan.get(0);
            }
        }else if (this.kode.equals("SPP-XII")) {
            if (bulan.size()>1) {
                namaByr = "SPP KELAS XII BULAN " +bulan.get(0)+" - "+bulan.get(bulan.size()-1);
            }else{
                namaByr = "SPP KELAS XII BULAN " +bulan.get(0);
            }
        }else if (this.kode.equals("SPP-XIII")) {
            if (bulan.size()>1) {
                namaByr = "SPP KELAS XIII BULAN " +bulan.get(0)+" - "+bulan.get(bulan.size()-1);
            }else{
                namaByr = "SPP KELAS XIII BULAN " +bulan.get(0);
            }
        }else{
            namaByr = this.nama;
        }
        return namaByr;
    }
    
    public int getNominal(){
        int jmlNominal = 0;
        if (this.kode.equals("SPP-X")) {
            if (bulan.size()>0) {
                jmlNominal = rentangBulan(bulan.get(0), bulan.get(bulan.size()-1)) * this.nominal;
                jml_blnBayar = rentangBulan(bulan.get(0), bulan.get(bulan.size()-1));
//                jmlNominal = bulan.size() * this.nominal;
            }else{
                jmlNominal = this.nominal;
            }
        }else if (this.kode.equals("SPP-XI")) {
            if (bulan.size()>0) {
                jmlNominal = rentangBulan(bulan.get(0), bulan.get(bulan.size()-1)) * this.nominal;
                jml_blnBayar = rentangBulan(bulan.get(0), bulan.get(bulan.size()-1));
//                jmlNominal = bulan.size() * this.nominal;
            }else{
                jmlNominal = this.nominal;
            }
        }else if (this.kode.equals("SPP-XII")) {
            if (bulan.size()>0) {
                jmlNominal = rentangBulan(bulan.get(0), bulan.get(bulan.size()-1)) * this.nominal;
                jml_blnBayar = rentangBulan(bulan.get(0), bulan.get(bulan.size()-1));
//                jmlNominal = bulan.size() * this.nominal;
            }else{
                jmlNominal = this.nominal;
            }
        }else if (this.kode.equals("SPP-XIII")) {
            if (bulan.size()>0) {
                jmlNominal = rentangBulan(bulan.get(0), bulan.get(bulan.size()-1)) * this.nominal;
                jml_blnBayar = rentangBulan(bulan.get(0), bulan.get(bulan.size()-1));
//                jmlNominal = bulan.size() * this.nominal;
            }else{
                jmlNominal = this.nominal;
            }
        }else{
            jmlNominal = this.nominal;
        }
        return jmlNominal;
    }
    
    public int rentangBulan(String blnAwal, String blnAkhir){
        int rentang = 0, i = 0,j = 0;
        for (int k = 0; k < this.bulan_lengkap.length; k++) {
            if (bulan_lengkap[k].equals(blnAwal)) {
                i=k;
            }
        }
        for (int k = 0; k < this.bulan_lengkap.length; k++) {
            if (bulan_lengkap[k].equals(blnAkhir)) {
                j=k;
            }
        }
        rentang = (j-i)+1;
        return rentang;
    }
}
