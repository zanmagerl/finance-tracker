package si.magerl.spending.tracker.rest.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import si.magerl.spending.tracker.entitites.Category;
import si.magerl.spending.tracker.rest.dto.CategoryDTO;

@Mapper(componentModel = "cdi")
public interface CategoryMapper {

    @Mapping(source = "creatorUid", target = "uid")
    @Mapping(source = "categoryName", target = "name")
    CategoryDTO toDTO(Category category);

    @Mapping(source = "uid", target = "creatorUid")
    @Mapping(source = "name", target = "categoryName")
    Category fromDTO(CategoryDTO categoryDTO);
}
