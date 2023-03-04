package si.magerl.spending.tracker.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import lombok.RequiredArgsConstructor;
import si.magerl.spending.tracker.authentication.User;
import si.magerl.spending.tracker.dao.ExpenseDao;
import si.magerl.spending.tracker.dao.impl.source.BigQueryDao;
import si.magerl.spending.tracker.entitites.Expense;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class ExpenseDaoImpl implements ExpenseDao {

    private final BigQueryDao<Expense> bigQueryDao;

    @Override
    public List<Expense> getUsersSpendingRecords(User user) {
        return bigQueryDao.executeQuery(bigQueryDao.query().filterEq("email", user.getEmail(), String.class));
    }

    @Override
    public void deleteUserRecord(User user, String id) {
        List<Expense> spendingRecords = bigQueryDao.executeQuery(bigQueryDao
                .query()
                .filterEq("email", user.getEmail(), String.class)
                .filterEq("id", id, String.class));
        if (spendingRecords.isEmpty()) {
            throw new IllegalArgumentException(
                    "There is no user's '%s' record with id '%s'".formatted(user.getEmail(), id));
        }
        if (spendingRecords.size() > 1) {
            throw new IllegalArgumentException("More than one record with id '%s'".formatted(id));
        }
        delete(id);
    }

    @Override
    public Optional<Expense> get(String id) {
        return bigQueryDao.get(id);
    }

    @Override
    public List<Expense> getAll() {
        return bigQueryDao.getAll();
    }

    @Override
    public void save(Expense spendingRecord) {
        bigQueryDao.save(spendingRecord);
    }

    @Override
    public void update(Expense expense, Map<String, Object> parameters) {
        bigQueryDao.update(expense, parameters);
    }

    @Override
    public void delete(String id) {
        bigQueryDao.delete(id);
    }
}
