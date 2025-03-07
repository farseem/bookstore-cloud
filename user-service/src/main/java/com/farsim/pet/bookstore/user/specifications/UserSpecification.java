package com.farsim.pet.bookstore.user.specifications;

import com.farsim.pet.bookstore.user.dto.search.UserSearchDTO;
import com.farsim.pet.bookstore.user.entity.User;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class UserSpecification {

    public static Specification<User> buildSpecification(UserSearchDTO searchDTO) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (searchDTO.getUsername() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.like(root.get("username"), "%" + searchDTO.getUsername() + "%"));
            }

            if (searchDTO.getEmail() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.like(root.get("email"), "%" + searchDTO.getEmail() + "%"));
            }

            if (searchDTO.getRole() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("role"), searchDTO.getRole()));
            }

            return predicate;
        };
    }
}


