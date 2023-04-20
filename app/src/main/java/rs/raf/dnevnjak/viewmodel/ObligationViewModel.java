package rs.raf.dnevnjak.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import rs.raf.dnevnjak.model.Obligation;

public class ObligationViewModel extends ViewModel {
    private MutableLiveData<Obligation> obligationMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<Obligation> getObligationMutableLiveData() {
        return obligationMutableLiveData;
    }
    public void setObligationMutableLiveData(MutableLiveData<Obligation> obligationMutableLiveData) {
        this.obligationMutableLiveData = obligationMutableLiveData;
    }
}
