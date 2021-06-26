import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        publish = true
)

public class RunnerTest {

}
