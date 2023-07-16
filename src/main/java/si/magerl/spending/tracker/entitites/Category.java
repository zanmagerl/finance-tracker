package si.magerl.spending.tracker.entitites;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Value
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = true)
public class Category extends BaseEntity {

    String creatorUid;
    String name;
}
