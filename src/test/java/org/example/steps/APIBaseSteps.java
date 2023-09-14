package org.example.steps;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.APIBaseTest;
import org.junit.Assert;

public class APIBaseSteps extends APIBaseTest {

    private static final Logger logger = LogManager.getLogger(APIBaseSteps.class);

    public static final String BaseUrl = "apiUrl";
    private Response response;

    @Step("API <api> için GET isteğini test et")
    public void testGETRequest(String api) {
        response = RestAssured.get(api);
        int statusCode = response.getStatusCode();
        logger.info("GET Request Status Code: " + statusCode);
        Assert.assertEquals(200, statusCode);
    }

    @Step("API <api> için POST isteğini test et")
    public void testPOSTRequest(String api) {
        RequestSpecification request = RestAssured.given();
        response = request.post(api);
        int statusCode = response.getStatusCode();
        logger.info("POST Request Status Code: " + statusCode);
        Assert.assertEquals(200, statusCode);
    }

    @Step("API <api> için <type> isteğinin olmadığını test et")
    public static void testApiRequest(String api, String type) {
        Response response = null;

        if (type.equalsIgnoreCase("GET")) {
            response = RestAssured.get(api);
        } else if (type.equalsIgnoreCase("POST")) {
            response = RestAssured.post(api);
        } else {
            // Geçerli bir istek türü belirtilmediyse, hata veriyoruz.
            Assert.fail("Geçerli bir istek türü belirtmelisiniz (GET, POST)");
        }

        // Yanıtın null olmadığını ve durum kodunun 405 (Method Not Allowed) olduğunu doğrula
        Assert.assertNotNull(response);
        Assert.assertEquals(405, response.getStatusCode());
    }


    @Step("<BaseUrl> API'sine giriş yap ve başlığı kullanarak test et")
    public void testUserEndpoint(String BaseUrl) {
        // Kullanıcı girişi yapılır ve başlık alınır
        // Bu işlemi taklit eden bir kod eklemelisiniz
        String userToken = performUserLoginAndReturnToken();

        // Başlık kontrol edilir
        RequestSpecification request = RestAssured.given().header("Authorization", "Bearer " + userToken);
        response = request.get(BaseUrl);
        int statusCode = response.getStatusCode();
        logger.info("User Endpoint GET Request Status Code: " + statusCode);
        Assert.assertEquals(200, statusCode);
    }

    @Step("API <api> için kullanıcı adı <username> ve şifre <password> ile kimlik doğrulama işlemini test et")
    public void testApiAuthentication(String api, String username, String password) {
        RequestSpecification request = RestAssured.given().auth().basic(username, password);
        response = request.get(api);
        int statusCode = response.getStatusCode();
        logger.info("Authenticated GET Request Status Code: " + statusCode);
        Assert.assertEquals(200, statusCode);
    }

    @Step("API <api> için <operation> matematik işlemini <paramTable> parametreleri ile test et")
    public void testMathOperationsEndpoint(String api, String operation, Table paramTable) {
        int[] params = paramTable.getColumnValues("params").stream().mapToInt(Integer::parseInt).toArray();
        if (params.length > 5) {
            logger.error("Too many parameters provided. Maximum allowed is 5.");
            return;
        }

        RequestSpecification request = RestAssured.given();
        StringBuilder paramsString = new StringBuilder();
        for (int param : params) {
            paramsString.append(param).append(",");
        }
        paramsString.deleteCharAt(paramsString.length() - 1); // Son virgülü kaldır
        request.param("params", paramsString.toString());

        response = request.post(api + "/" + operation);
        int statusCode = response.getStatusCode();
        logger.info("Math Operations POST Request Status Code: " + statusCode);
        Assert.assertEquals(200, statusCode);

        String responseBody = response.getBody().asString();
        logger.info("Math Operations Response: " + responseBody);
        Assert.assertTrue(responseBody.contains("result"));
    }
}