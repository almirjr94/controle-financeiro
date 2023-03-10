package br.com.almir.domain.validation.handler;


import br.com.almir.domain.exceptions.DomainException;
import br.com.almir.domain.validation.Error;
import br.com.almir.domain.validation.ValidationHandler;
import java.util.List;

public class ThrowsValidationHandler implements ValidationHandler {

  @Override
  public ValidationHandler append(final Error anError) {
    throw DomainException.with(anError);
  }

  @Override
  public ValidationHandler append(final ValidationHandler anHandler) {
    throw DomainException.with(anHandler.getErrors());
  }

  @Override
  public <T> T validate(final Validation<T> validation) {
    try {
      return validation.validate();
    } catch (final Exception ex) {
      throw DomainException.with(new Error(ex.getMessage()));
    }
  }

  @Override
  public List<Error> getErrors() {
    return List.of();
  }
}
