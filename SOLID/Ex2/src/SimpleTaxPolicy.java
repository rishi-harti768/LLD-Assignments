public class SimpleTaxPolicy implements TaxPolicy {
    @Override
    public double taxPercent(CustomerType customerType) {
        // reuse original hard-coded policy based on enum
        if (customerType == CustomerType.STUDENT) return 5.0;
        if (customerType == CustomerType.STAFF) return 2.0;
        return 8.0;
    }
}