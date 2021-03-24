package com.example.mychat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import android.text.format.DateFormat;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private ImageButton btnSend;
    private ListView chatView;
    private EditText inputMessage;

    private static int SIGN_IN_REQUEST_CODE = 1;
    private FirebaseListAdapter<MessageArea> adapter;
    private FirebaseListOptions<MessageArea> options;
    Query query = FirebaseDatabase.getInstance().getReference().child("chats");;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSend = (ImageButton) findViewById(R.id.btnSend);
        chatView = (ListView) findViewById(R.id.chatView);
        inputMessage = (EditText) findViewById(R.id.inputMessage);
        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivityForResult(AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .build(), SIGN_IN_REQUEST_CODE);  
        }
        else{
            showChat();
        }
    }

    private void showChat() {
        options = new FirebaseListOptions.Builder<MessageArea>()
                .setQuery(query, MessageArea.class)
                .setLayout(R.layout.area_chat)
                .build();
        adapter = new FirebaseListAdapter<MessageArea>(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull MessageArea model, int position) {
                TextView messageText = (TextView)v.findViewById(R.id.messageText);
                TextView messageUser = (TextView)v.findViewById(R.id.messageUser);
                TextView messageTime = (TextView)v.findViewById(R.id.messageTime);

                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());
                messageTime.setText(DateFormat.format("HH:mm", model.getMessageTime()));
            }
        };
        chatView.setAdapter(adapter);
    }
    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SIGN_IN_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                Toast.makeText(this, "Logining...", Toast.LENGTH_SHORT).show();
                showChat();
            } else{
                Toast.makeText(this, "Logining is failed", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
    public void onBtnSendClick(View view) {
        FirebaseDatabase.getInstance().getReference().push()
                .setValue(new MessageArea(inputMessage.getText().toString(),
                        FirebaseAuth.getInstance().getCurrentUser().getEmail()));
        inputMessage.setText("");
    }
}