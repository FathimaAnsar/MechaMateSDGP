const { By } = require('selenium-webdriver');
const webdriver = require('./webdriver');

describe('Sign In', () => {
    it('should sign in successfully with valid credentials', async () => {
        // Navigate to the sign-in page
        await webdriver.get('http://localhost:3000/');

        // Find the sign-in input fields and submit button
        const usernameInput = await webdriver.findElement(By.name('username'));
        const passwordInput = await webdriver.findElement(By.name('password'));
        const submitButton = await webdriver.findElement(By.xpath("//button[contains(text(), 'Sign In')]"));

        // Enter credentials
        await usernameInput.sendKeys('justin');
        await passwordInput.sendKeys('Pass123!');

        // Click the submit button
        await submitButton.click();

        // Wait for some time for the sign-in process to complete
        await webdriver.sleep(2000);

        // You might need to handle redirection or wait for the next page to load,
        // depending on how your application behaves after successful sign-in.

        // For demonstration, let's assert that we are on the dashboard page after signing in.
        const dashboardElement = await webdriver.findElement(By.id('dashboard-element'));
        expect(await dashboardElement.isDisplayed()).toBe(true);
    });
});
