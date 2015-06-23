package com.example.leesama.interactivestory1.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leesama.interactivestory1.R;
import com.example.leesama.interactivestory1.model.Page;
import com.example.leesama.interactivestory1.model.Story;


public class StoryActivity extends ActionBarActivity {


    public static final String TAG = StoryActivity.class.getSimpleName();

    private Story storyObj = new Story();
    private ImageView mImageView;
    private TextView mTextView;
    private Button mChoice1;
    private Button mChoice2;
    private String mName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        Intent intent = getIntent();
        mName = intent.getStringExtra(getString(R.string.key_name));

        Log.d(TAG, mName);

        mImageView = (ImageView)findViewById(R.id.storyImageView);
        mTextView = (TextView)findViewById(R.id.storyTextView);
        mChoice1 = (Button)findViewById(R.id.choiceButton1);
        mChoice2 = (Button)findViewById(R.id.choiceButton2);
        loadPages(0);
    }

    public void loadPages(int choice) {
        final Page page = storyObj.getPage(choice);
        Drawable drawable = getResources().getDrawable(page.getImageId());

        mImageView.setImageDrawable(drawable);

        String pageText = String.format(page.getText(),mName);
        mTextView.setText(pageText);

        if(page.getIsFinalPage()) {
            mChoice1.setVisibility(View.INVISIBLE);
            mChoice2.setText("Play Again!");

            mChoice2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        else {
            mChoice1.setText(page.getChoice1().getText());
            mChoice2.setText(page.getChoice2().getText());

            mChoice1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadPages(page.getChoice1().getNextPage());
                }
            });

            mChoice2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadPages(page.getChoice2().getNextPage());
                }
            });
        }
    }
}
