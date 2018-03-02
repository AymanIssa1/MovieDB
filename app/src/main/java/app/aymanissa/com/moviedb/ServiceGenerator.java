package app.aymanissa.com.moviedb;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ayman on 3/2/2018.
 */

public class ServiceGenerator{

        private static final String BASE_URL = "http://api.themoviedb.org";

        private static Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        private static Retrofit retrofit = builder.build();

        public static <S> S createService(
                Class<S> serviceClass) {
            return retrofit.create(serviceClass);
        }

}
