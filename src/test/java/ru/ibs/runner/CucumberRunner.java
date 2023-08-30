package ru.ibs.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/scenario"},
        glue = {"ru/ibs/framework/cucumberSteps",
                "ru/ibs/framework/hooks"},
        tags = {"@regress"},
        plugin = {"ru.ibs.framework.utils.MyAllureListener"})
public class CucumberRunner {
    
}
