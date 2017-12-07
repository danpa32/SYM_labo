package ch.heigvd.iict.sym.sym_labo3.model;

/**
 * Created by Daniel on 07.12.2017.
 */

public class BeaconSummary {
    private String name;
    private String minor;
    private String major;
    private String address;
    private int rssi;
    private double distance;


    public BeaconSummary(String name, String minor, String major, String address, int rssi, double distance) {
        this.name = name;
        this.minor = minor;
        this.major = major;
        this.address = address;
        this.rssi = rssi;
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMinor() {
        return minor;
    }

    public void setMinor(String minor) {
        this.minor = minor;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
