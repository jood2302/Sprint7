import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class SuccessfulLoginCourierTest {

    @Rule
    public StartRules startRules = new StartRules();

    @Test
    @DisplayName("Check successful courier login")
    @Description("Check if the courier can successfully login")
    @Step("Send POST request to login")
    public void loginCourier() {
        String loginCourierJson = "courier/login/loginAllFields.json";
        File json = new File(startRules.getJsonPath() + loginCourierJson);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post(startRules.getApiUrlVersion1() + "/courier/login")
                .then()
                .assertThat()
                .body("id", notNullValue())
                .and()
                .statusCode(200);
    }
}
