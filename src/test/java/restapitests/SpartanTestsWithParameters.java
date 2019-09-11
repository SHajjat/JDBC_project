package restapitests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.*;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.get;

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
		Assert.assertEquals(200,response.statusCode());
		//verify headers
		Assert.assertEquals("text/plain;charset=UTF-8",response.contentType());
		//verify date
		System.out.println(response.getHeader("Date"));
		//this give you the headers and check if that header is there
		Assert.assertTrue(response.headers().hasHeaderWithName("Date"));
		
		Assert.assertEquals("17",response.getHeader("Content-Length"));//header response is always String
		Assert.assertEquals("Hello from Sparta",response.asString());
		Assert.assertEquals("Hello from Sparta",response.body().asString());//ORRRRRR
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
	Assert.assertEquals(200,response.statusCode());
	Assert.assertEquals("application/json;charset=UTF-8",response.contentType());
	Assert.assertTrue(response.body().asString().contains("Blythe"));
	
	}
	
}
