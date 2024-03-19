const {  Builder, By } = require('selenium-webdriver');
const webdriver = require('./webdriver');

// Define the test function
async function signInTest() {
    let driver = await new Builder().forBrowser('chrome').build();
    try {
        // Navigate to the sign-in page
        await webdriver.get('http://localhost:3000/signin');

        // Find the sign-in input fields and submit button
        const usernameInput = await webdriver.findElement(By.id('username'));
        const passwordInput = await webdriver.findElement(By.id('password'));
        const submitButton = await webdriver.findElement(By.xpath("//button[contains(text(), 'Sign In')]"));

        // Enter credentials
        await usernameInput.sendKeys('justin');
        await passwordInput.sendKeys('Pass123!');

        // Click the submit button
        await submitButton.click();

        // Wait for the dashboard page to load
        await webdriver.wait(async () => {
            const currentUrl = await webdriver.getCurrentUrl();
            return currentUrl.includes('/dashboard');
        }, 5000, 'Dashboard page did not load within 5 seconds');

        // Check if the dashboard page is displayed
        const currentUrl = await webdriver.getCurrentUrl();
        const isDashboardDisplayed = currentUrl.includes('/dashboard');
        if (isDashboardDisplayed) {
            console.log('Sign in successful. Dashboard page is displayed.');
        } else {
            console.error('Sign in failed. Dashboard page is not displayed.');
        }

        // Close the browser
        await webdriver.quit();
    } catch (error) {
        console.error('An error occurred:', error);
    }finally {
        // Close the browser
        await driver.quit();
    }
}

// Execute the test function
signInTest();
