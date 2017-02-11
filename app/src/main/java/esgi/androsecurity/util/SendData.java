package esgi.androsecurity.util;

import android.content.Context;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Tensa on 11/02/2017.
 */

public class SendData {

    public static final String API_URL = "http://jsonplaceholder.typicode.com";

    public static void send(String number, String message, Context context){

        DataAvailability da = new DataAvailability(context);
        if (da.isAvailable()) {
            final Retrofit retrofit = new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();

            HttpServices service = retrofit.create(HttpServices.class);

            Call<HttpServices> call = service.testPost(1);

            call.enqueue(new Callback<HttpServices>() {
                @Override
                public void onResponse(Call<HttpServices> call, Response<HttpServices> response) {
                    System.out.println("Response status code: " + response.code());
                    Log.d("Etat", "OK");
                }

                @Override
                public void onFailure(Call<HttpServices> call, Throwable t) {
                    Log.d("Etat","KO");
                }
            });
        }
        else {
            Log.d("Erreur :", "Internet is not available");
        }
    }

    public interface HttpServices {
        @POST("/posts")
        Call<HttpServices> testPost(@Body int user);
    }

    static class HttpResponse {
        int user;
    }

}
