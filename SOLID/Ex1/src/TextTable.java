public class TextTable {
    /**
     * Simple helper: turn a list of records into a fixed-width table string.
     */
    public static String renderRecords(java.util.List<StudentRecord> rows) {
        StringBuilder sb = new StringBuilder();
        sb.append("| ID             | NAME | PROGRAM |\n");
        for (StudentRecord r : rows) {
            sb.append(String.format("| %-14s | %-4s | %-7s |\n", r.id, r.name, r.program));
        }
        return sb.toString();
    }
}
