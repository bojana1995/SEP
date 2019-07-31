-- rola
insert into naucnacentrala.rola(id, naziv) values(1, 'ADMIN');
insert into naucnacentrala.rola(id, naziv) values(2, 'GLAVNI_UREDNIK');
insert into naucnacentrala.rola(id, naziv) values(3, 'UREDNIK');
insert into naucnacentrala.rola(id, naziv) values(4, 'RECENZENT');
insert into naucnacentrala.rola(id, naziv) values(5, 'AUTOR');
insert into naucnacentrala.rola(id, naziv) values(6, 'KOAUTOR');
insert into naucnacentrala.rola(id, naziv) values(7, 'CITALAC');


-- permisija
--insert into naucnacentrala.permisija(id, naziv) values(1, 'GET_CASOPIS');
--insert into naucnacentrala.permisija(id, naziv) values(2, 'GET_CASOPISI');
insert into naucnacentrala.permisija(id, naziv) values(3, 'DODAJ_CASOPIS');
insert into naucnacentrala.permisija(id, naziv) values(4, 'AZURIRAJ_CASOPIS');
insert into naucnacentrala.permisija(id, naziv) values(5, 'OBRISI_CASOPIS');
insert into naucnacentrala.permisija(id, naziv) values(6, 'PROVERI_MERCHANTA');
insert into naucnacentrala.permisija(id, naziv) values(7, 'KUPI_CASOPIS');
--insert into naucnacentrala.permisija(id, naziv) values(8, 'GET_KORISNIK');
--insert into naucnacentrala.permisija(id, naziv) values(9, 'GET_KORISNICI');
--insert into naucnacentrala.permisija(id, naziv) values(10, 'REGISTRACIJA');
--insert into naucnacentrala.permisija(id, naziv) values(11, 'AKTIVIRAJ_KORISNICKI_NALOG');
--insert into naucnacentrala.permisija(id, naziv) values(12, 'PRIJAVA');
--insert into naucnacentrala.permisija(id, naziv) values(13, 'GET_TRENUTNO_AKTIVAN');
--insert into naucnacentrala.permisija(id, naziv) values(14, 'ODJAVA');
--insert into naucnacentrala.permisija(id, naziv) values(15, 'AZURIRAJ_KORISNIKA');
--insert into naucnacentrala.permisija(id, naziv) values(16, 'KRIPTOVANJE_EMAIL_ADRESE');
--insert into naucnacentrala.permisija(id, naziv) values(17, 'DEKRIPTOVANJE_EMAIL_ADRESE');
insert into naucnacentrala.permisija(id, naziv) values(18, 'GET_NAUCNE_OBLASTI_KOJE_POKRIVA_UREDNIK');
--insert into naucnacentrala.permisija(id, naziv) values(19, 'GET_NAUCNA_OBLAST');
--insert into naucnacentrala.permisija(id, naziv) values(20, 'GET_NAUCNE_OBLASTI');
insert into naucnacentrala.permisija(id, naziv) values(21, 'DODAJ_NAUCNU_OBLAST');
insert into naucnacentrala.permisija(id, naziv) values(22, 'AZURIRAJ_NAUCNU_OBLAST');
insert into naucnacentrala.permisija(id, naziv) values(23, 'OBRISI_NAUCNU_OBLAST');
--insert into naucnacentrala.permisija(id, naziv) values(24, 'GET_RAD');
--insert into naucnacentrala.permisija(id, naziv) values(25, 'GET_RADOVI');
insert into naucnacentrala.permisija(id, naziv) values(26, 'DODAJ_RAD');
insert into naucnacentrala.permisija(id, naziv) values(27, 'AZURIRAJ_RAD');
insert into naucnacentrala.permisija(id, naziv) values(28, 'OBRISI_RAD');
insert into naucnacentrala.permisija(id, naziv) values(29, 'GET_RECENZIJA');
insert into naucnacentrala.permisija(id, naziv) values(30, 'GET_RECENZIJE');
insert into naucnacentrala.permisija(id, naziv) values(31, 'DODAJ_RECENZIJU');
insert into naucnacentrala.permisija(id, naziv) values(32, 'AZURIRAJ_RECENZIJU');
insert into naucnacentrala.permisija(id, naziv) values(33, 'OBRISI_RECENZIJU');


-- rola_permisija
-- ADMIN
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(1, 1);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(1, 2);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(1, 8);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(1, 9);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(1, 13);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(1, 19);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(1, 20);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(1, 24);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(1, 25);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(1, 12);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(1, 14);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(1, 15);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(1, 16);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(1, 17);
insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(1, 21);
insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(1, 22);
insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(1, 23);
insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(1, 5); --zbog kaskadnog brisanja
insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(1, 28); --zbog kaskadnog brisanja
-- GLAVNI_UREDNIK
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(2, 1);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(2, 2);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(2, 8);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(2, 9);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(2, 13);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(2, 19);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(2, 20);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(2, 24);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(2, 25);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(2, 12);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(2, 14);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(2, 15);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(2, 16);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(2, 17);
insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(2, 3);
insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(2, 4);
insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(2, 5);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(2, 10);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(2, 11);
insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(2, 26);
insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(2, 27);
insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(2, 28);
-- UREDNIK
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(3, 1);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(3, 2);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(3, 8);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(3, 9);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(3, 13);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(3, 19);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(3, 20);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(3, 24);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(3, 25);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(3, 12);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(3, 14);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(3, 15);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(3, 16);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(3, 17);
insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(3, 18);
insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(3, 3);
insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(3, 4);
insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(3, 5);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(3, 10);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(3, 11);
insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(3, 26);
insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(3, 27);
insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(3, 28);
--RECENZENT
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(4, 1);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(4, 2);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(4, 8);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(4, 9);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(4, 13);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(4, 19);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(4, 20);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(4, 24);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(4, 25);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(4, 12);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(4, 14);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(4, 15);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(4, 16);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(4, 17);
insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(4, 29);
insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(4, 30);
insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(4, 3);
insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(4, 4);
insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(4, 5);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(4, 10);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(4, 11);
insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(4, 26);
insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(4, 27);
insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(4, 28);
insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(4, 31);
insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(4, 32);
insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(4, 33);
-- AUTOR
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(5, 1);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(5, 2);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(5, 8);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(5, 9);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(5, 13);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(5, 19);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(5, 20);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(5, 24);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(5, 25);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(5, 12);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(5, 14);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(5, 15);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(5, 16);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(5, 17);
insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(5, 3);
insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(5, 4);
insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(5, 5);
insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(5, 6);
insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(5, 7);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(5, 10);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(5, 11);
insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(5, 26);
insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(5, 27);
insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(5, 28);
-- KOAUTOR
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(6, 1);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(6, 2);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(6, 8);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(6, 9);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(6, 13);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(6, 19);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(6, 20);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(6, 24);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(6, 25);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(6, 12);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(6, 14);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(6, 15);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(6, 16);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(6, 17);
insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(6, 3);
insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(6, 4);
insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(6, 5);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(6, 10);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(6, 11);
insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(6, 26);
insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(6, 27);
insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(6, 28);
-- CITALAC
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(7, 1);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(7, 2);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(7, 8);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(7, 9);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(7, 13);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(7, 19);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(7, 20);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(7, 24);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(7, 25);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(7, 12);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(7, 14);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(7, 15);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(7, 16);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(7, 17);
insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(7, 6);
insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(7, 7);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(7, 10);
--insert into naucnacentrala.rola_permisija(rola_id, permisija_id) values(7, 11);

-- korisnik
--email adresa je kriptovana (AES algoritam, CBC rezim rada); lozinka je hesirana
--lozinka za sve korisnike: lozinka@L123
--ADMIN (email: admin@gmail.com)
insert into naucnacentrala.korisnik(id, aktiviran_nalog, drzava, email, grad, ime, lozinka, prezime, titula, id_rola)
values(1, 1, 'Srbija', 'mzsFBvKXIjDpo1AC5QKiFA==', 'Novi Sad', 'Admin', '$2a$10$FRKEcOzWp0xvfsgI.lekceX5Va5poxcwPxzRHWekLbzkxhtHQ65oO', 'Admin', '', 1);
--GLAVNI UREDNIK (email-ovi: glavniUrednik1@gmail.com, glavniUrednik2@gmail.com, glavniUrednik3@gmail.com, glavniUrednik4@gmail.com) 
insert into naucnacentrala.korisnik(id, aktiviran_nalog, drzava, email, grad, ime, lozinka, prezime, titula, id_rola)
values(2, 1, 'Srbija', '84i4+67CmsnL4gtq+6WPxW3eSPRorljSqAh+XUXP2Lg=', 'Novi Sad', 'GlavniUrednik1', '$2a$10$KIthzI5Mw/b/bonqLo2vnOHOBZBJAhEE1Y0rsSkJyk7jybuXuI6VO', 'GlavniUrednik1', 'titula', 2);
insert into naucnacentrala.korisnik(id, aktiviran_nalog, drzava, email, grad, ime, lozinka, prezime, titula, id_rola)
values(3, 1, 'Srbija', 'yf2f8Bkf6ivr4zg7HNxtlC7tm1DZVn729yqfgnrqpA0=', 'Novi Sad', 'GlavniUrednik2', '$2a$10$j4epvofP2W.JYnMwWmLF0e3BPbqA/IG9F1P5NJN1ISuWb7ww7aEIW', 'GlavniUrednik2', 'titula', 2);
insert into naucnacentrala.korisnik(id, aktiviran_nalog, drzava, email, grad, ime, lozinka, prezime, titula, id_rola)
values(4, 1, 'Srbija', 'PV1ZvqjxkrJiSCsCf/kZWQ4Fejm9ewHLBKeSmSju1B8=', 'Novi Sad', 'GlavniUrednik3', '$2a$10$XmrSPJ57VskF4LL6cTGvOOj5O1B7KjzC7M/iQTAo.c6042sHVM4wW', 'GlavniUrednik3', 'titula', 2);
insert into naucnacentrala.korisnik(id, aktiviran_nalog, drzava, email, grad, ime, lozinka, prezime, titula, id_rola)
values(5, 1, 'Srbija', 'gjYUqIdK79xmHTmvDItD30XsDRXi0rPSnxs6SZv+MvM=', 'Novi Sad', 'GlavniUrednik4', '$2a$10$KooAyRfje1a3HhMicl86z.rWNlANCsirI68fB33D1io0w2G80PePe', 'GlavniUrednik4', 'titula', 2);
--UREDNIK (email: urednik@gmail.com)
insert into naucnacentrala.korisnik(id, aktiviran_nalog, drzava, email, grad, ime, lozinka, prezime, titula, id_rola)
values(6, 1, 'Srbija', '/o1ezE1a4c/yEo/A4yodjC1KzxD15UwnvUV5cwiMJmY=', 'Novi Sad', 'Urednik', '$2a$10$u8LoEM48KbVWLjleYvWdX.2/QDWiNgpmWsZ/bzvA/04VkB/WjzrRa', 'Urednik', 'titula', 3);
--RECENZENT (email: recenzent@gmail.com)
insert into naucnacentrala.korisnik(id, aktiviran_nalog, drzava, email, grad, ime, lozinka, prezime, titula, id_rola)
values(7, 1, 'Srbija', '+ex/f+zAwklqK6ye66pHlLe/5PuAZC644XVKcI/awiI=', 'Novi Sad', 'Recenzent', '$2a$10$dlXN/DbDqn3kXh2bviqKbuQ6tB75bCdukUV73XLVWy5vURVAY1wnS', 'Recenzent', 'titula', 4);
--AUTOR (email-ovi: autor1@gmail.com, autor2@gmail.com, autor3@gmail.com, autor4@gmail.com, autor5@gmail.com)
insert into naucnacentrala.korisnik(id, aktiviran_nalog, drzava, email, grad, ime, lozinka, prezime, titula, id_rola)
values(8, 1, 'Srbija', 'KYU44MshcruZ9eBaNpXm3h+HB335LYsYZKkWD4EQ7Hg=', 'Novi Sad', 'Autor1', '$2a$10$vu4gZ5ny30ajy1bYQb4l5eud17iuY7uyDP3wNVDevZ.FxUnLs.r9m', 'Autor1', '', 5);
insert into naucnacentrala.korisnik(id, aktiviran_nalog, drzava, email, grad, ime, lozinka, prezime, titula, id_rola)
values(9, 1, 'Srbija', 'y9f0NLJTPCpDjANG4SD2PGX48CawV50kuLtYMqt1Vzk=', 'Novi Sad', 'Autor2', '$2a$10$G6gWi7Y0zHyHAyu9HGgRuuURvsrdC8VGKvFnhrLn.klD8LZo1yWx2', 'Autor2', '', 5);
insert into naucnacentrala.korisnik(id, aktiviran_nalog, drzava, email, grad, ime, lozinka, prezime, titula, id_rola)
values(10, 1, 'Srbija', 'wMR4zkWPsGlfj7Oxxnp7xjS5dGMC2aBhEyhLjpR38y0=', 'Novi Sad', 'Autor3', '$2a$10$sDGEdCzp9TGV45ya.SQ8yeUsIJ/3BLeEwcf5Lc0drpL8eqyIE.yKm', 'Autor3', '', 5);
insert into naucnacentrala.korisnik(id, aktiviran_nalog, drzava, email, grad, ime, lozinka, prezime, titula, id_rola)
values(11, 1, 'Srbija', 'RmJqgyGhjG1c+G7840shK0zeomWNMN83OfvikWo1mSM=', 'Novi Sad', 'Autor4', '$2a$10$zotgc9YQ.tvZZ9bttaA/QOVuihXtMBWW8K7BXU1wVWVQG.pxY2xeC', 'Autor4', '', 5);
insert into naucnacentrala.korisnik(id, aktiviran_nalog, drzava, email, grad, ime, lozinka, prezime, titula, id_rola)
values(12, 1, 'Srbija', '1BK7sn54iQRlB6LOVC0/snC4ex84YU/sc3RUPObPOlA=', 'Novi Sad', 'Autor5', '$2a$10$jXbgZ5eheDs6xMaQBRlSEu6QDLQIcv6ZtoTQf6rxnc5TBCK0eyGwC', 'Autor5', '', 5);
--KOAUTOR (email: koautor@gmail.com)
insert into naucnacentrala.korisnik(id, aktiviran_nalog, drzava, email, grad, ime, lozinka, prezime, titula, id_rola)
values(13, 0, 'Srbija', 'MULWvcoJaz9QP7gYUzX+YSWAybN5V5M72R6NytvwvNw=', 'Novi Sad', 'Koautor', '$2a$10$kps0Pz2s4DQB/kt3KSugbOLILnfY.2uv9RsNBT3hiYWtvXORyF3ue', 'Koautor', '', 6);
--KOAUTOR podnosilac rada (mora biti registrovan; email-ovi: koautorPodnosilacRada1@gmail.com, koautorPodnosilacRada2@gmail.com, koautorPodnosilacRada3@gmail.com, koautorPodnosilacRada4@gmail.com, koautorPodnosilacRada5@gmail.com)
insert into naucnacentrala.korisnik(id, aktiviran_nalog, drzava, email, grad, ime, lozinka, prezime, titula, id_rola)
values(14, 1, 'Srbija', 'kY2DPE3kVx0kCggwpmbmWHlNYYxVCQ1oAGFYbBXpj7w78v66gAZsFNiZppwGPZqo', 'Novi Sad', 'KoautorPodnosilacRada1', '$2a$10$7GjwWwGgnTypYhvdiT0WoejEKMq8RAjdrT19CNCTPicyueA3eNHmG', 'KoautorPodnosilacRada1', '', 6);
insert into naucnacentrala.korisnik(id, aktiviran_nalog, drzava, email, grad, ime, lozinka, prezime, titula, id_rola)
values(15, 1, 'Srbija', 'kY2DPE3kVx0kCggwpmbmWLrUpaJEK5GzQKBRDmp8dKr+MEwRMRUkCr0tD9fIeeCo', 'Novi Sad', 'KoautorPodnosilacRada2', '$2a$10$wDEx.RC7pScRCUAD8cGOFuRYTUPr/3cd5/Et6Z8H.Ads1GkgPyxUe', 'KoautorPodnosilacRada2', '', 6);
insert into naucnacentrala.korisnik(id, aktiviran_nalog, drzava, email, grad, ime, lozinka, prezime, titula, id_rola)
values(16, 1, 'Srbija', 'kY2DPE3kVx0kCggwpmbmWH65mIzxqYM21wV+ZLOluUNxKh775X6I3n5Gls3Dn1us', 'Novi Sad', 'KoautorPodnosilacRada3', '$2a$10$rJpeKqp.gGTJVTAv/qJ7x.aGGVROy/0qcP4WtcvVIjvlGf77wFxAu', 'KoautorPodnosilacRada3', '', 6);
insert into naucnacentrala.korisnik(id, aktiviran_nalog, drzava, email, grad, ime, lozinka, prezime, titula, id_rola)
values(17, 1, 'Srbija', 'kY2DPE3kVx0kCggwpmbmWDTkXenkk4220j73ScyWXPsnF0KCxxMLhGSJ7Kb6US21', 'Novi Sad', 'KoautorPodnosilacRada4', '$2a$10$8qp68KXP8IcCjDoAtO/6kOq5xNObN4uYwHekS4tHBuSKsTFjK27OC', 'KoautorPodnosilacRada4', '', 6);
insert into naucnacentrala.korisnik(id, aktiviran_nalog, drzava, email, grad, ime, lozinka, prezime, titula, id_rola)
values(18, 1, 'Srbija', 'kY2DPE3kVx0kCggwpmbmWDtdU71Iki9HnHjYRkFFFZGhxxScNtxkJp17ofQ8nlBt', 'Novi Sad', 'KoautorPodnosilacRada5', '$2a$10$GMcktmbt2Rp0OSztEcek1.NkYsF9ZcQ2H30idIMucnuPMHmaqZxr2', 'KoautorPodnosilacRada5', '', 6);
--CITALAC (email: citalac@gmail.com)
insert into naucnacentrala.korisnik(id, aktiviran_nalog, drzava, email, grad, ime, lozinka, prezime, titula, id_rola)
values(19, 1, 'Srbija', 'rL31COVBUq5Q6XIiV4MNHhiSwwRb5mmNAIZ35346mFo=', 'Novi Sad', 'Citalac', '$2a$10$ga5wTYpJGv5tLiCJ/TcKIOyAEoc207Uusd455uId68IxjPAQOrLbm', 'Citalac', '', 7);

-- naucna oblast
insert into naucnacentrala.naucna_oblast(id, naziv, opis)
values(1, 'Geografija', 'opsta geografija');
insert into naucnacentrala.naucna_oblast(id, naziv, opis)
values(2, 'Biologija', 'o coveku');
insert into naucnacentrala.naucna_oblast(id, naziv, opis)
values(3, 'Fizika', 'gravitaciona sila');
insert into naucnacentrala.naucna_oblast(id, naziv, opis)
values(4, 'Hemija', 'kripton');
insert into naucnacentrala.naucna_oblast(id, naziv, opis)
values(5, 'Istorija', 'balkanski ratovi');
