const { Builder, By, Key, until } = require('selenium-webdriver');

async function clickParkingFinderButton() {
    // Create a new instance of the WebDriver
    let driver = await new Builder().forBrowser('chrome').build();
    
    try {
        // Open the dashboard page
        await driver.get('http://localhost:3000/dashboard');

        // Wait until the button with the text "Parking Finder" is visible
        await driver.wait(until.elementLocated(By.xpath("//button[contains(text(), 'Parking Finder')]")), 10000);

        // Find the button by its text
        const parkingFinderButton = await driver.findElement(By.xpath("//button[contains(text(), 'Parking Finder')]"));

        // Click the button
        await parkingFinderButton.click();

        // Wait for a brief moment to allow any actions triggered by the button click to complete
        await driver.sleep(2000);
    } finally {
        // Close the browser
        await driver.quit();
    }
}

// Call the function to execute the test
clickParkingFinderButton();
