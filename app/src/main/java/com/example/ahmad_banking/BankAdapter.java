package com.example.ahmad_banking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BankAdapter extends RecyclerView.Adapter<BankAdapter.BankAdapterVh> implements Filterable {
    private List<BankModel> bankModelList;
    private List<BankModel> getBankModelListFiltered;
    private Context context;
    private SelectedBank selectedBank;

    public BankAdapter(List<BankModel> bankModelList, SelectedBank selectedBank) {
        this.bankModelList = bankModelList;
        this.getBankModelListFiltered = bankModelList;
        this.selectedBank = selectedBank;
    }

    // Create view holder for each item in the RecyclerView
    @NonNull
    @Override
    public BankAdapterVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        // Inflate the layout for the item view
        return new BankAdapterVh(LayoutInflater.from(context).inflate(R.layout.row, null));
    }

    // Bind the data to the item view
    @Override
    public void onBindViewHolder(@NonNull BankAdapterVh holder, int position) {
        BankModel bankModel = bankModelList.get(position);

        String bankname = bankModel.getBankName();

        holder.BankNameTV.setText(bankname);
    }

    // Return the number of items in the RecyclerView
    @Override
    public int getItemCount() {
        return bankModelList.size();
    }

    // Implement the filtering functionality
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();

                // If the search query is empty, show the full list
                if (charSequence == null || charSequence.length() == 0) {
                    filterResults.count = getBankModelListFiltered.size();
                    filterResults.values = getBankModelListFiltered;
                } else {
                    String searchChr = charSequence.toString().toLowerCase();
                    List<BankModel> resultData = new ArrayList<>();

                    // Filter the list based on the search query
                    for (BankModel bankModel : getBankModelListFiltered) {
                        if (bankModel.getBankName().toLowerCase().contains(searchChr)) {
                            resultData.add(bankModel);
                        }
                    }

                    filterResults.count = resultData.size();
                    filterResults.values = resultData;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                // Update the filtered list and notify the adapter
                bankModelList = (List<BankModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };

        return filter;
    }

    // Interface to handle the selection of a bank
    public interface SelectedBank {
        void selectedBank(BankModel bankModel);
    }

    // ViewHolder class for the item view in the RecyclerView
    public class BankAdapterVh extends RecyclerView.ViewHolder {
        RelativeLayout iconRelativeLayout;
        TextView BankNameTV;
        ImageView navigateIV;

        public BankAdapterVh(@NonNull View itemView) {
            super(itemView);
            iconRelativeLayout = itemView.findViewById(R.id.iconRL);
            BankNameTV = itemView.findViewById(R.id.bankname);
            navigateIV = itemView.findViewById(R.id.navigateView);

            // Set a click listener for the item view
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Call the selectedBank method in the activity when a bank is selected
                    selectedBank.selectedBank(bankModelList.get(getAdapterPosition()));
                }
            });
        }
    }
}
