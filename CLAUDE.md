# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project overview

Java/Maven test automation project targeting https://practicesoftwaretesting.com (a demo e-commerce "Tool Shop" site and its API at api.practicesoftwaretesting.com). Built with Playwright for browser/API automation, Serenity BDD for reporting and step annotations, JUnit 5 as the test engine, and Cucumber for the Gherkin-based feature tests. Originally built while following a Udemy Playwright course.

All source lives under `src/test/java` — there is no `src/main` production code; this repo *is* the test suite.

## Commands

Run everything from the repo root (Maven).

- Run the full suite (Playwright tests + Cucumber features) and generate the Serenity report:
  ```
  mvn verify
  ```
  Note: `maven-surefire-plugin` has `skipTests=true`, so the unit-test phase is skipped — all tests run via `maven-failsafe-plugin` in the integration-test phase (matches `**/*Test.java` and `**/*Tests.java`).

- Run a single test class:
  ```
  mvn verify -Dit.test=LoginPageTests
  ```

- Run a single test method:
  ```
  mvn verify -Dit.test=LoginPageTests#invalidUserShouldNotBeLoggedIn
  ```

- Filter Serenity/Cucumber runs by tag (passed through to the `serenity-maven-plugin`):
  ```
  mvn verify -Dtags="@smoke"
  ```

- The Serenity HTML report is aggregated post-integration-test and written to `target/site/serenity`. Cucumber's own HTML report goes to `target/cucumber-reports/cucumber.html`.

- Playwright browser binaries: if the browser isn't installed, run `mvn exec:java` equivalent isn't configured — instead use `npx playwright install` (Node/npx must be available) as done in CI, or let Playwright auto-manage it.

## CI

`.github/workflows/playwright-tests.yml` runs on every push/PR to any branch: sets up Node + JDK 17, installs Playwright OS deps (`npx playwright install-deps`), runs `mvn verify`, uploads `target/traces/*.zip` as artifacts, and deploys the Serenity report (`target/site/serenity`) to GitHub Pages.

## Architecture

**Test execution flow**: two parallel styles of tests exist side by side and share fixtures/page objects:
1. Plain JUnit 5 tests under `src/test/java/com/playwright/toolshop/tests/**` (e.g. `LoginPageTests`, `ProductPageTests`, `SearchTests`, `ProductAPITests`).
2. Cucumber/Gherkin scenarios: `src/test/resources/features/*.feature` + step definitions in `src/test/java/com/playwright/toolshop/cucumber/stepdefinitions/`, wired together by the JUnit-Platform-Suite runner `CucumberTests.java`.

**Base test runners** (`fixtures/`) own the Playwright lifecycle and are extended by every test class rather than duplicated per test:
- `BaseTestRunner` — per-test `BrowserContext`/`Page`, thread-local `Playwright`/`Browser` (headless Chromium, shared across a thread for parallel execution), sets an API `APIRequestContext` for API calls, registers the page with `PlaywrightSerenity.registerPage(page)` for Serenity+Playwright integration.
- `BaseAPITestRunner` — same idea but browser/context are class-scoped (`@BeforeAll`/`@AfterAll`) since these tests are primarily API-driven.
- `Tracer` — starts/stops Playwright tracing (screenshots, snapshots, sources) around every test, writing zips to `target/traces/trace-<test-name>.zip`.
- `TestWatcherExtension` — on a *successful* test, deletes that test's trace zip and screenshot so only failing-test artifacts are kept (uploaded by CI).
- Cucumber scenarios don't extend these runners; `PlaywrightCucumberFixtures` reimplements the same thread-local Playwright/Browser/Context/Page setup via `@Before`/`@After` hooks instead, exposing `getPage()`/`getBrowserContext()` statically to step definition classes.

Parallel execution is enabled for both JUnit (`junit-platform.properties`, fixed pool of 4) and Cucumber (same file, fixed pool of 4) — thread-local Playwright/Browser instances exist specifically to make this safe.

**Page Object Model** (`pages/`): every page object extends abstract `BasePage`, which defines `getUrl()` and common interaction helpers (`clickElementByText`, `getElementByAltText`, `navigate()`, etc.), each annotated `@Step(...)` so it shows up in the Serenity report. Concrete pages (`MainPage`, `LoginPage`, `ContactPage`, `ProductPage`, `LeftNavigationPage`) declare Playwright `Locator` fields in their constructor (mostly via `data-test` attributes through `getByTestId`, matching `playwright.selectors().setTestIdAttribute("data-test")` set globally) and add page-specific `@Step` assertion/action methods. Page objects are injected into tests via Serenity's `@Steps` annotation, not constructed manually, in the JUnit-style tests; Cucumber step definitions construct them directly, passing `PlaywrightCucumberFixtures.getPage()`.

**Utilities** (`utils/`, `testresources/`):
- `Resources` centralizes shared constants: base URLs, browser launch args, expected product-name fixtures used by multiple tests, and hardcoded credentials/paths (note: `SAMPLE_FILE_URI` is a hardcoded absolute Windows path).
- `User`, `Address`, `ProductSummary` are simple data records/POJOs; `User.randomUser()` uses `datafaker` for fake test data.
- `UserAPIClient` registers a new user directly through the API (bypassing the UI) for test setup.
- `MockAPI` wraps `page.route(...)` to stub network responses; combined with fixtures in `testresources/data/mocks/` (e.g. `ProductsMock`) for deterministic UI tests against mocked API responses.

**Reporting config**: `src/test/resources/serenity.conf` sets the project name and test root package; `allure.properties`/`.allure/` are also present for Allure-based reporting alongside Serenity.

## Conventions worth knowing

- New browser-driving tests should extend `BaseTestRunner` (or `BaseAPITestRunner` for API-only flows), and always carry `@ExtendWith(SerenityJUnit5Extension.class)`, `@ExtendWith(SerenityPlaywrightExtension.class)`, `@UsePlaywright(HeadlessChromeOptions.class)` — copy this trio from an existing test class (e.g. `LoginPageTests`).
- Locators should prefer `data-test` attributes (`page.getByTestId(...)`) to match how this app's test IDs are exposed, consistent with the global `setTestIdAttribute("data-test")`. Don't use css or xpath selectors
- Page-object action/assertion methods should be annotated `@Step("...")` so they render meaningfully in the Serenity report.
- Cucumber step definitions belong in `cucumber/stepdefinitions/`; new `.feature` files go in `src/test/resources/features/` and are auto-discovered by `CucumberTests` (`@SelectClasspathResource("features")`).
- Tests should be grouped by @Nested classes for logical structure

## Methodology
- Outside-ib: write the test first, then the page object
- One tests at a time - never batch-generate