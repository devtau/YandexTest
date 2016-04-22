package tau.yandextest;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
/**
 * Created by TAU on 21.04.2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<Singer> singers;
    private Listener listener;

    public RecyclerViewAdapter(ArrayList<Singer> singers) {
        this.singers = singers;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        //Создание нового представления
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_captioned_image, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position){
        //Заполнение заданного представления данными
        CardView cardView = holder.cardView;
        Resources res = cardView.getContext().getResources();
        Locale locale = res.getConfiguration().locale;
        Singer singer = singers.get(position);

        ImageView imageView = (ImageView) cardView.findViewById(R.id.info_image);
        Drawable drawable = ContextCompat.getDrawable(cardView.getContext(), singer.getImageResourceId());
        imageView.setImageDrawable(drawable);
        imageView.setContentDescription(singer.getName());

        TextView tv_singerName = (TextView) cardView.findViewById(R.id.singer_name);
        tv_singerName.setText(singer.getName());

        TextView tv_genres = (TextView) cardView.findViewById(R.id.genres);
        StringBuilder genres = new StringBuilder();
        String prefix = "";
        for (String genre : singer.getGenres()) {
            genres.append(prefix);
            prefix = ", ";
            genres.append(genre);
        }
        tv_genres.setText(genres);

        TextView tv_albumsAndTracksQty = (TextView) cardView.findViewById(R.id.albums_and_tracks_qty);
        String albums = res.getQuantityString(R.plurals.count_of_albums, singer.getAlbumsQty(), singer.getAlbumsQty());
        String tracks = res.getQuantityString(R.plurals.count_of_tracks, singer.getTracksQty(), singer.getTracksQty());
        tv_albumsAndTracksQty.setText(String.format(locale, "%s, %s", albums, tracks));

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(singers.get(holder.getAdapterPosition()));
                }
            }
        });
    }

    @Override
    public int getItemCount(){
        return singers.size();
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }

    public interface Listener {
        void onClick(Singer selectedSinger);
    }
}