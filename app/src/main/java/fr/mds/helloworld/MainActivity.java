package fr.mds.helloworld;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import fr.mds.helloworld.data.api.ApiService;
import fr.mds.helloworld.data.api.character.Character;
import fr.mds.helloworld.data.api.character.CharacterAdapter;
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

        RecyclerView recyclerView = findViewById(R.id.characterList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        EditText editText = findViewById(R.id.inputCharacter);
        editText.requestFocus();
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
                            List<Character> characters = response.body().getResults();
                            CharacterAdapter adapter = new CharacterAdapter(MainActivity.this, characters);
                            recyclerView.setAdapter(adapter);
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