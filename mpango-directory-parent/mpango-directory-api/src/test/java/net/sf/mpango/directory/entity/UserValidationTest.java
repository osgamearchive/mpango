package net.sf.mpango.directory.entity;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import net.sf.mpango.directory.test.DirectoryTestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.hamcrest.CoreMatchers;
import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author <a href="mailto:eduardo.devera@gmail.com">Eduardo de Vera</a>
 * TODO Abstract this so that it covers the validation of any validatable entity
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
        User testing = DirectoryTestUtils.createValidUser();
        final Set<ConstraintViolation<User>> constraintValidationSet = validator.validate(testing);
        Assert.assertThat(constraintValidationSet.size(), Is.is(CoreMatchers.equalTo(0)));
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
            DirectoryTestUtils.createUser(invalidEmail, invalidUsername, invalidPassword);
            Assert.fail("Expected exception not raised");
        } catch (final ConstraintViolationException expected) {
            final Set<ConstraintViolation<?>> constraintValidationSet = expected.getConstraintViolations();
            for (ConstraintViolation<?> violation : constraintValidationSet) {
                final String invalidValue = (String) violation.getInvalidValue();
                final String message = violation.getMessage();
                Assert.assertThat(message, Is.is(CoreMatchers.notNullValue()));
                if (invalidValue.equals(invalidEmail)) {
                    emailFailed = true;
                    Assert.assertThat("The message for an email failure is not the expected one", message, Is.is(CoreMatchers.equalTo(User.VALIDATOR_MSG_INVALID_EMAIL)));
                } else if (invalidValue.equals(invalidPassword)) {
                    passwordFailed = true;
                    Assert.assertThat("The message for an password failure is not the expected one", message, Is.is(CoreMatchers.equalTo(User.VALIDATOR_MSG_INVALID_PASSWORD)));
                } else if (invalidValue.equals(invalidUsername)) {
                    usernameFailed = true;
                    Assert.assertThat("The message for an username failure is not the expected one", message, Is.is(CoreMatchers.equalTo(User.VALIDATOR_MSG_INVALID_USERNAME)));
                } else {
                    Assert.fail("Unable to determine the failed field");
                }
            }
            Assert.assertThat("The expected number of constraints is 3", constraintValidationSet.size(), Is.is(CoreMatchers.equalTo(3)));
            Assert.assertTrue("Not all expected failures have taken place", emailFailed && usernameFailed && passwordFailed);
        }
    }

}
