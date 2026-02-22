import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Student Onboarding ===");
        StudentRepository db = new FakeDb();
        ConsolePrinter printer = new ConsolePrinter();
        // programs could come from config; hard‑code default set for now
        Set<String> programs = new HashSet<>();
        programs.add("CSE"); programs.add("AI"); programs.add("SWE");
        StudentValidator validator = new StudentValidator(programs);
        OnboardingService svc = new OnboardingService(db, printer, validator);

        String raw = "name=Riya;email=riya@sst.edu;phone=9876543210;program=CSE";
        svc.registerFromRawInput(raw);

        System.out.println();
        printer.printDbDump(db.all());
    }
}
