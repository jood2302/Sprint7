import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.*;
import org.junit.Rule;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class OrderListCheckTest {
    @Rule
    public StartRules startRules = new StartRules();

    @Test
    @DisplayName("Check order list")
    @Description("Check that a list of orders is returned in the response body")
    @Step("Send GET request to orders")
    public void getListOrder() {
       given()
               .get(startRules.getApiUrlVersion1() + "/orders")
               .then()
               .assertThat()
               .body("orders", notNullValue())
               .and()
               .statusCode(200);
    }
}
