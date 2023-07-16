package si.magerl.spending.tracker.rest.dto;

public record ExpenseDTO(String id, String description, String category, Long date, Double amount) {}
