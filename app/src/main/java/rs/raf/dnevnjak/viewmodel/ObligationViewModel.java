package rs.raf.dnevnjak.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.Closeable;

import rs.raf.dnevnjak.model.Obligation;

public class ObligationViewModel extends ViewModel {
    private MutableLiveData<Obligation> obligationMutableLiveData;

    public ObligationViewModel(Obligation obligation) {
        this.obligationMutableLiveData = new MutableLiveData<>(obligation);
    }

    public ObligationViewModel(MutableLiveData<Obligation> obligationMutableLiveData) {
        this.obligationMutableLiveData = obligationMutableLiveData;
    }

    public ObligationViewModel(MutableLiveData<Obligation> obligationMutableLiveData, @NonNull Closeable... closeables) {
        super(closeables);
        this.obligationMutableLiveData = obligationMutableLiveData;
    }


    public MutableLiveData<Obligation> getObligationMutableLiveData() {
        return obligationMutableLiveData;
    }

    public void setObligationMutableLiveData(MutableLiveData<Obligation> obligationMutableLiveData) {
        this.obligationMutableLiveData = obligationMutableLiveData;
    }
}
