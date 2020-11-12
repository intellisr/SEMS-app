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
    int micro;
    int tv;
    int refig;
    int iron;
    int geys;
    int wash;

    public Home(int solar, int maleCount, int femaleCount, int childCount, int adultCount, int empCount, int income, int district, int size, int aircon, int fan, int micro, int tv, int refig, int iron, int geys, int wash) {
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
        this.micro = micro;
        this.tv = tv;
        this.refig = refig;
        this.iron = iron;
        this.geys = geys;
        this.wash = wash;
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

    public int getMicro() {
        return micro;
    }

    public int getTv() {
        return tv;
    }

    public int getRefig() {
        return refig;
    }

    public int getIron() {
        return iron;
    }

    public int getGeys() {
        return geys;
    }

    public int getWash() {
        return wash;
    }
}
