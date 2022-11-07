package com.example.quizapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quizapp.R;
import com.example.quizapp.model.Quiz;

import java.util.List;

public class HistoryAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<Quiz> quizzes;

    public HistoryAdapter(Context context, int layout, List<Quiz> quizzes) {
        this.context = context;
        this.layout = layout;
        this.quizzes = quizzes;
    }

    @Override
    public int getCount() {
        return quizzes.size();
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
        TextView twTest = view.findViewById(R.id.tw_name_test);
        ImageView img = view.findViewById(R.id.img_test);
        TextView twScore = view.findViewById(R.id.tw_score_test);

        Quiz quiz = quizzes.get(i);
        twTest.setText(quiz.getNameQuiz());

        if (i == 0) {
            img.setImageResource(R.drawable.success);
        } else if (i == 1) {
            img.setImageResource(R.drawable.silver_medal);
        } else if (i == 2) {
            img.setImageResource(R.drawable.medal);
        }



        twScore.setText("Point: " + quiz.getScore());


//        MaterialButton btnDel = view.findViewById(R.id.btn_del);
//        btnDel.setTag(i);
//
//        btnDel.setOnClickListener(v -> {
//            int positionToRemove = (int) v.getTag(); //get the position of the view to delete stored in the tag
////            removeItem(positionToRemove);
////            notifyDataSetChanged(); //remove the item
//            Toast.makeText(context, positionToRemove + "", Toast.LENGTH_SHORT).show();
//        });

        return view;
    }
}
