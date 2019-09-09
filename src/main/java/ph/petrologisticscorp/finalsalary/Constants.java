package ph.petrologisticscorp.finalsalary;

public enum Constants {
    PRESIDENT("PRESIDENT"),
    DATE_FROM("DATE_FROM"),
    DATE_TO("DATE_TO"),
    COMPANY("COMPANY"),
    COMPANY_ID("COMPANY_ID"),
    AREA("AREA"),
    AREA_ID("AREA_ID"),
    YEAR("YEAR"),
    DATE_NOW("DATE_NOW");

    private String value;

    Constants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
