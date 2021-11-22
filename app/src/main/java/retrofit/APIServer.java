package retrofit;

import Notification.MyResponse;
import Notification.Sender;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIServer {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAvPYt_QU:APA91bGDSH0EWa24DLljAuZC83v-JczGI16mgyVw-oP6qrtzrEN0zdMLNCcEMatMUFDMveUizD3eh7vhYd72U8ewd7k7VOFXk-CmEHpnYW1J2ZUJ3oUf-SEUoAX_9Qijgqjrs3zW8hru"
            }
    )
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);

}
