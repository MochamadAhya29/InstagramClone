package com.mochamadahya.instagramclone;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

class AdapterPost extends RecyclerView.Adapter<AdapterPost.PostHolder> implements View.OnClickListener {


    // deklarasi variable data dari home fragment
    private ArrayList<HashMap<String, String>> listData;
    private Context context;


    public AdapterPost(FragmentActivity activity, ArrayList<HashMap<String, String>> listPost) {

        // data dari home fragment di masukkan ke dalam variable adapter
        listData = listPost;
        context = activity;
    }

    @NonNull
    @Override
    public AdapterPost.PostHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // memasang layout item ke dalam adapter
        View view = LayoutInflater.from(context).inflate(R.layout.item_home, viewGroup, false);
        // memasukkan  layout ke dalam viewhlder
        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPost.PostHolder postHolder, int i) {
        // menampilkan data ke masing2 komponen layout
        postHolder.txtUserName.setText(listData.get(i).get("username"));
        postHolder.txtUserNameCap.setText(listData.get(i).get("username"));
        postHolder.txtCaption.setText(listData.get(i).get("caption"));
        String URL_GAMBAR = "https://mahyazserver.000webhostapp.com/SMPIDN/webdatabase/img/";
        Glide.with(context)
                .load(URL_GAMBAR + listData.get(i).get("gambar"))
                .into(postHolder.imgPost);
        Glide.with(context)
                .load(URL_GAMBAR + listData.get(i).get("p_image"))
                .into(postHolder.imgPhotoProfile);
        postHolder.txtLikes.setOnClickListener(this);
        postHolder.btnShare.setOnClickListener(this);
        postHolder.btnMore.setOnClickListener(this);
        postHolder.btnFav.setOnClickListener(this);
        postHolder.btnComment.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return listData.size();
        // menentukan jumlah data yang ingin ditampilkan
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(context, "Kamu menekan tombol", Toast.LENGTH_SHORT).show();
    }

    class PostHolder extends RecyclerView.ViewHolder{
        // deklarasi variable utk komponen
        TextView txtUserName, txtUserNameCap, txtCaption, txtLikes;
        ImageView imgPhotoProfile, imgPost, btnMore, btnComment, btnShare, btnFav;

        PostHolder(@NonNull View itemView) {
            super(itemView);

            // inisialisasi komponen layout
            txtUserName = itemView.findViewById(R.id.txtusername);
            txtUserNameCap = itemView.findViewById(R.id.txtusernamehome);
            txtCaption = itemView.findViewById(R.id.txtcaption);
            imgPhotoProfile = itemView.findViewById(R.id.imgusername);
            imgPost = itemView.findViewById(R.id.imgpost);
            txtLikes = itemView.findViewById(R.id.txtlikes);
            btnComment = itemView.findViewById(R.id.imgcomment);
            btnFav = itemView.findViewById(R.id.imgfavourite);
            btnMore = itemView.findViewById(R.id.imgmenuhome);
            btnShare = itemView.findViewById(R.id.imgshare);
        }
    }
}
