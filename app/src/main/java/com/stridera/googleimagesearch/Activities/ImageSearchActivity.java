package com.stridera.googleimagesearch.Activities;

import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.stridera.googleimagesearch.Adapters.GoogleImageSearchArrayAdapter;
import com.stridera.googleimagesearch.Fragments.EditFiltersDialog;
import com.stridera.googleimagesearch.Fragments.EditSettingsDialog;
import com.stridera.googleimagesearch.GoogleImageSearchModel.Filters.GoogleImageSearchFilters;
import com.stridera.googleimagesearch.GoogleImageSearchModel.GoogleImageSearch;
import com.stridera.googleimagesearch.Listeners.EndlessScrollListener;
import com.stridera.googleimagesearch.R;

public class ImageSearchActivity extends AppCompatActivity implements EditFiltersDialog.OnFiltersChangedListener, EditSettingsDialog.OnSettingsChangedListener {
    GoogleImageSearch gis;
    GoogleImageSearchArrayAdapter gisAA;

    StaggeredGridView gridView;
    MenuItem searchBar;
    TextView searchHint;
    int landscapeCols;
    int portraitCols;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_search);

        gridView = (StaggeredGridView) findViewById(R.id.grid_view);
        searchHint = (TextView) findViewById(R.id.search_hint);

        gis = new GoogleImageSearch();
        gisAA = new GoogleImageSearchArrayAdapter(this, gis.stream);

        gridView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                Toast.makeText(ImageSearchActivity.this, "Page " + page, Toast.LENGTH_SHORT).show();
                Log.d("blah", "Page " + page);
                gis.do_search(page);
            }
        });
        gridView.setAdapter(gisAA);
        gis.setAdapter(gisAA);
        gis.setNoResults((TextView) findViewById(R.id.search_no_results));
        gis.setProgressBar((ProgressBar) findViewById(R.id.searching));
//        do_search("Catgirls");
        handleIntent(getIntent());
    }

    private void do_search() {
        do_search("");
    }

    private void do_search(String query) {
        if (!query.isEmpty()) {
            setTitle(query);
            gis.setQuery(query);
            searchHint.setVisibility(View.INVISIBLE);
        }
        gis.do_search();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        Log.d("blah", intent.getAction());

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            if (isNetworkAvailable()) {
                do_search(intent.getStringExtra(SearchManager.QUERY));
                searchBar.collapseActionView();
            } else {
                Toast.makeText(this, "No internet connection available.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        searchBar = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_search, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.searchable.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            showSettingsDialog();
            return true;
        }

        if (id == R.id.action_filters) {
            showFiltersDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showFiltersDialog() {
        FragmentManager fm = getFragmentManager();
        EditFiltersDialog dialog = EditFiltersDialog.newInstance(gis.filters);
        dialog.show(fm, "fragment_edit_filters");
    }

    private void showSettingsDialog() {
        FragmentManager fm = getFragmentManager();
        EditSettingsDialog dialog = EditSettingsDialog.newInstance(portraitCols, landscapeCols);
        dialog.show(fm, "fragment_edit_settings");
    }

    @Override
    public void onFiltersChanged(GoogleImageSearchFilters filters) {
        gis.setFilters(filters);
        do_search();
    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    @Override
    public void onSettingsChanged(int portrait_column, int landscape_columns) {
        portraitCols = portrait_column;
        landscapeCols = landscape_columns;
        gridView.setColumnCountPortrait(portrait_column);
        gridView.setColumnCountLandscape(landscape_columns);
    }
}
