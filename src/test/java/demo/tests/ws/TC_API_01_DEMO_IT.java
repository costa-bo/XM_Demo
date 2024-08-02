package demo.tests.ws;

import demo.utils.BaseTest;
import demo.utils.common.HttpStatusCode;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.annotations.WithTag;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.IOException;

@RunWith(SerenityRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TC_API_01_DEMO_IT extends BaseTest {
    @Test
    @WithTag(type = "WebService")
    @Title("TC_API_01_DEMO_01_USE_CASE: StarWars Film")
    public void TC_API_01_DEMO_01_USE_CASE() {
        step(1, "Get latest release date film")
                .rest.film.latestFilm();
        end();
        step(2, "Verify status code")
                .rest
                .validate.verifyStatusCodes(HttpStatusCode.OK.getValue());
        end();
        step(3, "Tallest character in latest film")
                .rest.film.tallestCharacterLastFilm();
        end();
        step(4, "Tallest character")
                .rest.film.tallestCharacter();
        end();
    }

    @Test
    @WithTag(type = "WebService")
    @Title("TC_API_01_DEMO_02_USE_CASE: Contract Test")
    public void TC_API_01_DEMO_02_USE_CASE() throws IOException {
        step(1, "Contract Test for People")
                .rest.film.contractTest();
        end();
    }
}
