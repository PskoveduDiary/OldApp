package com.Alex.diary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

public class ProgramAdapter extends ArrayAdapter<String> {
    Context context;
    List<String> Items;
    List<String> Vypiska;
    List<Double> Itog;

    // This is the constructor of the class. It's called when you create an object of the class.
    public ProgramAdapter(Context context, List<String> Items, List<String> Vypiska, List<Double> Itog) {
        super(context, R.layout.single_item, R.id.textView1, Items);
        this.context = context;
        this.Items = Items;
        this.Vypiska = Vypiska;
        this.Itog = Itog;
    }


    /**
     * When you're creating a new item, you'll inflate the layout and initialize the ViewHolder.
     * When you're recycling, you'll get the ViewHolder from the singleItem
     *
     * @param position The position of the item in the list.
     * @param convertView The view that you want to reuse.
     * @param parent The ViewGroup object that contains the list view.
     * @return The View object that is being returned is a View object that is created by the getView()
     * method.
     */
    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // The parameter convertView is null when your app is creating a new item for the first time. It's not null when
        // recycling.
        // Assign the convertView in a View object
        View singleItem = convertView;
        // Find a View from the entire View hierarchy by calling findViewById() is a fairly expensive task.
        // So, you'll create a separate class to reduce the number of calls to it.
        // First, create a reference of ProgramViewHolder and assign it to null.
        ProgramViewHolder holder = null;
        // Since layout inflation is a very expensive task, you'll inflate only when creating a new item in the ListView. The first
        // time you're creating a new item, convertView will be null. So, the idea is when creating an item for the first time,
        // we should perform the inflation and initialize the ViewHolder.
        if(singleItem == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            singleItem = layoutInflater.inflate(R.layout.single_item, parent, false);
            // Pass the singleItem to the constructor of ProgramViewHolder. This singleItem object contains a LinearLayout
            // as the root element for single_item.xml file that contains other Views as well for the ListView.
            holder = new ProgramViewHolder(singleItem);
            // When you create an object of ProgramViewHolder, you're actually calling findViewById() method inside the constructor.
            // By creating ProgramViewHolder only when making new items, you call findViewById() only when making new rows.
            // At this point all the three Views have been initialized. Now you need to store the holder so that you don't need to
            // create it again while recycling and you can do this by calling setTag() method on singleItem and passing the holder as a parameter.
            singleItem.setTag(holder);
        }
        // If singleItem is not null then we'll be recycling
        else{
            // Get the stored holder object
            holder = (ProgramViewHolder) singleItem.getTag();
        }

        holder.Items.setText(Items.get(position));
        holder.Vypiska.setText(Vypiska.get(position));
        if (Itog.get(position) >= 4.5) holder.Itog.setTextColor(context.getResources().getColor(R.color.Five));
        else if (Itog.get(position) >= 3.5) holder.Itog.setTextColor(context.getResources().getColor(R.color.Four));
        else if (Itog.get(position) >= 2.5) holder.Itog.setTextColor(context.getResources().getColor(R.color.Three));
        else if (Itog.get(position) >= 1.5) holder.Itog.setTextColor(context.getResources().getColor(R.color.Two));
        else holder.Itog.setTextColor(context.getResources().getColor(android.R.color.tab_indicator_text));
        holder.Itog.setText(Itog.get(position).toString());
        singleItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                XLSParser.OpenInfo(position);
            }
        });
        return singleItem;
    }
}
