/*
 * Copyright (c) 2019. Luiz Artur Boing Imhof
 */

package cl.ucn.disc.dsm.news.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.commons.lang3.time.StopWatch;
import org.ocpsoft.prettytime.PrettyTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.threeten.bp.DateTimeUtils;

import java.util.ArrayList;
import java.util.List;

import cl.ucn.disc.dsm.news.R;
import cl.ucn.disc.dsm.news.databinding.RowNoticiaBinding;
import cl.ucn.disc.dsm.news.model.Noticia;

public final class RecyclerNoticiaAdapter extends RecyclerView.Adapter<RecyclerNoticiaAdapter.NoticiaViewHolder> {

    /**
     * The logger
     */
    private static final Logger log = LoggerFactory.getLogger(RecyclerNoticiaAdapter.class);
    /**
     * Listado de {@link cl.ucn.disc.dsm.news.model.Noticia}
     */
    private List<Noticia> noticiaList;

    /**
     *
     */
    public RecyclerNoticiaAdapter() {
        this.noticiaList = new ArrayList<>();
    }

    /**
     * Set the List of Noticia.
     *
     * @param noticias to use.
     */
    public void setNoticias(final List<Noticia> noticias) {

        // No noticias.
        if (this.noticiaList.size() == 0) {

            log.debug("New list of Noticias !!");
            this.noticiaList = noticias;
            this.notifyItemRangeChanged(0, this.noticiaList.size());
            return;
        }

        final StopWatch stopWatch = StopWatch.createStarted();

        final DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {

            @Override
            public int getOldListSize() {
                return noticiaList.size();
            }

            @Override
            public int getNewListSize() {
                return noticias.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return noticiaList.get(oldItemPosition).getId().equals(noticias.get(newItemPosition).getId());
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return noticiaList.get(oldItemPosition).getId().equals(noticias.get(newItemPosition).getId());
            }
        });

        log.debug("CalculateDiff: {}", stopWatch);

        this.noticiaList = noticias;
        result.dispatchUpdatesTo(this);

    }


    /**
     * Called when RecyclerView needs a new {@link NoticiaViewHolder} of the given type to represent
     * an item.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public NoticiaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        return new NoticiaViewHolder(RowNoticiaBinding.inflate(LayoutInflater.from(parent.getContext())));
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull NoticiaViewHolder holder, int position) {
        holder.bind(this.noticiaList.get(position));

    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return noticiaList.size();
    }


    public static class NoticiaViewHolder extends RecyclerView.ViewHolder {

        /**
         * The Logger
         */
        private static final Logger log = LoggerFactory.getLogger(NoticiaViewHolder.class);

        /**
         * The Date formatter
         */
        private static final PrettyTime PRETTY_TIME = new PrettyTime();

        /**
         * The binding
         */
        private final RowNoticiaBinding binding;

        /**
         * Create the ViewHolder.
         *
         * @param rowNoticiaBinding to use.
         */
        public NoticiaViewHolder(RowNoticiaBinding rowNoticiaBinding) {
            super(rowNoticiaBinding.getRoot());
            this.binding = rowNoticiaBinding;
        }

        /**
         * Bind the Noticia to ViewHolder.
         *
         * @param noticia to use.
         */
        public void bind(final Noticia noticia) {
            this.binding.tvTitulo.setText(noticia.getTitulo());
            this.binding.tvFuente.setText(noticia.getFuente());

            this.binding.tvFecha.setText(PRETTY_TIME.format(DateTimeUtils.toDate(noticia.getFecha().toInstant())));

            if (noticia.getUrlFoto() != null) {
                final Uri uri = Uri.parse(noticia.getUrlFoto());
                this.binding.sdvImage.setImageURI(uri);
            } else {
                this.binding.sdvImage.setImageResource(R.drawable.engineering_sketch_user);
            }
        }
    }

}
