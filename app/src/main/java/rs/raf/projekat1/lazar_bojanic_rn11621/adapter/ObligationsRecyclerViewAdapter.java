package rs.raf.projekat1.lazar_bojanic_rn11621.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import rs.raf.projekat1.lazar_bojanic_rn11621.R;
import rs.raf.projekat1.lazar_bojanic_rn11621.activity.AddObligationActivity;
import rs.raf.projekat1.lazar_bojanic_rn11621.activity.ObligationDetailsActivity;
import rs.raf.projekat1.lazar_bojanic_rn11621.model.Obligation;
import rs.raf.projekat1.lazar_bojanic_rn11621.util.Util;
import rs.raf.projekat1.lazar_bojanic_rn11621.viewmodel.ObligationListViewModel;

public class ObligationsRecyclerViewAdapter extends RecyclerView.Adapter<ObligationsRecyclerViewAdapter.ObligationViewHolder>{
    private List<Obligation> obligationList;
    private LayoutInflater layoutInflater;

    public void setObligationList(List<Obligation> obligationList){
        this.obligationList = obligationList;
    }


    public ObligationsRecyclerViewAdapter() {

    }
    public ObligationsRecyclerViewAdapter(Context context, ObligationListViewModel obligationListViewModel) {
        this.obligationList = obligationListViewModel.getObligationListMutableLiveDataExisting().getValue();
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ObligationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_obligation, parent, false);
        return new ObligationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ObligationsRecyclerViewAdapter.ObligationViewHolder holder, int position) {
        holder.obligation = obligationList.get(position);
        String timeString = Util.localTimeToString(holder.obligation.getStartTime()) + " - " + Util.localTimeToString(holder.obligation.getEndTime());
        holder.textViewTime.setText(timeString);
        holder.textViewTitle.setText(holder.obligation.getTitle());
    }

    @Override
    public int getItemCount() {
        return obligationList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ObligationViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageViewPriority;
        private TextView textViewTime;
        private TextView textViewTitle;
        private ImageButton imageButtonEdit;
        private ImageButton imageButtonDelete;
        private Obligation obligation;

        public ObligationViewHolder(View itemView) {
            super(itemView);
            initView();
            initListeners();
        }
        private void initView(){
            imageViewPriority = itemView.findViewById(R.id.imageViewPriority);
            textViewTime = itemView.findViewById(R.id.textViewTime);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            imageButtonEdit = itemView.findViewById(R.id.imageButtonEdit);
            imageButtonDelete = itemView.findViewById(R.id.imageButtonDelete);
        }
        private void initListeners(){

            imageButtonEdit.setOnClickListener(view -> {
                Intent intent = new Intent(itemView.getContext(), AddObligationActivity.class);
                intent.putExtra(itemView.getContext().getResources().getString(R.string.extraObligation), obligation);
                Log.i(itemView.getContext().getResources().getString(R.string.dnevnjakTag), "Putting extra " +  obligation.toString());
                itemView.getContext().startActivity(intent);
            });
            imageViewPriority.setOnClickListener(view -> {
                Intent intent = new Intent(itemView.getContext(), ObligationDetailsActivity.class);
                intent.putExtra(itemView.getContext().getResources().getString(R.string.extraObligation), obligation);
                Log.i(itemView.getContext().getResources().getString(R.string.dnevnjakTag), "Putting extra " +  obligation.toString());
                itemView.getContext().startActivity(intent);
            });
        }

    }

}
