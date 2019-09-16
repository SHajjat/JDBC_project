package restapitests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.*;
import utils.ConfigurationReader;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class HRAPIwithParameters {
	@BeforeClass
	public static void setUp() { //
		baseURI = ConfigurationReader.getProperty("hrapi.baseuri");
	}
	
	@Test
	public void getCuntriesUsingQueryParams(){
		
		Response response = given().accept(ContentType.JSON).and().queryParam("q","{\"region_id\":2}").when().get(
				"/countries");
		
		response.prettyPrint();
		Assert.assertEquals(response.statusCode(),200);
		Assert.assertEquals("application/json",response.contentType());
		Assert.assertTrue(response.body().asString().contains("United States of America"));
	}
	
	@Test
	public void getCuntriesUsingQueryParamsEmployees(){
		
		Response response = given().accept(ContentType.JSON).and().queryParam("q","{\"job_id\":\"IT_PROG\"}").when().get(
				"/employees");
		
		response.prettyPrint();
		Assert.assertEquals(response.statusCode(),200);
		Assert.assertEquals("application/json",response.contentType());
		Assert.assertTrue(response.body().asString().contains("Alexander"));
		Assert.assertTrue(response.body().asString().contains("IT_PROG"));
	}
	
	

}
