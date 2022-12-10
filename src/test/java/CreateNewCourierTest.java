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
public class CreateNewCourierTest {

    @Rule
    public StartRules startRules = new StartRules();
    private final String createNewCourierJson;

    public CreateNewCourierTest(String createNewCourierJson) {
        this.createNewCourierJson = createNewCourierJson;
    }
    @Parameterized.Parameters
    public static Object[][] getTestDataNew() {
        return new Object[][]{
                {"courier/create/allFields.json"},
                {"courier/create/withoutName.json"},
        };
    }

    @Test
    @DisplayName("Check new courier created")
    @Description("Check if the  new courier can be created")
    @Step("Send POST request to create courier")
    public void createNewCourier() {
        File json = new File(startRules.getJsonPath() + createNewCourierJson);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post(startRules.getApiUrlVersion1() + "/courier")
                .then()
                .assertThat()
                .body("ok", equalTo(true))
                .and()
                .statusCode(201);
    }
}
