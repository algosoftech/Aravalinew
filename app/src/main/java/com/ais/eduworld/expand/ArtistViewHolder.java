package com.ais.eduworld.expand;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import com.ais.eduworld.R;
import com.ais.eduworld.activities.ProfileActivity;

public class ArtistViewHolder extends ChildViewHolder {
  private Context context;
  private TextView childTextView;
  private ImageView icon;

  public ArtistViewHolder(View itemView) {
    super(itemView);
    childTextView = (TextView) itemView.findViewById(R.id.list_item_artist_name);
    icon   = itemView.findViewById(R.id.list_item_artist_icon);
  }

  public void setArtistName(String name) {

    childTextView.setText(name);
  }


  public void setArtistIcon(int iconRes){

    icon.setImageResource(iconRes);

  }
}
