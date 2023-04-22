package rs.raf.projekat1.lazar_bojanic_rn11621.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import rs.raf.projekat1.lazar_bojanic_rn11621.model.Obligation;

public class ObligationViewModel extends ViewModel {
    private MutableLiveData<Obligation> obligationMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<Obligation> getObligationMutableLiveData() {
        return obligationMutableLiveData;
    }
    public void setObligationMutableLiveData(MutableLiveData<Obligation> obligationMutableLiveData) {
        this.obligationMutableLiveData = obligationMutableLiveData;
    }
}
