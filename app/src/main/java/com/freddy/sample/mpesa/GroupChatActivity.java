package com.freddy.sample.mpesa;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

public class GroupChatActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ImageButton SendMessageButton;
    private EditText userMessageInput;
    private NestedScrollView mScrollView;
   private TextView displayTextMessage;
   private FirebaseAuth mAuth;
   private String CurrentGroupName,currentUserId,currentUserName,currentDate,currentTime;
   private DatabaseReference UserRef, GroupNameRef,GroupMessageKeyRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);
        mAuth=FirebaseAuth.getInstance();
        currentUserId=mAuth.getCurrentUser().getUid();
        CurrentGroupName= getIntent().getExtras().get("groupName").toString();
        Toast.makeText(GroupChatActivity.this, CurrentGroupName, Toast.LENGTH_SHORT).show();

        UserRef= FirebaseDatabase.getInstance().getReference().child("Users");
        GroupNameRef= FirebaseDatabase.getInstance().getReference().child("Groups").child(CurrentGroupName);
        mToolbar=(Toolbar)findViewById(R.id.group_chat_bar_layout);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(CurrentGroupName);
        SendMessageButton=(ImageButton)findViewById(R.id.send_message_button);
        userMessageInput=(EditText)findViewById(R.id.input_group_message);
        displayTextMessage=(TextView)findViewById(R.id.group_chat_text_display);
        mScrollView=(NestedScrollView)findViewById(R.id.my_scroll_view);

        GetUserInfo();
        SendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveMessageInfoToDatabase();
                userMessageInput.setText("");
                mScrollView.fullScroll(NestedScrollView.FOCUS_DOWN);
            }
        });
        

    }

    @Override
    protected void onStart() {
        super.onStart();
        GroupNameRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()){
                    DisplayMessage(dataSnapshot);
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()){
                    DisplayMessage(dataSnapshot);
                }

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void DisplayMessage( DataSnapshot dataSnapshot) {
        Iterator iterator=dataSnapshot.getChildren().iterator();
        while(iterator.hasNext()){
            String chatDate=(String) ((DataSnapshot)iterator.next()).getValue();
            String chatMessage=(String) ((DataSnapshot)iterator.next()).getValue();
            String chatName=(String) ((DataSnapshot)iterator.next()).getValue();
            String chatTime=(String) ((DataSnapshot)iterator.next()).getValue();
            displayTextMessage.append(chatName + " :\n" + chatMessage + " \n" + chatTime + "      " + chatDate + " \n\n\n");
            mScrollView.fullScroll(NestedScrollView.FOCUS_DOWN);

        }
    }

    private void SaveMessageInfoToDatabase() {

        String message=userMessageInput.getText().toString();
        String messageKey=GroupNameRef.push().getKey();
        if(TextUtils.isEmpty(message)){
            Toast.makeText(this,"Please write your message first", Toast.LENGTH_SHORT).show();

        }
        else{
            Calendar calForDate = Calendar.getInstance();
            SimpleDateFormat currentDateFormat=new SimpleDateFormat("MMM dd, yyyy");
            currentDate=currentDateFormat.format(calForDate.getTime());

            Calendar calForTime = Calendar.getInstance();
            SimpleDateFormat currentTimeFormat=new SimpleDateFormat("hh:mm a");
            currentTime=currentTimeFormat.format(calForTime.getTime());
            HashMap<String, Object> groupMessageKey= new HashMap<>();
            GroupNameRef.updateChildren(groupMessageKey);
            GroupMessageKeyRef=GroupNameRef.child(messageKey);

            HashMap<String, Object> MessageInfoMap= new HashMap<>();
            MessageInfoMap.put("name", currentUserName);
            MessageInfoMap.put("message", message);
            MessageInfoMap.put("date",currentDate);
            MessageInfoMap.put("time",currentTime);
GroupMessageKeyRef.updateChildren(MessageInfoMap);









        }


    }

    private void GetUserInfo() {
        UserRef.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    currentUserName=dataSnapshot.child("name").getValue().toString();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
