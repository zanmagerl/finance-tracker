package si.magerl.spending.tracker.services;

import java.util.List;
import java.util.function.Supplier;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import si.magerl.spending.tracker.authentication.User;
import si.magerl.spending.tracker.dao.CategoryDao;
import si.magerl.spending.tracker.dao.ExpenseDao;
import si.magerl.spending.tracker.entitites.Category;
import si.magerl.spending.tracker.entitites.Expense;
import si.magerl.spending.tracker.rest.dto.ExpenseDTO;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class ExpenseProcessingService {

    final ExpenseDao bigQueryDao;

    final CategoryDao categoryDao;

    final Supplier<String> idGenerator;

    public void processExpense(User user, ExpenseDTO expenseDTO) {
        Expense spendingRecord = prepareExpense(expenseDTO, user);
        bigQueryDao.save(spendingRecord);
    }

    public void processExpenses(User user, List<ExpenseDTO> expenseDTOList) {
        expenseDTOList.forEach(expenseDTO -> this.processExpense(user, expenseDTO));
    }

    private Expense prepareExpense(ExpenseDTO expense, User user) {
        String categoryName = getCategoryName(user, expense);
        return Expense.builder()
                .id(idGenerator.get())
                .email(user.getEmail())
                .description(expense.description())
                .date(expense.date())
                .category(categoryName)
                .amount(expense.amount())
                .build();
    }

    private String getCategoryName(User user, ExpenseDTO expenseDTO) {
        return categoryDao.getUserCategoriesByName(user, expenseDTO.category().toLowerCase()).stream()
                .findFirst()
                .map(Category::getName)
                .orElseGet(() -> categoryDao
                        .createNewUserCategory(user, expenseDTO.category().toLowerCase())
                        .getName());
    }
}
