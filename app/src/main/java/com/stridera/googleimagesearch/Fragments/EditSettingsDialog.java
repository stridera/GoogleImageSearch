package com.stridera.googleimagesearch.Fragments;

import android.app.DialogFragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.stridera.googleimagesearch.R;

public class EditSettingsDialog extends DialogFragment {

    public interface OnSettingsChangedListener {
        public void onSettingsChanged(int portrait_column, int landscape_colums);
    }

    public static EditSettingsDialog newInstance(int portraitCols, int landscapeCols) {
        EditSettingsDialog frag = new EditSettingsDialog();
        Bundle args = new Bundle();
        args.putInt("portrait_columns", portraitCols);
        args.putInt("landscape_columns", landscapeCols);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_settings, container);
        Resources res = getResources();

        getDialog().setTitle(getString(R.string.settings_dialog_title));

        final TextView tvCurrentLandscapeCols = (TextView) view.findViewById(R.id.tvCurLandscapeCols);
        final TextView tvCurrentPortraitCols = (TextView) view.findViewById(R.id.tvCurPortCols);

        final SeekBar sbLCols = (SeekBar) view.findViewById(R.id.sbLandscapeCols);
        final SeekBar sbPCols = (SeekBar) view.findViewById(R.id.sbPortraitCols);

        // Portrait Settings
        final int portraitMin = res.getInteger(R.integer.min_portrait_columns);
        int portraitMax = res.getInteger(R.integer.max_portrait_columns);
        int portraitDefault = res.getInteger(R.integer.default_portrait_columns);

        sbPCols.setMax(portraitMax - portraitMin);
        sbPCols.setProgress(portraitDefault - portraitMin);

        tvCurrentPortraitCols.setText("" + portraitDefault);

        sbPCols.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int current = progress + portraitMin;
                tvCurrentPortraitCols.setText("" + current);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // Landscape Settings
        final int landscapeMin = res.getInteger(R.integer.min_landscape_columns);
        int landscapeMax = res.getInteger(R.integer.max_landscape_columns);
        int landscapeDefault = res.getInteger(R.integer.default_landscape_columns);

        sbLCols.setMax(landscapeMax - landscapeMin);
        sbLCols.setProgress(landscapeDefault - landscapeMin);
        tvCurrentLandscapeCols.setText("" + landscapeDefault);

        sbLCols.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int current = progress + landscapeMin;
                tvCurrentLandscapeCols.setText("" + current);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ((Button) view.findViewById(R.id.btnSettingsSave)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnSettingsChangedListener listener = (OnSettingsChangedListener) getActivity();
                listener.onSettingsChanged(
                        sbPCols.getProgress() + portraitMin,
                        sbLCols.getProgress() + landscapeMin);
                dismiss();
            }
        });
        return view;
    }
}
