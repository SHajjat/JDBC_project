package restapitests;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;

import org.hamcrest.Matchers;
import org.junit.*;

import java.util.*;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertEquals;
public class HamcrestMatchers_APITest {
	/*given accept type is Json
	and path param id 15
	when user send get request to spartans/{id}
	then status code is 200
	and Content type is json
	and
	 */
	@Test
	public void getSpartanAnd_assertUsing_hamcrest(){
		given().accept(ContentType.JSON).and().pathParam("id",15)
				.when().get("http://ec2-54-221-8-122.compute-1.amazonaws.com:8000/api/spartans/{id}")
				.then().assertThat().contentType(ContentType.JSON).assertThat().statusCode(200)
				.and().assertThat().body("id", equalTo(15),"name",equalTo("Meta"),
				"gender",equalTo("Female"),"phone",equalTo(1938695106));
	}
	
	@Test
	public void getCybertrainingTeacherName(){
		//http://api.cybertektraining.com/teacher/name/Esen
		/*
		 “teacherId”: 2381,
     “firstName”: “Esen”,
     “lastName”: “Niiazov”,
     “emailAddress”: “eniiazov@gmail.com”
		 */
		given().accept(ContentType.JSON).and().pathParam("name","Esen").when().get("http://api.cybertektraining.com/teacher/name/{name}")
				.then().assertThat().contentType(ContentType.JSON).assertThat().statusCode(200)
				.and().body("teachers.firstName",contains("Esen"),"teachers.lastName",contains("Niiazov")
				,"teachers.emailAddress",contains("eniiazov@gmail.com")).log().all();// this will print out on the console
	}
	
	
	//send ger request to the following url
	//
	
	@Test
	public void WelcomeToMaps(){
		given().accept(ContentType.JSON).pathParam("department","Computer")
				.when().get("http://api.cybertektraining.com/teacher/department/{department}")
		.then().contentType(ContentType.JSON).statusCode(200).body("teachers.firstName",Matchers.hasItems("Esen","Mike","ULANBEK","Albina"));
	}
	
	
	/*
	Send Get request to : "http://ec2-54-221-8-122.compute-1.amazonaws.com:8000/api/spartans/{id}"
	Convert Json response to map
	print map values
	 */
	
	@Test
	public void MapIntroduction(){
		Map<String , Object> map = given().accept(ContentType.JSON).and().pathParam("id",33).
		when().get("http://ec2-54-221-8-122.compute-1.amazonaws.com:8000/api/spartans/{id}")
				.body().as(Map.class);
		System.out.println(map);
	}
	
}
