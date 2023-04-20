package rs.raf.dnevnjak.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import rs.raf.dnevnjak.model.Obligation;
import rs.raf.dnevnjak.util.DatabaseHelper;

public class ObligationListViewModel extends ViewModel {
    private MutableLiveData<List<Obligation>> obligationListMutableLiveData;

    public MutableLiveData<List<Obligation>> getObligationListMutableLiveData(Context context) {
        if (obligationListMutableLiveData == null) {
            obligationListMutableLiveData = new MutableLiveData<>();
            loadObligations(context);
        }
        return obligationListMutableLiveData;
    }
    public void setObligationMutableLiveData(MutableLiveData<List<Obligation>> obligationListMutableLiveData) {
        this.obligationListMutableLiveData = obligationListMutableLiveData;
    }
    private void loadObligations(Context context) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        List<Obligation> obligationListRegular = databaseHelper.getAllObligations(context);
        obligationListMutableLiveData = new MutableLiveData<>(obligationListRegular);
    }
}
