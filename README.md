# Tugas Kecil 1 Strategi Algoritma IF2211

## Cara Menjalankan Aplikasi

1. Clone repository GitHub:

   ```bash
   git clone https://github.com/Incheon21/Tucil1_13523033.git
   cd Tucil1_13523033
   ```

2. Compile semua file Java:

   ```bash
   javac -d bin src/*.java
   ```

3. Jalankan aplikasi:
   ```bash
   java -cp bin Main
   ```

## Fitur Aplikasi

- Upload file input puzzle yang ingin diselesaikan
- Menampilkan visualisasi board puzzle
- Solve puzzle menggunakan algoritma brute force
- Menyimpan solusi dalam format .txt / gambar (.png)
- Menampilkan waktu eksekusi dan jumlah kasus yang dicoba

## Struktur Project

```
├── bin/            # file-file .class yang telah dikompilasi
├── src/            # file-file source code
├── test/           # file-file testcase
│   ├── input/      # Input test cases
│   └── output/     # Output solutions
└── README.md
```

## Author

13523033 - Alvin Christopher Santausa

## Cara Penggunaan

1. Jalankan aplikasi sesuai langkah di atas
2. Klik tombol "Upload File" untuk memilih file input puzzle
3. Klik tombol "Solve" untuk mencari solusi puzzle
4. Setelah solusi ditemukan, dapat disimpan dalam format .txt atau .png menggunakan tombol yang tersedia

## Format File Input

Format file input terdiri dari:

- Baris pertama: N M P (dimensi puzzle NxM dan jumlah piece P)
- Baris kedua: Tipe kasus (DEFAULT/CUSTOM)
- Jika CUSTOM: N baris berikutnya berisi konfigurasi board
- Selanjutnya: Definisi piece-piece puzzle

Contoh file input dapat dilihat di folder [`test/input`](test/input)
