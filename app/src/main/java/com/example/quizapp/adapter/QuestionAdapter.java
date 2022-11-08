package com.example.quizapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.quizapp.R;
import com.example.quizapp.database.DBHelper;
import com.example.quizapp.model.Question;
import com.example.quizapp.model.Topic;
import com.example.quizapp.ui_admin.UpdateQuestionActivity;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class QuestionAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<Question> questions;
    DBHelper dbHelper;

    public QuestionAdapter(Context context, int layout, List<Question> questions) {
        this.context = context;
        this.layout = layout;
        this.questions = questions;
    }

    @Override
    public int getCount() {
        return questions.size();
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
        TextView twName = view.findViewById(R.id.tw_name_question);
        TextView twTopic = view.findViewById(R.id.tw_topic_name_question);

        dbHelper = new DBHelper(view.getContext());

        Question question = questions.get(i);
        twName.setText("Question: " + question.getContent());
        Topic topic = dbHelper.getTopicById(questions.get(i).getIdTopic());
        twTopic.setText(topic.getNameTopic());

        MaterialButton btnEdit = view.findViewById(R.id.btn_edit_ques);
        btnEdit.setTag(i);
        MaterialButton btnDel = view.findViewById(R.id.btn_del_ques);
        btnDel.setTag(i);

        btnEdit.setOnClickListener(v -> {
            int position = (int) v.getTag();
            Question ques = questions.get(position);

            Bundle bundle = new Bundle();
            bundle.putSerializable("data", ques);
            Intent intent = new Intent(context, UpdateQuestionActivity.class);
            intent.putExtra("bundle", bundle);
            context.startActivity(intent);
        });

        btnDel.setOnClickListener(v -> {
            int positionToRemove = (int) v.getTag(); //get the position of the view to delete stored in the tag
            Question ques = questions.get(positionToRemove);

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Xác nhận xoá");
            builder.setMessage("Bạn có thật sự muốn xoá question \"" + questions.get(i).getContent() + "\" không?");
            builder.setPositiveButton("Có", (dialogInterface, i1) -> {
//                TODO: delete data in database -> delete item in listView


//            TODO: remove data by id topic in database


//            TODO: remove item in adapter
//            questions.remove(positionToRemove);
//            notifyDataSetChanged(); //remove the item
            });

            builder.setNegativeButton("Không", (dialogInterface, i1) -> {
            });

            builder.show();
        });

        return view;
    }
}
