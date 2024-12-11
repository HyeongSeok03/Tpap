package com.example.tpap.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tpap.R;
import com.example.tpap.chat_gpt.GptRequest;
import com.example.tpap.chat_gpt.GptResponse;
import com.example.tpap.chat_gpt.GptService;
import com.example.tpap.chat_gpt.Message;
import com.example.tpap.fragments.TravelLocationFragment;
import com.example.tpap.states.BaseState;
import com.example.tpap.states.LocationState;
import com.example.tpap.view_models.TravelInfoViewModel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlanningActivity extends AppCompatActivity {
    enum FragState {location, date, style}
    FragState fragState = FragState.location;
    Button previous_button, next_button;
    ImageButton exit_button;
    TextView title_textView, subTitle_textView;

    RelativeLayout title_layout;
    RelativeLayout actionButton_layout;
    private TravelInfoViewModel travelInfoVM;

    public String travel_destination = "";
    public String travel_startDate = "";
    public String travel_endDate = "";
    public String travel_style = "";
    public String travel_fileName = "";

    BaseState currentState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning);

        exit_button = findViewById(R.id.close_button);
        previous_button = findViewById(R.id.previous_button);
        next_button = findViewById(R.id.next_button);

        title_textView = findViewById(R.id.title_textView);
        subTitle_textView = findViewById(R.id.subTitle_textView);

        title_layout = findViewById(R.id.title_layout);
        actionButton_layout = findViewById(R.id.actionButton_layout);

        travelInfoVM = new ViewModelProvider(this).get(TravelInfoViewModel.class);

        currentState = new LocationState(this);
        currentState.updateUI();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_planning, new TravelLocationFragment()).commit();
        }

        exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityTransaction(MainActivity.class);
            }
        });

        previous_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseState previousState = currentState.previousState();
                if (previousState != null)
                {
                    currentState = previousState;
                    currentState.updateUI();
                }
            }
        });

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseState nextState = currentState.nextState();
                if (nextState != null)
                {
                    currentState = nextState;
                    currentState.updateUI();
                }
            }
        });

        travelInfoVM.destination.observe(this, location -> {
            // location 데이터가 업데이트되면 Toast로 표시
            travel_destination = location;
            Toast.makeText(this, "선택된 여행지: " + travel_destination, Toast.LENGTH_SHORT).show();
        });
        travelInfoVM.startDate.observe(this, startDate -> {
            // location 데이터가 업데이트되면 Toast로 표시
            travel_startDate = startDate;
            Toast.makeText(this, "선택된 여행 시작 날짜: " + travel_startDate, Toast.LENGTH_SHORT).show();
        });
        travelInfoVM.endDate.observe(this, endDate -> {
            // location 데이터가 업데이트되면 Toast로 표시
            travel_endDate = endDate;
            Toast.makeText(this, "선택된 여행 종료 날짜: " + travel_endDate, Toast.LENGTH_SHORT).show();
        });
        travelInfoVM.style.observe(this, style -> {
            // location 데이터가 업데이트되면 Toast로 표시
            travel_style = style;
            Toast.makeText(this, "선택된 여행 스타일: " + travel_style, Toast.LENGTH_SHORT).show();
        });
        travelInfoVM.fileName.observe(this, fileName -> {
            // location 데이터가 업데이트되면 Toast로 표시
            travel_fileName = fileName;
            Toast.makeText(this, "선택된 파일 이름: " + travel_fileName, Toast.LENGTH_SHORT).show();
        });
    }
    public void resetVM(int i)
    {
        switch (i)
        {
            case 1:
                travelInfoVM.destination.setValue("");
                travelInfoVM.startDate.setValue("");
                travelInfoVM.endDate.setValue("");
                break;
            case 2:
                travelInfoVM.startDate.setValue("");
                travelInfoVM.endDate.setValue("");
                travelInfoVM.style.setValue("");
                break;
            case 3:
                travelInfoVM.style.setValue("");
                travelInfoVM.fileName.setValue("");
                break;
        }
    }
    public void fragmentTransaction(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_planning, fragment).commit();
    }
    public void activityTransaction(Class<?> activity)
    {
        Intent intent = new Intent(PlanningActivity.this, activity);
        startActivity(intent);
    }
    public void makeToast(String txt)
    {
        Toast.makeText(getApplicationContext(), txt, Toast.LENGTH_SHORT).show();
    }
    public void setTitle(String title, String subtitle)
    {
        title_textView.setText(title);
        subTitle_textView.setText(subtitle);
    }
    public void setButtonText(String txt1, String txt2)
    {
        previous_button.setText(txt1);
        next_button.setText(txt2);
    }
    public void setFragmentFull()
    {
        title_layout.setVisibility(View.GONE);
        actionButton_layout.setVisibility(View.GONE);
    }

    public void generatePlan()
    {
        new Thread(() -> {
            String response = requestTravelPlan();
            String file_name = savePlanFile(response, travel_fileName);
            runOnUiThread(() -> {
                // Navigate to PlanActivity
                Intent intent = new Intent(this, PlanDisplayActivity.class);
                intent.putExtra("fileName", file_name);
                startActivity(intent);
                finish();
            });
        }).start();
    }

    public String requestTravelPlan() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openai.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GptService service = retrofit.create(GptService.class);

        Message msg1 = new Message(Message.ROLE_ASSISTANT, "You are a travel planning assistant");
        Message msg2 = new Message(Message.ROLE_USER, requestMessage());

        List<Message> msgList = new ArrayList<>();
        msgList.add(msg1);
        msgList.add(msg2);

        GptRequest request = new GptRequest(msgList);

        Call<GptResponse> call = service.generatePlan(request);

        try {
            Response<GptResponse> response = call.execute();
            if (response.isSuccessful()) {
                return response.body().getChoices().get(0).getMessage().content;
            } else {
                throw new IOException("API Request failed: " + response.errorBody().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to retrieve plan." + e.getMessage();
        }
    }

    private String requestMessage() {
        return String.format(
                "Generate a travel plan for a trip to %s from %s to %s." +
                        " I prefer the following travel styles, so please reflect them as much as possible: %s." +
                        " Your answer format: \"Destination*Paris*Start Date*2024-12-14*End Date*2024-12-20*Duration*7*Plan Start*SAT 11/26*Morning*[GPT's plan]*Lunch*[Meal Recommendation]*Afternoon*[GPT's plan]*Dinner*[Meal Recommendation]*Night*[GPT's plan]*SUN 11/27*Morning*[GPT's plan]*Lunch*[Meal Recommendation]*Afternoon*[GPT's plan]*Dinner*[Meal Recommendation]*Night*[GPT's plan]* …\"." +
                        " This is an example of your answer inside quotation marks." +
                        " I need to convert your response into data, so you must strictly follow the format." +
                        " Text will be split using the * symbol, so make sure your generated text does not contain any * symbols." +
                        " Ignore quotation marks and square brackets. you need to modify and fill in the destination, dates, duration.",
                travel_destination,
                travel_startDate,
                travel_endDate,
                travel_style
        );
    }

    public String savePlanFile(String plan, String fileName) {
        String file_name = "plan_" + fileName.replace(" ", "_") + ".txt";
        try (FileOutputStream fos = openFileOutput(file_name, MODE_PRIVATE)) {
            fos.write(plan.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file_name;
    }
}