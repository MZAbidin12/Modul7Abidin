/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package uts_pemograman_visual;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author User
 */
public class MainController implements Initializable {
    private Buku selectedBuku;
    
    
    @FXML private Button btnAdd;
    @FXML private Button btnDelete;
    @FXML private Button btnUpdate;
    
    @FXML private TextField txtkdbuku;
    @FXML private TextField txtjudul;
    @FXML private TextField txtpengarang;
    @FXML private TextField txtpenerbit;
    @FXML private TextField txttahun;
    @FXML private TextField txtedisi;
    
    
    @FXML
    private TableColumn<Buku, String> colkdbuku;

    @FXML
    private TableColumn<Buku, String> coljudul;

    @FXML
    private TableColumn<Buku, String> colpengarang;
    
    @FXML
    private TableColumn<Buku, String> colpenerbit;
    
    @FXML
    private TableColumn<Buku, String> coltahun;
    
    @FXML
    private TableColumn<Buku, String> coledisi;
    
    @FXML
    private TableView<Buku> tblview;
    
    private void loadDataBuku() {
        
        ObservableList<Buku> bukuList = BukuDAO.getBuku();
        
        tblview.setItems(bukuList);
    }
    
    private void clearFields(){
        txtkdbuku.clear();
        txtjudul.clear();
        txtpengarang.clear();
        txtpenerbit.clear();
        txttahun.clear();
        txtedisi.clear();
        selectedBuku = null;
    }
    
    private void showAlert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void showSuccessAlert() {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Sukses");
    alert.setHeaderText("Data berhasil ditambahkan");
    alert.setContentText("Buku baru berhasil ditambahkan ke dalam sistem.");
    alert.showAndWait();
    }
    
    private void showUpdateAlert() {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Sukses");
    alert.setHeaderText("Data berhasil diperbaharui");
    alert.showAndWait();
    }
    
    private void selectBuku(Buku buku){
        if (buku != null){
            selectedBuku = buku;
            txtkdbuku.setText(buku.getKodebuku());
            txtjudul.setText(buku.getJudul());
            txtpengarang.setText(buku.getPengarang());
            txtpenerbit.setText(buku.getPenerbit());
            txttahun.setText(buku.getTahun());
            txtedisi.setText(buku.getEdisi());
        }
    }
    
    @FXML
    private void addBuku() {
        String kodebuku = txtkdbuku.getText();
        String judul = txtjudul.getText();
        String pengarang = txtpengarang.getText();
        String penerbit = txtpenerbit.getText();
        String tahun = txttahun.getText();
        String edisi = txtedisi.getText();
        
        if (kodebuku.isEmpty() || judul.isEmpty() || pengarang.isEmpty() || penerbit.isEmpty() || tahun.isEmpty() || edisi.isEmpty()){
            showAlert("WARNING", "Field tidak boleh kosong!");
            return;
        }
        
        Buku newBuku = new Buku(kodebuku, judul, pengarang, penerbit, tahun, edisi);
        BukuDAO.addBuku(newBuku);
        showSuccessAlert();
        loadDataBuku();
        clearFields();
    }
    
    @FXML
    private void updateBuku() {
        if (selectedBuku == null) {
            showAlert("WARNING", "Tidak ada buku yang dipilih!");
            return;
        }
        
        String kodebuku = txtkdbuku.getText();
        String judul = txtjudul.getText();
        String pengarang = txtpengarang.getText();
        String penerbit = txtpenerbit.getText();
        String tahun = txttahun.getText();
        String edisi = txtedisi.getText();
        
        if (kodebuku.isEmpty() || judul.isEmpty() || pengarang.isEmpty() || penerbit.isEmpty() || tahun.isEmpty() || edisi.isEmpty()){
            showAlert("WARNING", "Field tidak boleh ada yang kosong!");
            return;
        }
        
        selectedBuku.setKodebuku(kodebuku);
        selectedBuku.setJudul(judul);
        selectedBuku.setPengarang(pengarang);
        selectedBuku.setPenerbit(penerbit);
        selectedBuku.setTahun(tahun);
        selectedBuku.setEdisi(edisi);
        
        BukuDAO.updateBuku(selectedBuku);
        showUpdateAlert();
        loadDataBuku();
        clearFields();
    }
    
    @FXML
    private void deleteBuku(){
        if (selectedBuku == null){
            showAlert("WARNING", "Tidak ada buku yang dipilih!");
            return;
        }
        
            // Tampilkan dialog konfirmasi
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Konfirmasi Penghapusan");
            alert.setHeaderText("Apakah Anda yakin ingin menghapus data?");
            alert.setContentText("Data yang dihapus tidak dapat dipulihkan.");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Hapus dari database hanya jika pengguna klik OK
                BukuDAO.deleteBuku(selectedBuku.getKodebuku());

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Sukses");
                successAlert.setHeaderText("Data berhasil dihapus");
                successAlert.showAndWait();

                loadDataBuku();
                clearFields();
            } else {
                // Tampilkan alert pembatalan
                Alert cancelAlert = new Alert(Alert.AlertType.INFORMATION);
                cancelAlert.setTitle("Batal");
                cancelAlert.setHeaderText("Penghapusan dibatalkan");
                cancelAlert.showAndWait();
            }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colkdbuku.setCellValueFactory(new PropertyValueFactory<Buku, String>("kodebuku"));
        coljudul.setCellValueFactory(new PropertyValueFactory<Buku, String>("judul"));
        colpengarang.setCellValueFactory(new PropertyValueFactory<Buku, String>("pengarang"));
        colpenerbit.setCellValueFactory(new PropertyValueFactory<Buku, String>("penerbit"));
        coltahun.setCellValueFactory(new PropertyValueFactory<Buku, String>("tahun"));
        coledisi.setCellValueFactory(new PropertyValueFactory<Buku, String>("edisi"));
        
        
        loadDataBuku();
        
        tblview.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> selectBuku(newValue)
        );
        
//      VALIDASI

        // txtjudul: hanya bisa diisi jika txtkdbuku sudah diisi
        txtjudul.setOnKeyTyped(event -> {
            if (txtkdbuku.getText().trim().isEmpty()) {
                event.consume(); // blok input
                showAlert("Input Tidak Valid", "Silakan isi Kode Buku terlebih dahulu!");
                txtkdbuku.requestFocus();
            }
        });
        txtjudul.setOnMouseClicked(event -> {
            if (txtkdbuku.getText().trim().isEmpty()) {
                showAlert("Input Tidak Valid", "Silakan isi Kode Buku terlebih dahulu!");
                txtkdbuku.requestFocus();
            }
        });

        // txtpengarang: hanya bisa diisi jika txtjudul sudah diisi
        txtpengarang.setOnKeyTyped(event -> {
            if (txtjudul.getText().trim().isEmpty()) {
                event.consume();
                showAlert("Input Tidak Valid", "Silakan isi Judul Buku terlebih dahulu!");
                txtjudul.requestFocus();
            }
        });
        txtpengarang.setOnMouseClicked(event -> {
            if (txtjudul.getText().trim().isEmpty()) {
                showAlert("Input Tidak Valid", "Silakan isi Judul Buku terlebih dahulu!");
                txtjudul.requestFocus();
            }
        });
        
        // Saat user klik kolom penerbit
       txtpenerbit.setOnMouseClicked(event -> {
           if (txtpengarang.getText().trim().length() < 5) {
               showAlert("Validasi", "Isi nama pengarang minimal 5 karakter terlebih dahulu!");
               txtpengarang.requestFocus();
           }
       });

       // Saat user ketik di kolom penerbit
       txtpenerbit.setOnKeyTyped(event -> {
           if (txtpengarang.getText().trim().length() < 5) {
               event.consume(); // mencegah input
               showAlert("Validasi", "Nama pengarang harus minimal 5 karakter!");
               txtpengarang.requestFocus();
           }
       });

        // txttahun: hanya bisa diisi jika txtpenerbit sudah diisi
        txttahun.setOnKeyTyped(event -> {
            if (txtpenerbit.getText().trim().isEmpty()) {
                event.consume();
                showAlert("Input Tidak Valid", "Silakan isi Nama Penerbit terlebih dahulu!");
                txtpenerbit.requestFocus();
            }
        });
        txttahun.setOnMouseClicked(event -> {
            if (txtpenerbit.getText().trim().isEmpty()) {
                showAlert("Input Tidak Valid", "Silakan isi Nama Penerbit terlebih dahulu!");
                txtpenerbit.requestFocus();
            }
        });

        // txtedisi: hanya bisa diisi jika txttahun sudah diisi
        txtedisi.setOnKeyTyped(event -> {
            if (txttahun.getText().trim().isEmpty()) {
                event.consume();
                showAlert("Input Tidak Valid", "Silakan isi Tahun terlebih dahulu!");
                txttahun.requestFocus();
            }
        });
        txtedisi.setOnMouseClicked(event -> {
            if (txttahun.getText().trim().isEmpty()) {
                showAlert("Input Tidak Valid", "Silakan isi Tahun terlebih dahulu!");
                txttahun.requestFocus();
            }
        });

        
        txttahun.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txttahun.setText(newValue.replaceAll("[^\\d]", ""));
                showAlert("Selection Error", "Hanya Bisa Menginputkan Angka!");
                return;
            }
        });
    }  
}
