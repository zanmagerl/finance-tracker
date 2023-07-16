package si.magerl.spending.tracker.entitites;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = true)
public class Expense extends BaseEntity {

    String email;
    String description;
    String category;
    Long date;
    Double amount;
}
