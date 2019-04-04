package com.mochamadahya.instagramclone;


import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {


    public Home() {
        // Required empty public constructor
    }



    // deklarasi komponen recyclerview
    private RecyclerView lvPost;
    // deklarasi variable untuk data dari api
    private ArrayList<HashMap<String, String>> listPost;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lvPost = view.findViewById(R.id.lv_post);
        lvPost.setLayoutManager(new LinearLayoutManager(getActivity()));
        listPost = new ArrayList<>();
        showDataPost();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showDataPost() {
        // menampilkan progres dialog ketika mengambil data
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        String URL = "https://mahyazserver.000webhostapp.com/igclone/apitampilpost.php";
        JsonObjectRequest requestPost = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        Log.d("log", "onResponse: " + response.toString());
                        try {
                            // mengambil data dari json array dengan index post
                            JSONArray arrayPost = response.getJSONArray("post");
                            for (int a = 0; a < arrayPost.length(); a++) {
                                JSONObject objectPost = arrayPost.getJSONObject(a);
                                HashMap<String, String> map = new HashMap<>();
                                map.put("waktu", objectPost.getString("waktu"));
                                map.put("username", objectPost.getString("username"));
                                map.put("caption", objectPost.getString("caption"));
                                map.put("gambar", objectPost.getString("gambar"));
                                map.put("id_user", objectPost.getString("id_user"));
                                map.put("p_image", objectPost.getString("p_image"));
                                map.put("id_post", objectPost.getString("id_post"));
                                listPost.add(map);
                            }

                            AdapterPost adapterPost = new AdapterPost(getActivity(), listPost);
                            lvPost.setAdapter(adapterPost);
                        } catch (JSONException e) {
                            Log.d("log", "JSONException: " + e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.d("log", "onErrorResponse: " + error.getMessage());
                Toast.makeText(getActivity(), "Gagal menampilkan data.\nCoba Lagi", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));
        requestQueue.add(requestPost);
    }

}
