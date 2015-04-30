CREATE DATABASE IF NOT EXISTS `unitedvision_tvkabel` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `unitedvision_tvkabel`;

DROP TABLE IF EXISTS `kecamatan`;
CREATE TABLE IF NOT EXISTS `kecamatan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nama` varchar(100) NOT NULL,
  `id_kota` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_kota` (`id_kota`,`nama`)
  FOREIGN KEY `kota` (`id_kota`) REFERENCES `kota`(`id`) ON DELETE SET NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=32 ;

DROP TABLE IF EXISTS `kelurahan`;
CREATE TABLE IF NOT EXISTS `kelurahan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nama` varchar(100) NOT NULL,
  `id_kecamatan` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_kecamatan` (`id_kecamatan`,`nama`)
  FOREIGN KEY `kecamatan` (`id_kecamatan`) REFERENCES `kecamatan`(`id`) ON DELETE SET NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=118 ;

DROP TABLE IF EXISTS `kota`;
CREATE TABLE IF NOT EXISTS `kota` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nama` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nama` (`nama`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=16 ;

DROP TABLE IF EXISTS `pegawai`;
CREATE TABLE IF NOT EXISTS `pegawai` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `kode` varchar(50) NOT NULL,
  `status` int(1) NOT NULL,
  `nama` varchar(255) NOT NULL,
  `id_perusahaan` int(11) NOT NULL,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `role` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `kode` (`id_perusahaan`,`kode`),
  UNIQUE KEY `nama` (`id_perusahaan`,`nama`),
  UNIQUE KEY `username` (`username`),
  KEY `id_perusahaan` (`id_perusahaan`)
  FOREIGN KEY `perusahaan` (`id_perusahaan`) REFERENCES `perusahaan`(`id`) ON DELETE SET NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=19 ;

DROP TABLE IF EXISTS `pelanggan`;
CREATE TABLE IF NOT EXISTS `pelanggan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `kode` varchar(100) NOT NULL,
  `nomor_buku` varchar(11) DEFAULT '0',
  `id_perusahaan` int(11) NOT NULL,
  `nama` varchar(255) NOT NULL,
  `profesi` varchar(255) DEFAULT NULL,
  `id_kelurahan` int(11) NOT NULL,
  `lingkungan` int(2) DEFAULT NULL,
  `detail_alamat` varchar(255) DEFAULT NULL,
  `latitude` double DEFAULT '0',
  `longitude` double DEFAULT '0',
  `telepon` varchar(20) DEFAULT NULL,
  `hp` varchar(20) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `status` int(1) NOT NULL,
  `tanggal_mulai` date NOT NULL,
  `jumlah_tv` int(2) NOT NULL,
  `iuran` mediumtext NOT NULL,
  `pembayaran_terakhir` int(11) DEFAULT NULL,
  `tunggakan` int(2) NOT NULL,
  `id_source` int(11),
  PRIMARY KEY (`id`),
  UNIQUE KEY `kode` (`kode`,`id_perusahaan`),
  UNIQUE KEY `nama` (`id_perusahaan`,`nama`),
  UNIQUE KEY `nomor_buku` (`nomor_buku`,`id_perusahaan`),
  KEY `id_kelurahan` (`id_kelurahan`),
  KEY `id_perusahaan` (`id_perusahaan`),
  KEY `pembayaran_terakhir` (`pembayaran_terakhir`)
  FOREIGN KEY `perusahaan` (`id_perusahaan`) REFERENCES `perusahaan`(`id`) ON DELETE SET NULL
  FOREIGN KEY `kelurahan` (`id_kelurahan`) REFERENCES `kelurahan`(`id`) ON DELETE SET NULL
  FOREIGN KEY `source` (`id_source`) REFERENCES `alat`(`id`) ON DELETE SET NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=922 ;

DROP TABLE IF EXISTS `pembayaran`;
CREATE TABLE IF NOT EXISTS `pembayaran` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `kode_referensi` varchar(100) NOT NULL,
  `tanggal_bayar` date NOT NULL,
  `tahun` year(4) NOT NULL,
  `bulan` int(2) NOT NULL,
  `jumlah_bayar` mediumtext NOT NULL,
  `id_pelanggan` int(11) NOT NULL,
  `id_pegawai` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `kode_referensi` (`kode_referensi`),
  UNIQUE KEY `id_pelanggan` (`id_pelanggan`,`tahun`,`bulan`),
  FOREIGN KEY `pelanggan` (`id_pelanggan`) REFERENCES `pelanggan`(`id`) ON DELETE SET NULL
  FOREIGN KEY `pegawai` (`id_pegawai`) REFERENCES `pegawai`(`id`) ON DELETE SET NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=12471 ;

DROP TABLE IF EXISTS `perusahaan`;
CREATE TABLE IF NOT EXISTS `perusahaan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `kode` varchar(50) NOT NULL,
  `nama` varchar(255) NOT NULL,
  `nama_pt` varchar(255) DEFAULT NULL,
  `id_kelurahan` int(11) NOT NULL,
  `lingkungan` int(2) DEFAULT NULL,
  `detail_alamat` varchar(255) DEFAULT NULL,
  `latitude` double DEFAULT '0',
  `longitude` double DEFAULT '0',
  `telepon` varchar(20) DEFAULT NULL,
  `hp` varchar(20) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `status` int(1) NOT NULL,
  `iuran` int(5) NOT NULL DEFAULT '1000',
  PRIMARY KEY (`id`),
  UNIQUE KEY `kode` (`kode`),
  UNIQUE KEY `email` (`email`),
  FOREIGN KEY `kelurahan` (`id_kelurahan`) REFERENCES `kelurahan`(`id`) ON DELETE SET NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=18 ;

DROP TABLE IF EXISTS `history`;
CREATE TABLE IF NOT EXISTS `history` (
  `id	` int(11) NOT NULL AUTO_INCREMENT,
  `id_pelanggan` int(11) NOT NULL,
  `tanggal` DATE NOT NULL,
  `status` int(1) NOT NULL,
  `keterangan` varchar(255),
  `jumlah_aktif` int(11) NOT NULL,
  `jumlah_putus` int(11) NOT NULL,
  `jumlah_berhenti` int(11) NOT NULL,
  `jumlah_gratis` int(11) NOT NULL,
  PRIMARY KEY(`id`),
  FOREIGN KEY `pelanggan` (`id_pelanggan`) REFERENCES `pelanggan`(`id`) ON DELETE SET NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=18 ;

DROP TABLE IF EXISTS `token`;
CREATE TABLE IF NOT EXISTS `token` (
  `token` VARCHAR(255) NOT NULL,
  `id_pegawai` INT(11) NOT NULL,
  `tanggal` DATE NOT NULL,
  `expire` DATE NOT NULL,
  `status` INT(1) NOT NULL,
  PRIMARY KEY(`token`),
  FOREIGN KEY `pegawai` (`id_pegawai`) REFERENCES `pegawai`(`id`) ON DELETE SET NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=18 ;

DROP TABLE IF EXISTS `alat`;
CREATE TABLE IF NOT EXISTS `alat` (
  `id` int(11) NOT NULL,
  `id_perusahaan` int(11) NOT NULL,
  `kode` varchar(50) NOT NULL,
  `nama` varchar(255) NOT NULL,
  `tipe` int(1) NOT NULL,
  `deskripsi` varchar(255),
  `latitude` int(11) DEFAULT 0,
  `longitude` int(11) DEFAULT 0,
  `id_source` int(11),
  PRIMARY KEY(id),
  UNIQUE KEY `perusahaan_kode` (`id_perusahaan`,`kode`),
  UNIQUE KEY `perusahaan_nama` (`id_perusahaan`,`nama`),
  FOREIGN KEY `perusahaan` (`id_perusahaan`) REFERENCES `perusahaan`(`id`) ON DELETE SET NULL
  FOREIGN KEY `source` (`id_source`) REFERENCES `alat`(`id`) ON DELETE SET NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=18 ;
