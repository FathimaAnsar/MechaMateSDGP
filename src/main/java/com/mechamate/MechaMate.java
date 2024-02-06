package com.mechamate;

import com.mechamate.entity.UserProfile;
import com.mechamate.entity.Vehicle;
import com.mechamate.repo.UserProfileRepository;
import com.mechamate.service.DatabaseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@SpringBootApplication
public class MechaMate implements CommandLineRunner {
	@Autowired
	private UserProfileRepository userProfileRepository;

	public static void main(String[] args) {
		SpringApplication.run(MechaMate.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//////		 Create a few Vehicle entities
//		Vehicle vehicle1 = new Vehicle("ABC123");
////		Vehicle vehicle2 = new Vehicle("XYZ789");
////
////		// Save the vehicles
////
////		// Create a Person with a list of referenced vehicles
//		UserProfile person = new UserProfile("P1" );
////
////		vehicle1.setOwner(person);
////		vehicle2.setOwner(person);
////
////		vehicleRepository.save(vehicle1);
////		vehicleRepository.save(vehicle2);
////
////		Vehicle vehicle3 = new Vehicle("TEstVehicle");
////
////		Person person2 = new Person("P2", "Jane Doe");
////
////		person2.setVehicles(Arrays.asList(vehicle3));
////		person.setVehicles(Arrays.asList(vehicle1, vehicle2));
////
////		vehicle3.setOwner(person2);
////		vehicleRepository.save(vehicle3);
//////		// Save the person
//		userProfileRepository.save(person);
////		personRepository.save(person2);
//
////		// Fetch the person with referenced vehicles
//		Person fetchedPerson = personRepository.findById("P1").orElse(null);
//		if (fetchedPerson != null) {
//			System.out.println("Fetched Person: " + fetchedPerson.getName());
//			System.out.println("Vehicles: ");
//			for (Vehicle vehicle: fetchedPerson.getVehicles()){
//				System.out.println(vehicle);
//			}
//		}
//
//		Vehicle fetchedVehicle = vehicleRepository.findById("TEstVehicle").orElse(null);
//		if (fetchedVehicle != null){
//			System.out.println("fetchedVehicle: " + fetchedVehicle);
//			System.out.println(fetchedVehicle.getOwner().getId());
//		}



		DatabaseManager dbHandler = new DatabaseManager();
//
//		Vehicle vehicle = new Vehicle("AAA-5678");
//
//
//
//		dbHandler.addVehicle();

		UserProfile testProfile = new UserProfile("gg ");
		dbHandler.addUserProfile(testProfile);


//		UserProfile profile = userProfileRepository.findById("test").orElse(null);
//
//		System.out.println(profile.getFirstName());






//	public static void main(String[] args) {
//		SpringApplication.run(MechaMate.class, args);
//
//	}
}
}

