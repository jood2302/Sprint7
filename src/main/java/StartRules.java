import io.restassured.RestAssured;
import org.junit.rules.ExternalResource;



public class StartRules extends ExternalResource {

    public String apiUrl;
    public String jsonPath;
    public String getApiUrlVersion1() {
        return apiUrl;
    }

    public  String getJsonPath() {
        return jsonPath;
    }
    @Override
    protected void before() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/api/v1";
        apiUrl =  RestAssured.baseURI;
        jsonPath = "src/test/resources/";
    }
}
