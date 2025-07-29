package com.sadou.archivage.bdd;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/com.sadou.archivage.bdd",
    glue = "com.sadou.archivage.bdd",
    plugin = {"pretty", "html:target/cucumber-reports"},
    monochrome = true
)
public class RunCucumberTest {
}
