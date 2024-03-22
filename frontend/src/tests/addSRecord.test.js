const { Builder, By, Key, until } = require('selenium-webdriver');

async function testAddServiceRecord() {
    // Create a new instance of the WebDriver
    let driver = await new Builder().forBrowser('chrome').build();

    try {
        // Navigate to the page
        await driver.get('URL_OF_YOUR_REACT_APP');

        // Fill in the form fields
        await driver.findElement(By.id('services')).sendKeys('EngineOilChange');
        await driver.findElement(By.id('description')).sendKeys('Performed engine oil change');
        await driver.findElement(By.id('date')).sendKeys('2024-03-15'); // Set service date
        await driver.findElement(By.id('mileage')).sendKeys('50000');

        // Submit the form
        await driver.findElement(By.xpath('//button')).click();


        // Wait for success alert
        await driver.wait(until.elementLocated(By.xpath('//div[contains(text(), "Service Record Added Successfully")]')), 5000);

        console.log('Service record added successfully');
    } catch (error) {
        console.error('An error occurred:', error);
    } finally {
        // Close the browser
        await driver.quit();
    }
}

// Run the test function
testAddServiceRecord();
