package com.example.prueba2;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class FragmentFilter extends Fragment {
    private fragmentLisener listener;
    private EditText et;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_filter, container,false);

        et = v.findViewById(R.id.filter);
        CharSequence input = et.getText();
        listener.onInputASent(input);
        return v;
    }

    public interface fragmentLisener{
        void onInputASent(CharSequence input);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof fragmentLisener)
        {
            listener = (fragmentLisener) context;
        }
        else
        {
            throw new RuntimeException(context.toString()
            + " No puede Implementar Fragmentos");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
