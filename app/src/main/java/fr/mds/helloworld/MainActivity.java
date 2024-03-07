package fr.mds.helloworld;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.mds.helloworld.data.api.ApiService;
import fr.mds.helloworld.data.api.character.Character;
import fr.mds.helloworld.data.api.character.CharacterAdapter;
import fr.mds.helloworld.data.api.character.CharactersResponse;
import fr.mds.helloworld.decoration.GridSpacingItemDecoration;
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
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));

        int spacingInPixels = 20;
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spacingInPixels));


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

                            recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                                @Override
                                public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                                    if (e.getAction() == MotionEvent.ACTION_UP) {
                                        View childView = rv.findChildViewUnder(e.getX(), e.getY());
                                        int position = rv.getChildAdapterPosition(childView);

                                        if (position != RecyclerView.NO_POSITION) {
                                            Character character = characters.get(position);
                                            showCharacterDetailsDialog(character);
                                        }
                                    }
                                    return false;
                                }

                                @Override
                                public void onTouchEvent(RecyclerView rv, MotionEvent e) {}

                                @Override
                                public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}
                            });
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<CharactersResponse> call, Throwable t) {

                    }
                });
            }
        });
    }

    // Méthode pour afficher la boîte de dialogue
    // Méthode pour afficher la boîte de dialogue
    private void showCharacterDetailsDialog(Character character) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_character_details, null);
        builder.setView(dialogView);

        TextView characterNameTextView = dialogView.findViewById(R.id.character_name);
        TextView characterStatusTextView = dialogView.findViewById(R.id.character_status);
        TextView characterTypeTextView = dialogView.findViewById(R.id.character_type);
        TextView characterSpeciesTextView = dialogView.findViewById(R.id.character_species);
        TextView characterGenderTextView = dialogView.findViewById(R.id.character_gender);
        ImageView characterImageView = dialogView.findViewById(R.id.character_image);
        Button closeButton = dialogView.findViewById(R.id.closeButton);

        characterNameTextView.setText("Name: " + character.getName());
        characterStatusTextView.setText("Status: " + character.getStatus());
        characterTypeTextView.setText("Type: " + character.getType());
        characterSpeciesTextView.setText("Species: " + character.getSpecies());
        characterGenderTextView.setText("Gender: " + character.getGender());

        // Charger l'image ici depuis l'URL character.getImage() avec une bibliothèque comme Picasso ou Glide

        final AlertDialog dialog = builder.create();

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
