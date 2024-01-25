package by.currencyexchange.accountservice.mapper;

public interface BaseMapper<E, D> {

    E toEntity(D d);

    D toDto(E e);
}
