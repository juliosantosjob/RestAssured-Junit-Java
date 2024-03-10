package bookstore.automation.api.runners;

import bookstore.automation.api.tests.BooksTest;
import bookstore.automation.api.tests.DeleteAccountTest;
import bookstore.automation.api.tests.LoginTest;
import bookstore.automation.api.tests.RegisterTest;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        DeleteAccountTest.class,
        LoginTest.class,
        RegisterTest.class,
        BooksTest.class
})
@SelectPackages("src/test/java/bookstore/automation/api/tests")
@IncludeTags("regression")
public class RunningTests {
}