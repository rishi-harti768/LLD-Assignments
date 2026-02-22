import java.util.*;

// simple status enum replaces magic strings
enum Status {
    ELIGIBLE,
    NOT_ELIGIBLE;
}

public class EligibilityEngine {
    private final FakeEligibilityStore store;
    private final List<EligibilityRule> rules;

    public EligibilityEngine(FakeEligibilityStore store, List<EligibilityRule> rules) {
        this.store = store;
        this.rules = rules;
    }

    public void runAndPrint(StudentProfile s) {
        ReportPrinter p = new ReportPrinter();
        EligibilityEngineResult r = evaluate(s);
        p.print(s, r);
        store.save(s.rollNo, r.status.name());
    }

    public EligibilityEngineResult evaluate(StudentProfile s) {
        // quick sanity checks on profile values
        if (s.attendancePct < 0 || s.attendancePct > 100) {
            throw new IllegalArgumentException("attendancePct out of range");
        }
        if (Double.isNaN(s.cgr)) {
            throw new IllegalArgumentException("CGR is NaN");
        }

        List<String> reasons = new ArrayList<>();
        Status status = Status.ELIGIBLE;

        // we intentionally stop at the first failing rule to mimic original
        // else-if chain; changing this behaviour requires a conscious decision.
        for (EligibilityRule rule : rules) {
            String reason = rule.check(s);
            if (reason != null) {
                status = Status.NOT_ELIGIBLE;
                reasons.add(reason);
                break;
            }
        }

        return new EligibilityEngineResult(status, reasons);
    }
}

class EligibilityEngineResult {
    public final Status status;
    public final List<String> reasons;
    public EligibilityEngineResult(Status status, List<String> reasons) {
        this.status = status;
        this.reasons = reasons;
    }
}
