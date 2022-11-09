package com.example.quizapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.quizapp.R;
import com.example.quizapp.model.Topic;
import com.example.quizapp.ui_admin.UpdateTopicActivity;
import com.google.android.material.button.MaterialButton;

import java.io.Serializable;
import java.util.List;

public class TopicAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<Topic> topics;

    public TopicAdapter(Context context, int layout, List<Topic> topics) {
        this.context = context;
        this.layout = layout;
        this.topics = topics;
    }

    @Override
    public int getCount() {
        return topics.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint({"SetTextI18n", "ViewHolder"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(layout, null);
        TextView twNum = view.findViewById(R.id.tw_name_topic);
        ImageView img = view.findViewById(R.id.img_topic);

        Topic topic = topics.get(i);
        twNum.setText(topic.getNameTopic());
        Bitmap bmp = BitmapFactory.decodeByteArray(topic.getImageTopic(), 0, topic.getImageTopic().length);
        img.setImageBitmap(bmp);

        MaterialButton btnEdit = view.findViewById(R.id.btn_edit_topic);
        MaterialButton btnDel = view.findViewById(R.id.btn_del);
        btnEdit.setTag(i);
        btnDel.setTag(i);

        btnEdit.setOnClickListener(v ->{
            int position = (int) v.getTag();
            Topic top = topics.get(position);

            Bundle bundle = new Bundle();
            bundle.putSerializable("data", top);
            Intent intent = new Intent(context, UpdateTopicActivity.class);
            intent.putExtra("bundle", bundle);
            context.startActivity(intent);
        });

        btnDel.setOnClickListener(v -> {
            int positionToRemove = (int) v.getTag(); //get the position of the view to delete stored in the tag
            Topic topi1 = topics.get(positionToRemove);

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Xác nhận xoá");
            builder.setMessage("Bạn có thật sự muốn xoá topic \"" + topics.get(i).getNameTopic() + "\" không?");
            builder.setPositiveButton("Có", (dialogInterface, i1) -> {
//                TODO: delete data in database -> delete item in listView


//            TODO: remove data by id topic in database


//            TODO: remove item in adapter
//            topics.remove(positionToRemove);
//            notifyDataSetChanged(); //remove the item
            });

            builder.setNegativeButton("Không", (dialogInterface, i1) -> {
            });

            builder.show();
        });

        return view;
    }
}