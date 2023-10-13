package bookstore.automation.api.runners;

import bookstore.automation.api.tests.BooksTest;
import bookstore.automation.api.tests.DeleteAccountTest;
import bookstore.automation.api.tests.LoginTest;
import bookstore.automation.api.tests.RegistUserTest;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ DeleteAccountTest.class, LoginTest.class, RegistUserTest.class, BooksTest.class })
@SelectPackages("src/test/java/bookstore/automation/api/tests")
@IncludeTags("accssBooks")
public class RunningTests {}