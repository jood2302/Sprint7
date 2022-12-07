import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class LoginCourierTest {
    @Rule
    public StartRules startRules = new StartRules();

    private final String loginCourierJson;

    public LoginCourierTest(String loginCourierJson) {
        this.loginCourierJson = loginCourierJson;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {"courier/login/loginAllFields.json"},
                {"courier/login/unexistingLogin.json"},
                {"courier/login/wrongLogin.json"},
                {"courier/login/wrongPassword.json"},
        };
    }


    @Test
    @DisplayName("Check courier login")
    @Description("Check if the courier can login")
    @Step("Send POST request to login")
    public void loginCourier() {
        File json = new File(startRules.getJsonPath() + loginCourierJson);
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post(startRules.getApiUrlVersion1() + "/courier/login");

        switch( response.getStatusCode()) {
            case 200:
                response.then().assertThat().body("id", notNullValue());
                break;
            case 400:
                response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"));
                break;
            case 404:
                response.then().assertThat().body("message", equalTo("Учетная запись не найдена"));
                break;
        }
    }
}
