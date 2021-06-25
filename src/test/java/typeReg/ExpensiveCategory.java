package typeReg;

public enum ExpensiveCategory {
    По_умолчанию("101"),
    Дороже("2"),
    Дешевле("1"),
    По_дате("104");

    public String value;

    public String getValue() {
        return value;
    }

    ExpensiveCategory(String value) {
        this.value = value;
    }
}
