package rs.raf.projekat1.lazar_bojanic_rn11621.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import rs.raf.projekat1.lazar_bojanic_rn11621.R;
import rs.raf.projekat1.lazar_bojanic_rn11621.activity.ChangePasswordActivity;
import rs.raf.projekat1.lazar_bojanic_rn11621.activity.LoginAndRegisterActivity;
import rs.raf.projekat1.lazar_bojanic_rn11621.model.ServiceUser;
import rs.raf.projekat1.lazar_bojanic_rn11621.util.Util;

public class AccountFragment extends Fragment {



    private TextView textViewUsername;
    private TextView textViewEmail;
    private Button buttonChangePassword;
    private Button buttonLogout;

    public AccountFragment() {
        // Required empty public constructor
    }


    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private void initListeners(){
        buttonChangePassword.setOnClickListener(view ->{
            Intent intent = new Intent(requireActivity(), ChangePasswordActivity.class);
            startActivity(intent);
        });
        buttonLogout.setOnClickListener(view ->{
            if(Util.removeUserSharedPreference(requireActivity())){
                Intent intent = new Intent(requireActivity(), LoginAndRegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    public void initView(){
        textViewUsername = requireActivity().findViewById(R.id.textViewUsername);
        textViewEmail = requireActivity().findViewById(R.id.textViewEmail);

        buttonChangePassword = requireActivity().findViewById(R.id.buttonChangePassword);
        buttonLogout = requireActivity().findViewById(R.id.buttonLogout);
    }
    public void populateView(){
        ServiceUser serviceUser = Util.getUserSharedPreference(requireActivity());
        if(serviceUser != null){
            textViewUsername.setText(serviceUser.getUsername());
            textViewEmail.setText(serviceUser.getEmail());
        }

    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        populateView();
        initListeners();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }
}