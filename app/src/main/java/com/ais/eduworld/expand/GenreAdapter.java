package com.ais.eduworld.expand;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.listeners.GroupExpandCollapseListener;
import com.thoughtbot.expandablerecyclerview.listeners.OnGroupClickListener;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.models.ExpandableListPosition;

import java.util.List;

import com.ais.eduworld.R;
import com.ais.eduworld.activities.AssignmentActivity;
import com.ais.eduworld.activities.AttendanceActivity;
import com.ais.eduworld.activities.CalenderActivity;
import com.ais.eduworld.activities.CircularActivity;
import com.ais.eduworld.activities.DatesheetActivity;
import com.ais.eduworld.activities.DirectoryActivity;
import com.ais.eduworld.activities.FeeActivity;
import com.ais.eduworld.activities.HolidayActivity;
import com.ais.eduworld.activities.HomeWorkActivity;
import com.ais.eduworld.activities.NotificationActivity;
import com.ais.eduworld.activities.ProfileActivity;
import com.ais.eduworld.activities.ResultActivity;
import com.ais.eduworld.activities.SocialMediaActivity;
import com.ais.eduworld.activities.StudentIdentityActivity;
import com.ais.eduworld.activities.YoutubeActivity;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class GenreAdapter extends ExpandableRecyclerViewAdapter<GenreViewHolder, ArtistViewHolder> {
    private Context context;
    List<? extends ExpandableGroup> mGroups;
    public GenreAdapter(List<? extends ExpandableGroup> groups, Context context) {
        super(groups);
        this.context = context;
        mGroups = groups;
    }

    @Override
    public GenreViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_genre, parent, false);
        return new GenreViewHolder(view);
    }

    @Override
    public ArtistViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_artist, parent, false);
        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(ArtistViewHolder holder, final int flatPosition,
                                      final ExpandableGroup group, final int childIndex) {

        final Artist artist = ((Genre) group).getItems().get(childIndex);
        holder.setArtistName(artist.getName());
        holder.setArtistIcon(artist.getIcon());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (childIndex == 0 && group.getTitle() == "Student") {
                    Intent intent = new Intent(context, ProfileActivity.class);
                    context.startActivity(intent);
                } else if (childIndex == 1 && group.getTitle() == "Student") {
                    Intent intent = new Intent(context, StudentIdentityActivity.class);
                    context.startActivity(intent);
                } else if (childIndex == 0 && group.getTitle() == "Academics") {
                    Intent intent = new Intent(context, HomeWorkActivity.class);
                    context.startActivity(intent);
                } else if (childIndex == 1 && group.getTitle() == "Academics") {
                    Intent intent = new Intent(context, AssignmentActivity.class);
                    context.startActivity(intent);
                } else if (childIndex == 2 && group.getTitle() == "Academics") {
                    Intent intent = new Intent(context, DatesheetActivity.class);
                    context.startActivity(intent);
                } else if (childIndex == 3 && group.getTitle() == "Academics") {
                    Intent intent = new Intent(context, CalenderActivity.class);
                    context.startActivity(intent);
                } else if (childIndex == 4 && group.getTitle() == "Academics") {
                    Intent intent = new Intent(context, AttendanceActivity.class);
                    context.startActivity(intent);
                } else if (childIndex == 5 && group.getTitle() == "Academics") {
                    Intent intent = new Intent(context, HolidayActivity.class);
                    context.startActivity(intent);
                } else if (childIndex == 0 && group.getTitle() == "Notification Circular") {
                    Intent intent = new Intent(context, NotificationActivity.class);
                    context.startActivity(intent);
                } else if (childIndex == 1 && group.getTitle() == "Notification Circular") {
                    Intent intent = new Intent(context, CircularActivity.class);
                    context.startActivity(intent);
                } else if (childIndex == 0 && group.getTitle() == "Other") {
                    Intent intent = new Intent(context, DirectoryActivity.class);
                    context.startActivity(intent);
                } else if (childIndex == 1 && group.getTitle() == "Other") {
                    Intent intent = new Intent(context, FeeActivity.class);
                    context.startActivity(intent);
//                }
//                else if (childIndex == 2 && group.getTitle() == "Other") {
//                    Intent intent = new Intent(context, ResultActivity.class);
//                    context.startActivity(intent);
                }else if (childIndex == 0 && group.getTitle() == "Social Media"){
                    Intent intent = new Intent(context,SocialMediaActivity.class);
                    context.startActivity(intent);
                }else if (childIndex == 1 && group.getTitle() == "Social Media"){
                    Intent intent = new Intent(context, YoutubeActivity.class);
                    context.startActivity(intent);
                }

            }
        });

    }

    @Override
    public void onGroupExpanded(int positionStart, int itemCount) {
        if (itemCount > 0) {
            int groupIndex = expandableList.getUnflattenedPosition(positionStart).groupPos;
            notifyItemRangeInserted(positionStart, itemCount);
            for (ExpandableGroup grp : mGroups) {
                if (grp != mGroups.get(groupIndex)) {
                    if (this.isGroupExpanded(grp)) {
                        this.toggleGroup(grp);
                        this.notifyDataSetChanged();
                    }
                }
            }
        }
    }

    @Override
    public void onBindGroupViewHolder(GenreViewHolder holder, int flatPosition,
                                      ExpandableGroup group) {

        holder.setGenerBackground(group, flatPosition);
        holder.setGenreTitle(group);
    }


}
