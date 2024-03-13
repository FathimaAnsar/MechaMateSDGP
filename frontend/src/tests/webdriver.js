const { Builder } = require('selenium-webdriver');
const chrome = require('selenium-webdriver/chrome');
const chromedriver = require('chromedriver');

// Set Chrome options
const chromeOptions = new chrome.Options();
chromeOptions.addArguments('--headless'); // Optionally, run Chrome in headless mode

// Set up Chrome WebDriver
const webdriver = new Builder()
    .forBrowser('chrome')
    .setChromeOptions(chromeOptions)
    .build();

// Export WebDriver instance
module.exports = webdriver;
