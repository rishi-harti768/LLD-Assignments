import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Cafeteria Billing ===");

        // wire concrete implementations
        TaxPolicy taxPolicy = new SimpleTaxPolicy();
        DiscountPolicy discountPolicy = new SimpleDiscountPolicy();
        InvoiceFormatter formatter = new DefaultInvoiceFormatter();
        InvoiceStore store = new FileStore();

        CafeteriaSystem sys = new CafeteriaSystem(taxPolicy, discountPolicy, formatter, store);
        sys.addToMenu(new MenuItem("M1", "Veg Thali", 80.00));
        sys.addToMenu(new MenuItem("C1", "Coffee", 30.00));
        sys.addToMenu(new MenuItem("S1", "Sandwich", 60.00));

        List<OrderLine> order = List.of(
                new OrderLine("M1", 2),
                new OrderLine("C1", 1)
        );

        sys.checkout(CustomerType.STUDENT, order);
    }
}
