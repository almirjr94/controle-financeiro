package br.com.almir.domain.exceptions;

import br.com.almir.domain.validation.Error;
import java.util.List;

public class DomainException extends NoStacktraceException {

  protected final List<Error> errors;

  protected DomainException(final String aMessage, final List<Error> errors) {
    super(aMessage);
    this.errors = errors;
  }

  public static DomainException with(final Error error) {
    return new DomainException(error.message(), List.of(error));
  }

  public static DomainException with(final List<Error> errors) {
    return new DomainException("", errors);
  }

  public List<Error> getErrors() {
    return errors;
  }
}
