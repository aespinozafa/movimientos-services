package ec.domain.messages;

import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageSourceUtil {

  private final MessageSource messageSource;

  public MessageSourceUtil(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  public String getMessage(String key) {
    Locale currentLocale = LocaleContextHolder.getLocale();
    return messageSource.getMessage(key, null, currentLocale);
  }
}
