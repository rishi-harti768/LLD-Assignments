import java.util.*;

public class CafeteriaSystem {
    private final Map<String, MenuItem> menu = new LinkedHashMap<>();
    private final TaxPolicy taxPolicy;
    private final DiscountPolicy discountPolicy;
    private final InvoiceFormatter formatter;
    private final InvoiceStore store;
    private int invoiceSeq = 1000;

    public CafeteriaSystem(TaxPolicy taxPolicy,
                           DiscountPolicy discountPolicy,
                           InvoiceFormatter formatter,
                           InvoiceStore store) {
        this.taxPolicy = taxPolicy;
        this.discountPolicy = discountPolicy;
        this.formatter = formatter;
        this.store = store;
    }

    public void addToMenu(MenuItem i) { menu.put(i.id, i); }

    // now acts purely as an orchestrator; specifics live in injected collaborators
    public void checkout(CustomerType customerType, List<OrderLine> lines) {
        if (lines == null || lines.isEmpty()) {
            throw new IllegalArgumentException("order must contain at least one line");
        }

        String invId = "INV-" + (++invoiceSeq);

        double subtotal = 0.0;
        List<Invoice.Line> invoiceLines = new ArrayList<>();
        for (OrderLine l : lines) {
            if (l.qty <= 0) {
                throw new IllegalArgumentException("quantity must be positive");
            }
            MenuItem item = menu.get(l.itemId);
            if (item == null) {
                throw new IllegalArgumentException("unknown menu item " + l.itemId);
            }
            double lineTotal = item.price * l.qty;
            subtotal += lineTotal;
            invoiceLines.add(new Invoice.Line(item.name, l.qty, lineTotal));
        }

        double taxPct = taxPolicy.taxPercent(customerType);
        double tax = subtotal * (taxPct / 100.0);

        double discount = discountPolicy.discountAmount(customerType, subtotal, lines.size());
        double total = subtotal + tax - discount;

        Invoice invoice = new Invoice(invId, invoiceLines, subtotal, taxPct, tax, discount, total);
        String printable = formatter.format(invoice);
        System.out.print(printable);

        store.save(invId, printable);
        System.out.println("Saved invoice: " + invId + " (lines=" + store.countLines(invId) + ")");
    }
}
