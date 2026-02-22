public interface InvoiceStore {
    void save(String invoiceId, String invoiceText);
    int countLines(String invoiceId);
}