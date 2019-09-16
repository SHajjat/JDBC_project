package restapitests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.*;
import sun.awt.image.GifImageDecoder;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class HRAPiORDS {
	
	
	
	
	@BeforeClass
	public static void setUp() { // **** this is important ****
		baseURI = "http://ec2-54-164-195-86.compute-1.amazonaws.com:1000/ords/hr";
		
	}
	
	/*
	given accept type is json
	path param value - US
	when users send request to countries
	then status code is 200
	and coutnry_id is US
	and country_name is United state of america
	and region_id is 2
	
	 */
	@Test
	public void vefityCountryDetaiksUsing_path_method(){
		Response response = given().accept(ContentType.JSON).pathParam("country_id","US").when().get("/countries/{country_id}");
		assertEquals(response.statusCode(),200);
		assertEquals(response.body().path("country_name"),"United States of America");
		assertEquals(response.body().path("country_id"),"US");
		assertEquals((int)response.body().path("region_id"),2);
		
	}
	
	/*
	Given accept type is json
	query param -q{"department_id":"80"}
	when users send request to /employees
	then status code is 200
	and all job_id statrt with 'SA'
	and all departmen_id are 80
	count is 25
	
	 */
	
	@Test
	public void vefityCountryDetaiksUsing_query_param(){
		Response response = given().accept(ContentType.JSON).queryParam("q","{\"department_id\":80}")
				.and().when().get("/employees");
		
		assertEquals(200,response.statusCode());
		assertEquals(25,(int)response.path("count"));
		List<String > job_id = response.path("items.job_id");
		List<Integer> departmentID = response.path("items.department_id");
		
		for(int i = 0 ; i<job_id.size() && i< job_id.size() ;i++){
			assertTrue(job_id.get(i).startsWith("SA"));
			assertTrue(departmentID.get(i) ==80);
		}
		
		departmentID.forEach(id ->assertEquals(80,(int)id));
		job_id.forEach(id->assertTrue(id.startsWith("SA")));
	}
	//ways to navigate Json and extract values
	// 1- path() method of rest assured. it used Gpath syntax(groovy)
	//2- JsonPath() / XmlPath() is used to manipulate Json data , extract values
	
}
