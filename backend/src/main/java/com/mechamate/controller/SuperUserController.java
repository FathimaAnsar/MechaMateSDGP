package com.mechamate.controller;

import com.mechamate.common.Common;
import com.mechamate.common.Validation;
import com.mechamate.dto.*;
import com.mechamate.entity.*;
import com.mechamate.features.PdM;
import com.mechamate.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/super")
public class SuperUserController {

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private ProfileManager profileManager;

    @Autowired
    private APIManager apiManager;

    @Autowired
    private SuperUserActionManager superUserActionManager;

    @Autowired
    private LanguageManager lang;

    @Autowired
    private PdM predictiveMaintenance;
    private final String merchantId = "1226081";
    private final String merchantSecret = "MzA4ODI0OTY4NTM2NzgzNTA0ODUxMDM0ODQ3MDk0MTgyNzA1NzU3NQ==";
    private final String returnUrl = "https://mechamate-413916.el.r.appspot.com/";
    private final String cancelUrl = "https://mechamate-413916.el.r.appspot.com/";
    private final String notifyUrl = "https://mechamate-413916.el.r.appspot.com/";

    @PostMapping("/add-maintenance")
    public ResponseEntity<?> addMaintenance(HttpServletRequest request, HttpServletResponse response,
                                            @RequestBody(required = false) MaintenanceDTO maintenanceDTO) {
        Object obj = Validation.authenticate(request, response, sessionManager, lang);
        if(!(obj instanceof UserProfile)) return (ResponseEntity<ErrorDTO>) (obj);
        UserProfile userProfile = (UserProfile) obj;

        if(!userProfile.isSuperUser())
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorUnauthorized,
                            lang.get("error.superuser.operation", userProfile.getLanguage()),
                            lang.get("error.superuser.operation.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        if(maintenanceDTO == null)
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.mobj.notfound", userProfile.getLanguage()),
                            lang.get("error.mobj.notfound.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        if (maintenanceDTO.getMaintenanceId() == null || maintenanceDTO.getMaintenanceId().isEmpty()) maintenanceDTO.setMaintenanceId("");
        if (maintenanceDTO.getMaintenanceType() == null) maintenanceDTO.setMaintenanceType(Maintenance.MaintenanceType.Default);
        if (maintenanceDTO.getName() == null || maintenanceDTO.getName().isEmpty()) maintenanceDTO.setName("");
        if (maintenanceDTO.getDescription() == null || maintenanceDTO.getDescription().isEmpty()) maintenanceDTO.setDescription("");
        try {
            if (maintenanceDTO.getPredictionModels() == null || maintenanceDTO.getPredictionModels().isEmpty())
                maintenanceDTO.setPredictionModels(new ArrayList<>());
        } catch (Exception e) {}

        Maintenance maintenance = new Maintenance(maintenanceDTO.getMaintenanceType(), maintenanceDTO.getName(),
                maintenanceDTO.getDescription(), Common.getPredictionModelObjectIds(maintenanceDTO.getPredictionModels()));

        try {
            maintenance.set_id(new ObjectId(maintenanceDTO.getMaintenanceId()));
        } catch (Exception e) {}

        ResponseEntity<ErrorDTO> resp = superUserActionManager.addMaintenance(maintenance, userProfile);
        if(resp != null) return resp;

        return new ResponseEntity<>
                (new SuccessDTO(SuccessDTO.SuccessStatus.OperationSucceeded,
                        lang.get("success.addmobj.succeeded", userProfile.getLanguage()),
                        lang.get("success.addmobj.succeeded.info", userProfile.getLanguage())),
                        HttpStatus.OK);

    }




    @GetMapping("/get-maintenance-list")
    public ResponseEntity<?> getMaintenanceList(HttpServletRequest request, HttpServletResponse response) {
        Object obj = Validation.authenticate(request, response, sessionManager, lang);
        if(!(obj instanceof UserProfile)) return (ResponseEntity<ErrorDTO>) (obj);
        UserProfile userProfile = (UserProfile) obj;
        if(!userProfile.isSuperUser())
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorUnauthorized,
                            lang.get("error.superuser.operation", userProfile.getLanguage()),
                            lang.get("error.superuser.operation.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        return superUserActionManager.getMaintenanceList();
    }





    @PostMapping("/delete-maintenance")
    public ResponseEntity<?> deleteMaintenance(HttpServletRequest request, HttpServletResponse response,
                                               @RequestBody(required = false) MaintenanceDTO maintenanceDTO) {
        Object obj = Validation.authenticate(request, response, sessionManager, lang);
        if(!(obj instanceof UserProfile)) return (ResponseEntity<ErrorDTO>) (obj);
        UserProfile userProfile = (UserProfile) obj;
        if(!userProfile.isSuperUser())
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorUnauthorized,
                            lang.get("error.superuser.operation", userProfile.getLanguage()),
                            lang.get("error.superuser.operation.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        if(maintenanceDTO == null)
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.mobj.notfound", userProfile.getLanguage()),
                            lang.get("error.mobj.notfound.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        if (maintenanceDTO.getMaintenanceId() == null || maintenanceDTO.getMaintenanceId().isEmpty()) maintenanceDTO.setMaintenanceId("");

        Maintenance maintenance = new Maintenance(Maintenance.MaintenanceType.Default, "", "", null);

        try {
            maintenance.set_id(new ObjectId(maintenanceDTO.getMaintenanceId()));
        } catch (Exception e) {}

        ResponseEntity<ErrorDTO> resp = superUserActionManager.deleteMaintenance(maintenance, userProfile);
        if(resp != null) return resp;

        return new ResponseEntity<>
                (new SuccessDTO(SuccessDTO.SuccessStatus.OperationSucceeded,
                        lang.get("success.delmobj.succeeded", userProfile.getLanguage()),
                        lang.get("success.delmobj.succeeded.info", userProfile.getLanguage())),
                        HttpStatus.OK);

    }


    @PostMapping("/add-prediction-model")
    public ResponseEntity<?> addPredictionModel(HttpServletRequest request, HttpServletResponse response,
                                            @RequestBody(required = false) PredictionModelDTO predictionModelDTO,
                                            @RequestParam(required = false) String datasetFileName) {
        Object obj = Validation.authenticate(request, response, sessionManager, lang);
        if(!(obj instanceof UserProfile)) return (ResponseEntity<ErrorDTO>) (obj);
        UserProfile userProfile = (UserProfile) obj;

        if(!userProfile.isSuperUser())
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorUnauthorized,
                            lang.get("error.superuser.operation", userProfile.getLanguage()),
                            lang.get("error.superuser.operation.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        if(predictionModelDTO == null)
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.pmodel.notfound", userProfile.getLanguage()),
                            lang.get("error.pmodel.notfound.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        if (predictionModelDTO.getModelId() == null || predictionModelDTO.getModelId().isEmpty()) predictionModelDTO.setModelId("");
        if (predictionModelDTO.getName() == null || predictionModelDTO.getName().isEmpty()) predictionModelDTO.setName("<No name>");
        if (predictionModelDTO.getDescription() == null || predictionModelDTO.getDescription().isEmpty()) predictionModelDTO.setDescription("<No description>");
        try {
            if (predictionModelDTO.getAppliedMaintenanceList() == null || predictionModelDTO.getAppliedMaintenanceList().isEmpty())
                predictionModelDTO.setAppliedMaintenanceList(new ArrayList<>());
        } catch (Exception e) {}

        File f = new File(System.getProperty("user.dir") + "/datasets/" + datasetFileName);
        if(!f.exists() || f.isDirectory())
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.file.doesnt.exist", userProfile.getLanguage()),
                            lang.get("error.file.doesnt.exist.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        PredictionModel predictionModel = predictiveMaintenance.getTrainedPredictionModel(datasetFileName,
                predictionModelDTO.getName(), predictionModelDTO.getDescription(), predictionModelDTO.getModelType(),
                predictionModelDTO.getAppliedMaintenanceList());


        try {
            predictionModel.set_id(new ObjectId(predictionModelDTO.getModelId()));
        } catch (Exception e) {}

        ResponseEntity<ErrorDTO> resp = superUserActionManager.addPredictionModel(predictionModel, userProfile);
        if(resp != null) return resp;

        return new ResponseEntity<>
                (new SuccessDTO(SuccessDTO.SuccessStatus.OperationSucceeded,
                        lang.get("success.addpmodel.succeeded", userProfile.getLanguage()),
                        lang.get("success.addpmodel.succeeded.info", userProfile.getLanguage())),
                        HttpStatus.OK);
    }




    @GetMapping("/get-prediction-model-list")
    public ResponseEntity<?> getPredictionModelList(HttpServletRequest request, HttpServletResponse response) {
        Object obj = Validation.authenticate(request, response, sessionManager, lang);
        if(!(obj instanceof UserProfile)) return (ResponseEntity<ErrorDTO>) (obj);
        UserProfile userProfile = (UserProfile) obj;
        if(!userProfile.isSuperUser())
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorUnauthorized,
                            lang.get("error.superuser.operation", userProfile.getLanguage()),
                            lang.get("error.superuser.operation.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        return superUserActionManager.getPredictionModelList();
    }





    @PostMapping("/delete-prediction-model")
    public ResponseEntity<?> deletePredictionModel(HttpServletRequest request, HttpServletResponse response,
                                               @RequestBody(required = false) PredictionModelDTO predictionModelDTO) {
        Object obj = Validation.authenticate(request, response, sessionManager, lang);
        if(!(obj instanceof UserProfile)) return (ResponseEntity<ErrorDTO>) (obj);
        UserProfile userProfile = (UserProfile) obj;
        if(!userProfile.isSuperUser())
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorUnauthorized,
                            lang.get("error.superuser.operation", userProfile.getLanguage()),
                            lang.get("error.superuser.operation.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        if(predictionModelDTO == null)
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.pmodel.notfound", userProfile.getLanguage()),
                            lang.get("error.pmodel.notfound.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        if (predictionModelDTO.getModelId() == null || predictionModelDTO.getModelId().isEmpty()) predictionModelDTO.setModelId("");

        PredictionModel predictionModel = new PredictionModel("", "", null, null);

        try {
            predictionModel.set_id(new ObjectId(predictionModelDTO.getModelId()));
        } catch (Exception e) {}

        ResponseEntity<ErrorDTO> resp = superUserActionManager.deletePredictionModel(predictionModel, userProfile);
        if(resp != null) return resp;

        return new ResponseEntity<>
                (new SuccessDTO(SuccessDTO.SuccessStatus.OperationSucceeded,
                        lang.get("success.delpmodel.succeeded", userProfile.getLanguage()),
                        lang.get("success.delpmodel.succeeded.info", userProfile.getLanguage())),
                        HttpStatus.OK);

    }


    @PostMapping("/process-payment")
    public RedirectView processPayment(@RequestBody PaymentInfoDTO paymentInfoDTO) {
        DecimalFormat df = new DecimalFormat("0.00");
        String amountFormatted = df.format(Double.parseDouble(paymentInfoDTO.getAmount()));
        String hash = "";
        try {
            hash = getMd5(merchantId + paymentInfoDTO.getOrderId() + amountFormatted + paymentInfoDTO.getCurrency() + "2" + getMd5(merchantSecret).toUpperCase()).toUpperCase();
        } catch(Exception ignore) {}

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://sandbox.payhere.lk/pay/checkout")
                .queryParam("merchant_id", merchantId)
                .queryParam("return_url", returnUrl)
                .queryParam("cancel_url", cancelUrl)
                .queryParam("notify_url", notifyUrl)
                .queryParam("order_id", paymentInfoDTO.getOrderId())
                .queryParam("items", paymentInfoDTO.getItems())
                .queryParam("currency", paymentInfoDTO.getCurrency())
                .queryParam("amount", amountFormatted)
                .queryParam("first_name", paymentInfoDTO.getFirstName())
                .queryParam("last_name", paymentInfoDTO.getLastName())
                .queryParam("email", paymentInfoDTO.getEmail())
                .queryParam("phone", paymentInfoDTO.getPhone())
                .queryParam("address", paymentInfoDTO.getAddress())
                .queryParam("city", paymentInfoDTO.getCity())
                .queryParam("country", paymentInfoDTO.getCountry())
                .queryParam("hash", hash);

        return new RedirectView(builder.toUriString());
    }

    public static String getMd5(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(input.getBytes());
        BigInteger no = new BigInteger(1, messageDigest);
        String hashtext = no.toString(16);
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        return hashtext.toUpperCase();
    }
    @PostMapping("/payment-notification")
    public ResponseEntity<?> handlePaymentNotification(@RequestBody Map<String, Object> payload) {
        boolean isProcessed = apiManager.processPaymentNotification(payload);

        if (isProcessed) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("Error processing payment notification");
        }
    }

}





