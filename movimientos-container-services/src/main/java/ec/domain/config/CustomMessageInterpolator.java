package ec.domain.config;

import jakarta.validation.MessageInterpolator;
import java.util.Locale;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class CustomMessageInterpolator implements MessageInterpolator {

  private final MessageInterpolator defaultInterpolator;
  private final MessageSource messageSource;

  public CustomMessageInterpolator(MessageSource messageSource) {
    this.defaultInterpolator = new ResourceBundleMessageInterpolator();
    this.messageSource = messageSource;
  }

  @Override
  public String interpolate(String messageTemplate, Context context) {
    String interpolatedMessage =
        messageSource.getMessage(messageTemplate, null, messageTemplate, null);
    return defaultInterpolator.interpolate(interpolatedMessage, context);
  }

  @Override
  public String interpolate(String messageTemplate, Context context, Locale locale) {
    String interpolatedMessage =
        messageSource.getMessage(messageTemplate, null, messageTemplate, locale);
    return defaultInterpolator.interpolate(interpolatedMessage, context, locale);
  }
}
