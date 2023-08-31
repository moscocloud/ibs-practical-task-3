package ru.ibs.runner;

import org.junit.platform.suite.api.*;

import static io.cucumber.core.options.Constants.*;

@Suite
@IncludeEngines("cucumber")
@ConfigurationParameters({
//        @ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "@all"),
        @ConfigurationParameter(key = FEATURES_PROPERTY_NAME, value = "src/test/resources/features"),
        @ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "ru.ibs.framework.cucumberSteps, ru.ibs.framework.hooks"),
        @ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "ru.ibs.framework.core.utils.MyAllureListener")
})
public class TestRunner {

}
