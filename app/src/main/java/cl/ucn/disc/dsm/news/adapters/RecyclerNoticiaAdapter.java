/*
 * Copyright (c) 2019. Luiz Artur Boing Imhof
 */

package cl.ucn.disc.dsm.news.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.ocpsoft.prettytime.PrettyTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.threeten.bp.DateTimeUtils;

import java.util.ArrayList;
import java.util.List;

import cl.ucn.disc.dsm.news.R;
import cl.ucn.disc.dsm.news.model.Noticia;

public final class RecyclerNoticiaAdapter extends RecyclerView.Adapter<RecyclerNoticiaAdapter.NoticiaViewHolder> {

    /**
     * The logger
     */
    private static final Logger logger = LoggerFactory.getLogger(RecyclerNoticiaAdapter.class);
    /**
     * Listado de {@link cl.ucn.disc.dsm.news.model.Noticia}
     *
     */
    private List<Noticia> noticias = new ArrayList<>();
    /**
     * The context
     */
    private Context mContext;

    /**
     *
     * @param context the context for the adapter
     * @param noticias the list of noticias
     */
    public RecyclerNoticiaAdapter(Context context, List<Noticia> noticias) {
        this.noticias = noticias;
        mContext = context;
    }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @NonNull
    @Override
    public NoticiaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_noticia, parent, false);
        NoticiaViewHolder viewHolder = new NoticiaViewHolder(view);
        return viewHolder;
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull NoticiaViewHolder holder, int position) {
        logger.atDebug().log("Called onBindViewHolder");

        // Set the titulo
        holder.tvTitulo.setText(noticias.get(position).getTitulo());

        // Set the fuente
        holder.tvFuente.setText(noticias.get(position).getFuente());

        final PrettyTime prettyTime = new PrettyTime();
        //Set the prettyTime fecha
        holder.tvFecha.setText(prettyTime.format(DateTimeUtils.toDate(noticias.get(position).getFecha().toInstant())));

        final Uri uri;
        if(noticias.get(position).getUrlFoto() != null){
            uri = Uri.parse(noticias.get(position).getUrlFoto());
        }else{
            uri = Uri.parse("https://raw.githubusercontent.com/facebook/fresco/master/docs/static/logo.png");
        }
        // Set the image
        holder.sdvFoto.setImageURI(uri);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return noticias.size();
    }


    public static class NoticiaViewHolder extends RecyclerView.ViewHolder{

        SimpleDraweeView sdvFoto;
        TextView tvTitulo;
        TextView tvFecha;
        TextView tvFuente;
        LinearLayout parentLayout;

        /**
         *
         * @param itemView the view for each item of the recycler view
         */
        public NoticiaViewHolder(@NonNull View itemView) {
            super(itemView);
            sdvFoto = itemView.findViewById(R.id.rn_sdv_image);
            tvTitulo = itemView.findViewById(R.id.rn_tv_titulo);
            tvFecha = itemView.findViewById(R.id.rn_tv_fecha);
            tvFuente = itemView.findViewById(R.id.rn_tv_fuente);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }

}
