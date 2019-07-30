package pl.jcommerce.joannajaromin.studentbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface MyJpaRepository<T, I> extends JpaRepository<T, I> {

    default T findByIdCustom(I id) {
        return (T) findById(id).orElse(null);
    }
}
