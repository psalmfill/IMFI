package com.samfieldhawb.imfiresultcalculator.student;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.samfieldhawb.imfiresultcalculator.R;
import com.samfieldhawb.imfiresultcalculator.models.Department;
import com.samfieldhawb.imfiresultcalculator.models.Faculty;
import com.samfieldhawb.imfiresultcalculator.models.User;

import java.util.ArrayList;
import java.util.List;

public class StudentRegisterActivity extends AppCompatActivity {
    public static String ROLE = "Student";
    private  Spinner mFaculties,mDepartments,mGenders;
    private ArrayAdapter mAdapter;
    private Button mRegisterButton;
    private RadioGroup mModeRB;
    private RadioButton mModeButton;
    private EditText mRegNoField, mFirstNameField, mMiddleNameField, mLastNameField, mEmailField,mPasswordField, mPhoneField;

    private ArrayAdapter mFacultiesAdapter;
    private List<Faculty> mFacultiesList = new ArrayList<>();
    private List<Department> mDepartmentList = new ArrayList<>();
    //declare strings for data
    String mRegNo,mFirstName, mMiddleName, mLastName,mEmail,mPassword,mFaculty,mDepartment,mGender,mPhoneNumber,mMode;
    private  String mFacId = "nd";
    private  Department departmentObj;
    private  Faculty facultyObj;
    //Firebase
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            if (firebaseAuth.getCurrentUser() !=null){
                startActivity(new Intent(getApplicationContext(),StudentActivity.class));
            }
        }
    };

    ValueEventListener facultiesListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            // Get Post object and use the values to update the UI
            for(DataSnapshot mFac : dataSnapshot.getChildren()){
                facultyObj = mFac.getValue(Faculty.class);
                mFacultiesList.add(facultyObj);
                mFacultiesAdapter = new ArrayAdapter<Faculty>(StudentRegisterActivity.this,android.R.layout.simple_list_item_1,mFacultiesList);
                mFaculties.setAdapter(mFacultiesAdapter);
            }
            // ...
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Getting Post failed, log a message
            // ...
        }
    };
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);
         mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(mAuthListener);
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference();
        mReference.child("Faculties").addValueEventListener(facultiesListener);
        if (mAuth.getCurrentUser() != null) {
            // User is signed in (getCurrentUser() will be null if not signed in)
            Intent intent = new Intent(this, StudentActivity.class);
            startActivity(intent);
            finish();
        }
        initViews();
    }

    public void initViews(){
        mFaculties = findViewById(R.id.faculties);
        mDepartments = findViewById(R.id.department);
        mGenders = findViewById(R.id.gender);
        mModeRB = findViewById(R.id.mode);
        mRegisterButton = findViewById(R.id.register_button);
        //EditText initialization
        mRegNoField = findViewById(R.id.reg_no);
        mFirstNameField = findViewById(R.id.first_name);
        mMiddleNameField = findViewById(R.id.middle_name);
        mLastNameField = findViewById(R.id.last_name);
        mPhoneField = findViewById(R.id.phone_number);
        mEmailField = findViewById(R.id.email);
        mPasswordField = findViewById(R.id.password);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRegisteration()  ;
            }
        });

        mFaculties.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mAdapter.clear();
                mReference.child("Departments").child(
                     mFaculties.getSelectedItem().toString().isEmpty()?mFacId:getSelectedFacultyCode(mFaculties.getSelectedItem().toString())
                ).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for(DataSnapshot dep : dataSnapshot.getChildren()){
                            departmentObj = dep.getValue(Department.class);
                            mDepartmentList.add(departmentObj);
                            mAdapter = new ArrayAdapter<>(StudentRegisterActivity.this,android.R.layout.simple_list_item_1,mDepartmentList);
                            mDepartments.setAdapter(mAdapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                mDepartments.setAdapter(mAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void doRegisteration(){
        mRegNo =  mRegNoField.getText().toString().trim();
        mFirstName = mFirstNameField.getText().toString().trim();
        mMiddleName = mMiddleNameField.getText().toString().trim();
        mLastName  = mLastNameField.getText().toString().trim();
        mPassword = mPasswordField.getText().toString().trim();
        mEmail = mEmailField.getText().toString().trim();
        mPhoneNumber = mPhoneField.getText().toString().trim();
        mDepartment = mDepartments.getSelectedItem().toString();
        mFaculty = mFaculties.getSelectedItem().toString();
        mGender = mGenders.getSelectedItem().toString();

        int selectedId = mModeRB.getCheckedRadioButtonId();

        // find the radiobutton by returned id

        mModeButton =findViewById(selectedId);
        mMode = mModeButton.getText().toString().trim();

        final User user = new User(mFirstName,mLastName,mMiddleName,mGender,mPhoneNumber,mEmail,mRegNo,facultyObj,departmentObj, mMode,"Student");
        Toast.makeText(this,mDepartment,Toast.LENGTH_LONG).show();
        if(validate()){
            mAuth.createUserWithEmailAndPassword(mEmail,mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                   if(task.isSuccessful()){
                       String userId = mAuth.getCurrentUser().getUid();
                       DatabaseReference reference = mReference.child("Users").child(userId);
                       reference.setValue(user);
                       Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_LONG).show();
                   }else{
                       Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();

                   }
                }
            });



        }

    }
    public Boolean validate(){
        if(mRegNo.isEmpty()){
            mRegNoField.setError("invalid registration No");
            return false;
        }
        if(mFirstName.isEmpty()){
            mFirstNameField.setError("First Name is required");
            return false;
        }
        if(mMiddleName.isEmpty()){
            mMiddleNameField.setError("First Name is required");
            return false;
        }
        if(mLastName.isEmpty()){
            return false;
        }
        if(mPhoneNumber.isEmpty()){
            return false;
        }
        if(mEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()){
            mLastNameField.setError("Last Name is required");
            mEmailField.setError("invalid email");
            return false;
        }
        if(mPassword.isEmpty()){
            mPasswordField.setError("Password cannot be empty");
            return false;
        }
        return true;
    }

    private  String getSelectedFacultyCode(String name){
        for(Faculty faculty : mFacultiesList)
        {
            if (faculty.getName().equals(name)){
                return faculty.getShort_code().toLowerCase();
            }
        }
        return "";
    }
}
