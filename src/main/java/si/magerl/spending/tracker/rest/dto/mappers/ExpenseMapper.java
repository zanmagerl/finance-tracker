package si.magerl.spending.tracker.rest.dto.mappers;

import javax.inject.Inject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import si.magerl.spending.tracker.dao.CategoryDao;
import si.magerl.spending.tracker.entitites.Category;
import si.magerl.spending.tracker.entitites.Expense;
import si.magerl.spending.tracker.rest.dto.ExpenseDTO;

@Mapper(componentModel = "cdi")
public abstract class ExpenseMapper {

    @Inject
    CategoryDao categoryDao;

    @Mapping(source = "categoryId", target = "category", qualifiedByName = "mapCategoryIdToName")
    public abstract ExpenseDTO toDTO(Expense expense);

    @Named("mapCategoryIdToName")
    public String mapCategory(String categoryId) {
        return categoryDao.get(categoryId).map(Category::getCategoryName).orElse(null);
    }
}
