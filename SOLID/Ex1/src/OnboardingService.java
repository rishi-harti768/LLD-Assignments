import java.util.*;

public class OnboardingService {
    private final StudentRepository repo;
    private final ConsolePrinter printer;
    private final StudentValidator validator;

    public OnboardingService(StudentRepository repo, ConsolePrinter printer, StudentValidator validator) {
        this.repo = repo;
        this.printer = printer;
        this.validator = validator;
    }

    // orchestrates the registration process; no formatting done here
    public void registerFromRawInput(String raw) {
        printer.printInput(raw);

        StudentParser parser = new StudentParser();
        StudentData data = parser.parse(raw);

        String name = data.getName();
        String email = data.getEmail();
        String phone = data.getPhone();
        String program = data.getProgram();

        List<String> errors = validator.validate(data);

        if (!errors.isEmpty()) {
            printer.printErrors(errors);
            return;
        }

        String id = IdUtil.nextStudentId(repo.count());
        StudentRecord rec = new StudentRecord(id, name, email, phone, program);

        repo.save(rec);

        printer.printSuccess(rec, repo.count());
    }
}
