# Playwright Tool Shop Tests

Test automation project built while learning Playwright with Java, based on the
[Mastering Modern Test Automation with Playwright in Java](https://www.udemy.com/course/mastering-modern-test-automation-with-playwright-in-java/) Udemy course.

The suite exercises [practicesoftwaretesting.com](https://practicesoftwaretesting.com/), a demo e-commerce "Tool Shop" site, and its API (`api.practicesoftwaretesting.com`), covering UI flows (login, search, product catalog, contact form) and API requests/mocking.

## Tech stack

- **Playwright** — browser automation and API requests
- **JUnit 5** — test engine, including parallel execution
- **Cucumber** — Gherkin feature tests (`src/test/resources/features`)
- **Serenity BDD** — test reporting and step annotations
- **AssertJ / JUnit Assertions** — assertions
- **Datafaker** — random test data generation

## Project structure

- `src/test/java/com/playwright/toolshop/pages` — Page Object Model classes
- `src/test/java/com/playwright/toolshop/tests` — JUnit 5 test classes
- `src/test/java/com/playwright/toolshop/cucumber` — Cucumber suite runner and step definitions
- `src/test/resources/features` — Gherkin feature files
- `src/test/java/com/playwright/toolshop/fixtures` — base test runners, tracing, and test lifecycle hooks
- `src/test/java/com/playwright/toolshop/utils` — test data models, API client, and request mocking helpers

## Running the tests

Run the full suite (JUnit tests + Cucumber features) and generate the Serenity report:

```bash
mvn verify
```

Run a single test class or method:

```bash
mvn verify -Dit.test=LoginPageTests
mvn verify -Dit.test=LoginPageTests#invalidUserShouldNotBeLoggedIn
```

Filter by Serenity/Cucumber tag:

```bash
mvn verify -Dtags="@smoke"
```

## Reports and artifacts

- Serenity HTML report: `target/site/serenity`
- Cucumber HTML report: `target/cucumber-reports/cucumber.html`
- Playwright traces for failed tests: `target/traces/*.zip`

CI (`.github/workflows/playwright-tests.yml`) runs the suite on every push/PR, uploads failure traces as artifacts, and publishes the Serenity report to GitHub Pages.
