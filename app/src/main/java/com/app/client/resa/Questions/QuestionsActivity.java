package com.app.client.resa.Questions;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.client.resa.Config.ConfigFile;
import com.app.client.resa.CustomViews.QuestionPaperView;
import com.app.client.resa.Main.MainActivity;
import com.app.client.resa.R;
import com.app.client.resa.UserAnswers.GenerateResultFlagRequest;
import com.app.client.resa.UserAnswers.InsertAnswersRequest;
import com.app.client.resa.UserAnswers.UploadAnswersToServer;
import com.app.client.resa.UserAnswers.UserAnswer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

public class QuestionsActivity extends AppCompatActivity {

    /*top bar settings*/





    // question settings
    private QuestionPaperView questionViewPager;
    List<View> viewList;
    ArrayList<Question> questions = new ArrayList<Question>();
    HashMap<Integer,UserAnswer> userAnswers = new HashMap<Integer, UserAnswer>();
    HashMap<Integer,Boolean> is_answered = new HashMap<Integer, Boolean>();
    HashMap<Integer,Integer> answer_for_question = new HashMap<Integer, Integer>();
    ProgressBar progressBar;
    LayoutInflater mInflater;
    boolean current_is_selected =false;
    SharedPreferences sharedPreferences;

    String user_id ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        mInflater = getLayoutInflater().from(this);
        questionSetup();
        System.out.println("number of questions loaded : "+ questions.size());
        sharedPreferences= QuestionsActivity.this.getSharedPreferences("user", Context.MODE_PRIVATE);
        user_id= sharedPreferences.getString("user_id","");
        System.out.println("user_id is : "+user_id);


        progressbarInit(questions.size());
        viewPaperInit(questions.size());
    }
    public void questionSetup()
    {
        GetQuestionsRequest getQuestionsRequest = new GetQuestionsRequest();
        Thread thread = new Thread(getQuestionsRequest);
        thread.start();
        try
        {
            thread.join();
            questions= getQuestionsRequest.getQuestions();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    public void viewSetup(final int paper_position)
    {
        TextView textView = (TextView)  viewList.get(paper_position).findViewById(R.id.text_question_number);
        TextView text_question = (TextView)  viewList.get(paper_position).findViewById(R.id.text_question_detail);
        ListView answers_listview = (ListView)  viewList.get(paper_position).findViewById(R.id.listview_answers);
        Button pre = (Button)  viewList.get(paper_position).findViewById(R.id.btn_pre);
        final Button next = (Button)  viewList.get(paper_position).findViewById(R.id.btn_next);
        int current_answer = -1;
        if(is_answered.containsKey(paper_position))
        {
            current_is_selected = true;
//            System.out.println("paper is :"+paper_position);
        }
        else
        {
            current_is_selected =false;
        }
        if(current_is_selected)
        {
            current_answer = answer_for_question.get(paper_position);
            if(paper_position ==viewList.size()-1)
            {
                next.setText("Done");
            }
//            System.out.println("answer is :"+current_answer);
        }
        progressBar.setProgress(paper_position);
        Question current_question = questions.get(paper_position);
        String question_detail = current_question.getQuestion_detail();
        text_question.setText((paper_position+1)+". "+question_detail);
        textView.setText("Question: "+(paper_position+1) +"/"+questions.size());
        String[] answers = getResources().getStringArray(R.array.question_answers);
        answers_listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        answers_listview.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                                R.layout.fragment_question_text_layout , answers));
        answers_listview.setClickable(true);
        if(current_answer!= -1)
        {
            answers_listview.setItemChecked(current_answer,true);
            questionViewPager.setScroll(false);
        }
        answers_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                is_answered.put(paper_position,true);
                answer_for_question.put(paper_position,position);
                questionViewPager.setScroll(false);
                next_btn_listener(next,paper_position);
                save_answers(paper_position,position);
                System.out.println("eqwewqeqweq");
                try {
                    System.out.println(ConfigFile.TIME_DELAY);
                    Thread.sleep(ConfigFile.TIME_DELAY);
                    questionViewPager.setCurrentItem(paper_position+1);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }

                if(paper_position ==viewList.size()-1)
                {
                    next.setText("Done");
                }
//                System.out.println("answer is : " + position);
            }
        });
        pre_btn_listener(pre,paper_position);
    }
    public void  progressbarInit(int length)
    {
        progressBar = (ProgressBar) findViewById(R.id.progressbar_questions);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setMax(length);
    }
    public void viewPaperInit(int length)
    {
        viewList = new ArrayList<View>();
        for(int i=0;i<length;i++)
        {
            View v1 = mInflater.inflate(R.layout.single_question_layout, null);
            viewList.add(v1);
        }
        questionViewPager = (QuestionPaperView) findViewById(R.id.viewpager);
        questionViewPager.setAdapter(new QuestionPaperAdapter(viewList));
        questionViewPager.setCurrentItem(0);
        viewSetup(0);
        questionViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(final int position) {
                // TODO Auto-generated method stub
                questionViewPager.setScroll(true);
                viewSetup(position);
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }
            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    public void next_btn_listener(final Button button, final int position)
    {
        button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    if(position!=viewList.size()-1)
                    {
                        questionViewPager.setCurrentItem(position+1);
                    }
                    if(position ==viewList.size()-1)
                    {
                        uploadUserAnswers(userAnswers);
                        Intent intent = new Intent(QuestionsActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            });
    }
    public void pre_btn_listener(Button button, final int position)
    {
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(position!=0)
                {
                    questionViewPager.setCurrentItem(position-1);
                }

            }
        });
    }

    public void save_answers(int question_p, int answer_p)
    {
        if(userAnswers.containsKey(question_p))
        {
            UserAnswer userAnswer = new UserAnswer();
            userAnswer.setAnswer_id( userAnswers.get(question_p).getAnswer_id());
            userAnswer.setUser_id(userAnswers.get(question_p).getUser_id());
            userAnswer.setQuestion_id(userAnswers.get(question_p).getQuestion_id());
            userAnswer.setQuestion_category_id(userAnswers.get(question_p).getQuestion_category_id());
            userAnswers.put(question_p,userAnswer);
        }
        else
        {
            UserAnswer userAnswer = new UserAnswer();
            userAnswer.setAnswer_id(String.valueOf(answer_p));
            String question_id = questions.get(question_p).getQuestion_id();
            userAnswer.setQuestion_id(question_id);
            String category_id = questions.get(question_p).getQuestion_category();
            userAnswer.setQuestion_category_id(category_id);
            // hard coding user ID, change after Login Module finished
            userAnswer.setUser_id(user_id);
            userAnswers.put(question_p,userAnswer);
        }
    }
    public void uploadUserAnswers(HashMap<Integer,UserAnswer> finishedAnswers)
    {

        String insert_id = generateResultID();
        if (!insert_id.equals("fail"))
        {
            for (Map.Entry<Integer, UserAnswer> entry : finishedAnswers.entrySet()) {

                InsertAnswersRequest uploadAnswersToServer = new InsertAnswersRequest(entry.getValue().getQuestion_id()
                        ,entry.getValue().getQuestion_category_id(),entry.getValue().getUser_id(),entry.getValue().getAnswer_id(),insert_id);
                Thread thread = new Thread(uploadAnswersToServer);
                thread.start();
                try {
                    thread.join();
                    String status = uploadAnswersToServer.getIs_insert();
                    if(status.equals("success"))
                    {
                        System.out.println("Status :"+status);
                        System.out.println("answer is inserted . . . . . .");
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

        }

    }
    public String generateResultID()
    {
        String generated_result_id = "fail";
        GenerateResultFlagRequest generateResultFlagRequest = new GenerateResultFlagRequest(user_id);
        Thread thread = new Thread(generateResultFlagRequest);
        thread.start();
        try {
            thread.join();
            generated_result_id = generateResultFlagRequest.getInsert_id();
//                System.out.println(status);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return generated_result_id;
    }
}
