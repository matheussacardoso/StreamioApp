package br.com.senaicimatec.streamio;

import android.content.Intent;
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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpFragment extends Fragment {
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    private EditText userIdEdt, userNameEdt, userPasswordEdt;
    private Button signUpBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DatabaseReference usuario = reference.child("users");

        userIdEdt = view.findViewById(R.id.alunoidEDT);
        userNameEdt = view.findViewById(R.id.alunoNicknameEDT);
        userPasswordEdt = view.findViewById(R.id.alunoSenhaEDT);
        signUpBtn = view.findViewById(R.id.cadatroBtn);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserModel user = register();
                if(user != null){

                    usuario.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            usuario.child(userIdEdt.getText().toString()).setValue(user);
                            showToast("Usuario Adicionado Com sucesso!");
                            moveToLogin();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            showToast("Erro ao adicionar usuario");
                        }
                    });
                } else {
                    showToast("Erro ao adicionar usuario");
                }
            }
        });
    }

    public UserModel register(){
        UserModel user = new UserModel();

        if(!TextUtils.isEmpty(userIdEdt.getText().toString()) && !TextUtils.isEmpty(userNameEdt.getText().toString()) &&
                !TextUtils.isEmpty(userPasswordEdt.getText().toString())){
            user.setUserName(userNameEdt.getText().toString());
            user.setUserPassword(userPasswordEdt.getText().toString());
            return user;
        }
        else {
            showToast("Preencha todos os campos de texto!");
            return null;
        }
    }

    public void moveToLogin(){
        LoginFragment loginFragment = new LoginFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, loginFragment).commit();
    }

    public void showToast(String message){
        // apresenta uma mensagem para confirmar que a acao foi realizada !SOMENTE PARA TESTE!
        Toast.makeText(getContext(),message, Toast.LENGTH_SHORT).show();
    }
}