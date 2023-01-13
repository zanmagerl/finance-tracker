package si.magerl.spending.tracker.entitites;

public record SpendingRecord(String uid, String name, String category, Long date, Double amount) {}
