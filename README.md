# LendBook-Project

nama aplikasi = lendbook

dekripsi singkat = lendbook aplikasi meminjam buku berbasis java dan javafx yang memungkinkan si pengguna(member) untuk : a) melihat daftar buku b) melihat detail buku c) minjem buku d) melihat riwayat peminjaman/yang sudah dibaca e) kasih rating ke buku

cara jalanin aplikasi : syarat awal : a) java jdk 17+ b) javafx c) ide java cara jalanin file : mainapp/Main.java

daftar class dan fungsi : a) Book = Menyimpan data buku (judul, author, deskripsi, rating, status tersedia) b) User = Menyimpan data user / member c) Loan = Menyimpan data peminjaman buku d) Catalog = Menyimpan kumpulan buku e) Category = Representasi kategori buku f) Fine = Mengelola denda keterlambatan g) LibraryService = Logic utama peminjaman, pengembalian, history, dan data buku h) AuthenticationService = Mengelola login user i) FileStorageService = Membaca & menulis data ke file CSV j) MainLayout = Mengatur perpindahan antar tampilan k) HomeView = Tampilan utama setelah login l) BookView = Menampilkan daftar semua buku m) BookDetailView = Menampilkan detail buku & tombol loan n) LoanView = Menampilkan buku yang sedang dipinjam o) HistoryView = Menampilkan riwayat peminjaman p) main = entry point aplikasi

konsep oop a) encapsulation = data di class book, user, loan menggunakan private agar tidak bisa diakses oleh semua class dan bisa diakses melalui getter & setter b) abstraction = memanggil method tanpa tau cara data disimpan. data csv disembunyikan dalam filestorageservice c) inheritance = menggunakan kembali kode seperti yang ada di javafx "class BookDetailView extends BorderPane" d) polymorphism = Pemanggilan method yang sama dengan objek berbeda (misalnya showView() di MainLayout)
