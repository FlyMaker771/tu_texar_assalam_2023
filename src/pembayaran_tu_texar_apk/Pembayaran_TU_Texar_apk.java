/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pembayaran_tu_texar_apk;

/**
 *
 * @author USER
 */
public class Pembayaran_TU_Texar_apk {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        FormLoading loading = new FormLoading();
//        loading.setVisible(true);
//        loading.setLocationRelativeTo(null);
//        login l = new login();
//        try {
//            for (int i = 0; i <=100; i++) {
//                Thread.sleep(5);
//                loading.textLoading.setText(Integer.toString(i)+"%");
//                loading.loadingbar.setValue(i);
//            }
//            l.setVisible(true);
//            l.setLocationRelativeTo(null);
//            loading.setVisible(false);
//        } catch (Exception e) {
//            
//        }
        siswa_spp s1 = new siswa_spp();
//        s1.insertSppXIII("2005493", 2);
        System.out.println("Sebelum: "+s1.getJmlSppX("2005496"));
        System.out.println(s1.upSppX("2005496", "oktober"));
        System.out.println("Sesudah: "+s1.getJmlSppX("2005496"));
//        siswa_spp s2 = new siswa_spp();
//        s2.insertSppXIII("2005519", 11);
//        siswa_spp s3 = new siswa_spp();
//        s3.insertSppXIII("2005521", 11);
//        siswa_spp s4 = new siswa_spp();
//        s4.insertSppXIII("2005534", 0);
//        siswa_spp s5 = new siswa_spp();
//        s5.insertSppXIII("2005532", 7);
    }
    
}
