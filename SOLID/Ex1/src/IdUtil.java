import java.time.Year;

public class IdUtil {
    public static String nextStudentId(int currentCount) {
        int next = currentCount + 1;
        String num = String.format("%04d", next);
        // prefix and year hard-coded for now, year computed dynamically
        int year = Year.now().getValue();
        return "SST-" + year + "-" + num;
    }
}
