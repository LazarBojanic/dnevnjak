package rs.raf.dnevnjak.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rs.raf.dnevnjak.R;
import rs.raf.dnevnjak.model.Obligation;
import rs.raf.dnevnjak.viewmodel.ObligationViewModel;

public class ObligationFragment extends Fragment {

    private ObligationViewModel obligationViewModel;
    public static String argObligation = "ARG_OBLIGATION";

    public ObligationFragment() {
        // Required empty public constructor
    }
    public ObligationFragment(Obligation obligation) {
        obligationViewModel = new ViewModelProvider(this).get(ObligationViewModel.class);
        obligationViewModel.getObligationMutableLiveData().postValue(obligation);
    }

    public static ObligationFragment newInstance(Obligation obligation) {
        ObligationFragment fragment = new ObligationFragment();
        Bundle args = new Bundle();
        args.putSerializable(argObligation, obligation);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Obligation obligation = getArguments().getSerializable(argObligation, Obligation.class);
            obligationViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory(){
                @NonNull
                @Override
                public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                    return (T) new ObligationViewModel(obligation);
                }
            }).get(ObligationViewModel.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_obligation, container, false);
    }
}