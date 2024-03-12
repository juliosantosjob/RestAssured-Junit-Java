package bookstore.automation.api.tests;

import bookstore.automation.api.support.BaseTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.Yaml;
import java.io.FileInputStream;

import static bookstore.automation.api.payloads.BooksPld.getBookList;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.hamcrest.Matchers.is;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author Julio C. Santos
 */

@Tag("regression")
public class BooksTest extends BaseTest {
    private final String pathYml = "src/test/java/bookstore/automation/api/data/books.yml";
    private final Random random = new Random();
    private final Yaml yaml = new Yaml();
    private static int randomIndex;
    private String isbn, title, subTitle;

    @Test
    @Tag("accessBooks")
    @DisplayName("Access a list of available books return - 200")
    public void accessAListOfAvailableBooks() {
        try (FileInputStream inputStream = new FileInputStream(pathYml);){
            Map<String, Object> data = yaml.load(inputStream);
            List<Map<String, Object>> books = (List<Map<String, Object>>) data.get("books");

            randomIndex = random.nextInt(books.size());
            isbn = (String) books.get(randomIndex).get("isbn");
            title = (String) books.get(randomIndex).get("title");
            subTitle = (String) books.get(randomIndex).get("subTitle");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        getBookList()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("books[" + randomIndex + "].isbn", is(isbn))
                .body("books[" + randomIndex + "].title", is(title))
                .body("books[" + randomIndex + "].subTitle", is(subTitle));
    }

}
