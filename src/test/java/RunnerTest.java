import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "test.java",
        tags = "@all"
)

public class RunnerTest {

}
