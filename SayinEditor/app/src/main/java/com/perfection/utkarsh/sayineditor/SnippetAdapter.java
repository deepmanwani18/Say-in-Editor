package com.perfection.utkarsh.sayineditor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.sql.Array;
import java.util.ArrayList;

/**
 * Created by utkarsh on 25/3/18.
 */

public class SnippetAdapter extends ArrayAdapter<Snippet> {

    public SnippetAdapter(Context context, ArrayList<Snippet> earthquakes) {
        super(context, 0, earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View gridItemView = convertView;
        if (gridItemView == null) {
            gridItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.content_snippet, parent, false);
        }

        Snippet currentSnippet = getItem(position);
        TextView codeView = (TextView) gridItemView.findViewById(R.id.code);
        TextView voiceView = (TextView) gridItemView.findViewById(R.id.voice);

        codeView.setText(currentSnippet.getCode());
        voiceView.setText(currentSnippet.getVoice());

        return gridItemView;
    }
}
