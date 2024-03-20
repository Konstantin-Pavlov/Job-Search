package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryDao {
    private final JdbcTemplate template;

    public List<Category> getCategories() {
        String sql = """
                select * from CATEGORIES
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(Category.class));
    }
}
