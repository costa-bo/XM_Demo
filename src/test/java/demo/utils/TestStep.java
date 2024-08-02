package demo.utils;

//import net.thucydides.core.annotations.Step;
import net.serenitybdd.annotations.Step;
import org.junit.Ignore;

public class TestStep {

    @Step("{0}")
    public void generateSerenityStep(String shortDescription) {
        System.out.println(shortDescription);
    }

    @Step("{0}")
    @Ignore
    public void generateSkippedSerenityStep(String shortDescription) {
        System.out.println(shortDescription);
    }

}
