package restapitests;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.*;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertEquals;

public class SpartabTestWithJsonPath {
	//ways to navigate Json and extract values
	// 1- path() method of rest assured. it used Gpath syntax(groovy)
	//2- JsonPath() / XmlPath() is used to manipulate Json data , extract values
	@BeforeClass
	public static void setUp() { // **** this is important ****
		//baseURI = utils.ConfigurationReader.getProperty("spartan.baseURI");
	}
	
	@Test
	public void test(){
		baseURI = utils.ConfigurationReader.getProperty("spartan.baseURI");
		Response response = get("/spartans/");
		assertEquals(200,response.statusCode());
	}
	
	/*
	   Given accept type is json
	   And path param spartan id is 11
	   When user sends a get request to /spartans/{id}
	   Then status code is 200
	   And content type is Json
	   And  "id": 11,
			"name": "Nona",
			"gender": "Female",
			"phone": 7959094216
		*/
	@Test
	public void test1(){
		baseURI = utils.ConfigurationReader.getProperty("spartan.baseURI");
		Response response = given().accept(ContentType.JSON).pathParam("id",11)
				.when().get("/spartans/{id}");
		
		
		JsonPath jsonPath = response.jsonPath();
		JsonPath jsonPath1 = new JsonPath(response.body().asString());//another way to do it
		assertEquals(200,response.statusCode());
		int id = jsonPath.getInt("id");
		String name = jsonPath.getString("name");
		String gender = jsonPath.getString("gender");
		long phone = jsonPath.getLong("phone");
		System.out.println("phone = " + phone);
		System.out.println("gender = " + gender);
		System.out.println("name = " + name);
		System.out.println("id = " + id);
		assertEquals(11,id);
		assertEquals("Nona",name);
		assertEquals("Female",gender);
		assertEquals(7959094216L,phone);
	}
	
	
	@Test
	public void getAStudent_cbtraining_api_jsonpath(){
		JsonPath jsonPath =when().get("http://api.cybertektraining.com/student/58201").jsonPath();
		System.out.println(jsonPath.toString());
		
		String firstName = jsonPath.getString("students.firstName");
		String lastName = jsonPath.getString("students.lastName");
		String phonenumber = jsonPath.getString("students.contact.phone");
		String email = jsonPath.getString("students.contact.emailAddress");
		String companyName = jsonPath.getString("students.company.companyName");
		String companycity = jsonPath.getString("students.company.address.city");
		System.out.println("firstName = " + firstName);
		System.out.println("lastName = " + lastName);
		System.out.println("phonenumber = " + phonenumber);
		System.out.println("email = " + email);
		System.out.println("companyName = " + companyName);
		System.out.println("companycity = " + companycity);
	}
	
	
	@Test
	public void hr_api_countries_jsonpath_list(){
		JsonPath jsonPath = get("http://ec2-54-221-8-122.compute-1.amazonaws.com:1000/ords/hr/countries").jsonPath();
		//jsonPath.prettyPrint();
		List<String> countires = jsonPath.getList("items.country_name");
		List<String > country_ids = jsonPath.getList("items.country_id");
		System.out.println("countires = " + countires);
		System.out.println("country_ids = " + country_ids);
		List<String> countriesInRegion2 =jsonPath.getList("items.findAll {it.region_id == 2}.country_name");
		System.out.println("countriesInRegion2 = " + countriesInRegion2);
		
	}
}
