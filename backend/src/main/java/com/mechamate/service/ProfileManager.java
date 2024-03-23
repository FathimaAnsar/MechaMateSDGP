

package com.mechamate.service;

import com.mechamate.common.Common;
import com.mechamate.common.Validation;
import com.mechamate.dto.*;
import com.mechamate.entity.QrLink;
import com.mechamate.entity.ServiceRecord;
import com.mechamate.entity.UserProfile;
import com.mechamate.entity.Vehicle;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileManager {

    @Autowired
    private DatabaseAbstractLayer databaseAbstractLayer;

    @Autowired
    private NotificationManager notificationManager;

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private LanguageManager lang;


//Creates a new user profile.
    public ResponseEntity<ErrorDTO> createUserProfile(HttpServletRequest request, UserProfile userProfile) {

        if(databaseAbstractLayer.isUserExists(userProfile.getUsername())) {
            if(userProfile.getStatus() == UserProfile.Status.StatusDeleted ||
                    userProfile.getStatus() == UserProfile.Status.StatusBlocked ||
                    userProfile.getStatus() == UserProfile.Status.StatusInactive) {
                return new ResponseEntity<>
                        (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorUsernameNotAllowed,
                                lang.get("error.username.notallowed", userProfile.getLanguage()),
                                lang.get("error.username.notallowed.help", userProfile.getLanguage())),
                                HttpStatus.OK);
            } else {
                return new ResponseEntity<>
                        (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorUserExists,
                                lang.get("error.user.exists", userProfile.getLanguage()),
                                lang.get("error.user.exists.help", userProfile.getLanguage())),
                                HttpStatus.OK);
            }
        }

        if(databaseAbstractLayer.isEmailExists(userProfile.getEmail())) {
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorEmailExists,
                            lang.get("error.email.exists", userProfile.getLanguage()),
                            lang.get("error.email.exists.help", userProfile.getLanguage())),
                            HttpStatus.OK);
        }

        userProfile.createActivationKey();

        if(!databaseAbstractLayer.addUserProfile(userProfile)) {
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.InternalError,
                            lang.get("error.internal.user.add.failed", userProfile.getLanguage()),
                            lang.get("error.internal.user.add.failed.help", userProfile.getLanguage())),
                            HttpStatus.OK);
        }

        UserProfile testUProf = databaseAbstractLayer.getUserProfile(userProfile.getUsername());
        if(testUProf == null) {
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.InternalError,
                            lang.get("error.internal.user.add.failed", userProfile.getLanguage()),
                            lang.get("error.internal.user.add.failed.help", userProfile.getLanguage())),
                            HttpStatus.OK);
        }

        userProfile.set_id(testUProf.get_id());
//        if(!notificationManager.sendActivationEmail(userProfile)) {
//            return new ResponseEntity<>
//                    (new ErrorDTO(ErrorDTO.ErrorStatus.InternalError,
//                            lang.get("error.internal.send.actemail.failed", userProfile.getLanguage()),
//                            lang.get("error.internal.send.actemail.failed.help", userProfile.getLanguage())),
//                            HttpStatus.OK);
//        }

        return null;
    }




//Activates a user profile.
    public ResponseEntity<ErrorDTO> activateUserProfile(HttpServletRequest request, String key,
                                                        UserProfile userProfile) {

        ResponseEntity<ErrorDTO> resp = Validation.profileOperationAllowed(userProfile, true, false,
                lang.get("error.invalid.account.activate.help", lang.getLanguage(request.getSession())),
                false, lang.getLanguage(request.getSession()), lang);
        if(resp != null) return resp;

        if (userProfile.getStatus() != UserProfile.Status.StatusPendingActivation) {
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.user.not.pending.activation", userProfile.getLanguage()),
                            lang.get("error.user.not.pending.activation.help", userProfile.getLanguage())),
                            HttpStatus.OK);
        }

        if(!userProfile.getActivationKey().equals(key)) {
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorActivationFailed,
                            lang.get("error.user.activation.failed", userProfile.getLanguage()),
                            lang.get("error.user.activation.failed.help", userProfile.getLanguage())),
                            HttpStatus.OK);
        }

        userProfile.clearActivationKey(UserProfile.Status.StatusActive);

        if(!databaseAbstractLayer.updateUserProfile(userProfile)) {
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.InternalError,
                            lang.get("error.internal.user.update.failed", userProfile.getLanguage()),
                            lang.get("error.internal.user.update.failed.help", userProfile.getLanguage())),
                            HttpStatus.OK);
        }

//        notificationManager.sendWelcomeEmail(userProfile);

        return null;
    }



//Handles user sign-in process.
    public ResponseEntity<ErrorDTO> signin(HttpServletRequest request, String username, String password,
                                           UserProfile userProfile) {

        ResponseEntity<ErrorDTO> resp = Validation.profileOperationAllowed(userProfile, false, false,
                lang.get("error.invalid.account.signin.help", lang.getLanguage(request.getSession())),
                false, lang.getLanguage(request.getSession()), lang);
        if(resp != null) return resp;

        if(userProfile.getUnsuccessfulLoginAttempts() > 5) {
            if((System.currentTimeMillis() - userProfile.getLastLoginAttemptTime()) < 3600000) {
                return new ResponseEntity<>
                        (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorMaxAttemptCountExceeded,
                                lang.get("error.attempt.count.exceeded", userProfile.getLanguage()),
                                lang.get("error.attempt.count.exceeded.help", userProfile.getLanguage())),
                                HttpStatus.OK);
            }
        }

        userProfile.setLastLoginAttemptTime(System.currentTimeMillis());

        if(!userProfile.getPassword().equals(Common.getSha256("AUTH#>>(" + password + ")<<#"))) {
            userProfile.setUnsuccessfulLoginAttempts(userProfile.getUnsuccessfulLoginAttempts() + 1);
            if(!databaseAbstractLayer.updateUserProfile(userProfile)) {
                return new ResponseEntity<>
                        (new ErrorDTO(ErrorDTO.ErrorStatus.InternalError,
                                lang.get("error.internal.userdata.update.failed", userProfile.getLanguage()),
                                lang.get("error.internal.userdata.update.failed.help", userProfile.getLanguage())),
                                HttpStatus.OK);
            }

            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorLoginFailed,
                            lang.get("error.user.login.failed", userProfile.getLanguage()),
                            lang.get("error.user.login.failed.help", userProfile.getLanguage())),
                            HttpStatus.OK);
        } else {
            userProfile.setUnsuccessfulLoginAttempts(0);
        }

        if(!databaseAbstractLayer.updateUserProfile(userProfile)) {
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.InternalError,
                            lang.get("error.internal.userdata.update.failed", userProfile.getLanguage()),
                            lang.get("error.internal.userdata.update.failed.help", userProfile.getLanguage())),
                            HttpStatus.OK);
        }

//        notificationManager.sendLoginAlert(userProfile);

        return null;
    }


//Handles user sign-out process.
    public ResponseEntity<ErrorDTO> signout(HttpServletRequest request, UserProfile userProfile) {
        // Future use: Anything to clean or set before signing out?
        return null;
    }




//Initiates password recovery process for a user profile.
    public ResponseEntity<ErrorDTO> recoverPassword(HttpServletRequest request, UserProfile userProfile) {

        ResponseEntity<ErrorDTO> resp = Validation.profileOperationAllowed(userProfile, false, true,
                lang.get("error.invalid.account.recoverpass.help", lang.getLanguage(request.getSession())),
                false, lang.getLanguage(request.getSession()), lang);
        if(resp != null) return resp;

        if(userProfile.getRecoveryKey().length() != 6) {
            userProfile.createRecoveryKey();
            if (!databaseAbstractLayer.updateUserProfile(userProfile)) {
                return new ResponseEntity<>
                        (new ErrorDTO(ErrorDTO.ErrorStatus.InternalError,
                                lang.get("error.internal.user.update.failed", userProfile.getLanguage()),
                                lang.get("error.internal.user.update.failed.help", userProfile.getLanguage())),
                                HttpStatus.OK);
            }
        }

        if(!notificationManager.sendRecoveryEmail(userProfile)) {
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.InternalError,
                            lang.get("error.internal.send.recvemail.failed", userProfile.getLanguage()),
                            lang.get("error.internal.send.recvemail.failed.help", userProfile.getLanguage())),
                            HttpStatus.OK);
        }

        return null;
    }


//Resets the password for a user profile.
    public ResponseEntity<ErrorDTO> resetPassword(HttpServletRequest request, String password,
                                                  UserProfile userProfile) {

        if(userProfile == null)
                return new ResponseEntity<>
                        (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                                lang.get("error.recovery.info.not.found", lang.getLanguage(request.getSession())),
                                lang.get("error.recovery.info.not.found.help", lang.getLanguage(request.getSession()))),
                                HttpStatus.OK);

        ResponseEntity<ErrorDTO> resp = Validation.profileOperationAllowed(userProfile, false, false,
                lang.get("error.invalid.account.resetpass.help", lang.getLanguage(request.getSession())),
                false, lang.getLanguage(request.getSession()), lang);
        if(resp != null) return resp;

        userProfile.clearRecoveryKey();
        userProfile.setPassword(password);

        if(!databaseAbstractLayer.updateUserProfile(userProfile)) {
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.InternalError,
                            lang.get("error.internal.user.update.failed", userProfile.getLanguage()),
                            lang.get("error.internal.user.update.failed.help", userProfile.getLanguage())),
                            HttpStatus.OK);
        }

        if(!notificationManager.sendResetConfirmEmail(userProfile)) {
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.InternalError,
                            lang.get("error.internal.send.resetmail.failed", userProfile.getLanguage()),
                            lang.get("error.internal.send.resetmail.failed.help", userProfile.getLanguage())),
                            HttpStatus.OK);
        }

        return null;
    }




//Updates user profile information.
    public ResponseEntity<ErrorDTO> updateUserProfile(UserProfile userProfile) {


        if(!databaseAbstractLayer.updateUserProfile(userProfile))
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorOperationFailed,
                            lang.get("error.user.update.failed", userProfile.getLanguage()),
                            lang.get("error.user.update.failed.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        return null;
    }






//Retrieves a user profile by email.
    public UserProfile getUserProfileByEmail(String email) {
        return databaseAbstractLayer.isEmailExists(email) ? databaseAbstractLayer.getUserProfileByEmail(email) : null;
    }

    //Retrieves a user profile by username.
    public UserProfile getUserProfileByUsername(String username) {
        return databaseAbstractLayer.isUserExists(username) ? databaseAbstractLayer.getUserProfile(username) : null;
    }

    public UserProfile getUserProfileByRecoveryKey(String key) {
        return databaseAbstractLayer.isRecoveryKeyExists(key) ? databaseAbstractLayer.getUserProfileByRecoveryKey(key) : null;
    }

    public ProfileDTO getProfileInfo(UserProfile userProfile) {
        return new ProfileDTO(
                userProfile.get_id().toHexString(),
                userProfile.getUsername(),
                userProfile.getEmail(),
                userProfile.getTelephone(),
                userProfile.getFirstname(),
                userProfile.getLastname(),
                userProfile.getLanguage(),
                getVehicles(userProfile).size());
    }

    public DetailedProfileDTO getDetailedProfileInfo(UserProfile userProfile) {

        List<VehicleDTO> vehicleDTOS = databaseAbstractLayer.getVehicleDTOs(userProfile.get_id());
        for(VehicleDTO v: vehicleDTOS) {
            List<ServiceRecordDTO> serviceRecordDTOS = databaseAbstractLayer.getServiceRecordDTOs(v.getRegistrationNumber());
            v.setServiceRecords(serviceRecordDTOS);
        }

        return new DetailedProfileDTO(
                userProfile.get_id().toHexString(),
                userProfile.getUsername(),
                userProfile.getEmail(),
                userProfile.getTelephone(),
                userProfile.getFirstname(),
                userProfile.getLastname(),
                userProfile.getLanguage(),
                vehicleDTOS);
    }

    public List<Vehicle> getVehicles(UserProfile userProfile) {
        return databaseAbstractLayer.getVehicles(userProfile.get_id());
    }

    public List<VehicleDTO> getVehicleDTOs(UserProfile userProfile) {
        return databaseAbstractLayer.getVehicleDTOs(userProfile.get_id());
    }

    public Vehicle getVehicle(String regNo) {
        return databaseAbstractLayer.isVehicleExists(regNo) ? databaseAbstractLayer.getVehicle(regNo) : null;
    }


    public ResponseEntity<ErrorDTO> addVehicle(Vehicle vehicle, UserProfile userProfile) {
        if(databaseAbstractLayer.isVehicleExists(vehicle.getRegNo()))
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorVehicleExists,
                            lang.get("error.vehicle.exists", userProfile.getLanguage()),
                            lang.get("error.vehicle.exists.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        if(!databaseAbstractLayer.addVehicle(vehicle))
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorOperationFailed,
                            lang.get("error.vehicle.add.failed", userProfile.getLanguage()),
                            lang.get("error.vehicle.add.failed.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        return null;
    }





    public ResponseEntity<ErrorDTO> updateVehicle(Vehicle vehicle, UserProfile userProfile) {
        if(!databaseAbstractLayer.isVehicleExists(vehicle.getRegNo()))
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorVehicleExists,
                            lang.get("error.vehicle.doesnt.exist", userProfile.getLanguage()),
                            lang.get("error.vehicle.doesnt.exist.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        Vehicle v = databaseAbstractLayer.getVehicle(vehicle.getRegNo());
        if(v == null)
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorOperationFailed,
                            lang.get("error.vehicle.update.failed", userProfile.getLanguage()),
                            lang.get("error.vehicle.update.failed.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        if(!v.getOwner().equals(vehicle.getOwner()))
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorUnauthorized,
                            lang.get("error.no.permission", userProfile.getLanguage()),
                            lang.get("error.no.permission.help", userProfile.getLanguage())),
                            HttpStatus.OK);


        if(!databaseAbstractLayer.updateVehicle(vehicle))
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorOperationFailed,
                            lang.get("error.vehicle.update.failed", userProfile.getLanguage()),
                            lang.get("error.vehicle.update.failed.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        return null;
    }






    public ResponseEntity<ErrorDTO> deleteVehicle(Vehicle vehicle, UserProfile userProfile) {
        if(!databaseAbstractLayer.isVehicleExists(vehicle.getRegNo()))
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorVehicleExists,
                            lang.get("error.vehicle.doesnt.exist", userProfile.getLanguage()),
                            lang.get("error.vehicle.doesnt.exist.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        Vehicle v = databaseAbstractLayer.getVehicle(vehicle.getRegNo());
        if(v == null)
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorOperationFailed,
                            lang.get("error.vehicle.delete.failed", userProfile.getLanguage()),
                            lang.get("error.vehicle.delete.failed.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        if(!v.getOwner().equals(vehicle.getOwner()))
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorUnauthorized,
                            lang.get("error.no.permission", userProfile.getLanguage()),
                            lang.get("error.no.permission.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        vehicle.set_id(v.get_id());
        if(!databaseAbstractLayer.deleteVehicle(vehicle))
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorOperationFailed,
                            lang.get("error.vehicle.delete.failed", userProfile.getLanguage()),
                            lang.get("error.vehicle.delete.failed.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        return null;
    }




    public ResponseEntity<ErrorDTO> addServiceRecord(ServiceRecord serviceRecord, UserProfile userProfile) {

        Vehicle vehicle = getVehicle(serviceRecord.getVehicleRegNo());
        if(vehicle == null)
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.vehicle.notfound", userProfile.getLanguage()),
                            lang.get("error.vehicle.notfound.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        if(!vehicle.getOwner().equals(userProfile.get_id()))
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorUnauthorized,
                            lang.get("error.no.permission", userProfile.getLanguage()),
                            lang.get("error.no.permission.help", userProfile.getLanguage())),
                            HttpStatus.OK);


        if(serviceRecord.get_id() != null && databaseAbstractLayer.isServiceRecordExists(serviceRecord.get_id())) {
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.srecord.exists", userProfile.getLanguage()),
                            lang.get("error.srecord.exists.help", userProfile.getLanguage())),
                            HttpStatus.OK);
        }

        if(!databaseAbstractLayer.addServiceRecord(serviceRecord))
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorOperationFailed,
                            lang.get("error.srecord.add.failed", userProfile.getLanguage()),
                            lang.get("error.srecord.add.failed.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        return null;
    }


    public ResponseEntity<ErrorDTO> updateServiceRecord(ServiceRecord serviceRecord, UserProfile userProfile) {

        Vehicle vehicle = getVehicle(serviceRecord.getVehicleRegNo());
        if(vehicle == null)
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.vehicle.notfound", userProfile.getLanguage()),
                            lang.get("error.vehicle.notfound.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        if(!vehicle.getOwner().equals(userProfile.get_id()))
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorUnauthorized,
                            lang.get("error.no.permission", userProfile.getLanguage()),
                            lang.get("error.no.permission.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        if(serviceRecord.get_id() == null)
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.srecord.id.missing", userProfile.getLanguage()),
                            lang.get("error.srecord.id.missing.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        if(!databaseAbstractLayer.isServiceRecordExists(serviceRecord.get_id())) {
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.srecord.doesnt.exist", userProfile.getLanguage()),
                            lang.get("error.srecord.doesnt.exist.help", userProfile.getLanguage())),
                            HttpStatus.OK);
        }

        ServiceRecord svr = databaseAbstractLayer.getServiceRecord(serviceRecord.get_id());
        if(svr == null)
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorOperationFailed,
                            lang.get("error.srecord.update.failed", userProfile.getLanguage()),
                            lang.get("error.srecord.update.failed.help", userProfile.getLanguage())),
                            HttpStatus.OK);


        if(!svr.getVehicleRegNo().equals(serviceRecord.getVehicleRegNo()))
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorUnauthorized,
                            lang.get("error.no.permission", userProfile.getLanguage()),
                            lang.get("error.no.permission.help", userProfile.getLanguage())),
                            HttpStatus.OK);


        if(!databaseAbstractLayer.updateServiceRecord(serviceRecord))
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorOperationFailed,
                            lang.get("error.srecord.update.failed", userProfile.getLanguage()),
                            lang.get("error.srecord.update.failed.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        return null;
    }





    public ResponseEntity<ErrorDTO> deleteServiceRecord(ServiceRecord serviceRecord, UserProfile userProfile) {

        Vehicle vehicle = getVehicle(serviceRecord.getVehicleRegNo());
        if(vehicle == null)
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.vehicle.notfound", userProfile.getLanguage()),
                            lang.get("error.vehicle.notfound.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        if(!vehicle.getOwner().equals(userProfile.get_id()))
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorUnauthorized,
                            lang.get("error.no.permission", userProfile.getLanguage()),
                            lang.get("error.no.permission.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        if(serviceRecord.get_id() == null)
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.srecord.id.missing", userProfile.getLanguage()),
                            lang.get("error.srecord.id.missing.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        if(!databaseAbstractLayer.isServiceRecordExists(serviceRecord.get_id())) {
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.srecord.doesnt.exist", userProfile.getLanguage()),
                            lang.get("error.srecord.doesnt.exist.help", userProfile.getLanguage())),
                            HttpStatus.OK);
        }


        ServiceRecord svr = databaseAbstractLayer.getServiceRecord(serviceRecord.get_id());
        if(svr == null)
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorOperationFailed,
                            lang.get("error.srecord.delete.failed", userProfile.getLanguage()),
                            lang.get("error.srecord.delete.failed.help", userProfile.getLanguage())),
                            HttpStatus.OK);
        
        if(!svr.getVehicleRegNo().equals(serviceRecord.getVehicleRegNo()))
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorUnauthorized,
                            lang.get("error.no.permission", userProfile.getLanguage()),
                            lang.get("error.no.permission.help", userProfile.getLanguage())),
                            HttpStatus.OK);


        if(!databaseAbstractLayer.deleteServiceRecord(serviceRecord))
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorOperationFailed,
                            lang.get("error.srecord.delete.failed", userProfile.getLanguage()),
                            lang.get("error.srecord.delete.failed.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        return null;
    }



    public ResponseEntity<ErrorDTO> addQrLink(QrLink qrLink, UserProfile userProfile) {
        if(databaseAbstractLayer.isQrLinkExists(qrLink.getQrKey())) databaseAbstractLayer.deleteQrLink(qrLink);
        if(!databaseAbstractLayer.addQrLink(qrLink))
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorOperationFailed,
                            lang.get("error.qrlink.add.failed", userProfile.getLanguage()),
                            lang.get("error.qrlink.add.failed.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        return null;
    }


    public boolean isQrLinkExist(String qrKey) {
        if(qrKey.isEmpty()) return false;
        return databaseAbstractLayer.isQrLinkExists(qrKey);
    }




















}
