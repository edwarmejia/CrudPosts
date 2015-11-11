package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import mendoza.luigui.holamundo.R;
import models.Post;

/**
 * Created by ludk on 31/10/15.
 */
public class PostAdapter extends ArrayAdapter<Post> {

    private int resource;

    public PostAdapter(Context context, int resource, List<Post> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null)

            convertView = inflater.inflate(this.resource, parent, false);

        Post item = getItem(position);

        ((TextView)convertView.findViewById(R.id.txtUserId)).setText(item.id);
        ((TextView)convertView.findViewById(R.id.txtUsuario)).setText(item.user);
        ((TextView)convertView.findViewById(R.id.txtTitle)).setText(item.title);
        ((TextView)convertView.findViewById(R.id.txtContent)).setText(item.content);


        return convertView;

    }
}
