package
com.csierra.demo03072025.externalclients.notification;

import com.csierra.demo03072025.externalclients.notification.model.NotificationRequest;
import com.csierra.demo03072025.externalclients.notification.model.NotificationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class NotificationServiceRestClient {

    public ResponseEntity<NotificationResponse> deliverNotification(NotificationRequest request) {
        return ResponseEntity.ok(NotificationResponse.builder().messageAcceptedForDelivery(true).build());
    }
}
