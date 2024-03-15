//package com.mechamate;
//import com.mechamate.dto.ErrorDTO;
//import com.mechamate.entity.Maintenance;
//import com.mechamate.entity.PredictionModel;
//import com.mechamate.entity.UserProfile;
//import com.mechamate.service.*;
//import org.bson.types.ObjectId;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//class SuperUserActionManagerTest {
//
//    @InjectMocks
//    private SuperUserActionManager superUserActionManager;
//
//    @Mock
//    private DatabaseAbstractLayer databaseAbstractLayer;
//
//    @Mock
//    private NotificationManager notificationManager;
//
//    @Mock
//    private SessionManager sessionManager;
//
//    @Mock
//    private LanguageManager lang;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testAddMaintenance_withExistingMaintenance_returnsErrorDTO() {
//
//        Maintenance maintenance = new Maintenance();
//        maintenance.set_id(new ObjectId());
//        UserProfile userProfile = new UserProfile();
//        userProfile.setLanguage("en");
//
//        when(databaseAbstractLayer.isMaintenanceExists(any(Maintenance.class))).thenReturn(true);
//        when(lang.get(anyString(), anyString())).thenReturn("error message");
//
//        ResponseEntity<ErrorDTO> response = superUserActionManager.addMaintenance(maintenance, userProfile);
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals(ErrorDTO.ErrorStatus.ErrorInvalidRequest, response.getBody().getError());
//
//    }
//    @Test
//    void testDeletePredictionModel_withExistingModel_returnsNull() {
//        PredictionModel predictionModel = new PredictionModel();
//        predictionModel.set_id(new ObjectId());
//        UserProfile userProfile = new UserProfile();
//        userProfile.setLanguage("en");
//
//        when(databaseAbstractLayer.isPredictionModelExists(predictionModel)).thenReturn(true);
//        when(databaseAbstractLayer.deletePredictionModel(predictionModel)).thenReturn(true);
//        when(lang.get(anyString(), anyString())).thenReturn("success message");
//
//        ResponseEntity<ErrorDTO> response = superUserActionManager.deletePredictionModel(predictionModel, userProfile);
//
//        assertNull(response);
//    }
//
//}
