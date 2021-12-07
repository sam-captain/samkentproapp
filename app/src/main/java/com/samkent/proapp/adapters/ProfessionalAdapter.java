package com.samkent.proapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.samkent.proapp.ProDetailsActivity;
import com.samkent.proapp.R;
import com.samkent.proapp.models.Professional;

import java.util.List;

public class ProfessionalAdapter  extends RecyclerView.Adapter<ProfessionalAdapter.ViewHolder> {

    Context context;
    List <Professional> professionals;

    public ProfessionalAdapter(Context context, List<Professional> professionals) {
        this.context = context;
        this.professionals = professionals;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_professional,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Professional professional = professionals.get(position);

        holder.txtOccupation.setText(professional.getOccupation())  ;
        holder.txtName.setText(professional.getName());

    }

    @Override
    public int getItemCount() {
        return professionals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtOccupation;
        Button btnContact;
        ImageView imgPro;
        long id;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txt_pro_name);
            txtOccupation = itemView.findViewById(R.id.txt_pro_occupation);
            btnContact = itemView.findViewById(R.id.btn_pro_contact);
            imgPro = itemView.findViewById(R.id.img_pro_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    String name = professionals.get(position).getName();
                    String occupation = professionals.get(position).getOccupation();

                    Intent intent = new Intent(context, ProDetailsActivity.class);

                    intent.putExtra("NAME",name);
                    intent.putExtra("OCCUPATION ", occupation);

                    context.startActivity(intent);



                }
            });

            btnContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:0759820947"));
                    context.startActivity(intent);
                }
            });
        }
    }
}
