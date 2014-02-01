package net.sf.mpango.common.utils;

import java.util.Locale;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author <a href="mailto:eduardo.devera@gmail.com">Eduardo de Vera</a>
 */
public class LocalizedMessageBuilderTest {

    private static final String EXPECTED_MESSAGE = "Hello Internationalization world";
    private static final String EXPECTED_MESSAGE_WITH_VALUES = "Hello user World 1 of Internationalization";

    @Test
    public void getLoggingMessage() {
        final String message = LocalizedMessageBuilder.getSystemMessage(this, "message.key");
        assertThat(message, is(equalTo(EXPECTED_MESSAGE)));
    }

    @Test
    public void getLoggingMessageWithValues() {
        final String message = LocalizedMessageBuilder.getSystemMessage(this, "message.key_with_values", "user", 1);
        assertThat(message, is(equalTo(EXPECTED_MESSAGE_WITH_VALUES)));
    }

    @Test
    public void getUserMessage() {
        final String message = LocalizedMessageBuilder.getUserMessage(this, Locale.getDefault(), "user.message.key");
        assertThat(message, is(equalTo(EXPECTED_MESSAGE)));
    }

    @Test
    public void getUserMessageWithValues() {
        final String message = LocalizedMessageBuilder.getUserMessage(this, Locale.getDefault(), "user.message.key_with_values", "user", 1);
        assertThat(message, is(equalTo(EXPECTED_MESSAGE_WITH_VALUES)));
    }
}
