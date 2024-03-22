const { Builder, By, Key, until } = require('selenium-webdriver');
const assert = require('assert');
const { API_BASE_URL } = require('../Common.js'); // Adjust the path accordingly

(async function example() {
    let driver = await new Builder().forBrowser('chrome').build();
    try {
        // Navigate to the TrackMyVehicle page
        await driver.get(API_BASE_URL + '/track-vehicle');

        // Wait for the page to load
        await driver.wait(until.elementLocated(By.xpath('//h2[contains(text(), "Theft Tracking System")]')), 10000);

        // Find the "Find My Car" button and click it
        const findMyCarButton = await driver.findElement(By.xpath('//button[contains(text(), "Find My Car")]'));
        await findMyCarButton.click();

        // Wait for the map to load
        await driver.wait(until.elementLocated(By.className('leaflet-container')), 10000);

        // Wait for the vehicle location marker to appear on the map
        await driver.wait(until.elementLocated(By.className('leaflet-marker-icon')), 10000);

        // Verify if the vehicle location marker is displayed on the map
        const vehicleMarker = await driver.findElement(By.className('leaflet-marker-icon'));
        const markerStyle = await vehicleMarker.getAttribute('style');
        assert(markerStyle.includes('background-image: url("../images/car.png")'));

        console.log('TrackMyVehicle test passed successfully');
    } catch (error) {
        console.error('An error occurred:', error);
    } finally {
        // Close the browser
        await driver.quit();
    }
})();
