const { Builder, By, Key, until } = require('selenium-webdriver');

async function dashboardTest() {
    let driver = await new Builder().forBrowser('chrome').build();
    try {
        // Navigate to the dashboard page
        await driver.get('http://localhost:3000/dashboard');

        // Wait for the button to be clickable
        await driver.wait(until.elementLocated(By.id('dash-park_btn')), 5000);

       
        // Wait for the button to be located
        const button = await driver.wait(until.elementLocated(By.id('dash-park_btn')), 5000);

        // Click on the button
        await button.click();

        // Wait for the parking-finder page to load
        await driver.wait(until.urlIs('http://localhost:3000/parking-finder'), 5000);

        console.log("Button clicked successfully. Navigated to the parking-finder page.");
    } catch (error) {
        console.error("An error occurred:", error);
    } finally {
        
        // Close the browser
        await driver.quit();
    }
}

// Execute the test function
dashboardTest();
