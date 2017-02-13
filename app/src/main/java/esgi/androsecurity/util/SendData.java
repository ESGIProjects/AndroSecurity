package esgi.androsecurity.util;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Tensa on 11/02/2017.
 */

public class SendData {

    public static final String API_URL = "https://androsecurityapi.herokuapp.com";

    public static void send(String number, String message, double lat, double lng,Context context){

        DataAvailability da = new DataAvailability(context);
        if (da.isAvailable()) {

            String versionAndroid = Build.VERSION.RELEASE;

            DateFormat df = new SimpleDateFormat("dd MM yyyy, HH:mm");
            String date = df.format(Calendar.getInstance().getTime());

            final Retrofit retrofit = new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();

            HttpServices service = retrofit.create(HttpServices.class);

            Call<HttpServices> call = service.testPost(number, message, lat, lng, date, versionAndroid);

            call.enqueue(new Callback<HttpServices>() {
                @Override
                public void onResponse(Call<HttpServices> call, Response<HttpServices> response) {
                    System.out.println("Response status code: " + response.code());
                }

                @Override
                public void onFailure(Call<HttpServices> call, Throwable t) {
                    Log.d("Error :", t.getMessage());
                }
            });
        }
        else {
            Log.d("Erreur :", "Internet is not available");
        }
    }

    public interface HttpServices {
        @FormUrlEncoded
        @POST("/messages")
        Call<HttpServices> testPost(@Field("number") String number,@Field("message") String message,
                                    @Field("lat") double lat,@Field("lng") double lng,
                                    @Field("date") String date,@Field("androidVersion") String androidVersion);
    }

    static class HttpResponse {
        String number;
        String message;
        double lat;
        double lng;
        String date;
        String androidVersion;
    }
}
