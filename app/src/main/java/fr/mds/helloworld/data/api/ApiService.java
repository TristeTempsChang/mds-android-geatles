package fr.mds.helloworld.data.api;

import fr.mds.helloworld.data.api.character.CharactersResponse;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public class ApiService {

    private static final String BASE_URL = "https://rickandmortyapi.com/api/";
    private static Retrofit retrofit;

    static {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public interface ApiEndpoints {
        @GET("character")
        Call<List<Character>> getCharacters();
        @GET("character")
        Call<CharactersResponse> getCharacterByName(@Query("name") String name);
    }

    public static Call<CharactersResponse> fetchCharacter(String name) {
        ApiEndpoints api = retrofit.create(ApiEndpoints.class);
        return api.getCharacterByName(name);
    }


    public static ApiEndpoints getApi() {
        return retrofit.create(ApiEndpoints.class);
    }
}
