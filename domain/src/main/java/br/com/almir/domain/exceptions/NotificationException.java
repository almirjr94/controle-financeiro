package br.com.almir.domain.exceptions;


import br.com.almir.domain.validation.handler.Notification;

public class NotificationException extends DomainException {

  public NotificationException(final String aMessage, final Notification notification) {
    super(aMessage, notification.getErrors());
  }
}
