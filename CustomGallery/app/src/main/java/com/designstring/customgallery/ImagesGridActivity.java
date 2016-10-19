package com.designstring.customgallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;

/**
 * Created by vaishakha on 18/10/16.
 */
public class ImagesGridActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    RecyclerView mRecyclerView;
    private GridLayoutManager gridLayoutManager;
    private static final int URL_LOADER = 0;
    GalleryPickerAdapter adapter;
    static int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_grid);

        Intent intent = getIntent();
        position = intent.getIntExtra("POS",0);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        getLoaderManager().initLoader(URL_LOADER, null, this);

        if (adapter == null) {
            adapter = new GalleryPickerAdapter(getApplicationContext());
            mRecyclerView.setAdapter(adapter);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {

        if (id == URL_LOADER) {
            return new CursorLoader(getApplicationContext(),
                    GalleryPickerAdapter.uri,
                    GalleryPickerAdapter.projections,
                    GalleryPickerAdapter.projections[3] + " = \"" + GalleryPickerAdapter.data.get(position).getBucketId() + "\"",
                    null,
                    GalleryPickerAdapter.sortOrder);
        } else {
            return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        adapter.setData(PhotosData.getData(false, cursor));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        adapter.notifyDataSetChanged();
    }

}
