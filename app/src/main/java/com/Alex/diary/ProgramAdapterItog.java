package com.Alex.diary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ProgramAdapterItog extends ArrayAdapter<String> {
    Context context;
    List<String> Items;
    List<String> I;
    List<String> II;
    List<String> III;
    List<String> IV;

    public ProgramAdapterItog(Context context, List<String> Items, List<String> I, List<String> II, List<String> III, List<String> IV) {
        super(context, R.layout.single_item_itog, R.id.ItemItog, Items);
        this.context = context;
        this.Items = Items;
        this.I = I;
        this.II = II;
        this.II = II;
        this.III = III;
        this.IV = IV;

    }


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
        ProgramViewHolderItog holder = null;
        // Since layout inflation is a very expensive task, you'll inflate only when creating a new item in the ListView. The first
        // time you're creating a new item, convertView will be null. So, the idea is when creating an item for the first time,
        // we should perform the inflation and initialize the ViewHolder.
        if(singleItem == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            singleItem = layoutInflater.inflate(R.layout.single_item_itog, parent, false);
            // Pass the singleItem to the constructor of ProgramViewHolder. This singleItem object contains a LinearLayout
            // as the root element for single_item.xml file that contains other Views as well for the ListView.
            holder = new ProgramViewHolderItog(singleItem);
            // When you create an object of ProgramViewHolder, you're actually calling findViewById() method inside the constructor.
            // By creating ProgramViewHolder only when making new items, you call findViewById() only when making new rows.
            // At this point all the three Views have been initialized. Now you need to store the holder so that you don't need to
            // create it again while recycling and you can do this by calling setTag() method on singleItem and passing the holder as a parameter.
            singleItem.setTag(holder);
        }
        // If singleItem is not null then we'll be recycling
        else{
            // Get the stored holder object
            holder = (ProgramViewHolderItog) singleItem.getTag();
        }

        holder.ItemsItog.setText(Items.get(position));
        holder.I.setText(I.get(position));
        holder.II.setText(II.get(position));
        holder.III.setText(III.get(position));
        holder.IV.setText(IV.get(position));

        getColor(I, holder.I, position);
        getColor(II, holder.II, position);
        getColor(III, holder.III, position);
        getColor(IV, holder.IV, position);
        singleItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(), "You clicked:"+ Items.get(position), Toast.LENGTH_SHORT).show();
                //Intent openLinksIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urls[position]));
                //context.startActivity(openLinksIntent);
            }
        });
        return singleItem;
    }
    /**
     * Given a list of strings, a textview, and an index, set the textview's text to the string at the
     * index, and set the textview's color to the appropriate color based on the string
     *
     * @param ListStrings The list of strings that you want to color.
     * @param tw The TextView that you want to change the color of.
     * @param pos The position of the item in the list.
     */
    public void getColor(List<String> ListStrings, TextView tw, int pos){
        if (ListStrings.get(pos).startsWith("5")) tw.setTextColor(context.getResources().getColor(R.color.Five));
        else if (ListStrings.get(pos).startsWith("4")) tw.setTextColor(context.getResources().getColor(R.color.Four));
        else if (ListStrings.get(pos).startsWith("3")) tw.setTextColor(context.getResources().getColor(R.color.Three));
        else if (ListStrings.get(pos).startsWith("2")) tw.setTextColor(context.getResources().getColor(R.color.Two));
        else tw.setTextColor(context.getResources().getColor(android.R.color.tab_indicator_text));
    }
}
