package com.stridera.googleimagesearch.Fragments;

import android.app.DialogFragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.stridera.googleimagesearch.GoogleImageSearchModel.Filters.GoogleImageSearchFilters;
import com.stridera.googleimagesearch.R;

public class EditFiltersDialog extends DialogFragment {
    Spinner spImageSize;
    Spinner spColorFilter;
    Spinner spColorTypeFilter;
    Spinner spImageType;
    Spinner spImageRights;
    EditText etSiteFilter;
    SeekBar sbSafe;

    public interface OnFiltersChangedListener {
        public void onFiltersChanged(GoogleImageSearchFilters filters);
    }

    public static EditFiltersDialog newInstance(GoogleImageSearchFilters filters) {
        EditFiltersDialog frag = new EditFiltersDialog();
        Bundle args = new Bundle();
        args.putSerializable("filters", filters);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_filters, container);

        getDialog().setTitle(getString(R.string.dialog_title));

        final GoogleImageSearchFilters filters =
                (GoogleImageSearchFilters) getArguments().getSerializable("filters");
        // TODO: Load existing filters

        spImageSize = (Spinner) view.findViewById(R.id.spImageSize);
        spColorFilter = (Spinner) view.findViewById(R.id.spColorFilter);
        spColorTypeFilter = (Spinner) view.findViewById(R.id.spColorTypeFilter);
        spImageType = (Spinner) view.findViewById(R.id.spImageType);
//        spImageRights = (Spinner) view.findViewById(R.id.spImageRights);
//        etSiteFilter = (EditText) view.findViewById(R.id.etSiteFilter);
        sbSafe = (SeekBar) view.findViewById(R.id.sbSafe);
        Button btnSave = (Button) view.findViewById(R.id.btnSave);
        Button btnCancel = (Button) view.findViewById(R.id.btnCancel);

        setupSafeBar();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleImageSearchFilters gisf = new GoogleImageSearchFilters();
                gisf.setImageSizeFilter(
                        GoogleImageSearchFilters.ImageSizeFilter.valueOf(
                                spImageSize.getSelectedItem().toString()));
                gisf.setImageColorFilter(
                        GoogleImageSearchFilters.ImageColorFilter.valueOf(
                                spColorFilter.getSelectedItem().toString()));
                gisf.setImageColorTypeFilter(
                        GoogleImageSearchFilters.ImageColorTypeFilter.valueOf(
                                spColorTypeFilter.getSelectedItem().toString()));
                gisf.setImageTypeFilter(
                        GoogleImageSearchFilters.ImageTypeFilter.valueOf(
                                spImageType.getSelectedItem().toString()));
                gisf.setImageSafeFilter(
                        GoogleImageSearchFilters.ImageSafeFilter.valueOf(getSafeString()));
//                gisf.setImageSiteFilter(etSiteFilter.getText().toString());

                OnFiltersChangedListener listener = (OnFiltersChangedListener) getActivity();
                listener.onFiltersChanged(gisf);

                dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        return view;
    }

    private String getSafeString() {
        switch (sbSafe.getProgress()) {
            case 0: return "Off";
            case 1: return "Medium";
            case 2: return "Strict";
        }
        return null;
    }

    private void setupSafeBar() {
        sbSafe.setMax(2);
        sbSafe.setProgress(1);
        sbSafe.setThumb(getResources().getDrawable(R.mipmap.ic_moderate));
        sbSafe.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Resources r = getResources();
                switch (progress) {
                    case 0: // Off
                        seekBar.setThumb(r.getDrawable(R.mipmap.ic_off));
                        break;
                    case 1: // Moderate
                        seekBar.setThumb(getResources().getDrawable(R.mipmap.ic_moderate));
                        break;
                    case 2: // Strict
                        seekBar.setThumb(getResources().getDrawable(R.mipmap.ic_strict));
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
