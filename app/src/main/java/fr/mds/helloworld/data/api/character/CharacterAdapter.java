package fr.mds.helloworld.data.api.character;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;

import fr.mds.helloworld.MainActivity;
import fr.mds.helloworld.R;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> {
    private final List<Character> characters;
    private Context context;

    public CharacterAdapter(MainActivity mainActivity, List<Character> characters) {
        this.context = context;
        this.characters = characters;
    }



    @NonNull
    @Override
    public CharacterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_character, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterAdapter.ViewHolder holder, int position) {
        Character character = characters.get(position);
        holder.characterName.setText(character.getName());
        Picasso.get().load(character.getImage()).into(holder.characterImage);
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView characterImage;
        TextView characterName;

        public ViewHolder(View view) {
            super(view);
            characterImage = view.findViewById(R.id.character_image);
            characterName = view.findViewById(R.id.character_name);
        }
    }
}
