package demo.services.film;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import demo.services.RestEndpointEnum;
import demo.utils.config.Resources;
import io.cucumber.core.logging.Logger;
import io.cucumber.core.logging.LoggerFactory;
import net.serenitybdd.rest.SerenityRest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Set;

public class Film {
    private static final Logger logger = LoggerFactory.getLogger(Film.class);
    final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
    private String latestFilm = null;

    public void latestFilm() {
        SerenityRest.get(RestEndpointEnum.GET_FILM.getURL());
        String jsonString = SerenityRest.lastResponse().prettyPrint();
        JSONObject jobj = new JSONObject(jsonString);
        JSONArray results = jobj.getJSONArray("results");

        LocalDate minReleaseDate = LocalDate.EPOCH;
        for (Object o : results) {
            JSONObject jo = (JSONObject) o;
            if (LocalDate.parse(jo.getString("release_date"), dtf).isAfter(minReleaseDate)) {
                minReleaseDate = LocalDate.parse(jo.getString("release_date"), dtf);
                latestFilm = jo.getString("title");
            }
        }
        //logger.info("latest Film is: " + latestFilm);
        System.out.println("latest Film is: " + latestFilm);
    }

    public void tallestCharacterLastFilm() {
        String jsonString = SerenityRest.lastResponse().prettyPrint();
        JSONObject jobj = new JSONObject(jsonString);
        JSONArray results = jobj.getJSONArray("results");

        int tallestCharacterHeight = 0;
        String tallestCharacter = null;
        for (Object o: results) {
            JSONObject jo = (JSONObject) o;
            if (jo.getString("title").equals(latestFilm)){
                JSONArray jaChar = jo.getJSONArray("characters");
                for (Object oChars: jaChar){
                    String joChar = (String) oChars;
                    SerenityRest.get(joChar);
                    if (tallestCharacterHeight < Integer.parseInt(SerenityRest.lastResponse().jsonPath().getString("height").toString())) {
                        tallestCharacterHeight = Integer.parseInt(SerenityRest.lastResponse().jsonPath().getString("height"));
                        tallestCharacter = SerenityRest.lastResponse().jsonPath().getString("name");
                    }
                }
            }
        }
        System.out.println("Tallest Character: " + tallestCharacter + ", height: " + tallestCharacterHeight);
    }

    public void tallestCharacter() {
        SerenityRest.get("people");

        String jsonString = SerenityRest.lastResponse().prettyPrint();
        JSONObject jobj = new JSONObject(jsonString);
        JSONArray results = jobj.getJSONArray("results");

        int tallestCharacterHeight = 0;
        String tallestCharacter = null;
        for (Object o : results) {
            JSONObject jo = (JSONObject) o;
            if (tallestCharacterHeight < Integer.parseInt(jo.getString("height").toString())) {
                tallestCharacterHeight = Integer.parseInt(jo.getString("height"));
                tallestCharacter = jo.getString("name");
            }
        }
        System.out.println("Tallest character in all films is " + tallestCharacter + " with hight " + tallestCharacterHeight);
    }

    public void contractTest() throws IOException {
        //Scanner input = new Scanner(new File(Resources.getSchema("people.json")));
        //while (input.hasNextLine())
        //    System.out.println(input.nextLine());

        SerenityRest.get(RestEndpointEnum.GET_PEOPLE.getURL());
        String jsonString = SerenityRest.lastResponse().prettyPrint();
        JSONObject jobj = new JSONObject(jsonString);
        JSONArray results = jobj.getJSONArray("results");

        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
        JsonSchema jsonSchema = factory.getSchema(Files.readString(
                Path.of(Resources.getSchema("people.json")))); //https://www.liquid-technologies.com/online-json-to-schema-converter
        //Path.of(Resources.getSchema("people_invalid.json"))));
        ObjectMapper mapper = new ObjectMapper();
        for (int i = 0, size = results.length(); i < size; i++) {
            JsonNode jsonNode = mapper.readTree(String.valueOf(results.getJSONObject(i)));
            Set<ValidationMessage> errors = jsonSchema.validate(jsonNode);
            Assert.assertTrue(errors.isEmpty());
            //System.out.println("count: " + i); // just 9. Not 82 as said
        }
    }
}
