package hr.fipu.booktrack.service;

import java.util.Map;

public interface StatistikaService {

    long brojKnjiga();
    long brojDostupnihKnjiga();
    long brojPosudenihKnjiga();
    long brojKategorija();
    long brojAktivnihPosudbi();
    String najposudivanijaKnjiga();
    String najpopularnijaKategorija();
    double prosjecanBrojPosudbiPoKnjizi();
    Map<String, Long> brojKnjigaPoKategorijama();
    Map<String, Long> brojPosudbiPoKategorijama();


}