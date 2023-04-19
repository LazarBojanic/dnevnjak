package rs.raf.dnevnjak.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import rs.raf.dnevnjak.R;
import rs.raf.dnevnjak.activity.ChangePasswordActivity;
import rs.raf.dnevnjak.activity.LoginAndRegisterActivity;
import rs.raf.dnevnjak.model.ServiceUser;
import rs.raf.dnevnjak.util.Util;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView textViewUsername;
    private TextView textViewEmail;
    private Button buttonChangePassword;
    private Button buttonLogout;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }
}