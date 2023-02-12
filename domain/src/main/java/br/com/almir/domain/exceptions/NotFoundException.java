package br.com.almir.domain.exceptions;


import br.com.almir.domain.AggregateRoot;
import br.com.almir.domain.Identifier;
import br.com.almir.domain.validation.Error;
import java.util.Collections;
import java.util.List;

public class NotFoundException extends DomainException {

  protected NotFoundException(final String aMessage, final List<Error> errors) {
    super(aMessage, errors);
  }

  public static NotFoundException with(
      final Class<? extends AggregateRoot<?>> aggregate,
      final Identifier id
  ) {
    final var anError = "%s with ID %s was not found".formatted(
        aggregate.getSimpleName(),
        id.getValue()
    );
    return new NotFoundException(anError, Collections.emptyList());
  }
}
