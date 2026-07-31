package com.playwright.toolshop.cucumber;

import io.cucumber.junit.CucumberOptions;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("/features")
@ConfigurationParameter(
        key="cucumber.plugin",
        value="io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm," +
              "pretty," +
                "html:target/cucumber-reports/cucumber.html"
)
@CucumberOptions(
        features= "src/test/resources/features",
        glue = {"src.test.java.com.playwright.toolshop.cucumber.stepdefinitions"}
)
public class CucumberTestSuite {
}
