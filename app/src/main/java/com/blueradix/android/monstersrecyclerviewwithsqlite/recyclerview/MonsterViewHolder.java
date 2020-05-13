package com.blueradix.android.monstersrecyclerviewwithsqlite.recyclerview;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blueradix.android.monstersrecyclerviewwithsqlite.entities.Monster;
import com.blueradix.android.monstersrecyclerviewwithsqlite.R;




/**
 * We create this view holder representing the recycler_item_view.xml
 * The idea of this class is to create a class that can manipulate the view
 */
public class MonsterViewHolder extends RecyclerView.ViewHolder {

    //bind the recycler_item_view.xml elements
    public final ImageView monsterImageView;
    public final TextView monsterNameEditText;
    public final TextView monsterDescriptionEditText;
    public final TextView monsterTotalVotesTextView;

    public MonsterViewHolder(@NonNull View itemView) {
        super(itemView);

        monsterImageView = itemView.findViewById(R.id.monsterImageView);
        monsterNameEditText = itemView.findViewById(R.id.monsterNameEditText);
        monsterDescriptionEditText = itemView.findViewById(R.id.monsterDescriptionEditText);
        monsterTotalVotesTextView = itemView.findViewById(R.id.totalVotesTextView);
    }

    /**
     * Method used to update the data of the ViewHolder of a particular monster
     * @param monster       The monster object containing the data to populate the correspondent MonsterViewHolder
     */
    public void updateMonster(Monster monster){

//        Picasso.get().load("file:///android_asset/monsters/" + monster.imageFileName.substring(3) + ".png").into(monsterImageView);
        View rootView = monsterImageView.getRootView();
        int resID = rootView.getResources().getIdentifier(monster.imageFileName , "drawable" , rootView.getContext().getPackageName()) ;
        monsterImageView.setImageResource(resID);
        this.monsterNameEditText.setText(monster.getName());
        this.monsterDescriptionEditText.setText(monster.getDescription());
        this.monsterTotalVotesTextView.setText(monster.getVotes() + " Votes");
    }


}
