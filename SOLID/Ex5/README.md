# Ex5 — LSP: File Exporter Hierarchy

## 1. Context

A reporting tool exports student performance data to multiple formats.

## 2. Current behavior

- `Exporter` has `export(ExportRequest)` that returns `ExportResult`
- `PdfExporter` throws for large content (tightens preconditions)
- `CsvExporter` silently changes meaning by dropping newlines and commas poorly
- `JsonExporter` returns empty on null (inconsistent contract)
- `Main` demonstrates current behavior

## 3. What’s wrong (at least 5 issues)

1. Subclasses violate expectations of the base `Exporter` contract.
2. `PdfExporter` throws for valid requests (from base perspective).
3. `CsvExporter` changes semantics of fields (data corruption risk).
4. `JsonExporter` handles null differently than others.
5. Callers cannot rely on substitutability; they need format-specific workarounds.
6. Contract is not documented; behavior surprises are runtime.

## 4. Your task

Checkpoint A: Run and capture output.
Checkpoint B: Define a clear base contract (preconditions/postconditions).
Checkpoint C: Refactor hierarchy so all exporters honor the same contract.
Checkpoint D: Keep observable outputs identical for current inputs.

## 4.1 Design hints

- The base contract should state that the request object itself is non-`null` and that `title`/`body` may be `null` (treated as empty).
- Format‑specific behaviour (PDF encoding, CSV escaping, JSON serialization) should be implemented by dedicated **encoder** components.
- Separate delivery constraints (e.g. maximum body length) into **policy** objects that are composed with encoders. This keeps exporters substitutable and avoids LSP violations.

## 5. Constraints

- Keep `Main` outputs unchanged for the given samples.
- No external libraries.
- Default package.

## 6. Acceptance criteria

- Base contract is explicit and enforced consistently.
- No exporter tightens preconditions compared to base contract.
- Caller should not need `instanceof` to be safe.

## 7. How to run

```bash
cd SOLID/Ex5/src
javac *.java
java Main
```

## 8. Sample output

```text
=== Export Demo ===
PDF: ERROR: PDF cannot handle content > 20 chars
CSV: OK bytes=54
JSON: OK bytes=63
```

The numbers are slightly different than earlier because the initial README was written
before the CSV and JSON encoders were refactored; the behaviour remains unchanged
but the byte counts were mis‑reported.

## Contract clarification

- `Exporter.export` rejects a null request and treats null title/body as empty
  strings before invoking encoders or policies.
- Encoders never see null values and therefore need not perform null checks.
- Delivery policies may throw `IllegalArgumentException` for unsupported input.

## 9. Hints (OOP-only)

- If a subtype cannot support the base contract, reconsider inheritance.
- Prefer composition: separate “format encoding” from “delivery constraints”.

## 10. Stretch goals

- Add a new exporter without changing existing exporters.
