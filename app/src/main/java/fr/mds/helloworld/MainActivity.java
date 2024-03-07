package fr.mds.helloworld;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import fr.mds.helloworld.data.api.ApiService;
import fr.mds.helloworld.data.api.character.Character;
import fr.mds.helloworld.data.api.character.CharactersResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private StudentViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText = findViewById(R.id.inputCharacter); // Assurez-vous que l'ID correspond à celui de votre layout
        Button searchButton = findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String characterName = editText.getText().toString();
                Call<CharactersResponse> call = ApiService.fetchCharacter(characterName);

                call.enqueue(new Callback<CharactersResponse>() {
                    @Override
                    public void onResponse(Call<CharactersResponse> call, Response<CharactersResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            TextView resultTextView = findViewById(R.id.getCharacter);
                            List<Character> characters = response.body().getResults();
                            StringBuilder stringBuilder = new StringBuilder();
                            for (Character character : characters) {
                                stringBuilder.append(character.getName()).append("\n");
                            }
                            resultTextView.setText(stringBuilder.toString());
                        } else {
                            // Gérez l'erreur ici
                        }
                    }


                    @Override
                    public void onFailure(Call<CharactersResponse> call, Throwable t) {
                        // Gérez l'échec de l'appel ici
                    }
                });
            }
        });
    }
}