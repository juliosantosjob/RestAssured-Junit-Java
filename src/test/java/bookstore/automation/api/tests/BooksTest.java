package bookstore.automation.api.tests;

import bookstore.automation.api.domain.LoginDmn;
import bookstore.automation.api.support.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static bookstore.automation.api.payloads.BooksPld.getBookList;
import static bookstore.automation.api.payloads.LoginPld.login;
import static bookstore.automation.api.support.PropertiesSupport.getSecret;
import static org.hamcrest.Matchers.is;

/**
 * @author Julio C. Santos
 */

@Tag("regression")
public class BooksTest extends BaseTest {
    private final String userName = getSecret("NAME");
    private final String password = getSecret("PASSWORD");
    private final LoginDmn user = new LoginDmn(userName, password);
    private String token;

    @Test
    @Tag("accssBooks")
    @DisplayName("Access a list of available books return - 200")
    public void accessAListOfAvailableBooks() {
        getBookList()
                .then()
                .body(
                        "books[0].isbn", is("9781449325862"),
                        "books[0].title", is("Git Pocket Guide"),
                        "books[0].subTitle", is("A Working Introduction"));
    }

}