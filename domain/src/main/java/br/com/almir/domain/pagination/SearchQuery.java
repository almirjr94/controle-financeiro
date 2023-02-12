package br.com.almir.domain.pagination;

public record SearchQuery<T>(
    int page,
    int perPage,
    T filter,
    String sort,
    String direction
) {

}
