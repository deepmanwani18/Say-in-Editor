package com.perfection.utkarsh.sayineditor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by utkarsh on 25/3/18.
 */

public class SnippetAdapter extends ArrayAdapter<Snippet> {

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Snippet currentSnippet = getItem(position);

        TextView codeView = (TextView) convertView.findViewById(R.id.code);
        TextView voiceView = (TextView) convertView.findViewById(R.id.voice);

        codeView.setText(currentSnippet.getCode());
        voiceView.setText(currentSnippet.getVoice());

        return convertView;
    }

    public SnippetAdapter(Context context, int resource ,ArrayList<Snippet> snippets) {
        super(context, resource, snippets);
    }
}
