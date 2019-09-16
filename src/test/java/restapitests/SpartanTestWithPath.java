package restapitests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.*;

import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class SpartanTestWithPath {
	/*
	how to navigate through Json and read values from the keys
	there are multiple different ways of navigating through json body payload
	
	popular ones :
	-using path() method
	- using JsonPath
	-using deserialization to a collection
	-using deserilaziation to a java custom object (POJO)
	 */
	
	/*
	When our json is not nested or in an array , we can simply call path() method and put the keyname as a parameter and it will return
	the value from the json
	it follows Gpath syntax -->groovy
	it syntax come from groovy scripting langauage  Groovy runs on JVM
	 */
	
	@BeforeClass
	public static void setUp() { // **** this is important ****
		baseURI = "http://ec2-54-197-2-98.compute-1.amazonaws.com:8000/api/";
		assertTrue(true);
		
	}
	
	/*
	Given accept type is Json
	And path param id is 10
	when user sends a get request to "/spartans/{id}"
	then status code is 200
	and content-type is "application/json"
	and payload values math the following
	"id":10
	"name":"Lorenza",
	"gender":"Female"
	"phone":3312820936
	 */
	@Test
	public void spartanPath(){
		Response response = given().accept(ContentType.JSON).and().pathParam("id",10)
				.when().get("/spartans/{id}");
		
		// the reason you have to do toString() its because path() is T so it assign to what ever you assign it to
		System.out.println(response.path("id").toString());
		System.out.println(response.path("name").toString());
		System.out.println(response.path("gender").toString());
		System.out.println(response.path("phone").toString());
		
		int id = response.body().path("id");
		String firstName = response.body().path("name");
		String spartanGender = response.path("gender");
		long phoneNumber = response.path("phone");
		
		System.out.println(id);
		System.out.println(firstName);
		System.out.println(spartanGender);
		System.out.println(phoneNumber);
		
		assertEquals(200,response.statusCode());
		assertEquals("application/json",ContentType.JSON.toString());
		assertEquals("10",response.path("id").toString());
		assertEquals("10",response.path("id").toString());
		assertEquals("Female",response.path("gender").toString());
		assertEquals("3312820936",response.path("phone").toString());
	}
	
	@Test
	public void mutliplevalue(){
		Response response = given().accept(ContentType.JSON).when().get("/spartans/");
		
		//extract first id
		int firstID = response.path("id[0]");
		System.out.println("firstID = " + firstID);
		System.out.println(response.path("id[-1]").toString());
		
		String firstName = response.path("name[0]");
		System.out.println("firstName = " + firstName);
		
		//get last first name
		
		String lastFirstName = response.path("name[-1]");//this automatically give you last name
		System.out.println("lastFirstName = " + lastFirstName);
		
		
		//extract all first names
		List<String> names = response.path("name");
		System.out.println("names = " + names);
		System.out.println("number of names "+ names.size());
		
		List<Integer> phones = response.path("phone");
		System.out.println(phones);
		for (Integer phone : phones){
			System.out.println(phone);
		}
		
	}
	//                   the dot goes from parent to child
	//response.path("items.country_name[0]);
	
	@Test
	public void mutliplevalue2() {
		baseURI = "http://ec2-54-164-195-86.compute-1.amazonaws.com:1000/ords/hr";
		
		Response response = given().queryParam("q","{\"region_id\":2}").when().get("/countries");
		Response response1 = given().queryParam("q","{\"region_id\":2}").get("/countries");
		
		
		String firstCountryId = response.path("items.country_id[0]").toString();
		String firstCountryName = response.path("items.country_name[0]").toString();
		System.out.println("firstCountryId = " + firstCountryId);
		System.out.println("firstCountryName = " + firstCountryName);
		
		List<String > countries = response.path("items.country_name");
		
		List<Integer> regionID = response.path("items.region_id");
		
		for(int region : regionID){
			assertEquals(2,region);
			System.out.println("match");
		}
	}
}

