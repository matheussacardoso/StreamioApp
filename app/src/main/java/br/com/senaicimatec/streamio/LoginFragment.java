package br.com.senaicimatec.streamio;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginFragment extends Fragment {

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    private EditText usernameLogin, userPasswordLogin;
    private Button loginBtn;
    private TextView signUpTxt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DatabaseReference usuario = reference.child("users");

        usernameLogin = view.findViewById(R.id.loginUserName);
        userPasswordLogin = view.findViewById(R.id.loginPassword);
        loginBtn = view.findViewById(R.id.loginBtn);
        signUpTxt = view.findViewById(R.id.registerClick);

        signUpTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToSignup();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserModel user = login();

                if(user != null){
                    usuario.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot curretUser: snapshot.getChildren()){
                                if(user.getUserName().equals(curretUser.child("userName").getValue().toString()) &&
                                   user.getUserPassword().equals(curretUser.child("userPassword").getValue().toString())){
                                    moveToUserProfile();
                                } else {
                                    showToast("EU NAUM GOSTUUUMMMMM");
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    showToast("Erro ao adicionar usuario");
                }
            }
        });

    }

    public void moveToSignup(){
        SignUpFragment signUpFragment = new SignUpFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, signUpFragment).commit();
    }

    public void moveToUserProfile(){
        UserProfileFragment userProfileFragment = new UserProfileFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, userProfileFragment).commit();
    }

    public UserModel login(){
        UserModel user = new UserModel();

        if(!TextUtils.isEmpty(usernameLogin.getText().toString()) &&
                !TextUtils.isEmpty(userPasswordLogin.getText().toString())){

            user.setUserName(usernameLogin.getText().toString());
            user.setUserPassword(userPasswordLogin.getText().toString());
            return user;
        }
        else {
            showToast("Preencha todos os campos de texto!");
            return null;
        }
    }

    public void showToast(String message){
        // apresenta uma mensagem para confirmar que a acao foi realizada !SOMENTE PARA TESTE!
        Toast.makeText(getContext(),message, Toast.LENGTH_SHORT).show();
    }
}