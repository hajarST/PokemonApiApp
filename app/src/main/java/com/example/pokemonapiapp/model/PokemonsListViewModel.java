package com.example.pokemonapiapp.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pokemonapiapp.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.http.Url;

public class PokemonsListViewModel extends ArrayAdapter<GitPokemon> {

    int resource;

    public PokemonsListViewModel(@NonNull Context context, int resource , List<GitPokemon> data) {
        super(context, resource,data);

        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listViewItem = convertView;
        if(listViewItem == null)
        {
            listViewItem = LayoutInflater.from(getContext()).inflate(resource,parent,false);

        }
        CircleImageView imageViewPokemon = listViewItem.findViewById(R.id.imageViewPokemon);
        TextView textView1 = listViewItem.findViewById(R.id.textView1);
        TextView textView2 = listViewItem.findViewById(R.id.textView2);
        textView1.setText(getItem(position).name);
        textView2.setText(getItem(position).url);

        try {
            URL url = new URL("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+position+".png");
            Bitmap bitmap = BitmapFactory.decodeStream(url.openStream());
            imageViewPokemon.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //imageViewPokemon.setImageBitmap()
        return listViewItem;
    }
}
