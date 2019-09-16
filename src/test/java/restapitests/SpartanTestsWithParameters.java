package restapitests;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.*;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.get;
import static org.junit.Assert.*;

public class SpartanTestsWithParameters{
	@BeforeClass
	public static void setUp() { // **** this is important ****
		baseURI = "http://ec2-54-197-2-98.compute-1.amazonaws.com:8000/api/";
		
	}
	/*
Given no headers provided
When Users sends request to /api/hello
Then response status code should be 200
And Content type header should be "text/plain;charset=UTF-8"
And header should contain date
And Content-Length should be 17
And body should be "Hello from Sparta"
	 */
	@Test
	public void helloTest(){
		//request
		Response response = get("/hello/");
		
		//response
		assertEquals(200,response.statusCode());
		//verify headers
		assertEquals("text/plain;charset=UTF-8",response.contentType());
		//verify date
		System.out.println(response.getHeader("Date"));
		//this give you the headers and check if that header is there
		assertTrue(response.headers().hasHeaderWithName("Date"));
		
		assertEquals("17",response.getHeader("Content-Length"));//header response is always String
		assertEquals("Hello from Sparta",response.asString());
		assertEquals("Hello from Sparta",response.body().asString());//ORRRRRR
	}
	
	
	/*
	Positive Scenario
	send Get request with valid ID that exist in database
	
	Negative Scenario
	send Get request with invalid ID that doesn't not exist
	 */
	/*
	TestCase:
Given accept type is Json
And Id parameter value is 5
When user sends GET request to /api/spartans/{id}
Then response status code should be 200
And response content-type: application/json;charset=UTF-8
And "Blythe" should be in response payload
	 */
	@Test
	public void getSpartanByID_positive_Path_parameter_test(){
	Response response =given().accept(ContentType.JSON)
			           .and().pathParam("id",5)
			           .and().when().get("spartans/{id}");
	response.prettyPrint();
	assertEquals(200,response.statusCode());
	assertEquals("application/json;charset=UTF-8",response.contentType());
	assertTrue(response.body().asString().contains("Blythe"));
	
	}
	
	/*
Given accept type is Json
And Id parameter value is 500
When user sends GET request to /api/spartans/{id}
Then response status code should be 404
And response content-type: application/json;charset=UTF-8
And Spartan Not Found" message should be in response payload
	 */
	
	@Test
	public void getSpartanByID_Negative_Path_parameter_test() {
		//request part
		
		Response response = given().accept(ContentType.JSON)
				.and().pathParam("id", 500)
				.and().when().get("spartans/{id}");
		
		//response part
		response.prettyPrint();
		assertEquals(404, response.statusCode());
		assertEquals("application/json;charset=UTF-8", response.contentType());
		assertTrue(response.body().asString().contains("Spartan Not Found"));
	}
	
	/*
	Given accept type is Json
And query parameter values are :
    gender|Female
    nameContains|e
When user sends GET request to /api/spartans/search
Then response status code should be 200
And response content-type: application/json;charset=UTF-8
And "Female" should be in response payload
And "Janette" should be in response payload
	 */
	@Test
	public void queryParameters(){
		//all 3 responses are the same
		Response response =given().accept(ContentType.JSON)
				.and().queryParam("gender","Female")
				.and().queryParam("nameContains","e")
				.when().get("/spartans/search");
		Response response1 =given().accept(ContentType.JSON)
				.and().queryParams("gender","Female","nameContains","e")
				.when().get("/spartans/search");
		Response response2 = given().accept(ContentType.JSON).when().get("/spartans/search?gender=Female&nameContains=e");
		
		assertEquals(200,response.statusCode());
		assertEquals(200,response1.statusCode());
		assertEquals(200,response2.statusCode());
		
		assertEquals("application/json;charset=UTF-8",response.contentType());
		assertEquals("application/json;charset=UTF-8",response1.contentType());
		assertEquals("application/json;charset=UTF-8",response2.contentType());
		
		assertTrue(response.body().asString().contains("Female"));
		assertTrue(response1.body().asString().contains("Female"));
		assertTrue(response2.body().asString().contains("Female"));
		
		assertTrue(response.body().asString().contains("Janette"));
		assertTrue(response1.body().asString().contains("Janette"));
		assertTrue(response2.body().asString().contains("Janette"));
		
	}
	/*
	Given accept type is Xml
	And query parameter values are :
	gender|Female
	nameContains|e
	When user sends GET request to /api/spartans/search
	Then response status code should be 406
	And "Could not find acceptable representation" should be in response payload
   */
	
	@Test
	public void negativeTest1(){
		Response response = given().accept(ContentType.XML)
				.and().queryParams("gender","Female","nameContains","e").when().get("/spartans/search");
		
		Map<String , Object> paramsMap = new HashMap<>();
		paramsMap.put("gender","Female");
		paramsMap.put("nameContains","e");
		
		Response response1 = given().accept(ContentType.XML).and().queryParams(paramsMap).when().get("/spartans/search");
		
		
		assertEquals(406,response.statusCode());
		assertEquals(406,response1.statusCode());
		assertTrue(response.body().asString().contains("Could not find acceptable representation"));
		assertFalse(!response1.body().asString().contains("Could not find acceptable representation"));
	}
	
	
	@Test
	public void warmup(){
		Response response = given().accept(ContentType.JSON).pathParam("country","US").when().get("/{country}");
	}
	
	
}
