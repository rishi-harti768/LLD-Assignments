public class SimpleDiscountPolicy implements DiscountPolicy {
    @Override
    public double discountAmount(CustomerType customerType, double subtotal, int distinctLines) {
        // reuse original hard-coded policy
        if (customerType == CustomerType.STUDENT) {
            if (subtotal >= 180.0) return 10.0;
            return 0.0;
        }
        if (customerType == CustomerType.STAFF) {
            if (distinctLines >= 3) return 15.0;
            return 5.0;
        }
        return 0.0;
    }
}