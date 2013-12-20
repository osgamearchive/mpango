package net.sf.mpango.common.directory.entity;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import net.sf.mpango.common.test.CommonTestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author <a href="mailto:eduardo.devera@gmail.com">Eduardo de Vera</a>
 */
public class UserValidationTest {

    private static Validator validator;

    @BeforeClass
    public static void setClassUp() {
        final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Before
    public void setUp() {}

    @After
    public void tearDown() {

    }

    @Test
    public void validation_successsful() {
        User testing = CommonTestUtils.createValidUser();
        final Set<ConstraintViolation<User>> constraintValidationSet = validator.validate(testing);
        assertThat(constraintValidationSet.size(), is(equalTo(0)));
    }

    @Test
    public void validation_unsuccessful() {

        boolean emailFailed = false;
        boolean usernameFailed = false;
        boolean passwordFailed = false;

        final String invalidEmail = RandomStringUtils.random(8);
        final String invalidUsername = RandomStringUtils.random(8);
        final String invalidPassword = RandomStringUtils.random(8);

        try {
            CommonTestUtils.createUser(invalidEmail, invalidUsername, invalidPassword);
            fail("Expected exception not raised");
        } catch (ConstraintViolationException expected) {
            final Set<ConstraintViolation<?>> constraintValidationSet = expected.getConstraintViolations();
            for (ConstraintViolation<?> violation : constraintValidationSet) {
                final String invalidValue = (String) violation.getInvalidValue();
                final String message = violation.getMessage();
                assertThat(message, is(notNullValue()));
                if (invalidValue.equals(invalidEmail)) {
                    emailFailed = true;
                    assertThat("The message for an email failure is not the expected one", message, is(equalTo(User.VALIDATOR_MSG_INVALID_EMAIL)));
                } else if (invalidValue.equals(invalidPassword)) {
                    passwordFailed = true;
                    assertThat("The message for an password failure is not the expected one", message, is(equalTo(User.VALIDATOR_MSG_INVALID_PASSWORD)));
                } else if (invalidValue.equals(invalidUsername)) {
                    usernameFailed = true;
                    assertThat("The message for an username failure is not the expected one", message, is(equalTo(User.VALIDATOR_MSG_INVALID_USERNAME)));
                } else {
                    fail("Unable to determine the failed field");
                }
            }
            assertThat("The expected number of constraints is 3", constraintValidationSet.size(), is(equalTo(3)));
            assertTrue("Not all expected failures have taken place", emailFailed && usernameFailed && passwordFailed);
        }
    }

}
