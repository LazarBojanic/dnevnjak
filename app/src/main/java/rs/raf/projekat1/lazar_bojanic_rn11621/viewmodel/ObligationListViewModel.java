package rs.raf.projekat1.lazar_bojanic_rn11621.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.LocalDate;
import java.util.List;

import rs.raf.projekat1.lazar_bojanic_rn11621.model.Obligation;
import rs.raf.projekat1.lazar_bojanic_rn11621.util.DatabaseHelper;

public class ObligationListViewModel extends ViewModel {
    private MutableLiveData<LocalDate> currentDateMutableLiveData;
    private MutableLiveData<List<Obligation>> obligationListMutableLiveData;

    public MutableLiveData<List<Obligation>> getObligationListMutableLiveData(Context context, LocalDate date) {
        if (obligationListMutableLiveData == null) {
            obligationListMutableLiveData = new MutableLiveData<>();
            loadObligations(context, date);
        }
        return obligationListMutableLiveData;
    }
    public MutableLiveData<List<Obligation>> getObligationListMutableLiveDataExisting() {
        if(obligationListMutableLiveData == null){
            obligationListMutableLiveData = new MutableLiveData<>();
        }
        return obligationListMutableLiveData;
    }
    public MutableLiveData<LocalDate> getCurrentDateMutableLiveData() {
        if(currentDateMutableLiveData == null){
            currentDateMutableLiveData = new MutableLiveData<>(LocalDate.now());
        }
        return currentDateMutableLiveData;
    }
    public void setObligationMutableLiveData(MutableLiveData<List<Obligation>> obligationListMutableLiveData) {
        this.obligationListMutableLiveData = obligationListMutableLiveData;
    }
    private void loadObligations(Context context, LocalDate date) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        List<Obligation> obligationListRegular = databaseHelper.getAllObligationsByDate(context, date);
        obligationListMutableLiveData = new MutableLiveData<>(obligationListRegular);
        obligationListMutableLiveData.postValue(obligationListRegular);
    }
}
