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

@RunWith(Parameterized.class)
public class CreateCourierTest {
    @Rule
    public StartRules startRules = new StartRules();

    private final String createCourierJson;

    public CreateCourierTest(String createCourierJson) {
        this.createCourierJson = createCourierJson;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {"courier/create/allFields.json"},
                {"courier/create/duplicateUser.json"},
                {"courier/create/withoutLogin.json"},
                {"courier/create/withoutPassword.json"},
                {"courier/create/withoutName.json"},
        };
    }

    @Test
    @DisplayName("Check courier created")
    @Description("Check if the courier can be created")
    @Step("Send POST request to create courier")
    public void createCourier() {
        File json = new File(startRules.getJsonPath() + createCourierJson);
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post(startRules.getApiUrlVersion1() + "/courier");

        switch( response.getStatusCode()) {
            case 201:
                response.then().assertThat().body("ok", equalTo(true));
                break;
            case 400:
                response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
                break;
            case 409:
                response.then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
                break;
        }
    }
}
