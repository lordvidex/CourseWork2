package ru.itis.stockmarket.dtos;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by IntelliJ IDEA
 * Date: 16.05.2022
 * Time: 5:18 AM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PagedResponse<T> {
    private List<T> content;
    private int totalPages;
    private long totalElements;
    private int pageSize;
    private int currentPage;

    public static <T> PagedResponse<T> from(Page<T> page) {
        return new PagedResponse<T>().toBuilder()
                .content(page.getContent())
                .pageSize(page.getSize())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .currentPage(page.getNumber() + 1)
                .build();
    }
}
