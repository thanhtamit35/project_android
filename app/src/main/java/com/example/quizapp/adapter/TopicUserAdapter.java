package com.example.quizapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp.R;
import com.example.quizapp.model.Topic;

import java.util.List;

public class TopicUserAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<Topic> topics;

    public TopicUserAdapter(Context context, int layout, List<Topic> topics) {
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
        TextView twName = view.findViewById(R.id.tw_topic_user);
        ImageView img = view.findViewById(R.id.img_topic_user);

        Topic topic = topics.get(i);
        twName.setText(topic.getNameTopic());
        Bitmap bmp= BitmapFactory.decodeByteArray(topic.getImageTopic(),0,topic.getImageTopic().length);
        img.setImageBitmap(bmp);
        view.setTag(topic);

//        MaterialButton btnDel = view.findViewById(R.id.btn_del);
//        btnDel.setTag(i);
//
//        btnDel.setOnClickListener(v -> {
//            int positionToRemove = (int)v.getTag(); //get the position of the view to delete stored in the tag
////            removeItem(positionToRemove);
////            notifyDataSetChanged(); //remove the item
//            Toast.makeText(context, positionToRemove + "", Toast.LENGTH_SHORT).show();
//        });

        return view;
    }
}
