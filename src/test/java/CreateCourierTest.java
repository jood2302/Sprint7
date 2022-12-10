import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class CreateCourierTest {
    @Rule
    public StartRules startRules = new StartRules();

    private final String createCourierJson;
    private final String result;
    private final int status;

    public CreateCourierTest(String createCourierJson, String result, int status) {
        this.createCourierJson = createCourierJson;
        this.result = result;
        this.status = status;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {"courier/create/duplicateUser.json",  "Этот логин уже используется. Попробуйте другой.", 409},
                {"courier/create/withoutLogin.json",  "Недостаточно данных для создания учетной записи", 400},
                {"courier/create/withoutPassword.json","Недостаточно данных для создания учетной записи", 400},
        };
    }

    @Test
    @DisplayName("Check courier created")
    @Description("Check if the courier can be created without required parameters or with the same login")
    @Step("Send POST request to create courier")
    public void createCourier() {
        File json = new File(startRules.getJsonPath() + createCourierJson);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post(startRules.getApiUrlVersion1() + "/courier")
                .then()
                .assertThat()
                .body("message", equalTo(result))
                .and()
                .statusCode(status);
    }
}
