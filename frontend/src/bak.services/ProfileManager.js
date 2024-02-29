// Define the ProfileManager class
class ProfileManager {
    constructor(storageKey) {
        this.storageKey = storageKey;
    }

    saveProfile(profile) {
        localStorage.setItem(this.storageKey, JSON.stringify(profile));
    }

    getProfile() {
        const profileString = localStorage.getItem(this.storageKey);
        return profileString ? JSON.parse(profileString) : null;
    }

    updateProfile(updatedFields) {
        const profile = this.getProfile();
        if (profile) {
            const updatedProfile = { ...profile, ...updatedFields };
            this.saveProfile(updatedProfile);
            return updatedProfile;
        }
        return null;
    }

    clearProfile() {
        localStorage.removeItem(this.storageKey);
    }
}

// Instantiate the ProfileManager with a storage key
const profileManager = new ProfileManager('userProfile');

// Save a new profile
profileManager.saveProfile({ name: 'John', age: 30, email: 'john@example.com' });

// Retrieve the profile
const profile = profileManager.getProfile();
console.log(profile); // Output: { name: 'John', age: 30, email: 'john@example.com' }

// Update the profile
const updatedProfile = profileManager.updateProfile({ age: 31 });
console.log(updatedProfile); // Output: { name: 'John', age: 31, email: 'john@example.com' }

// Clear the profile
profileManager.clearProfile();
