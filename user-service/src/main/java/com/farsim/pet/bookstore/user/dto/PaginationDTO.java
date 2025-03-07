package com.farsim.pet.bookstore.user.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class PaginationDTO {
    private int page = 0;
    private int size = 10;
    private String sortBy = "id"; // Default sort field
    private Sort.Direction sortDirection = Sort.Direction.ASC; // Default sorting order
}
