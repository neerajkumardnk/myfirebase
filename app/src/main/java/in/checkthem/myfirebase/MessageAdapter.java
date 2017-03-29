package in.checkthem.myfirebase;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by reach on 3/26/2017.
 */

public class MessageAdapter extends ArrayAdapter<Blog> {
    public MessageAdapter(Context context, int resource, List<Blog> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_message, parent, false);
        }


        TextView messageTextView = (TextView) convertView.findViewById(R.id.messageTextView);
        TextView titleTextView=(TextView) convertView.findViewById(R.id.titleTextView);
        TextView statusTextView=(TextView)convertView.findViewById(R.id.statusTextView);

        Blog message = getItem(position);
        Blog title=getItem(position);
        Blog status=getItem(position);



        messageTextView.setVisibility(View.VISIBLE);
        titleTextView.setVisibility(View.VISIBLE);
        statusTextView.setVisibility(View.VISIBLE);



        return convertView;
    }
}