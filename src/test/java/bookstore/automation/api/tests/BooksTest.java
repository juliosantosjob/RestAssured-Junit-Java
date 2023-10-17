package bookstore.automation.api.tests;

import bookstore.automation.api.support.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static bookstore.automation.api.payloads.BooksPld.getBookList;
import static org.hamcrest.Matchers.is;

/**
 * @author Julio C. Santos
 */

@Tag("regression")
public class BooksTest extends BaseTest {

    @Test
    @Tag("accessBooks")
    @DisplayName("Access a list of available books return - 200")
    public void accessAListOfAvailableBooks() {
        getBookList()
                .then()
                    .body("books[0].isbn", is("9781449325862"))
                    .body("books[0].title", is("Git Pocket Guide"))
                    .body("books[0].subTitle", is("A Working Introduction"));
    }

}