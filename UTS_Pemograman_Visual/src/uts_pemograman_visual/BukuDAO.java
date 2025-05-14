/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uts_pemograman_visual;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author User
 */
public class BukuDAO {
    public static ObservableList<Buku> getBuku() {
        ObservableList<Buku> bukuList = FXCollections.observableArrayList();
        String query = "SELECT * FROM buku";
        
        try (
            Connection koneksi = DBConnection.getConnection();
            Statement stmt = koneksi.createStatement();
            ResultSet rs = stmt.executeQuery(query)) {
        while (rs.next()) {
            String kodebuku = rs.getString("kode_buku");
            String judul = rs.getString("judul");
            String pengarang = rs.getString("pengarang");
            String penerbit = rs.getString("penerbit");
            String tahun = rs.getString("tahun_terbit");
            String edisi = rs.getString("edisi");
            
            bukuList.add(new Buku(
                kodebuku,
                judul,
                pengarang,
                penerbit,
                tahun,
                edisi
                ));
            }
            rs.close();
            stmt.close();
            koneksi.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return bukuList;
    }
    
    public static void addBuku(Buku buku){
        String query = "INSERT INTO buku (kode_buku, judul, pengarang, penerbit, tahun_terbit, edisi) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (
            Connection koneksi = DBConnection.getConnection();
            PreparedStatement smt = koneksi.prepareStatement(query)) {
            
            smt.setString(1, buku.getKodebuku());
            smt.setString(2, buku.getJudul());
            smt.setString(3, buku.getPengarang());
            smt.setString(4, buku.getPenerbit());
            smt.setString(5, buku.getTahun());
            smt.setString(6, buku.getEdisi());
            
            smt.executeUpdate();
            
            smt.close();
            koneksi.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void updateBuku(Buku buku) {
        String query = "UPDATE buku SET judul = ?, pengarang = ?, penerbit = ?, tahun_terbit = ?, edisi = ? WHERE kode_buku = ?";
        
        try (
            Connection koneksi = DBConnection.getConnection();
            PreparedStatement smt = koneksi.prepareStatement(query)) {
            
            
            smt.setString(1, buku.getJudul());
            smt.setString(2, buku.getPengarang());
            smt.setString(3, buku.getPenerbit());
            smt.setString(4, buku.getTahun());
            smt.setString(5, buku.getEdisi());
            smt.setString(6, buku.getKodebuku());
            
            smt.executeUpdate();
            
            smt.close();
            koneksi.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void deleteBuku(String kodebuku) {
        String query = "DELETE FROM buku Where kode_buku = ?";
        
        try (
            Connection koneksi = DBConnection.getConnection();
            PreparedStatement smt = koneksi.prepareStatement(query)) {
            
            smt.setString(1, kodebuku);
            
            smt.executeUpdate();
            
            smt.close();
            koneksi.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

