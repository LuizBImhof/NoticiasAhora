/*
 * Copyright (c) 2019. Luiz Artur Boing Imhof
 */

package cl.ucn.disc.dsm.news.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cl.ucn.disc.dsm.news.R;
import cl.ucn.disc.dsm.news.model.Noticia;

/**
 * Adaptador de {@link cl.ucn.disc.dsm.news.model.Noticia}
 * @author Luiz Artur Boing Imhof
 */
public final class NoticiaAdapter extends BaseAdapter {

    /**
     * Listado de {@link cl.ucn.disc.dsm.news.model.Noticia}
     *
     */
    private List<Noticia> noticias = new ArrayList<>();

    /**
     * The layout inflater
     */
    private final LayoutInflater layoutInflater;

    /**
     *
     * @param context to get the layout inflater.
     */
    public NoticiaAdapter(final Context context){
        this.layoutInflater = LayoutInflater.from(context);

    }

    /**
     *
     * @param noticiaList
     */
    public void setNoticias(List<Noticia> noticiaList) {
        //FIXME: Agregar funciona para descartar las noticias repetidas (usando la fecha?)

        // this.noticias.clear();
        this.noticias.addAll(noticiaList);

        // Notifica que cambio el conjunto de daods
        // TODO: Solamente en el caso que cambien las noticias
        this.notifyDataSetChanged();
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return this.noticias.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Noticia getItem(int position) {
        return this.noticias.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Get the noticia at position
        final Noticia noticia = this.getItem(position);

        //FIXME cambiara un ViewHolder
        //Hidratar/ Inflar el view
        final View rowNoticia = this.layoutInflater.inflate(R.layout.row_noticia, null);

        //Text view del titulo de la noticia
        final TextView tvTitulo = rowNoticia.findViewById(R.id.rn_tv_titulo);

        final TextView tvFecha = rowNoticia.findViewById(R.id.rn_tv_fecha);

        // Set the titulo
        tvTitulo.setText(noticia.getTitulo());

        // Set the Fecha
        tvFecha.setText(noticia.getFecha().toString());

        return rowNoticia;
    }
}
