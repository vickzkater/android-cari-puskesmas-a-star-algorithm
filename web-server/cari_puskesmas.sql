-- phpMyAdmin SQL Dump
-- version 4.7.3
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Dec 20, 2017 at 01:42 PM
-- Server version: 5.6.36-cll-lve
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `kinidite_android`
--

-- --------------------------------------------------------

--
-- Table structure for table `bagian`
--

CREATE TABLE IF NOT EXISTS `bagian` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nama` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nama` (`nama`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `bagian`
--

INSERT INTO `bagian` (`id`, `nama`) VALUES
(4, 'JAKARTA BARAT'),
(2, 'JAKARTA PUSAT'),
(5, 'JAKARTA SELATAN'),
(3, 'JAKARTA TIMUR'),
(1, 'JAKARTA UTARA');

-- --------------------------------------------------------

--
-- Table structure for table `puskesmas`
--

CREATE TABLE IF NOT EXISTS `puskesmas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bagian` int(11) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `alamat` text,
  `telp` varchar(50) DEFAULT NULL,
  `lat` varchar(50) NOT NULL,
  `lng` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `puskesmas`
--

INSERT INTO `puskesmas` (`id`, `bagian`, `nama`, `alamat`, `telp`, `lat`, `lng`) VALUES
(1, 1, 'Puskesmas Kelurahan Pademangan Timur Kecamatan Pademangan', 'Jl Pademangan II Gg 22 No10', '(021) 64710433', '-6.136822', '106.843689'),
(2, 1, 'Puskesmas Kelurahan Ancol Kecamatan Pademangan', 'Jl Ancol Barat No 7', '(021) 6296483', '-6.120989', '106.814768'),
(3, 1, 'Puskesmas Kalibaru Kecamatan Cilincing', 'Jl Kalibaru Bar V No 80', '(021) 4835305', '-6.101866', '106.929061'),
(4, 1, 'Puskesmas Kelurahan Sukapura Kecamatan Cilincing', 'Jl Tipar Cakung No 1', '(021) 44834952', '-6.148409', '106.924492'),
(5, 1, 'Puskesmas Kelapa Gading + RB Kecamatan Kelapa Gading', 'Jl Pelepah Elok HF 7', '(021) 45853300', '-6.155651', '106.904561'),
(6, 1, 'Puskesmas Kelapa Gading Timur Kecamatan Kelapa Gading', 'Komplek Dep Perdagangan No 50', '(021) 45845828', '-6.177033', '106.906422'),
(7, 1, 'Puskesmas Tugu Selatan Kecamatan Koja', 'Jl Bendungan Melayu Selatan Rt 001/05', '(021) 43908519', '-6.135371', '106.909604'),
(8, 1, 'Puskesmas Lagoa Kecamatan Koja', 'Jl Menteng No 1', '(021) 4302114', '-6.112947', '106.911079'),
(9, 1, 'Puskesmas Kelurahan Kebon Bawang I Kecamatan Tanjung Priok', 'Jl Swasembada Barat VII No2', '(021) 43905320', '-6.124130', '106.885346'),
(10, 1, 'Puskesmas Warakas Kecamatan Tanjung Priok', 'Jl Warakas IX Gg 13 No 22', '(021) 4308865', '-6.120671', '106.875497'),
(11, 1, 'Puskesmas Penjaringan I Kecamatan Penjaringan', 'Jl Pluit Selatan No 2', '(021) 66696374', '-6.125516', '106.800668'),
(12, 1, 'Puskesmas Penjaringan II Kecamatan Penjaringan', 'Jl Rawa Bebek Pasar Royal Rt4/10', '(021) 66696407', '-6.131874', '106.801199'),
(13, 2, 'Puskesmas Cempaka Putih Kecamatan Cempaka Putih', 'Jl Rawasari Raya Rawa Kerbo', '(021) 4219548', '-6.184599', '106.872295'),
(14, 2, 'Puskesmas Rawasari Kecamatan Cempaka Putih', 'Jl Pramuka Sari No1', '(021) 4256428', '-6.189924', '106.869509'),
(15, 2, 'Puskesmas Gambir + RB Kecamatan Gambir', 'Jl Tanah Abang I/10', '(021) 3810051', '-6.170781', '106.818902'),
(16, 2, 'Puskesmas Duri Pulo Kecamatan Gambir', 'Jl Taman Tanah Abang III', '(021) 6338341', '-6.162597', '106.809932'),
(17, 2, 'Puskesmas Johar Baru I Kecamatan Johar Baru', 'Jl Mardani Raya No 36', '(021) 4256443', '-6.183341', '106.862144'),
(18, 2, 'Puskesmas Galur Kecamatan Johar baru', 'Jl Kamp Rawa Tengah Gg IX', '(021) 4256442', '-6.177374', '106.857035'),
(19, 2, 'Puskesmas Serdang Kecamatan Kemayoran', 'Jl Eka V', '(021) 4256429', '-6.161557', '106.860303'),
(20, 2, 'Puskesmas Cempaka Baru Kecamatan Kemayoran', 'Jl Cempaka Baru Tengah', '(021) 4229103', '-6.171837', '106.859868'),
(21, 2, 'Puskesmas Menteng Kecamatan Menteng', 'Jl Pegangsaan Barat No 14', '(021) 335836', '-6.199475', '106.841133'),
(22, 2, 'Puskesmas Pegangsaan Kecamatan Menteng', 'Jl Tambak No 28', '(021) 31934455', '-6.206633', '106.848247'),
(23, 2, 'Puskesmas Mangga Dua Sel Kecamatan Sawah besar', 'Jl Mangga Dua Dalam/1', '(021) 6256101', '-6.137983', '106.827303'),
(24, 2, 'Puskesmas Pasar Baru Kecamatan Sawah Besar', 'Jl Krekot Bunder Raya No 10', '(021) 3440087', '-6.160369', '106.832186'),
(25, 2, 'Puskesmas Senen + RB Kecamatan Senen', 'Jl Kramat VII / 31', '(021) 3145194', '-6.189825', '106.842826'),
(26, 2, 'Puskesmas Bungur Kecamatan Senen', 'Jl Bungur Besar IX No 20', '(021) 4215473', '-6.169081', '106.844466'),
(27, 2, 'Puskesmas Kampung Bali Kecamatan Tanah Abang', 'Jl Kampung Bali Gg 23', '(021) 3915071', '-6.185712', '106.817140'),
(28, 2, 'Puskesmas Bend Hilir Kecamatan Tanah Abang', 'Jl Danau Toba Blok A/1', '(021) 5732767', '-6.206649', '106.808445'),
(29, 3, 'Puskesmas Cakung + RB Kecamatan Cakung', 'Jl Raya Bekasi Km 18', '(021) 4608446', '-6.197965', '106.905231'),
(30, 3, 'Puskesmas Cakung Barat Kecamatan Cakung', 'Jl Tipar Cakung Gg Impres Rt 01/07', '(021) 46829709', '-6.172429', '106.934920'),
(31, 3, 'Puskesmas Cipayung Kecamatan Cipayung', 'Jl Budi Murni Cipayung', '(021) 99092547', '-6.333246', '106.894698'),
(32, 3, 'Puskesmas Bambu Apus II Kecamatan Cipayung', 'Jl Mini III', '(021) 84995372', '-6.306620', '106.906974'),
(33, 3, 'Puskesmas Ciracas Kecamatan Ciracas', 'Jl Pt Centex', '(021) 8708800', '-6.326713', '106.876364'),
(34, 3, 'Puskesmas Cibubur Kecamatan Ciracas', 'Jl Mesjid fathul Ghoful', '(021) 87711043', '-6.355966', '106.879056'),
(35, 3, 'Puskesmas Duren sawit Kecamatan Duren Sawit', 'Jl Haji Dogol No 15A', '(021) 8660165', '-6.242005', '106.911082'),
(36, 3, 'Puskesmas Klender II Kecamatan Duren Sawit', 'Jl Bulak Timur I No 8', '(021) 86603350', '-6.216359', '106.907823'),
(37, 3, 'Puskesmas Bidara Cina I Kecamatan Jatinegara', 'Jl Otista No 82', '(021) 85903677', '-6.238719', '106.868070'),
(38, 3, 'Puskesmas Bidara Cina II Kecamatan Jatinegara', 'Jl Tanjung Lekong Rt 015/07', '(021) 85917587', '-6.241487', '106.865518'),
(39, 3, 'Puskesmas Kecamatan Kramat Jati Kecamatan Kramat Jati', 'Jl Kerja Bakti No 30', '(021) 8004381', '-6.277767', '106.870184'),
(40, 3, 'Puskesmas Balai Kambang Kecamatan Kramat Jati', 'Jl Gardu Condet', '(021) 31720211', '-6.283578', '106.851624'),
(41, 3, 'Puskesmas Makassar Kecamatan Makasar', 'Jl Diklat Depnaker No 37', '(021) 8003308', '-6.282298', '106.878156'),
(42, 3, 'Puskesmas Halim II Kecamatan Makasar', 'Jl Halim Golf', '(021) 8808641', '-6.267399', '106.878000'),
(43, 3, 'Puskesmas Matraman Kecamatan Matraman', 'Jl Kayumanis Timur No 4', '(021) 22986382', '-6.197622', '106.861898'),
(44, 3, 'Puskesmas Pisangan Baru Kecamatan Matraman', 'Jl Pisangan Baru Timur No 12', '(021) 8507756', '-6.214046', '106.872395'),
(45, 3, 'Puskesmas Pasar Rebo Kecamatan Pasar Rebo', 'Jl Raya Kalisari', '(021) 8720053', '-6.339338', '106.861421'),
(46, 3, 'Puskesmas Cijantung Kecamatan Pasar Rebo', 'Jl Pertengahan', '(021) 8705354', '-6.324852', '106.856443'),
(47, 3, 'Puskesmas Jati I Kecamatan Pulo Gadung', 'Jl Pulo Asem IV', '(021) 47868535', '-6.192436', '106.892962'),
(48, 3, 'Puskesmas Rawamangun Kecamatan Pulo Gadung', 'Jl Jeruk No 3', '(021) 47864411', '-6.197174', '106.888306'),
(49, 4, 'Puskesmas Cengkareng Kecamatan Cengkareng', 'Jl Kamal Raya', '(021) 29038167', '-6.144376', '106.728101'),
(50, 4, 'Puskesmas Rawa Buaya Kecamatan Cengkareng', 'Jl Bojong Raya Rt 002/04', '(021) 58300444', '-6.164967', '106.732816'),
(51, 4, 'Puskesmas Grogol I Kecamatan Grogol Petamburan', 'Jl Dr Nurdin I/35', '(021) 56943089', '-6.160167', '106.795718'),
(52, 4, 'Puskesmas Grogol II Kecamatan Grogol Petamburan', 'Jl Rawa Bahagia I/32', '(021) 56971251', '-6.163533', '106.797845'),
(53, 4, 'Puskesmas Kamal II Kecamatan Kali Deres', 'Jl Kampung Belakang No 54', '(021) 54390576', '-6.101672', '106.697269'),
(54, 4, 'Puskesmas Pegadungan II Kecamatan Kali Deres', 'Jl Tanjung Pura Rt 006/05', '(021) 54392924', '-6.130090', '106.696460'),
(55, 4, 'Puskesmas Kebon Jeruk Kecamatan Kebun Jeruk', 'Jl Karmel Raya Rt 02/04', '(021) 53679240', '-6.189955', '106.778958'),
(56, 4, 'Puskesmas Duri Kepa Kecamatan Kebun Jeruk', 'Jl Angsana Raya No 1', '(021) 56944087', '-6.180553', '106.775498'),
(57, 4, 'Puskesmas Kembangan Kecamatan Kembangan', 'Kembangan Utara Rt5/002', '(021) 5870834', '-6.181754', '106.741789'),
(58, 4, 'Puskesmas Joglo I Kecamatan Kembangan', 'Jl Komp Perumahan DKI Joglo', '(021) 5840451', '-6.220906', '106.731529'),
(59, 4, 'Puskesmas Pal Merah Kecamatan Pal Merah', 'Jl Palmerah Barat 120', '(021) 5482431', '-6.208265', '106.788829'),
(60, 4, 'Puskesmas Jati Pulo I Kecamatan Pal Merah', 'Jl Waru No 1 A', '(021) 56940307', '-6.312368', '106.868369'),
(61, 4, 'Puskesmas Taman Sari Kecamatan Taman Sari', 'Jl Ubi No 182', '(021) 6297752', '-6.142019', '106.818867'),
(62, 4, 'Puskesmas Keagungan Kecamatan Taman Sari', 'Jl Kebon Sayur I No 6', '(021) 6281130', '-6.153994', '106.813003'),
(63, 4, 'Puskesmas tambora Kecamatan Tambora', 'Jl Krendang Utara No 4', '(021) 6313651', '-6.148146', '106.804628'),
(64, 4, 'Puskesmas Duri Utara Kecamatan Tambora', 'Jl Duri Utara II No 24', '(021) 6301972', '-6.152702', '106.805914'),
(65, 5, 'Puskesmas Cilandak Kecamatan Cilandak', 'Jl Komp BNI 46 No 57', '(021) 7694279', '-6.286655', '106.793266'),
(66, 5, 'Puskesmas Cilandak Barat Kecamatan Cilandak', 'Jl Seroja Rt4/7', '(021) 7663586', '-6.285773', '106.805368'),
(67, 5, 'Puskesmas Jagakarsa Kecamatan Jagakarsa', 'Jl Batu No 33', '(021) 7874051', '-6.351400', '106.813893'),
(68, 5, 'Puskesmas Ciganjur Kecamatan Jagakarsa', 'Jl Pasir Rt1/6', '(021) 7873930', '-6.336780', '106.804174'),
(69, 5, 'Puskesmas Kebayoran Baru Kecamatan Kebayoran Baru', 'Jl Iskandarsyah Raya Rt 005/03', '(021) 7220274', '-6.247296', '106.805568'),
(70, 5, 'Puskesmas Cipete Utara Kecamatan Kebayoran Baru', 'Jl KH Moh Naim I No 74', '(021) 7237962', '-6.253717', '106.807008'),
(71, 5, 'Puskesmas Kebayoran Lama Kecamatan Kebayoran Lama', 'Jl Ciputat Raya Keb Lama Rt 005/03', '(021) 7245439', '-6.249733', '106.778397'),
(72, 5, 'Puskesmas Cipulir I Kecamatan Kebayoran Lama', 'Jl Cipulir I No 33 Keb Lama', '(021) 7257810', '-6.235753', '106.775454'),
(73, 5, 'Puskesmas Mampang Prapatan Kecamatan Mampang Prapatan', 'Jl Tegal Parang Utara II No 1', '(021) 79192187', '-6.245101', '106.834818'),
(74, 5, 'Puskesmas Bangka Kecamatan Mampang Prapatan', 'Jl Kemang Dalam III', '(021) 7196578', '-6.271188', '106.819914'),
(75, 5, 'Puskesmas Pancoran Kecamatan Pancoran', 'Jl Potlot II/6 Rt 001/06', '(021) 7974024', '-6.255045', '106.839242'),
(76, 5, 'Puskesmas Cikoko Kecamatan Pancoran', 'Jl Raya Cikoko Timur Rt004/01', '(021) 7946051', '-6.246317', '106.859668'),
(77, 5, 'Puskesmas Kebagusan Kecamatan Pasar Minggu', 'Jl Kebagusan Raya Gg Puskesmas', '(021) 7803117', '-6.297838', '106.827057'),
(78, 5, 'Puskesmas Pasar Minggu + RB Kecamatan Pasar Minggu', 'Jl Raya Kebagusan Rt 004/04', '(021) 7811265', '-6.320773', '106.825696'),
(79, 5, 'Puskesmas Pasanggrahan Kecamatan Pesanggrahan', 'Jl Palem 8', '(021) 7356087', '-6.223772', '106.759868'),
(80, 5, 'Puskesmas Bintaro Kecamatan Pesanggrahan', 'Jl Mufakat Rt001/03', '(021) 7356807', '-6.273023', '106.764392'),
(81, 5, 'Puskesmas Setia Budi Kecamatan Setiabudi', 'Jl Halimun No 13 ', '(021) 8295529', '-6.206981', '106.833625'),
(82, 5, 'Puskesmas Karet Kecamatan Setiabudi', 'Jl Komando Ujung Rt 010/02', '(021) 5264352', '-6.214110', '106.824332'),
(83, 5, 'Puskesmas Tebet Kecamatan Tebet', 'Jl Tebet Timur 2 Rt 006/005', '(021) 8350632', '-6.235437', '106.856753'),
(84, 5, 'Puskesmas Bukit Duri Kecamatan Tebet', 'Jl Bukit Duri Tjk Dalam 1 No 51', '(021) 8352556', '-6.219863', '106.857328'),
(85, 1, 'Puskesmas Kelurahan Sungai Bambu', 'Jl. Sungai Bambu 4, RT.6/RW.8, Sungai Bambu, Tj. Priok, Kota Jkt Utara, Daerah Khusus Ibukota Jakarta 14330', '(021) 43908280', '-6.1287312', '106.8839904'),
(86, 1, 'Puskesmas Kelurahan Papanggo I', 'Jl. Papanggo 2B, RT.6/RW.3, Papanggo, Tj. Priok, Kota Jkt Utara, Daerah Khusus Ibukota Jakarta 14340', '(021) 65834090', '-6.1284639', '106.8736024'),
(87, 1, 'Puskesmas Kelurahan Papanggo II', 'Jl. Bisma Utara, RT.12/RW.9, Sunter Jaya, Tj. Priok, Kota Jkt Utara, Daerah Khusus Ibukota Jakarta 14340', '(021) 65832994', '-6.1299021', '106.8699715'),
(88, 1, 'Puskesmas Kelurahan Marunda', 'Jl. Marunda Baru 2 No.25, RT.6/RW.5, Marunda, Cilincing, Kota Jkt Utara, Daerah Khusus Ibukota Jakarta 14150', '(021) 44852585', '-6.1246736', '106.9561978'),
(89, 1, 'Puskesmas Kelurahan Semper Barat I', 'Jl. Duren No.49, RT.2/RW.8, Semper Bar., Cilincing, Kota Jkt Utara, Daerah Khusus Ibukota Jakarta 14130', ' (021) 4412889', '-6.1246725', '106.9233668'),
(90, 1, 'Puskesmas Kelurahan Semper Barat II', 'Jl. Tipar Cakung, RT.6/RW.4, Semper Bar., Cilincing, Kota Jkt Utara, Daerah Khusus Ibukota Jakarta 14140', '(021) 4410227', '-6.1246725', '106.9233668'),
(91, 1, 'Puskesmas Kelurahan Semper Barat III', 'Jl. Pepaya V, RT.4/RW.16, Semper Bar., Cilincing, Kota Jkt Utara, Daerah Khusus Ibukota Jakarta 14130', '(021) 4403882', '-6.1246725', '106.9233668'),
(92, 1, 'Puskesmas Kelurahan Kalibaru Barat', 'Jl. Kalibaru Bar. V No.80, RT.13/RW.2, Kali Baru, Cilincing, Kota Jkt Utara, Daerah Khusus Ibukota Jakarta 14110', '', '-6.1019158', '106.9279814'),
(93, 1, 'Puskesmas Kelurahan Kalibaru Timur', 'Jl. Kalibaru Timur No.16, RT.4/RW.15, Kali Baru, Cilincing, Kota Jkt Utara, Daerah Khusus Ibukota Jakarta 14110', '(021) 4835305', '-6.1018282', '106.9263135'),
(94, 1, 'Puskesmas Kelurahan Rorotan', 'jalan Rorotan IX, Kec. Clincing, Jakarta Utara, DKI Jakarta', '(021) 44850175', '-6.1574768', '106.9545784'),
(95, 1, 'Puskesmas Kelurahan Kapuk Muara', 'Jl. SMP 122, RT.1/RW.3, Kapuk, Jakarta Utara, Kota Jkt Utara, Daerah Khusus Ibukota Jakarta 14460', '(021) 98284925', '-6.1359408', '106.7616099');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
