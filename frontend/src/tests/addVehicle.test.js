const { Builder, By, Key, until } = require('selenium-webdriver');

const BaseURL = "https://mechamate.site";

async function myVehiclesTest() {
    // Create a new instance of the WebDriver
    let driver = await new Builder().forBrowser('chrome').build();

    try {
        // Navigate to the MyVehicles page
        await driver.get(BaseURL + '/add-vehicles');

        // Find the input fields and enter values
        await driver.findElement(By.css('input[type="text"][placeholder="Toyota"]')).sendKeys('Toyota');
        await driver.findElement(By.css('input[type="text"][placeholder="Camry"]')).sendKeys('Camry');
        await driver.findElement(By.css('input[type="text"][placeholder="ABC1234"]')).sendKeys('ABC1234');

        // Select options from dropdowns
        await driver.findElement(By.css('select')).click(); // Click on the dropdown
        await driver.findElement(By.css('option[value="Car"]')).click(); // Select Car option
        await driver.findElement(By.css('select')).click(); // Click on the dropdown again
        await driver.findElement(By.css('option[value="Petrol"]')).click(); // Select Petrol option

        // Select dates using date picker
        const expDateField = await driver.findElement(By.id('vred'));
        await expDateField.click();
        await driver.findElement(By.className('react-datepicker__day--today')).click(); // Select today's date
        const insExpDateField = await driver.findElement(By.id('vied'));
        await insExpDateField.click();
        await driver.findElement(By.className('react-datepicker__day--today')).click(); // Select today's date

        // Click the Save button
        await driver.findElement(By.xpath('//button[text()="Save"]')).click();

        // Wait for confirmation message
        await driver.wait(until.elementLocated(By.xpath('//div[contains(text(), "Vehicle Registration Successful")]')), 5000);

        console.log('Test completed successfully');
    } catch (error) {
        console.error('An error occurred:', error);
    } finally {
        // Close the browser
        await driver.quit();
    }
}

// Run the test function
myVehiclesTest();
