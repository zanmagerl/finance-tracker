package si.magerl.spending.tracker.rest.dto.mappers;

import org.mapstruct.Mapper;
import si.magerl.spending.tracker.entitites.Expense;
import si.magerl.spending.tracker.rest.dto.ExpenseDTO;

@Mapper(componentModel = "cdi")
public abstract class ExpenseMapper {

    public abstract ExpenseDTO toDTO(Expense expense);
}
