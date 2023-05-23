# Java CRUD dengan GUI

Ini adalah proyek Java yang menunjukkan operasi dasar CRUD (Create, Read, Update, Delete) pada sebuah tabel database menggunakan antarmuka pengguna grafis (GUI). Proyek ini dibangun menggunakan Java Swing untuk komponen GUI dan Maven sebagai alat pembangunan.

## Fitur

- Menampilkan tabel yang menampilkan produk beserta ID, nama, kuantitas, dan harga.
- Menambahkan produk baru ke dalam tabel dengan mengisi kolom yang diperlukan.
- Memperbarui detail produk yang sudah ada dengan memilih baris pada tabel dan mengubah kolom yang sesuai.
- Menghapus produk dengan memilih baris pada tabel dan mengklik tombol hapus.

## Persyaratan

- Java Development Kit (JDK) 8 atau yang lebih tinggi
- Apache Maven

## Memulai

1. Klon repository:

   ```bash
   git clone https://github.com/adzibilal/crud-product-java.git
   
2. Masuk ke direktori proyek:

    ```bash
    cd java-crud-with-gui
    
3. Bangun proyek menggunakan Maven:

    ```bash
    mvn clean install

4. Jalankan aplikasi

    ```bash
    mvn exec:java

## Dependencies

Proyek ini menggunakan dependensi berikut:

- javax.swing - Komponen GUI Java Swing
- mysql-connector-java - MySQL Connector/J untuk koneksi ke database MySQL

## Konfigurasi

Detail koneksi database diatur dalam file src/main/resources/config.properties. Anda dapat memodifikasi file ini untuk memperbarui URL database, username, dan password sesuai dengan konfigurasi database MySQL Anda.

