// simple stub store used only by the assignment; it merely logs to stdout
public class FakeEligibilityStore {
    public void save(String roll, String status) {
        System.out.println("Saved evaluation for roll=" + roll);
    }
}
