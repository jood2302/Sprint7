import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    @Rule
    public StartRules startRules = new StartRules();

    private final String orderJson;
    public CreateOrderTest(String orderJson) {
        this.orderJson = orderJson;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {"orders/newOrderBlack.json"},
                {"orders/newOrderGrey.json"},
                {"orders/newOrderBlackAndGrey.json"},
                {"orders/newOrderWithoutColor.json"},
        };
    }

    @Test
    @DisplayName("Check creating order")
    @Description("Check if order can be created")
    @Step("Send POST request to create order")
    public void createNewOrderBlack() {
        File json = new File(startRules.getJsonPath() + orderJson);
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post(startRules.getApiUrlVersion1() + "/orders");
        response.then().assertThat().body("track", notNullValue());
    }
}
