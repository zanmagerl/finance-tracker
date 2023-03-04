package si.magerl.spending.tracker.entitites;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Base class for all entities in the project.
 */
@Getter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public abstract class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 42;

    protected String id;

    protected final long created = Instant.now().getEpochSecond();
}
