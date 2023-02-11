package br.com.almir.domain;

public abstract class AggregateRoot<ID extends Identifier> extends Entity<ID> {

  protected AggregateRoot(final ID id) {
    super(id);
  }
}