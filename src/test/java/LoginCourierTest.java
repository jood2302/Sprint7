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
    private final String result;
    private final int status;
    public LoginCourierTest(String loginCourierJson, String result, int status) {
        this.loginCourierJson = loginCourierJson;
        this.result=result;
        this.status=status;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {"courier/login/unexistingLogin.json", "Учетная запись не найдена", 404},
                {"courier/login/wrongLogin.json", "Недостаточно данных для входа", 400},
                {"courier/login/wrongPassword.json", "Недостаточно данных для входа", 400},
        };
    }


    @Test
    @DisplayName("Check courier login")
    @Description("Check if the courier can login")
    @Step("Send POST request to login")
    public void loginCourier() {
        File json = new File(startRules.getJsonPath() + loginCourierJson);
            given()
                    .header("Content-type", "application/json")
                    .and()
                    .body(json)
                    .when()
                    .post(startRules.getApiUrlVersion1() + "/courier/login")
                    .then()
                    .assertThat()
                    .body("message", equalTo(result))
                    .and()
                    .statusCode(status);
    }
}
