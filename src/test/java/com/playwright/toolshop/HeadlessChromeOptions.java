package com.playwright.toolshop;

import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;

import static com.playwright.toolshop.resources.Resources.BROWSER_LAUNCH_OPTION;

public class HeadlessChromeOptions implements OptionsFactory {

    @Override
    public Options getOptions() {
        return new Options().
                setHeadless(false).
                setLaunchOptions(new BrowserType.LaunchOptions()
                        .setArgs(BROWSER_LAUNCH_OPTION))
                        .setTestIdAttribute("data-test");
    }
}
