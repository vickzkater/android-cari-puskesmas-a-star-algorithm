![Cari Puskesmas](https://github.com/vickzkater/android-cari-puskesmas-a-star-algorithm/raw/master/CariPuskesmas/app/src/main/res/mipmap-hdpi/ic_launcher.png)

# Cari Puskesmas - Aplikasi Android
Aplikasi berbasis Android untuk mencari lokasi dan rute terpendek menuju Puskesmas terdekat dari posisi GPS perangkat dengan algoritma A-Star

Dibuat oleh [Vicky Budiman](https://id.linkedin.com/in/vickybudiman) pada November 2017 dan diperbarui Juni 2019

## Abstrak
Menurut Badan Pusat Statistik Provinsi DKI Jakarta, pertumbuhan penduduk di DKI Jakarta cukup signifikan tiap tahunnya. Salah satu aspek yang perlu diperhatikan untuk kesejahteraan masyarakat oleh pemerintah adalah kesehatan. Pemerintah telah menyediakan fasilitas kesehatan untuk masyarakat, salah satunya adalah Puskesmas. Namun keterbatasan informasi mengenai Puskesmas yang dimiliki oleh masyarakat menjadi kendala, seperti ketidaktahuan lokasi Puskesmas terdekat dan jalan yang harus ditempuh menuju Puskesmas. Penelitian ini menghasilkan aplikasi pencarian Puskesmas terdekat dan rute terpendek menuju Puskesmas dengan menerapkan algoritma A-Star. Algoritma A-Star merupakan salah satu algoritma pencarian graph terbaik yang mampu menemukan jalur dengan biaya pengeluaran/jarak paling sedikit dari titik permulaan yang diberikan sampai ke titik tujuan yang diharapkan. Setelah pengujian sebanyak 50 kali untuk menentukan rute terpendek antara algoritma A-Star dengan Google Maps menghasilkan 44 kali algoritma A-Star berhasil menampilkan rute terpendek dan 6 kali algoritma A-Star gagal menampilkan rute terpendek. Tingkat keberhasilan algoritma A-Star mencapai 80% sehingga dapat disimpulkan algoritma A-star berhasil menunjukkan jarak terpendek menuju Puskesmas tujuan.

## Jurnal Ilmiah
[APLIKASI BERBASIS ANDROID UNTUK MENCARI LOKASI PUSKESMAS TERDEKAT DENGAN ALGORITMA A-STAR DI PROVINSI DKI JAKARTA](https://jurnal.umj.ac.id/index.php/just-it/article/view/2572)

## PHP Version Support

- [x] PHP 5.6.x
- [ ] PHP 7.3.x (not tested)

## Environment

- [x] Android JAVA
- [x] PHP
- [x] Google Maps API

## Cara Mendapatkan API Credentials Google Maps
* Masuk ke [Google Cloud Console](https://console.cloud.google.com/) dengan menggunakan akun Gmail
* Buat project baru agar bisa mengaktifkan API Google Maps dengan [klik di sini](https://console.cloud.google.com/projectcreate)
* Beri nama project tersebut (misal: Cari Puskesmas) lalu tekan tombol `Create`, tunggu beberapa saat sampai project berhasil dibuat
* Setelah project berhasil dibuat, pilih `APIs & Services` > `Dashboard` pada bagian menu di samping kiri (sidebar menu) - bila tidak ada sidebar menu, klik `Navigation Menu` berupa ikon hamburger (3 baris garis mendatar) di pojok kiri atas
* Selanjutnya klik tombol `+ ENABLE APIS AND SERVICES` kemudian akan diarahkan ke halaman API Library
* Pilih `Maps SDK for Android` lalu `ENABLE` 
* Pilih `APIs` di sidebar menu, lalu di bagian `Additional APIs` pilih `Directions API` dan `ENABLE` juga
* Lalu kembali ke menu `APIs & Services` dengan memilih `APIs & Services` > `Credentials` pada bagian menu di samping kiri (sidebar menu)
* Klik tombol `Create credentials` > `API key` > isikan nama API yang diinginkan (misal: Cari Puskesmas) > pilih `SAVE`
* Copy `Key` untuk digunakan sebagai nilai `Google Maps API Key`

## Link Penting untuk Dipelajari
* [Dokumentasi Panduan Google Maps API - Android SDK](https://developers.google.com/maps/documentation/android-sdk/start)
* Untuk mendapatkan `Debug certificate fingerprint` dari aplikasi Android gunakan step-by-step dari [Stack Overflow ini](https://stackoverflow.com/questions/27609442/how-to-get-the-sha-1-fingerprint-certificate-in-android-studio-for-debug-mode/#27639043)

## Cara Menggunakan
* Folder `CariPuskesmas` adalah source code untuk aplikasi Android (client)
* Folder `web-server` adalah source code untuk server PHP
* Buat database terlebih dahulu, lalu jalankan `cari_puskesmas.sql`
* Persiapkan nilai `Key` dari `Cara Mendapatkan API Credentials Google Maps`
* Selanjutnya buka source code Android untuk mengubah beberapa value yang diperlukan
* Ubah nilai **Google Maps API Key** menggunakan nilai `Key` di file **google_maps_api.xml**
* Ubah nilai **"url"** di file **ListActivity.java** dengan URL yang merujuk ke folder `web-server`, misalnya: `http://[YOUR_URL]/index.php?from=`
* Ubah nilai **"url"** di file **RouteActivity.java** dengan URL yang merujuk ke folder `web-server`, misalnya: `http://[YOUR_URL]/index.php?from=`
* Ubah nilai **Google Directions API Key** menggunakan nilai `Key` pada variabel **"googleApiKey"** di file **RouteActivity.java** (function **getDirectionsUrl**)

## Catatan Lain
Data yang diberikan sebagai contoh adalah daftar lokasi Puskesmas di Provinsi DKI Jakarta saja sehingga aplikasi hanya dapat digunakan di wilayah DKI Jakarta saja. Bila ingin digunakan di wilayah lain, dapat dimasukkan data lokasi Puskesmas di daerah lainnya.

## Changelog

1.2 (13/06/2019)
- [x] Update project beserta README

1.1 (20/12/2017)
- [x] Using dependencies:
    *  `compile 'com.android.support:appcompat-v7:26.+'`
    *  `compile 'com.android.support:design:26.+'`
    *  `compile 'com.android.support.constraint:constraint-layout:1.0.2'`
    *  `compile 'com.google.android.gms:play-services-maps:10.2.1'`
    *  `compile 'com.google.android.gms:play-services-location:10.2.1'`
    *  `compile 'com.android.support:support-v4:26.+'`
    *  `compile 'com.mcxiaoke.volley:library-aar:1.0.0'`
    *  `compile 'com.google.maps.android:android-maps-utils:0.5'`
- [x] Using permissions:
    *  `<uses-permission android:name="android.permission.INTERNET" />`
    *  `<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />`
    *  `<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />`
    *  `<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />`

1.0 (21/11/2017)
- [x] Initialize Project using Android SDK 26
