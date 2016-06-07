package jason.xie.columns;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import jason.xie.columns.model.Column;

/**
 * Created by Jason Xie on 2016/6/4.
 */

public class ColumnsAdapter extends RecyclerView.Adapter<ColumnsAdapter.ColumnViewHolder> {

    private Context context;
    private List<Column> columns;
    private Callback callback;

    public ColumnsAdapter(Context context, List<Column> columns, Callback callback){
        this.context = context;
        this.columns = columns;
        this.callback = callback;
    }

    public void setColumns(List<Column> columns){
        this.columns = columns;
    }

    @Override
    public ColumnViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(context).inflate(R.layout.item_column, parent, false);
        final ColumnViewHolder viewHolder = new ColumnViewHolder(itemView);
        viewHolder.contentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(callback != null){
                    callback.onItemClick(viewHolder.column);
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ColumnViewHolder holder, int position) {
        if(columns == null){
            return;
        }
        Column column = columns.get(position);
        holder.column = column;
        holder.textTitle.setText(column.name);
        holder.textFollowersCount.setText(column.followersCount + "人关注");
        holder.textPostsCount.setText(column.postsCount + "篇文章");
        holder.textDescription.setText(column.description);
        String avatarUrl = column.avatar.template.replace("{id}", column.avatar.id)
                .replace("{size}", "l");
        Picasso.with(context)
                .load(avatarUrl)
                .into(holder.imageAvatar);
    }

    @Override
    public int getItemCount() {
        if(columns == null){
            return 0;
        }
        return columns.size();
    }

    public class ColumnViewHolder extends RecyclerView.ViewHolder {

        public View contentLayout;
        public CircleImageView imageAvatar;
        public TextView textTitle;
        public TextView textFollowersCount;
        public TextView textPostsCount;
        public TextView textDescription;
        public Column column;

       public ColumnViewHolder(View view){
           super(view);
           contentLayout = view.findViewById(R.id.layout_content);
           imageAvatar = (CircleImageView) view.findViewById(R.id.image_avatar);
           textTitle = (TextView) view.findViewById(R.id.text_title);
           textFollowersCount = (TextView) view.findViewById(R.id.text_followers_count);
           textPostsCount = (TextView) view.findViewById(R.id.text_posts_count);
           textDescription = (TextView) view.findViewById(R.id.text_description);
       }
    }

    public interface Callback {
        void onItemClick(Column column);
    }
}
