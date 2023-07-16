package si.magerl.spending.tracker.dao;

import java.util.List;
import si.magerl.spending.tracker.authentication.User;
import si.magerl.spending.tracker.entitites.Expense;

public interface ExpenseDao extends GenericDao<Expense> {

    List<Expense> getUsersSpendingRecords(User user);

    void deleteUsersExpense(User user, String id);
}
