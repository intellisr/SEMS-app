package lk.sliit.sems_app;

public class Home {
    int solar;
    int maleCount;
    int femaleCount;
    int childCount;
    int adultCount;
    int empCount;
    int income;
    int district;
    int size;
    int Aircon;
    int Fan;
    int Oven;
    int Microwaves;
    int Refrigerators;
    int Car;
    int Geysers;

    public Home(int solar, int maleCount, int femaleCount, int childCount, int adultCount, int empCount, int income, int district, int size, int aircon, int fan, int oven, int microwaves, int refrigerators, int car, int geysers) {
        this.solar = solar;
        this.maleCount = maleCount;
        this.femaleCount = femaleCount;
        this.childCount = childCount;
        this.adultCount = adultCount;
        this.empCount = empCount;
        this.income = income;
        this.district = district;
        this.size = size;
        Aircon = aircon;
        Fan = fan;
        Oven = oven;
        Microwaves = microwaves;
        Refrigerators = refrigerators;
        Car = car;
        Geysers = geysers;
    }

    public int getSolar() {
        return solar;
    }

    public int getMaleCount() {
        return maleCount;
    }

    public int getFemaleCount() {
        return femaleCount;
    }

    public int getChildCount() {
        return childCount;
    }

    public int getAdultCount() {
        return adultCount;
    }

    public int getEmpCount() {
        return empCount;
    }

    public int getIncome() {
        return income;
    }

    public int getDistrict() {
        return district;
    }

    public int getSize() {
        return size;
    }

    public int getAircon() {
        return Aircon;
    }

    public int getFan() {
        return Fan;
    }

    public int getOven() {
        return Oven;
    }

    public int getMicrowaves() {
        return Microwaves;
    }

    public int getRefrigerators() {
        return Refrigerators;
    }

    public int getCar() {
        return Car;
    }

    public int getGeysers() {
        return Geysers;
    }
}
