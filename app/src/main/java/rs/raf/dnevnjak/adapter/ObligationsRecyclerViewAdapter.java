package rs.raf.dnevnjak.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.List;

import rs.raf.dnevnjak.R;
import rs.raf.dnevnjak.activity.AddObligationActivity;
import rs.raf.dnevnjak.model.Obligation;
import rs.raf.dnevnjak.viewmodel.ObligationViewModel;

public class ObligationsRecyclerViewAdapter extends RecyclerView.Adapter<ObligationsRecyclerViewAdapter.ObligationViewHolder>{
    private List<Obligation> obligationList;
    private LayoutInflater layoutInflater;

    private ImageView imageViewPriority;
    private TextView textViewTime;
    private TextView textViewTitle;
    private ImageButton imageButtonEdit;
    private ImageButton imageButtonDelete;
    public ObligationsRecyclerViewAdapter() {

    }
    public ObligationsRecyclerViewAdapter(List<Obligation> obligationList) {
        this.obligationList = obligationList;
    }

    public ObligationsRecyclerViewAdapter(Context context, List<Obligation> obligationList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.obligationList = obligationList;
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
        Obligation obligation = obligationList.get(position);
        holder.obligation = obligation;
        textViewTime.setText("test1");
        textViewTitle.setText("test2");
    }

    @Override
    public int getItemCount() {
        return obligationList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ObligationViewHolder extends RecyclerView.ViewHolder {
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
                itemView.getContext().startActivity(intent);
            });
        }
    }

}
