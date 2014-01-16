package net.sf.mpango.common.utils;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author <a href="mailto:eduardo.devera@gmail.com">Eduardo de Vera</a>
 */
public class LocalizedMessageBuilder {

    public enum MessageType {

        SYSTEM(".systemMessages"),
        UI(".userMessages");

        private final String baseNameAppendix;

        private MessageType(final String baseNameAppendix) {
            assert baseNameAppendix != null;
            this.baseNameAppendix = baseNameAppendix;
        }

        public String getBaseNameAppendix() {
            return this.baseNameAppendix;
        }
    }

    private LocalizedMessageBuilder() {}

    public static String getSystemMessage(final Object caller, final String key) {
        return getMessage(caller, MessageType.SYSTEM, Locale.getDefault(), key);
    }

    public static String getSystemMessage(final Object caller, final String key, final Object... values) {
        return getMessage(caller, MessageType.SYSTEM, Locale.getDefault(), key, values);
    }

    public static String getUserMessage(final Object caller, final Locale locale, final String key) {
        return getMessage(caller, MessageType.UI, locale, key);
    }

    public static String getUserMessage(final Object caller, final Locale locale, final String key, final Object... values) {
        return getMessage(caller, MessageType.UI, locale, key, values);
    }

    private static String getMessage (final Object caller, final MessageType type, final Locale locale, final String key) {
        final String message = getPattern(caller, type, key, locale);
        return message;
    }

    private static String getMessage(final Object caller, final MessageType type, final Locale locale, final String key, final Object... values) {
        final String pattern = getPattern(caller, type, key, locale);
        final MessageFormat messageFormat = new MessageFormat(pattern, locale);
        return messageFormat.format(values);
    }

    private static String getPattern(final Object caller, final MessageType type, final String key, final Locale locale) {
        final String packageName = caller.getClass().getPackage().getName();
        final ResourceBundle resourceBundle = ResourceBundle.getBundle(packageName + type.getBaseNameAppendix(), locale);
        return resourceBundle.getString(key);
    }
}