import CustomAlert from "../screens/components/CustomAlert.js";
class ConnectionManager {


  constructor() {
    this.EndpointHost = "https://mechamate.site";
    // this.EndpointHost = "http://localhost:8080";
  }

  async postParamRequest(apiEndPoint, postParams) {
    document.body.style.cursor = "wait";
    try {
      const response = await fetch((this.EndpointHost + apiEndPoint), {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: postParams,
        credentials: "include",
      });
      const data = await response.json();
      document.body.style.cursor = "default";
      return JSON.stringify(data);
    } catch (error) {
      return (
        <CustomAlert
          show={true}
          error={{ heading: "Error!", message: error }}
        />
      );
    }
    document.body.style.cursor = "default";
    return null;
  }

  async postJsonRequest(apiEndPoint, jsonObject) {
    document.body.style.cursor = "wait";
    try {
      const response = await fetch((this.EndpointHost + apiEndPoint), {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: jsonObject,
        credentials: "include",
      });
      const data = await response.json();
      document.body.style.cursor = "default";
      return JSON.stringify(data);
    } catch (error) { }
    document.body.style.cursor = "default";
    return null;
  }

  async getRequest(apiEndPoint, urlEncodedData = null) {
    document.body.style.cursor = "wait";
    try {
      const response = await fetch(
          (this.EndpointHost +
        (urlEncodedData == null
          ? apiEndPoint
          : apiEndPoint + "?" + urlEncodedData)),
        {
          method: "GET",
          credentials: "include",
        }
      );
      const data = await response.json();
      document.body.style.cursor = "default";
      return JSON.stringify(data);
    } catch (error) { }
    document.body.style.cursor = "default";
    return null;
  }

  async signin(username, password) {
    const urlEncodedData = new URLSearchParams();
    urlEncodedData.append("username", username);
    urlEncodedData.append("password", password);
    return await this.postParamRequest("/api/v1/auth/signin", urlEncodedData);
  }

  async signup(
    username,
    password,
    email,
    firstName,
    lastName,
    telephone,
    agreedTOS
  ) {
    const urlEncodedData = new URLSearchParams();
    urlEncodedData.append("username", username);
    urlEncodedData.append("password", password);
    urlEncodedData.append("email", email);
    urlEncodedData.append("firstname", firstName);
    urlEncodedData.append("lastname", lastName);
    urlEncodedData.append("telephone", telephone);
    urlEncodedData.append("agreedTOS", agreedTOS);
    return await this.postParamRequest("/api/v1/auth/signup", urlEncodedData);
  }
  async addVehicle(vehicle) {
    return await this.postJsonRequest("/api/v1/general/add-vehicle", vehicle);
  }

  async activate(code) {
    return await this.getRequest("/api/v1/auth/activate?key=" + code);
  }

  async signout() {
    return await this.getRequest("/api/v1/auth/signout");
  }

  async requestPasswordRecovery(email) {
    const urlEncodedData = new URLSearchParams();
    urlEncodedData.append("email", email);
    return await this.postParamRequest("/api/v1/auth/recover", urlEncodedData);
  }

  async getUserProfile() {
    return await this.getRequest("/api/v1/general/profile");
  }

  async getVehicleList() {
    return await this.getRequest("/api/v1/general/get-vehicles");
  }

  async getDetailedUserProfile() {
    return await this.getRequest("/api/v1/general/detailed-profile");
  }

  async sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
  }

  async getPrediction(vehicleRegNo) {
    return await this.getRequest("/api/v1/features/get-maintenance-prediction?vehicleRegNo=" + vehicleRegNo);
  }


  async getNearbyParking(lat, lng, radius, limit) {
    return await this.getRequest(
      "/api/v1/features/get-nearby-parking?lat=" +
      lat +
      "&lng=" +
      lng +
      "&radius=" +
      radius +
      "&limit=" +
      limit
    );
  }

  async getNearbyAutoShops(lat, lng, radius, limit) {
    return await this.getRequest(
      "/api/v1/features/get-nearby-spare-part-shops?lat=" +
      lat +
      "&lng=" +
      lng +
      "&radius=" +
      radius +
      "&limit=" +
      limit
    );
  }
  async getVehicleLocation(vehicleRegNo) {
    return await this.getRequest(
      "/api/v1/features/get-device-location?vehicleRegNo=" + vehicleRegNo
    );
  }

  async getRequestForQR() {
    return await this.getRequest(
      "/api/v1/features/get-service-record-qr");
  }
  async getNearbyPoliceStations(lat, lng, radius, limit) {
    return await this.getRequest(
        "/api/v1/features/get-nearby-police-stations?lat=" +
        lat +
        "&lng=" +
        lng +
        "&radius=" +
        radius +
        "&limit=" +
        limit
    );
  }


}

export default ConnectionManager;
