package com.routinetracker.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.routinetracker.R;
import com.routinetracker.databinding.ItemUsersBinding;
import com.routinetracker.interfaces.IClickListener;
import com.routinetracker.models.UsersModel;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {

    private ArrayList<UsersModel> usersModelArrayList;
    private IClickListener iClickListener;

    public UsersAdapter(ArrayList<UsersModel> usersModelArrayList, IClickListener iClickListener) {
        this.usersModelArrayList = usersModelArrayList;
        this.iClickListener = iClickListener;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UsersViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_users, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder fileFoldersViewHolder, int position) {
        UsersModel usersModel = usersModelArrayList.get(position);
        fileFoldersViewHolder.itemFolderBinding.txtName.setText("User " + (position + 1));
        fileFoldersViewHolder.itemFolderBinding.txtEmail.setText("user" + (position + 1) + "@gmail.com");
        fileFoldersViewHolder.itemFolderBinding.getRoot().setOnClickListener(v -> {
            if (iClickListener != null) iClickListener.onClick(v, position);
        });
        fileFoldersViewHolder.itemFolderBinding.getRoot().setOnLongClickListener(v -> {
            if (iClickListener != null) iClickListener.onLongClick(v, position);
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return usersModelArrayList.size();
    }

    static class UsersViewHolder extends RecyclerView.ViewHolder {
        ItemUsersBinding itemFolderBinding;

        UsersViewHolder(@NonNull ItemUsersBinding itemFileFolderBinding) {
            super(itemFileFolderBinding.getRoot());
            this.itemFolderBinding = itemFileFolderBinding;
        }
    }

}
