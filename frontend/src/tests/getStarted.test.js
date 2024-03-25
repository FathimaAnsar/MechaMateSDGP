const { Builder, By, Key, until } = require('selenium-webdriver');

const BaseURL = "https://mechamate.site";

async function clickGetStartedButton() {
    // Initialize the WebDriver
    let driver = await new Builder().forBrowser('chrome').build();

    try {
        // Navigate to the webpage
        await driver.get(BaseURL + '/get-started');

        // Find the "Get Started" button by its text
        const getStartedButton = await driver.findElement(By.xpath("//button[contains(text(), 'Get Started')]"));

        // Click the "Get Started" button
        await getStartedButton.click();

       // Wait for the button on the next page to ensure it has loaded
        await driver.wait(until.elementLocated(By.id('get-started-button')), 5000);

        console.log("Clicked on the 'Get Started' button successfully.");
    } catch (error) {
        console.error("An error occurred:", error);
    } finally {
        // Quit the WebDriver session
        await driver.quit();
    }
}

// Call the function to execute the test
clickGetStartedButton();
